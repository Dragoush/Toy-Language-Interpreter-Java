package code.view;

import javafx.application.Platform;

public class ExitCommand extends Command
    {
        public ExitCommand(String key, String desc)
            {
                super(key, desc);
            }

        @Override
        public void execute()
            {
                Platform.exit();
                System.exit(0);
            }
    }