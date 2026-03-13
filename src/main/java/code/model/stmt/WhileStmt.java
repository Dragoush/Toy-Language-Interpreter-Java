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



public class WhileStmt implements IStmt
    {
        Exp exp;
        IStmt stmt;

        public WhileStmt(Exp exp, IStmt stmt)
            {
                this.exp = exp;
                this.stmt = stmt;
            }

        @Override
        public PrgState execute(PrgState prg) throws BaseException
            {
                IMyStack<IStmt> stack = prg.getStack();
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                Value value = exp.eval(symTable, heapTable);
                if (!value.getType().equals(new BoolType()))
                    {
                        throw new BaseException("Expression not a boolean");
                    }

                BoolValue boolValue = (BoolValue) value;
                if (boolValue.getValue())
                    {
                        stack.push(this);
                        stack.push(stmt);
                    }

                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typExp=exp.typeCheck(typeEnv);
                if (!typExp.equals(new BoolType()))
                    throw new BaseException("While expression not a boolean");
                stmt.typeCheck(typeEnv.deepCopy());
                return typeEnv;
            }

        @Override
        public IStmt deepCopy()
            {
                return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
            }

        @Override
        public String toString()
            {
                return "while (" + exp.toString() + ") " + stmt.toString();
            }

    }
