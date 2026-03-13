package code.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {

    @Override
    public void stop()
        {
            System.exit(0);
        }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/gui/main.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("Toy Language Interpreter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}