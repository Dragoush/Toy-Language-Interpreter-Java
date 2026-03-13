package code.model.prgstate.heap;

import code.model.exceptions.BaseException;
import code.model.exceptions.UndefinedSymbolException;

import java.util.HashMap;
import java.util.Map;

public class MyHeapTable<V> implements IMyHeapTable<V>
    {
        private Map<Integer, V> map;
        private int nextFreeAddr = 1; //smallest available address in address space

        public MyHeapTable()
            {
                map = new HashMap<>();
            }

        public MyHeapTable(Map<Integer, V> map)
            {
                this.map = map;
            }


        @Override
        public boolean isSym(int address)
            {
                return map.containsKey(address);
            }
        @Override
        public int getNextAddr()
            {
                int nextAddr = nextFreeAddr;
                do
                    {
                        nextAddr++;
                    } while (map.containsKey(nextAddr));
                return nextAddr;
            }

        @Override
        public V getValue(int address) throws BaseException, UndefinedSymbolException
            {
                if (address == 0)
                    {
                        throw new UndefinedSymbolException("Null address 0 not allowed");
                    }
                if (map.containsKey(address))
                    {
                        return map.get(address);
                    }
                throw new UndefinedSymbolException("Address " + address + " not defined");
            }

        @Override
        public V updateValue(int address, V value) throws BaseException, UndefinedSymbolException
            {
                if (address == 0)
                    {
                        throw new UndefinedSymbolException("Null address 0 not allowed");
                    }
                if (map.containsKey(address))
                    {
                        return map.put(address, value);
                    }
                throw new UndefinedSymbolException("Address " + address + " not defined");
            }

        @Override
        public V deleteValue(int address) throws BaseException, UndefinedSymbolException
            {
                if (address == 0)
                    {
                        throw new UndefinedSymbolException("Null address 0 not allowed");
                    }
                if (map.containsKey(address))
                    {
                        map.remove(address);
                        if (address < nextFreeAddr)
                            {
                                nextFreeAddr = address;
                            }
                    }
                throw new UndefinedSymbolException("Address " + address + " not defined");
            }

        @Override
        public int addValue(V value)
            {
                map.put(nextFreeAddr, value);
                int oldAddr = nextFreeAddr;
                nextFreeAddr = getNextAddr();
                return oldAddr;
            }

        @Override
        public Map<Integer, V> getContent()
            {
                return map;
            }

        @Override
        public void setContent(Map<Integer, V> content)
            {
                this.map = content;
            }

        @Override
        public String toString()
            {
                StringBuilder sb = new StringBuilder();
                for (Integer key : map.keySet())
                    {
                        sb.append(key.toString()).append("-->").append(map.get(key)).append("\n");
                    }
                return sb.toString();
            }
    }
