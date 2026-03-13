package code.gui;

import code.model.exceptions.BaseException;
import code.view.TextMenu;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import code.initialise.Instance;



public class MainController
    {

        private final TextMenu textMenu = Instance.getInstance();

        private void setupTextMenu()
            {
                programList.getItems().addAll(textMenu.getListOfCommands());
            }

        private String extractKey(String item)
            {
                return item.split(":")[0].trim();
            }

        private void showError(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("Program execution failed");
            alert.setContentText(message);

            alert.showAndWait();
        }


        private void setupListView()
            {
                programList.setOnMouseClicked(event ->
                    {
                        if (event.getClickCount() == 2)
                            {
                                String selected = programList.getSelectionModel().getSelectedItem();
                                if (selected == null)
                                    {
                                        return;
                                    }

                                String key = extractKey(selected);
                                try
                                    {
                                        textMenu.executeCommand(key);
                                    } catch (BaseException e)
                                    {
                                        showError(e.getMessage());
                                    }

                            }
                    });
            }

        @FXML
        private TextArea outputArea;

        @FXML
        private ListView<String> programList;

        @FXML
        public void initialize()
            {
                setupTextMenu();
                setupListView();
            }


    }
