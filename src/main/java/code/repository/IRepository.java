package code.repository;

import code.model.exceptions.BaseException;
import code.model.prgstate.PrgState;

import java.util.List;

public interface IRepository
    {

        public PrgState getCrtPrg();//make it return repo[0]

        public List<PrgState> getPrgList();
        public PrgState getPrgById(int id);
        public void setPrgList(List<PrgState> prglist);
        public void logPrgStateExec(PrgState prgState) throws BaseException;
    }
