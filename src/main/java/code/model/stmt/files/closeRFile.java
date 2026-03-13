package code.model.stmt.files;

import code.model.exceptions.BaseException;
import code.model.exceptions.FileException;
import code.model.exp.Exp;
import code.model.prgstate.PrgState;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.stmt.IStmt;
import code.model.type.StringType;
import code.model.type.Type;
import code.model.value.StringValue;
import code.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt
    {
        Exp exp;

        public closeRFile(Exp exp)
            {
                this.exp = exp;
            }
        @Override
        public PrgState execute(PrgState prg) throws BaseException, FileException
            {
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                Value v=exp.eval(symTable,heapTable);
                if (!v.getType().equals(new StringType()))
                    throw new FileException("Expression "+exp+" is not a string");
                StringValue sv= (StringValue) v;
                IMyDictionary<StringValue, BufferedReader> fileTable = prg.getFileTable();
                if (!fileTable.isSym(sv))
                    throw new FileException("File "+sv+" is not opened");
                BufferedReader br=fileTable.getValue(sv);
                try{
                    br.close();
                }catch(IOException e){
                    throw new FileException("Closing error");
                }
                fileTable.delete(sv);
                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typExp = exp.typeCheck(typeEnv);
                if (!typExp.equals(new StringType()))
                    throw new FileException("Type " + exp + " does not return a StringType");
                return typeEnv;
            }

        @Override
        public String toString()
            {
                return "closeRFile "+exp.toString();
            }

        @Override
        public IStmt deepCopy()
            {
                return new closeRFile(exp.deepCopy());
            }
    }
