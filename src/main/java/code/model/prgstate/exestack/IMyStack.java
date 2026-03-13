package code.model.prgstate.exestack;

import code.model.exceptions.BaseException;

import java.util.List;

public interface IMyStack<T>
    {
        void push(T stmt);

        T pop() throws BaseException;

        void clear();

        int size();

        boolean isEmpty();

        List<T> toList();

        //TODO T peek()


    }
