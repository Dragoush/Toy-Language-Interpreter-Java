package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.exceptions.DivisionByZero;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.IntType;
import code.model.type.Type;
import code.model.value.IntValue;
import code.model.value.Value;

public class ArithExp implements Exp
    {
        int op; //1-add; 2-subtract; 3-multiply; 4-divide
        Exp e1;
        Exp e2;

        public ArithExp(int op, Exp e1, Exp e2)
            {
                this.op = op;
                this.e1 = e1;
                this.e2 = e2;
            }

        @Override
        public Exp deepCopy()
            {
                return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
            }

        public Value eval(IMyDictionary<String, Value> tbl, IMyHeapTable<Value> heapTable) throws BaseException, DivisionByZero
            {
                Value v1, v2;
                v1 = e1.eval(tbl, heapTable);
                if (v1.getType().equals(new IntType()))
                    {
                        v2 = e2.eval(tbl, heapTable);
                        if (v2.getType().equals(new IntType()))
                            {
                                IntValue i1 = (IntValue) v1;
                                IntValue i2 = (IntValue) v2;
                                int n1, n2;
                                n1 = i1.getVal();
                                n2 = i2.getVal();
                                if (op == 1)
                                    {
                                        return new IntValue(n1 + n2);
                                    }
                                if (op == 2)
                                    {
                                        return new IntValue(n1 - n2);
                                    }
                                if (op == 3)
                                    {
                                        return new IntValue(n1 * n2);
                                    }
                                if (op == 4)
                                    {
                                        if (n2 == 0)
                                            {
                                                throw new DivisionByZero("Division by zero");
                                            }
                                        else
                                            {
                                                return new IntValue(n1 / n2);
                                            }
                                    }
                                else
                                    {
                                        throw new BaseException("Invalid operation");
                                    }
                            }
                        else
                            {
                                throw new BaseException("Operand2 not int");
                            }
                    }
                else
                    {
                        throw new BaseException("Operand1 not int");
                    }
            }


        @Override
        public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type t1, t2;
                t1 = e1.typeCheck(typeEnv);
                t2 = e2.typeCheck(typeEnv);
                if (t1.equals(new IntType()))
                    {
                        if (t2.equals(new IntType()))
                            {
                                return new IntType();
                            }
                        else
                            {
                                throw new BaseException("Second operand is not int");
                            }
                    }
                else throw new BaseException("First operand is not int");
            }

        @Override
        public String toString()
            {
                String operand;
                if (op == 1)
                    {
                        operand = "+";
                    }
                else if (op == 2)
                    {
                        operand = "-";
                    }
                else if (op == 3)
                    {
                        operand = "*";
                    }
                else
                    {
                        operand = "/";
                    }


                return e1.toString() + operand + e2.toString();
            }


    }
