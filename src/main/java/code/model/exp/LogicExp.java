package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.BoolType;
import code.model.type.Type;
import code.model.value.BoolValue;
import code.model.value.Value;

public class LogicExp implements Exp
    {
        Exp e1;
        Exp e2;
        int op; //1-AND; 2-OR

        public LogicExp(Exp e1, Exp e2,int op)
            {
                this.e1 = e1;
                this.e2 = e2;
                this.op=op;
            }
        @Override
        public Value eval(IMyDictionary<String, Value> tbl,IMyHeapTable<Value> heapTable) throws BaseException
            {
                Value v1, v2;
                v1 = e1.eval(tbl,heapTable);
                if (v1.getType().equals(new BoolType()))
                    {
                        v2 = e2.eval(tbl,heapTable);
                        if (v2.getType().equals(new BoolType()))
                            {
                                BoolValue i1=(BoolValue) v1;
                                BoolValue i2=(BoolValue)v2;
                                boolean b1=i1.getValue(),b2=i2.getValue();
                                if (op==1){return new BoolValue(b1 && b2);}
                                if (op==2){return new BoolValue(b1 || b2);}
                                throw new BaseException("Invalid Operation");
                            }
                        else throw new BaseException("Operand2 not bool");
                    }
                else throw new BaseException("Operand1 not bool");
            }

        @Override
        public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type t1, t2;
                t1 = e1.typeCheck(typeEnv);
                t2 = e2.typeCheck(typeEnv);
                if (!t1.equals(new BoolType()))
                    throw new BaseException("First operand is not bool");
                if (!t2.equals(new BoolType()))
                    throw new BaseException("Second operand is not bool");
                return new BoolType();
            }

        @Override
        public Exp deepCopy()
            {
                return new LogicExp(e1.deepCopy(),e2.deepCopy(),op);
            }
        @Override
        public String toString()
            {
                String operand;
                if (op==1)
                    operand="&&";
                else operand="||";
                return e1.toString()+operand+e2.toString();
            }
    }
