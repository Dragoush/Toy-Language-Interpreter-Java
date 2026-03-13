package code.model.type;

import code.model.value.IntValue;
import code.model.value.Value;

public class IntType implements Type
    {

        @Override
        public boolean equals(Object obj)
            {
                return (obj instanceof IntType);
            }

        @Override
        public String toString()
            {
                return "int";
            }

        @Override
        public Type deepCopy()
            {
                return new IntType();
            }

        @Override
        public Value defaultValue()
            {
                return new IntValue();
            }
    }
