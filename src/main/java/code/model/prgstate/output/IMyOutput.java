package code.model.prgstate.output;

import java.util.List;

public interface IMyOutput<T>
    {

        void add(T exp);

        public List<T> getList();

        void clear();

        int size();

        public String printAsColumn();
    }
