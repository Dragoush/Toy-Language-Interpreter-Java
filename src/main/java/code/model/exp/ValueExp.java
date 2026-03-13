package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;
import code.model.value.Value;

public class ValueExp implements Exp
    {
        Value e;

        public ValueExp(Value e)
            {
                this.e = e;
            }

        @Override
        public Exp deepCopy()
            {
                return new ValueExp(e.deepCopy());
            }

        @Override
        public Value eval(IMyDictionary<String, Value> symTable, IMyHeapTable<Value> heapTable) throws BaseException
            {
                return e;
            }

        @Override
        public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                return e.getType();
            }

        @Override
        public String toString()
            {
                return e.toString();
            }
    }


