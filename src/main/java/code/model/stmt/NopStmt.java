package code.model.stmt;

import code.model.exceptions.BaseException;
import code.model.prgstate.PrgState;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;

public class NopStmt implements IStmt
    {
        public PrgState execute(PrgState prgState)
            {
                return null;
            }


        public IStmt deepCopy()
            {
                return new NopStmt();
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                return typeEnv;
            }

        @Override
        public String toString()
            {
                return "";
            }
    }
