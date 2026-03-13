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

public class newStmt implements IStmt
    {
        String varName;
        Exp exp;

        public newStmt(String varName, Exp exp)
            {
                this.varName = varName;
                this.exp = exp;
            }
        @Override
        public PrgState execute(PrgState prg) throws BaseException, HeapException, UndefinedSymbolException
            {
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                //check if var_name in symbols table
                if (!symTable.isSym(varName))
                    throw new UndefinedSymbolException("Variable " + varName + " is not sym");
                Value value = symTable.getValue(varName);

                //check if value from symTable is a RefType
                if (!(value.getType() instanceof RefType))
                    throw new HeapException("Variable " + varName + " is not a ref type");
                RefValue refValue = (RefValue) value;

                //check if the Referenced type in symTable is equal to type returned by exp
                //eg: refValue.locationType=IntType; exp.eval().getType=IntType
                Value evaluated=exp.eval(symTable,heapTable);
                if (!refValue.getLocationType().equals(evaluated.getType()))
                    throw new HeapException("Expression and varName type mismatch");

                //put the value evaluated into heap and get the address
                int new_addr=heapTable.addValue(evaluated.deepCopy());

                //update the var_name to contain the new address
                refValue.setAddress(new_addr);
                symTable.update(varName, refValue);

                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typVar=typeEnv.getValue(varName);
                Type typExp=exp.typeCheck(typeEnv);
                if (!typVar.equals(new RefType(typExp)))
                    throw new BaseException("NEW stmt: right hand side and left hand side have different types");
                return typeEnv;
            }

        @Override
        public IStmt deepCopy()
            {
                return new newStmt(varName, exp.deepCopy());
            }

        @Override
        public String toString()
            {
                return "new("+varName+","+exp+")";
            }
    }
