package code.model.stmt.files;

import code.model.exceptions.BaseException;
import code.model.exceptions.FileException;
import code.model.exp.Exp;
import code.model.prgstate.PrgState;
import code.model.prgstate.heap.IMyHeapTable;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.stmt.IStmt;
import code.model.type.IntType;
import code.model.type.StringType;
import code.model.type.Type;
import code.model.value.IntValue;
import code.model.value.StringValue;
import code.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class readFile implements IStmt
    {
        Exp exp;
        String var_name;

        public readFile(Exp exp, String var_name)
            {
                this.exp = exp;
                this.var_name = var_name;
            }

        @Override
        public PrgState execute(PrgState prg) throws BaseException, FileException
            {
                IMyDictionary<String, Value> symTable = prg.getSymTable();
                IMyHeapTable<Value> heapTable = prg.getHeapTable();
                if (!symTable.isSym(var_name))
                    {
                        throw new FileException("Variable " + var_name + " is not a symbol");
                    }
                Value v = symTable.getValue(var_name);
                if (!v.getType().equals(new IntType()))
                    {
                        throw new FileException("Variable " + var_name + " is not an IntType");
                    }


                Value ssv = exp.eval(symTable,heapTable);
                if (!ssv.getType().equals(new StringType()))
                    {
                        throw new FileException("Variable " + exp + " does not return a StringType");
                    }
                StringValue sv = (StringValue) ssv;

                IMyDictionary<StringValue, BufferedReader> fileTable = prg.getFileTable();
                if (!fileTable.isSym(sv))
                    {
                        throw new FileException("File " + sv + " not opened");
                    }
                BufferedReader br = fileTable.getValue(sv);
                String line;
                try
                    {
                        line = br.readLine();
                    } catch (IOException e)
                    {
                        throw new FileException("Error reading file " + sv);
                    }
                int initial_value;
                if (line == null || Objects.equals(line, ""))
                    {
                        initial_value = 0;
                    }
                else
                    {
                        initial_value = Integer.parseInt(line);
                    }
                IntValue iv=new IntValue(initial_value);
                symTable.update(var_name, iv);

                return null;
            }

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                Type typVar=typeEnv.getValue(var_name);
                Type typExp=exp.typeCheck(typeEnv);
                if (!typVar.equals(new IntType()))
                    throw new FileException("Variable " + var_name + " is not an IntType");
                if (!typExp.equals(new StringType()))
                    throw new FileException("Variable " + exp + " does not return a StringType");
                return typeEnv;

            }

        @Override
        public String toString()
            {
                return "readFile " + exp.toString() + " into " + var_name;
            }
        @Override
        public IStmt deepCopy()
            {
                return new readFile(exp.deepCopy(), var_name);
            }
    }
