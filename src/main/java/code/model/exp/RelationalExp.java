package code.model.exp;

import code.model.exceptions.BaseException;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.BoolType;
import code.model.type.IntType;
import code.model.type.Type;
import code.model.value.BoolValue;
import code.model.value.IntValue;
import code.model.value.Value;

public class RelationalExp implements Exp
    {
        Exp left;
        Exp right;
        int op; //1='<';2='<=';3='==';4='!=';5='>';6='>='
        String operator;

        public RelationalExp(Exp left, Exp right, String operator)
            {
                this.left = left;
                this.right = right;
                this.operator = operator;
                switch (operator)
                    {
                        case "<":
                            op = 1;
                            break;
                        case "<=":
                            op = 2;
                            break;
                        case "==":
                            op = 3;
                            break;
                        case "!=":
                            op = 4;
                            break;
                        case ">":
                            op = 5;
                            break;
                        case ">=":
                            op = 6;
                            break;
                        default:
                            op = -1;
                    }

            }


        public Value eval(IMyDictionary<String, Value> tbl,IMyHeapTable<Value> heapTable) throws BaseException
            {
                Value v1, v2;
                v1 = left.eval(tbl,heapTable);
                if (v1.getType().equals(new IntType()))
                    {
                        v2 = right.eval(tbl,heapTable);
                        if (v2.getType().equals(new IntType()))
                            {
                                IntValue iv1 = (IntValue) v1;
                                IntValue iv2 = (IntValue) v2;
                                int i1 = iv1.getVal();
                                int i2 = iv2.getVal();
                                return switch (op)
                                    {
                                        case 1 -> new BoolValue(i1 < i2);
                                        case 2 -> new BoolValue(i1 <= i2);
                                        case 3 -> new BoolValue(i1 == i2);
                                        case 4 -> new BoolValue(i1 != i2);
                                        case 5 -> new BoolValue(i1 > i2);
                                        case 6 -> new BoolValue(i1 >= i2);
                                        default -> throw new BaseException("Not a valid operator provided");
                                    };
                            }
                        throw new BaseException("Right side not a ValueExp, returning IntValue");

                    }
                throw new BaseException("Left side not a ValueExp, returning IntValue");
            }

        @Override
        public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type t1,t2;
                t1 = left.typeCheck(typeEnv);
                t2 = right.typeCheck(typeEnv);
                if (!t1.equals(new IntType()))
                    throw new BaseException("Left side not int");
                if (!t2.equals(new IntType()))
                    throw new BaseException("Right side not int");
                return new BoolType();
            }

        public Exp deepCopy()
            {
                return new RelationalExp(this.left.deepCopy(), this.right.deepCopy(), this.operator);
            }

        @Override
        public String toString()
            {
                return left.toString() + " " + operator + " " + right.toString();
            }
    }
