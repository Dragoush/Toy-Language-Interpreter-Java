package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.exceptions.HeapException;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.RefType;
import code.model.type.Type;
import code.model.value.RefValue;
import code.model.value.Value;

public class rH implements Exp
    {
        Exp exp;

        public rH(Exp exp)
            {
                this.exp = exp;
            }

        @Override
        public Value eval(IMyDictionary<String, Value> tbl, IMyHeapTable<Value> heapTable) throws BaseException, HeapException
            {
                Value v = exp.eval(tbl, heapTable);
                if (!(v instanceof RefValue))
                    {
                        throw new HeapException("Value is not a RefValue");
                    }
                RefValue ref = (RefValue) v;
                int addr = ref.getAddress();

                return heapTable.getValue(addr);
            }

        @Override
        public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typ = exp.typeCheck(typeEnv);
                if (typ instanceof RefType reft)
                    {
                        return reft.getInner();
                    }
                throw new BaseException("Type is not a RefType");
            }

        @Override
        public Exp deepCopy()
            {
                return new rH(exp.deepCopy());
            }

        @Override
        public String toString()
            {
                return "rH(" + exp.toString() + ")";
            }


    }
