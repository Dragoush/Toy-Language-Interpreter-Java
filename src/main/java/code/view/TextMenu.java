package code.view;

import code.model.exceptions.BaseException;

import java.util.*;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){ commands=new HashMap<>(); }
    public void addCommand(Command c){ commands.put(c.getKey(),c);}
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public ArrayList<String> getListOfCommands()
        {
            ArrayList<String> list=new ArrayList<>();
            for (Command com : commands.values())
                {
                    String line=String.format("%4s : %s", com.getKey(), com.getDescription());
                    list.add(line);
                }
            return list;
        }

    public void executeCommand(String command) throws BaseException
        {
            Command c = commands.get(command);
            if (c == null)
                {
                    throw new BaseException("Invalid key");
                }
            c.execute();
        }


    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue; }
            com.execute();
        }
    }
}
