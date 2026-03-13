package code.view;

import code.controller.Controller;
import code.model.exp.*;
import code.model.stmt.*;
import code.model.type.*;
import code.model.exp.*;
import code.model.exceptions.BaseException;
import code.model.type.*;
import code.model.prgstate.PrgState;
import code.model.prgstate.symtable.MyDictionary;
import code.model.stmt.*;
import code.model.stmt.files.closeRFile;
import code.model.stmt.files.openRFile;
import code.model.stmt.files.readFile;
import code.model.stmt.heap.newStmt;
import code.model.stmt.heap.wH;
import code.model.value.BoolValue;
import code.model.value.IntValue;
import code.model.value.StringValue;
import code.repository.Repository;


//this is the CONSOLE view, made before the GUI
//the intended way is to use GuiMain for the GUI
public class Interpreter
    {
        static String logsPath = "src/main/java/code/logs/";
        static String getNextLogFile(String fileName)
            {
                //expected input: log<NR>.txt
                //output: log<NR+1>.txt
                String nrOnly =fileName.replaceAll("[^0-9]","");
                Integer nr=Integer.parseInt(nrOnly);
                nr=nr+1;

                return "log"+nr+".txt";
            }

        public static void main(String[] args)
            {
                String currentLog="log0.txt";
                TextMenu menu = new TextMenu();

                IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new PrintStmt(new VarExp("v"))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex1.typeCheck(typEnv);
                        PrgState program1 = new PrgState(ex1);

                        Repository r1 = new Repository(program1, logsPath+currentLog);
                        Controller ctr1 = new Controller(r1);
                        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                        new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex2.typeCheck(typEnv);
                        PrgState program2 = new PrgState(ex2);

                        Repository r2 = new Repository(program2, logsPath+currentLog);
                        Controller ctr2 = new Controller(r2);
                        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex3.typeCheck(typEnv);
                        PrgState program3 = new PrgState(ex3);

                        Repository r3 = new Repository(program3, logsPath+currentLog);
                        Controller ctr3 = new Controller(r3);
                        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex4 = new CompStmt(new VarDeclStmt("a", new BoolType()), new AssignStmt("b", new ValueExp(new IntValue(4))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex4.typeCheck(typEnv);
                        PrgState program4 = new PrgState(ex4);

                        Repository r4 = new Repository(program4, logsPath+currentLog);
                        Controller ctr4 = new Controller(r4);
                        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(2))),
                                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(3))),
                                                new IfStmt(new RelationalExp(new VarExp("a"), new VarExp("b"), "<"), new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b")))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex5.typeCheck(typEnv);
                        PrgState program5 = new PrgState(ex5);

                        Repository r5 = new Repository(program5, logsPath+currentLog);
                        Controller ctr5 = new Controller(r5);
                        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex6 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                        new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("main/java/test.in"))),
                                new CompStmt(new openRFile(new VarExp("varf")),
                                        new CompStmt(new VarDeclStmt("varc", new IntType()),
                                                new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                                new closeRFile(new VarExp("varf"))))))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex6.typeCheck(typEnv);
                        PrgState program6 = new PrgState(ex6);

                        Repository r6 = new Repository(program6, logsPath+currentLog);
                        Controller ctr6 = new Controller(r6);
                        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }


                IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new newStmt("a", new VarExp("v")),
                                                new CompStmt(new newStmt("v", new ValueExp(new IntValue(30))),
                                                        new PrintStmt(new rH(new rH(new VarExp("a")))))))));

                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex7.typeCheck(typEnv);
                        PrgState program7 = new PrgState(ex7);

                        Repository r7 = new Repository(program7, logsPath+currentLog);
                        Controller ctr7 = new Controller(r7);
                        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                                new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                        new PrintStmt(new VarExp("v")))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex8.typeCheck(typEnv);
                        PrgState program8 = new PrgState(ex8);

                        Repository r8 = new Repository(program8, logsPath+currentLog);
                        Controller ctr8 = new Controller(r8);
                        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new newStmt("a", new VarExp("v")),
                                                new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                        new PrintStmt(new ArithExp(1, new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5))))))
                                )));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex9.typeCheck(typEnv);
                        PrgState program9 = new PrgState(ex9);

                        Repository r9 = new Repository(program9, logsPath+currentLog);
                        Controller ctr9 = new Controller(r9);
                        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }


                IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                        new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ArithExp(1, new rH(new VarExp("v")), new ValueExp(new IntValue(5))))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex10.typeCheck(typEnv);
                        PrgState program10 = new PrgState(ex10);

                        Repository r10 = new Repository(program10, logsPath+currentLog);
                        Controller ctr10 = new Controller(r10);
                        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex11 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new newStmt("a", new VarExp("v")),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new VarExp("a")))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex11.typeCheck(typEnv);
                        PrgState program11 = new PrgState(ex11);

                        Repository r11 = new Repository(program11, logsPath+currentLog);
                        Controller ctr11 = new Controller(r11);
                        menu.addCommand(new RunExample("11", ex11.toString(), ctr11));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }

                IStmt ex12 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new rH(new VarExp("v")))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex12.typeCheck(typEnv);
                        PrgState program12 = new PrgState(ex12);

                        Repository r12 = new Repository(program12, logsPath+currentLog);
                        Controller ctr12 = new Controller(r12);
                        menu.addCommand(new RunExample("12", ex12.toString(), ctr12));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }


                IStmt ex13 = new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                        new CompStmt(new newStmt("a", new ValueExp(new IntValue(22))),
                                                new CompStmt(new forkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new rH(new VarExp("a"))))))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new rH(new VarExp("a")))))))));
                try
                    {
                        currentLog=getNextLogFile(currentLog);
                        MyDictionary<String, Type> typEnv = new MyDictionary<String, Type>();
                        ex13.typeCheck(typEnv);
                        PrgState program13 = new PrgState(ex13);

                        Repository r13 = new Repository(program13, logsPath+currentLog);
                        Controller ctr13 = new Controller(r13);
                        menu.addCommand(new RunExample("13", ex13.toString(), ctr13));
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }


                menu.addCommand(new ExitCommand("0", "exit"));

                menu.show();
            }
    }
