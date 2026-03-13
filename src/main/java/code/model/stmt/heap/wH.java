package code.model.stmt.heap;

import code.model.exceptions.BaseException;
import code.model.exceptions.HeapException;
import code.model.exceptions.UndefinedSymbolException;
import code.model.exp.Exp;
import code.model.prgstate.PrgState;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.stmt.IStmt;
import code.model.type.RefType;
import code.model.type.Type;
import code.model.value.RefValue;
import code.model.value.Value;

public class wH implements IStmt
    {
        String varName;
        Exp exp;

        public wH(String varName, Exp exp)
            {
                this.varName = varName;
                this.exp = exp;
            }


        @Override
        public PrgState execute(PrgState prg) throws BaseException, UndefinedSymbolException, HeapException
            {
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                if (!symTable.isSym(varName))
                    {
                        throw new UndefinedSymbolException("Variable " + varName + " is not sym");
                    }
                Value value = symTable.getValue(varName);
                if (!(value.getType() instanceof RefType))
                    {
                        throw new HeapException(varName + " is not a ref type");
                    }
                RefValue refValue = (RefValue) value;
                int addr = refValue.getAddress();
                if (!heapTable.isSym(addr))
                    {
                        throw new HeapException(addr + " is not in heap");
                    }

                Value evaluatedValue = exp.eval(symTable, heapTable);
                if (!refValue.getLocationType().equals(evaluatedValue.getType()))
                    {
                        throw new HeapException("Referenced value by " + varName + " does not match returned by expression");
                    }

                heapTable.updateValue(addr, evaluatedValue);
                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException, HeapException
            {
                Type typVar = typeEnv.getValue(varName);
                if (!(typVar instanceof RefType))
                    {
                        throw new HeapException(varName + " is not a ref type");
                    }
                Type typExp = exp.typeCheck(typeEnv);
                if (((RefType) typVar).getInner().equals(typExp))
                    {
                        return typeEnv;
                    }
                throw new HeapException("Referenced value by " + varName + " does not match returned by expression");
            }

        @Override
        public String toString()
            {
                return "wH(" + varName + ", " + exp + ")";
            }

        @Override
        public IStmt deepCopy()
            {
                return new wH(varName, exp.deepCopy());
            }
    }
