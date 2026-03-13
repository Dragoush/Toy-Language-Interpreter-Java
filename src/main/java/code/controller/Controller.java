package code.controller;


import code.model.exceptions.BaseException;
import code.model.exceptions.NullExecutionException;
import code.model.exceptions.ThreadException;
import code.model.prgstate.PrgState;

import code.model.value.RefValue;
import code.model.value.Value;
import code.repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller
    {
        IRepository repo;
        public ExecutorService executor;

        public Controller(IRepository repo)
            {
                this.repo = repo;
            }

        public List<PrgState> removeCompletedPrg(List<PrgState> prgstates)
            {
                return prgstates.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
            }

        public IRepository getRepo()
            {
                return repo;
            }

        public PrgState oneStepById(int id) throws BaseException
            {
                for (PrgState prg : repo.getPrgList())
                    {
                        if (prg.getId() == id)
                            return prg.oneStep();
                    }
                return null;
            }

        public boolean hasActivePrograms() {
            return repo.getPrgList().stream().anyMatch(PrgState::isNotCompleted);
        }


        public void oneStepForAllPrg(List<PrgState> prgList) throws ThreadException
            {
                //log initial state of prgs
                prgList.forEach(p ->
                    {
                        try
                            {
                                repo.logPrgStateExec(p);
                            } catch (BaseException e)
                            {
                                System.out.println(e.getMessage());
                            }
                    });
                List<Callable<PrgState>> callList = prgList.stream()
                        .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                        .collect(Collectors.toList());

                //get list of all new prgs (returned by forks)
                List<PrgState> newPrgList;
                try
                    {
                        newPrgList = executor.invokeAll(callList).stream().map(future ->
                            {
                                try
                                    {
                                        return future.get();
                                    } catch (InterruptedException | ExecutionException e)
                                    {
                                        System.out.println("Messed up");
                                        return null;
                                    }
                            }).filter(p -> p != null).collect(Collectors.toList());
                    } catch (InterruptedException e)
                    {
                        throw new ThreadException("Interrupted program");
                    }


                //add the newly created ones


                prgList.addAll(newPrgList);


                //log all prgs (with the ones newly created)
                prgList.forEach(p ->
                    {
                        try
                            {
                                repo.logPrgStateExec(p);
                            } catch (BaseException e)
                            {
                                System.out.println(e.getMessage());
                            }
                    });
                //set the repo to include the new prgs
                repo.setPrgList(prgList);
            }


        public void allSteps() throws NullExecutionException, BaseException, ThreadException
            {
                executor = Executors.newFixedThreadPool(2);
                List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
                while (prgList.size() > 0)
                    {
                        oneStepForAllPrg(prgList);
                        conservativeGarbageCollector(prgList);
                        prgList = removeCompletedPrg(repo.getPrgList());
                    }
                executor.shutdownNow();
                repo.setPrgList(prgList);
            }

        public void conservativeGarbageCollector(List<PrgState> prgstates)
            {
                List<Integer> symTableAddresses = Objects.requireNonNull(
                        prgstates.stream().map(p -> getAddrFromSymTable(p.getSymTable().getContent().values()))
                                .map(Collection::stream).reduce(Stream::concat).orElse(null).collect(Collectors.toList())

                );
                prgstates.forEach(p -> p.getHeapTable().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeapTable(p.getHeapTable().getContent().values()), p.getHeapTable().getContent())));
            }

        Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap)
            {
                return heap.entrySet().stream()
                        .filter(pair -> symTableAddr.contains(pair.getKey()) ||
                                heapAddr.contains(pair.getKey())
                        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }

        Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap)
            {
                return heap.entrySet().stream()
                        .filter(e -> symTableAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            }

        List<Integer> getAddrFromHeapTable(Collection<Value> heap)
            {
                return heap.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v ->
                            {
                                RefValue refValue = (RefValue) v;
                                return refValue.getAddress();
                            })
                        .collect(Collectors.toList());
            }

        List<Integer> getAddrFromSymTable(Collection<Value> symTableValues)
            {
                return symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v ->
                            {
                                RefValue v1 = (RefValue) v;
                                return v1.getAddress();
                            })
                        .collect(Collectors.toList());
            }


    }
