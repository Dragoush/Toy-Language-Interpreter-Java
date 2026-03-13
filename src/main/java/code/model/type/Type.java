package code.model.type;


import code.model.value.Value;

public interface Type
    {
        public boolean equals(Object other);

        @Override
        public String toString();

        public Type deepCopy();

        public Value defaultValue();

    }
