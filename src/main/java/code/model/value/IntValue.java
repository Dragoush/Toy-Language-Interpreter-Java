package code.model.value;

import code.model.type.IntType;
import code.model.type.Type;

public class IntValue implements Value
    {
        int val;

        public IntValue(int val)
            {
                this.val = val;
            }

        public IntValue()
            {
                this.val = 0;
            }

        public int getVal()
            {
                return this.val;
            }

        @Override
        public Value deepCopy()
            {
                return new IntValue(val);
            }

        public boolean equalTo(Object obj)
            {
                if (obj instanceof IntValue)
                    {
                        IntValue other = (IntValue) obj;
                        return this.getVal() == other.getVal();
                    }
                return false;
            }
        @Override
        public String toString()
            {
                return Integer.toString(this.val);
            }

        public Type getType()
            {
                return new IntType();
            }
    }
