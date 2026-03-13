package code.model.value;

import code.model.type.BoolType;
import code.model.type.Type;

public class BoolValue implements Value
    {
        boolean val;

        public BoolValue(boolean val)
            {
                this.val = val;
            }

        public BoolValue()
            {
                this.val = false;
            }

        public boolean getValue()
            {
                return this.val;
            }

        @Override
        public Value deepCopy()
            {
                return new BoolValue(this.val);
            }

        @Override
        public boolean equalTo(Object obj)
            {
                if  (obj instanceof BoolValue)
                {
                    BoolValue other = (BoolValue)obj;
                    return this.getValue() == other.getValue();
                }
                return false;
            }

        @Override
        public Type getType()
            {
                return new BoolType();
            }

        @Override
        public String toString()
            {
                return Boolean.toString(val);
            }

    }

