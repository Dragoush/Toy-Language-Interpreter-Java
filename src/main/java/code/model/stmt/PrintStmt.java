package code.model.stmt;


import code.model.exceptions.BaseException;
import code.model.exp.Exp;
import code.model.prgstate.PrgState;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.output.IMyOutput;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;
import code.model.value.Value;

public class PrintStmt implements IStmt
    {
        Exp exp;

        public PrintStmt(Exp exp)
            {
                this.exp = exp;
            }


        public IStmt deepCopy()
            {
                return null;
            }

        public PrgState execute(PrgState prgState) throws BaseException
            {
                IMyOutput<Value> myOutput = prgState.getOutput();
                IMyDictionary<String,Value> mySymTable=prgState.getSymTable();
                IMyHeapTable<Value> heapTable = prgState.getHeapTable();
                myOutput.add(exp.eval(mySymTable,heapTable));
                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                exp.typeCheck(typeEnv);
                return typeEnv;
            }

        @Override
        public String toString()
            {
                return "print("+exp.toString()+")";
            }

    }
