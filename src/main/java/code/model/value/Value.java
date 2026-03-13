package code.model.value;

import code.model.type.Type;

public interface Value
    {
        Type getType();
        boolean equalTo(Object obj);
        @Override
        public String toString();

        public Value deepCopy();
    }
