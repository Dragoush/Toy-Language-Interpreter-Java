package code.model.stmt;

import code.model.exceptions.BaseException;
import code.model.prgstate.PrgState;
import code.model.prgstate.exestack.IMyStack;
import code.model.prgstate.exestack.MyStack;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;
import code.model.value.Value;

public class forkStmt implements IStmt
    {
        IStmt stmt;

        public forkStmt(IStmt stmt)
            {
                this.stmt = stmt;
            }

        public IStmt getStmt()
            {
                return stmt;
            }

        public void setStmt(IStmt stmt)
            {
                this.stmt = stmt;
            }


        @Override
        public PrgState execute(PrgState prg) throws BaseException
            {
                IMyStack<IStmt> stack = new MyStack<IStmt>();
                IMyDictionary<String, Value> symTable=prg.getSymTable().deepCopy();
                return new PrgState(stack,symTable,prg.getOutput(),prg.getFileTable(),prg.getHeapTable(),stmt);
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                return typeEnv.deepCopy();
            }

        @Override
        public String toString()
            {
                return "fork("+stmt+")";
            }
        @Override
        public IStmt deepCopy()
            {
                return new forkStmt(stmt.deepCopy());
            }

    }
