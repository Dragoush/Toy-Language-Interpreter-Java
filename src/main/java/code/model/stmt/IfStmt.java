package code.model.stmt;

import code.model.exceptions.BaseException;
import code.model.exp.Exp;
import code.model.prgstate.PrgState;
import code.model.prgstate.exestack.IMyStack;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.BoolType;
import code.model.type.Type;
import code.model.value.BoolValue;
import code.model.value.Value;

public class IfStmt implements IStmt
    {
        Exp exp;
        IStmt thenStmt;
        IStmt elseStmt;

        public IfStmt(Exp exp, IStmt thenStmt, IStmt elseStmt)
            {
                this.exp = exp;
                this.thenStmt = thenStmt;
                this.elseStmt = elseStmt;
            }


        public IStmt deepCopy()
            {
                return new IfStmt(exp.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
            }

        @Override
        public String toString()
            {
                return "if " + exp.toString() + " then " + thenStmt.toString() + "; else " + elseStmt.toString();
            }


        public PrgState execute(PrgState prgState) throws BaseException
            {
                IMyStack<IStmt> stack = prgState.getStack();
                IMyDictionary<String, Value> symTable = prgState.getSymTable();
                IMyHeapTable<Value> heapTable = prgState.getHeapTable();
                Value v = exp.eval(symTable,heapTable);
                if (v.getType().equals(new BoolType()))
                    {
                        BoolValue bv = (BoolValue) v;
                        if (bv.getValue())
                            {
                                stack.push(thenStmt);
                            }
                        else
                            {
                                stack.push(elseStmt);
                            }
                    }
                else
                    {
                        throw new BaseException("Not a logical expression");
                    }
                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typeExp=exp.typeCheck(typeEnv);
                if (!typeExp.equals(new BoolType()))
                    throw new BaseException("The condition of IF has not the type bool");
                thenStmt.typeCheck(typeEnv.deepCopy());
                elseStmt.typeCheck(typeEnv.deepCopy());
                return typeEnv;
            }
    }
