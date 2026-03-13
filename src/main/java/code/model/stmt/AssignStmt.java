package code.model.stmt;

import code.model.exceptions.BaseException;
import code.model.exp.Exp;
import code.model.prgstate.PrgState;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;
import code.model.value.Value;

public class AssignStmt implements IStmt
    {
        String id;
        Exp exp;


        public AssignStmt(String id, Exp exp)
            {
                this.id = id;
                this.exp = exp;
            }


        public IStmt deepCopy()
            {
                return new AssignStmt(id, exp.deepCopy());
            }
        @Override
        public String toString()
            {
                return id + " = " + exp.toString();
            }


        public PrgState execute(PrgState prg) throws BaseException
            {
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                if (symTable.isSym(id))
                    {
                        Value val = exp.eval(symTable,heapTable);
                        Type typeId = (symTable.getValue(id)).getType();
                        if (val.getType().equals(typeId))
                            {
                                symTable.update(id,val);
                            }
                        else throw new BaseException("Declared type of variable "+id+" and type of the assigned expression do not match");
                    }
                else throw new BaseException("Used variable " +id + " was not declared before");

                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typeVar=typeEnv.getValue(id);
                Type typeExp=exp.typeCheck(typeEnv);
                if (typeVar.equals(typeExp))
                    return typeEnv;
                throw new BaseException("Assignment: right hand side and left hand side have different types");
            }
    }
