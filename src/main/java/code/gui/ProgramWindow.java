package code.gui;

import code.controller.Controller;
import code.model.exceptions.BaseException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import code.gui.ProgramController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramWindow
    {
        public static void open(Controller controller) {
            try {
                if (!controller.hasActivePrograms())
                    throw new BaseException("No active programs found!");

                FXMLLoader loader = new FXMLLoader(
                        ProgramWindow.class.getResource("/gui/program-view.fxml")
                );
                Parent root = (Parent)loader.load();
                ProgramController guiController = (ProgramController)loader.getController();
                guiController.setController(controller);

                Stage stage = new Stage();
                stage.setTitle("Program execution");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (BaseException e) {
                // show error WITHOUT creating ProgramWindow
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution Error");
                alert.setHeaderText("Cannot start program");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


/*
old but reliable (no controller validation)
        public static void open(Controller ctr)
            {
                try{
                    FXMLLoader loader = new FXMLLoader(
                            ProgramWindow.class.getResource("/gui/program-view.fxml")
                    );
                    Parent root = (Parent)loader.load();
                    ProgramController guiController = (ProgramController)loader.getController();
                    guiController.setController(ctr);

                    Stage stage = new Stage();
                    stage.setTitle("Program execution");
                    stage.setScene(new Scene(root));
                    stage.show();


                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
*/