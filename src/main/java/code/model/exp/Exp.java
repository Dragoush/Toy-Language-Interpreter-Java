package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.exceptions.HeapException;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;
import code.model.value.Value;

public interface Exp
    {
        //public Value eval(IMyDictionary<String,Value> tbl) throws BaseException;

        public Value eval(IMyDictionary<String, Value> tbl, IMyHeapTable<Value> heapTable) throws BaseException, HeapException;

        public Exp deepCopy();

        public Type typeCheck(IMyDictionary<String,Type> typeEnv) throws BaseException;
    }
