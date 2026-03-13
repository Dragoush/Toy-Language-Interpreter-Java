package code.repository;

import code.model.exceptions.BaseException;
import code.model.prgstate.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository
    {
        List<PrgState> elems;
        String logFilePath = "";

        public Repository(PrgState prgstate)
            {
                elems = new ArrayList<PrgState>();
                elems.add(prgstate);
            }

        public Repository(PrgState prgstate, String logFile)
            {
                elems = new ArrayList<PrgState>();
                elems.add(prgstate);
                this.logFilePath = logFile;
            }


        @Override
        public List<PrgState> getPrgList()
            {
                return elems;
            }

        @Override
        public void setPrgList(List<PrgState> prgList)
            {
                elems = prgList;
            }

        @Override
        public PrgState getCrtPrg()
            {
                return elems.getFirst();
            }


        @Override
        public void logPrgStateExec(PrgState prgState) throws BaseException
            {
                PrintWriter pw;
                try
                    {
                        pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
                    } catch (IOException e)
                    {
                        throw new BaseException("Could not open log file");
                    }
                if (prgState == null){
                    System.out.println("PrgState is null in logging");
                }
                pw.print(prgState.toString());
                pw.close();
            }

        @Override
        public PrgState getPrgById(int id)
            {
                for (PrgState prg : elems){
                    if (prg.getId() == id){
                        return prg;
                    }
                }
                return null;
            }
    }
