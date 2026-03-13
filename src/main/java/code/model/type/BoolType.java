package code.model.type;

import code.model.value.BoolValue;
import code.model.value.Value;

public class BoolType implements Type
    {
        @Override
        public boolean equals(Object other)
            {
                return other instanceof BoolType;
            }

        @Override
        public String toString()
            {
                return "bool";
            }

        @Override
        public Type deepCopy()
            {
                return new BoolType();
            }

        @Override
        public Value defaultValue()
            {
                return new BoolValue();
            }
    }
