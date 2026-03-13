package code.model.prgstate;


import code.model.collections.MyTree;
import code.model.collections.Node;
import code.model.exceptions.BaseException;
import code.model.exceptions.NullExecutionException;
import code.model.prgstate.exestack.IMyStack;
import code.model.prgstate.exestack.MyStack;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.heap.MyHeapTable;
import code.model.prgstate.output.IMyOutput;
import code.model.prgstate.output.MyOutput;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.prgstate.symtable.MyDictionary;
import code.model.stmt.CompStmt;
import code.model.stmt.IStmt;
import code.model.stmt.NopStmt;
import code.model.value.StringValue;
import code.model.value.Value;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PrgState
    {
        public static int lastId = 0;
        private int id;
        private IMyStack<IStmt> stack;
        private IMyDictionary<String, Value> symTable;
        private IMyOutput<Value> output;
        IStmt originalProgram;

        private IMyDictionary<StringValue, BufferedReader> fileTable;
        private IMyHeapTable<Value> heapTable;

        public PrgState(IStmt s)
            {
                id=setId();
                stack = new MyStack<IStmt>();
                symTable = new MyDictionary<String, Value>();
                output = new MyOutput<Value>();
                originalProgram = s.deepCopy();
                stack.push(s);
                fileTable = new MyDictionary<StringValue, BufferedReader>();
                heapTable = new MyHeapTable<Value>();
            }


        public PrgState(IMyStack<IStmt> stack, IMyDictionary<String, Value> symTable, IMyOutput<Value> output, IMyDictionary<StringValue, BufferedReader> fileTable, IMyHeapTable<Value> heapTable, IStmt s)
            {
                id=setId();
                this.stack = stack;
                this.symTable = symTable;
                this.output = output;
                this.originalProgram = s.deepCopy();
                stack.push(s);
                this.fileTable = fileTable;
                this.heapTable = heapTable;
            }

        private Node<IStmt> toTree(IStmt s)
            {

                Node<IStmt> node;
                if (s instanceof CompStmt)
                    {
                        CompStmt compStmt = (CompStmt) s;
                        node = new Node<>(new NopStmt());
                        node.setLeft(new Node<>(compStmt.getFirst()));
                        node.setRight(toTree(compStmt.getSnd()));
                    }
                else
                    {
                        node = new Node<>(s);
                    }
                return node;
            }

        public List<IStmt> statements()
            {
                MyTree<IStmt> tree = new MyTree<>();
                List<IStmt> l = new LinkedList<>();
                if (!getStack().isEmpty())
                    {
                        if (getStack().size() > 1)
                            {
                                l.add((IStmt) getStack().toList().get(1));
                            }
                        IStmt s = (IStmt) getStack().toList().getFirst();
                        tree.setRoot(toTree(s));
                        tree.inorderTraversal(l, tree.getRoot());
                    }

                return l;
            }

        public String stackToString()
            {
                List<IStmt> l = statements();
                StringBuilder sb = new StringBuilder();
                for (IStmt s : l)
                    {
                        //System.out.println(s);
                        if (Objects.equals(s.toString(), ""))
                            {
                                continue;
                            }
                        sb.append(s.toString());
                        sb.append("\n");

                    }
                //System.out.println("------------");
                return sb.toString();
            }


        @Override
        public String toString()
            {
                String line = "------------------------------------------\n";
                String str = "PrgState ID="+id+"\n";
                String stk = "Stack\n" + stackToString() + "\n";
                String sym = "SymTable\n" + symTable.printKeyValues() + "\n";
                String out = "Output\n" + output.printAsColumn() + "\n";
                String files = "FileTable\n" + fileTable.printKeys() + "\n";
                String heap = "HeapTable\n" + heapTable.toString() + "\n";
                return line + str + stk + sym + out + files + heap;

            }

        public boolean isNotCompleted()
            {
                return !stack.isEmpty();
            }

        public PrgState oneStep() throws NullExecutionException, BaseException
            {
                IMyStack<IStmt> stack = getStack();
                if (stack.isEmpty())
                    {
                        throw new NullExecutionException("Executing while stack is empty");
                    }
                IStmt s = stack.pop();
                return s.execute(this);
            }


        public IMyStack<IStmt> getStack()
            {
                return stack;
            }

        public IMyDictionary<String, Value> getSymTable()
            {
                return symTable;
            }

        public int getId()
            {
                return id;
            }

        public static synchronized int setId()
            {
                lastId++;
                return lastId;
            }

        public IMyOutput<Value> getOutput()
            {
                return output;
            }

        public IStmt getOriginalProgram()
            {
                return originalProgram;
            }

        public IMyDictionary<StringValue, BufferedReader> getFileTable()
            {
                return fileTable;
            }

        public IMyHeapTable<Value> getHeapTable()
            {
                return heapTable;
            }

        public void setStack(IMyStack<IStmt> stack)
            {
                this.stack = stack;
            }

        public void setSymTable(IMyDictionary<String, Value> symTable)
            {
                this.symTable = symTable;
            }

        public void setOutput(IMyOutput<Value> output)
            {
                this.output = output;
            }

        public void setOriginalProgram(IStmt s)
            {
                this.originalProgram = s;
            }

        public void setFileTable(IMyDictionary<StringValue, BufferedReader> fileTable)
            {
                this.fileTable = fileTable;
            }

        public void setHeapTable(IMyHeapTable<Value> heapTable)
            {
                this.heapTable = heapTable;
            }
    }
