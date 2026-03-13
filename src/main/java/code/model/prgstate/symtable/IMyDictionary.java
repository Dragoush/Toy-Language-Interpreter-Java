package code.model.prgstate.symtable;


import code.model.exceptions.BaseException;
import code.model.exceptions.UndefinedSymbolException;

import java.util.Map;

public interface IMyDictionary<S, V>
    {
        public boolean isSym(S symbol);

        public V getValue(S symbol) throws BaseException, UndefinedSymbolException;

        public void update(S symbol, V val) throws UndefinedSymbolException,BaseException;

        public void add(S symbol, V val) throws BaseException;

        public void delete(S symbol) throws BaseException;

        public Map<S,V> getContent();
        public String printKeys();
        public String printKeyValues();

        public IMyDictionary<S, V> deepCopy();
    }
