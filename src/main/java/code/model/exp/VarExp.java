package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;
import code.model.value.Value;

public class VarExp implements Exp
    {
        String symbol;

        public VarExp(String symbol)
            {
                this.symbol = symbol;
            }
        public Value eval(IMyDictionary<String,Value> symTable,IMyHeapTable<Value> heapTable)throws BaseException
            {
                return symTable.getValue(this.symbol);
            }

        @Override
        public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                return typeEnv.getValue(this.symbol);
            }

        @Override
        public Exp deepCopy()
            {
                return new VarExp(new String(symbol));
            }
        @Override
        public String toString()
            {
                return this.symbol;
            }
    }
