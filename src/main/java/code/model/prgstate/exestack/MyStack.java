package code.model.prgstate.exestack;

import code.model.exceptions.BaseException;
import code.model.exceptions.EmptyContainerException;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IMyStack<T>
    {
        private Stack<T> stack;

        public MyStack()
            {
                stack = new Stack<T>();
            }

        @Override
        public void push(T stmt)
            {
                stack.push(stmt);
            }

        @Override
        public T pop() throws BaseException, EmptyContainerException
            {
                if (!stack.isEmpty())
                    {
                        return stack.pop();
                    }
                throw new EmptyContainerException("Stack is empty");
            }

        @Override
        public void clear()
            {
                stack.clear();
            }

        @Override
        public int size()
            {
                return stack.size();
            }

        @Override
        public boolean isEmpty()
            {
                return stack.isEmpty();
            }

        @Override
        public String toString()
            {
                return stack.toString();
            }

        @Override
        public List<T> toList()
            {
                return stack.stream().toList();
            }
    }
