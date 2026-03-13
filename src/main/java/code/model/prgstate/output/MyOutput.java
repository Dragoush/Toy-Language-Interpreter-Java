package code.model.prgstate.output;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyOutput<T> implements IMyOutput<T>
    {
        List<T> out;

        public MyOutput()
            {
                out = new ArrayList<>();
            }

        public MyOutput(List<T> out)
            {
                this.out = out;
            }

        @Override
        public List<T> getList(){
            return out;
        }
        @Override
        public void add(T exp)
            {
                out.add(exp);
            }


        @Override
        public String toString()
            {
                return out.toString();
            }

        @Override
        public void clear()
            {
                out.clear();
            }

        @Override
        public int size()
            {
                return out.size();
            }

        @Override
        public String printAsColumn()
            {
                StringBuilder sb = new StringBuilder();
                for (T exp : out)
                    {
                        sb.append(exp.toString()).append("\n");
                    }
                return sb.toString();
            }
    }
