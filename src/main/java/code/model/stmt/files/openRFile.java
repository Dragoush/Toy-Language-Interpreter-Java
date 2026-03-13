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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements IStmt
    {
        Exp exp;

        public openRFile(Exp exp)
            {
                this.exp = exp;
            }

        @Override
        public PrgState execute(PrgState prg) throws BaseException, FileException
            {
                IMyDictionary<StringValue, BufferedReader> fileTable = prg.getFileTable();
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                Value v = exp.eval(symTable,heapTable);
                if (!v.getType().equals(new StringType()))
                    {
                        throw new FileException("Not a file path");
                    }

                StringValue sv = (StringValue) v;
                if (fileTable.isSym(sv))
                    {
                        throw new FileException("File already opened");
                    }

                try
                    {
                        BufferedReader br = new BufferedReader(new FileReader("src/"+sv.getVal()));
                        fileTable.add(sv, br);
                    } catch (FileNotFoundException e)
                    {
                        throw new FileException("File not found");
                    } catch (BaseException e)
                    {
                        throw new FileException(e.getMessage());
                    }

                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typExp=exp.typeCheck(typeEnv);
                if (!typExp.equals(new StringType()))
                    throw new FileException("Type " + exp + " does not return a StringType");
                return typeEnv;
            }

        @Override
        public String toString()
            {
                return "openRFile " + exp.toString();
            }
        @Override
        public IStmt deepCopy()
            {
                return new openRFile(exp.deepCopy());
            }
    }
