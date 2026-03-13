package code.model.prgstate.symtable;

import code.model.exceptions.BaseException;
import code.model.exceptions.UndefinedSymbolException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<S, V> implements IMyDictionary<S, V>
    {
        Map<S, V> symTable;

        //map
        public MyDictionary()
            {
                symTable = new HashMap<>();
            }

        public MyDictionary(Map<S, V> d)
            {
                symTable = d;
            }

        @Override
        public boolean isSym(S symbol)
            {
                return (symTable.get(symbol) != null);
            }

        @Override
        public V getValue(S symbol) throws BaseException
            {
                V value = symTable.get(symbol);
                if (value == null)
                    {
                        throw new UndefinedSymbolException("Symbol " + symbol + " not found");
                    }
                return value;
            }

        @Override
        public void update(S symbol, V val) throws BaseException, UndefinedSymbolException
            {
                V oldVal = symTable.get(symbol);
                if (oldVal == null)
                    {
                        throw new UndefinedSymbolException("Symbol " + symbol + " not found");
                    }
                symTable.put(symbol, val);
            }

        @Override
        public void add(S symbol, V val) throws BaseException
            {
                V inexistentVal = symTable.get(symbol);
                if (inexistentVal != null)
                    {
                        throw new BaseException("Symbol " + symbol + " already exists");
                    }
                symTable.put(symbol, val);
            }

        public void delete(S symbol) throws BaseException
            {
                if (!isSym(symbol))
                    {
                        throw new UndefinedSymbolException("Symbol " + symbol + " not found");
                    }
                symTable.remove(symbol);
            }

        @Override
        public String toString()
            {
                return symTable.toString();
            }

        @Override
        public String printKeys()
            {
                StringBuilder s = new StringBuilder();
                for (S symbol : symTable.keySet())
                    {
                        s.append(symbol.toString()).append("\n");
                    }
                return s.toString();
            }

        @Override
        public Map<S, V> getContent()
            {
                return symTable;
            }

        @Override
        public String printKeyValues()
            {
                StringBuilder s = new StringBuilder();
                for (S symbol : symTable.keySet())
                    {
                        V val = symTable.get(symbol);
                        s.append(symbol.toString()).append(" -> ").append(val.toString()).append("\n");
                    }
                return s.toString();
            }

        @Override
        public IMyDictionary<S, V> deepCopy()
            {
                MyDictionary<S, V> d = new MyDictionary<>();
                for (Map.Entry<S, V> entry : symTable.entrySet())
                    {
                        d.getContent().put(entry.getKey(), entry.getValue());
                    }
                return d;
            }

    }
