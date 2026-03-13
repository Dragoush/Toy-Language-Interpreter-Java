package code.model.type;

import code.model.value.StringValue;
import code.model.value.Value;

public class StringType implements Type
    {


        @Override
        public String toString()
            {
                return "string";
            }

        @Override
        public boolean equals(Object obj)
            {
                return (obj instanceof StringType);
            }

        @Override
        public Type deepCopy()
            {
                return new StringType();
            }


        @Override
        public Value defaultValue()
            {
                return new StringValue();
            }
    }
