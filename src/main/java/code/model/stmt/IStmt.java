package code.model.stmt;
import code.model.prgstate.PrgState;
import code.model.exceptions.BaseException;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;

public interface IStmt
    {
        public PrgState execute(PrgState prg) throws BaseException;

        public IStmt deepCopy();

        IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException;
    }
