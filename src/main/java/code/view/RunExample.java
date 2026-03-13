package code.view;

import code.controller.Controller;
import code.model.exceptions.BaseException;

public class RunExample extends Command
    {
        private Controller ctr;

        public RunExample(String key, String desc, Controller ctr)
            {
                super(key, desc);
                this.ctr = ctr;
            }

        @Override
        public void execute()
            {
                try
                    {
                        ctr.allSteps();
                    } catch (BaseException e)
                    {
                        System.out.println(e.getMessage());
                    }
            }
    }