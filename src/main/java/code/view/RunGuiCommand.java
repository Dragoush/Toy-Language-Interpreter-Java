package code.view;

import code.controller.Controller;
import code.gui.ProgramWindow;
import code.model.exceptions.BaseException;

public class RunGuiCommand extends Command
    {
        private Controller ctr;

        public RunGuiCommand(String key, String desc, Controller ctr)
            {
                super(key, desc);
                this.ctr = ctr;
            }

        @Override
        public void execute()
            {
                ProgramWindow.open(ctr);
            }
    }