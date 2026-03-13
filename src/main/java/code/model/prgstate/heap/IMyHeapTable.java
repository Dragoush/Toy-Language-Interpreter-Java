package code.model.prgstate.heap;

import code.model.exceptions.BaseException;
import code.model.exceptions.UndefinedSymbolException;

import java.util.Map;

public interface IMyHeapTable<V>
    {
        public int getNextAddr();

        public boolean isSym(int address);

        public V getValue(int address) throws BaseException, UndefinedSymbolException;

        public V updateValue(int address, V value) throws BaseException, UndefinedSymbolException;

        public V deleteValue(int address) throws BaseException, UndefinedSymbolException;

        public int addValue(V value);

        public void setContent(Map<Integer, V> content);

        public Map<Integer, V> getContent();

    }
