package code.model.value;

import code.model.type.RefType;
import code.model.type.Type;

public class RefValue implements Value
    {
        private int address;
        private Type locationType;

        public RefValue(int address, Type locationType)
            {
                this.address = address;
                this.locationType = locationType;
            }

        public int getAddress()
            {
                return address;
            }

        public void setAddress(int address)
            {
                this.address = address;
            }

        public Type getLocationType()
            {
                return locationType;
            }

        public void setLocationType(Type locationType)
            {
                this.locationType = locationType;
            }

        @Override
        public Type getType()
            {
                return new RefType(locationType);
            }

        @Override
        public boolean equalTo(Object obj)
            {
                if (obj instanceof RefValue other)
                    {
                        return other.getLocationType().equals(locationType) && other.getAddress() == address;
                    }
                return false;
            }

        @Override
        public Value deepCopy()
            {
                return new RefValue(address,locationType.deepCopy());
            }

        @Override
        public String toString()
            {
                return "RefValue{" + "address=" + address + " type=" + locationType.toString() + '}';
            }
    }
