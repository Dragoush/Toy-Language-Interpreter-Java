package code.model.type;

import code.model.value.RefValue;
import code.model.value.Value;

public class RefType implements Type
    {
        private Type inner;


        public RefType(Type inner)
            {
                this.inner = inner;
            }

        public void setInner(Type inner)
            {
                this.inner = inner;
            }
        public Type getInner()
            {
                return inner;
            }



        @Override
        public boolean equals(Object obj)
            {
                if (obj instanceof RefType other)
                    {
                        return this.inner.equals(other.getInner());
                    }
                return false;
            }
        @Override
        public Type deepCopy()
            {
                return new RefType(this.inner.deepCopy());
            }

        @Override
        public Value defaultValue()
            {
                return new RefValue(0,inner);
            }

        @Override
        public String toString()
            {
                return "Ref ("+inner.toString()+")";
            }
    }
