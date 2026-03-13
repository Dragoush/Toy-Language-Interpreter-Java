package code.model.stmt;

import code.model.exceptions.BaseException;
import code.model.prgstate.PrgState;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.*;
import code.model.value.*;
import code.model.type.*;
import code.model.value.*;

public class VarDeclStmt implements IStmt
    {
        Type typ;
        String symbol;

        public VarDeclStmt(String symbol, Type typ)
            {
                this.typ = typ;
                this.symbol = symbol;
            }

        public IStmt deepCopy()
            {
                return new VarDeclStmt(symbol, typ.deepCopy());
            }

        public PrgState execute(PrgState prgState) throws BaseException
            {
                IMyDictionary<String, Value> symTable = prgState.getSymTable();
                if (symTable.isSym(symbol))
                    {
                        throw new BaseException("Symbol already exists");
                    }
                if (typ.equals(new BoolType()))
                    {
                        BoolValue bv = (BoolValue) new BoolType().defaultValue();
                        symTable.add(symbol, bv);
                    }
                else if (typ.equals(new IntType()))
                    {
                        IntValue iv = (IntValue) new IntType().defaultValue();
                        symTable.add(symbol, iv);
                    }
                else if (typ.equals(new StringType()))
                    {
                        StringValue sv = (StringValue) new StringType().defaultValue();
                        symTable.add(symbol, sv);
                    }
                else if (typ instanceof RefType rt)
                    {
                        RefValue refValue=(RefValue) new RefType(rt.getInner()).defaultValue();
                        symTable.add(symbol, refValue);
                    }
                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                typeEnv.add(symbol, typ);
                return typeEnv;
            }

        @Override
        public String toString()
            {
                return typ.toString() + " " + symbol;
            }

    }
