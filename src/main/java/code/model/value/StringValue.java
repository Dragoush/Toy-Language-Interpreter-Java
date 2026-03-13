package code.model.value;

import code.model.type.StringType;
import code.model.type.Type;

public class StringValue implements Value
    {
        String str;

        public StringValue(){str="";}
        public StringValue(String str)
            {
                this.str = str;
            }

        public String getVal(){return str;}

        public void setVal(String str)
            {
                this.str = str;
            }
        @Override
        public Type getType()
            {
                return new StringType();
            }

        @Override
        public boolean equalTo(Object obj)
            {
                if (obj instanceof StringValue)
                {
                    StringValue other = (StringValue)obj;
                    String str2 = other.getVal();
                    return str.equals(str2);
                }
                return false;
            }

        @Override
        public String toString()
            {
                return str;
            }
        @Override
        public Value deepCopy()
            {
                return new StringValue(str);
            }
    }
