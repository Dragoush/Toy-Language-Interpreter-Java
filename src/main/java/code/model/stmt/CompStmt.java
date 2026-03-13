package code.model.stmt;


import code.model.prgstate.PrgState;
import code.model.exceptions.BaseException;
import code.model.prgstate.exestack.IMyStack;
import code.model.prgstate.symtable.IMyDictionary;
import code.model.type.Type;

public class CompStmt implements IStmt
    {
        IStmt first;
        IStmt snd;

        public CompStmt(IStmt first, IStmt snd)
            {
                this.first = first;
                this.snd = snd;
            }

        public IStmt getFirst()
            {
                return first;
            }

        public IStmt getSnd()
            {
                return snd;
            }

        public IStmt deepCopy()
            {
                return new CompStmt(first.deepCopy(), snd.deepCopy());
            }
        @Override
        public String toString()
            {
                return "("+first.toString()+"; "+snd.toString()+")";
            }

        public PrgState execute(PrgState prg) throws BaseException
            {
                IMyStack<IStmt> stack = prg.getStack();
                stack.push(snd);
                stack.push(first);
                return null;
            };

        @Override
        public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws BaseException
            {
                return snd.typeCheck(first.typeCheck(typeEnv));
            }
    }
