package code.gui;

import code.controller.Controller;
import code.model.exceptions.BaseException;
import code.model.prgstate.PrgState;
import code.model.type.Type;
import code.model.value.StringValue;
import code.model.value.Value;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProgramController
    {

        private Controller controller;

        @FXML
        private TextField prgCountField;

        //heap table
        @FXML
        private TableView<Pair<Integer, Value>> heapTable;
        @FXML
        private TableColumn<Pair<Integer, Value>, Integer> heapAddressCol;
        @FXML
        private TableColumn<Pair<Integer, Value>, String> heapValueCol;

        //sym table
        @FXML
        private TableView<Pair<String, Value>> symTable;
        @FXML
        private TableColumn<Pair<String, Value>, String> symNameCol;
        @FXML
        private TableColumn<Pair<String, Value>, String> symValueCol;


        @FXML
        private ListView<Value> outList;
        @FXML
        private ListView<String> fileTable;
        @FXML
        private ListView<Integer> prgIdList;

        @FXML
        private ListView<String> exeStackList;
        @FXML
        private Button runOneStepButton;

        private void showError(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("Program execution failed");
            alert.setContentText(message);

            alert.showAndWait();
        }

        @FXML
        private void onOneStep()
            {
                try
                    {
                        controller.executor= Executors.newFixedThreadPool(2);
                        List<PrgState> prgList= controller.getRepo().getPrgList();
                        controller.oneStepForAllPrg(prgList);
                        controller.conservativeGarbageCollector(prgList);

                        refreshAll();
                    } catch (BaseException e)
                    {
                        showError(e.getMessage());
                    }
            }

        private void refreshAll() throws BaseException
            {
                populateSharedStructures(controller.getRepo().getPrgList());
                Integer lastSelected = prgIdList.getSelectionModel().getSelectedItem();
                PrgState lastPrgState = controller.getRepo().getPrgById(lastSelected);

                List<PrgState> active = controller.getRepo().getPrgList()
                        .stream()
                        .filter(PrgState::isNotCompleted)
                        .collect(Collectors.toCollection(ArrayList::new));

                controller.getRepo().setPrgList(active);

                populatePrgCount(active);
                populatePrgList(active);


                if (active.isEmpty())
                    {
                        //clearAllViews();
                        if (lastPrgState != null)
                            {
                                populatePerPrgState(lastPrgState);
                            }
                        controller.executor.shutdownNow();
                        runOneStepButton.setDisable(true);
                        return;
                    }


                Integer selectedId = prgIdList.getSelectionModel().getSelectedItem();
                if (selectedId == null)
                    {
                        prgIdList.getSelectionModel().select(0);
                        selectedId = prgIdList.getSelectionModel().getSelectedItem();
                    }
                PrgState selected = controller.getRepo().getPrgById(selectedId);

                if (selected == null)
                    {

                        selected = active.getFirst();
                        prgIdList.getSelectionModel().select(0);
                    }

                populatePerPrgState(selected);
            }

        private void clearAllViews()
            {
                heapTable.getItems().clear();
                symTable.getItems().clear();
                exeStackList.getItems().clear();
                outList.getItems().clear();
                fileTable.getItems().clear();
            }


        private void initHeapTable()
            {
                heapAddressCol.setCellValueFactory(
                        cell -> new SimpleObjectProperty<>(cell.getValue().getKey())
                );

                heapValueCol.setCellValueFactory(
                        cell -> new SimpleStringProperty(
                                cell.getValue().getValue().toString()
                        )
                );
            }

        private void initSymTable() {
            symNameCol.setCellValueFactory(
                    cell -> new SimpleStringProperty(cell.getValue().getKey())
            );

            symValueCol.setCellValueFactory(
                    cell -> new SimpleStringProperty(
                            cell.getValue().getValue().toString()
                    )
            );
        }
        @FXML
        public void initialize()
            {
                initHeapTable();
                initSymTable();
                prgIdList.getSelectionModel()
                        .selectedItemProperty()
                        .addListener((obs, oldId, newId) ->
                            {
                                if (newId == null)
                                    {
                                        return;
                                    }
                                PrgState prg = controller.getRepo().getPrgById(newId);
                                populatePerPrgState(prg);
                            });
            }

        public void setController(Controller controller)
            {
                this.controller = controller;
                populateInitialState();
            }

        public void populateInitialState()
            {
                List<PrgState> prgs = controller.getRepo().getPrgList();

                populatePrgCount(prgs);
                populatePrgList(prgs);
                populateSharedStructures(prgs);

                if (!prgs.isEmpty())
                    {
                        prgIdList.getSelectionModel().select(0);
                        populatePerPrgState(prgs.getFirst());
                    }
            }

        private void populatePrgCount(List<PrgState> prgList)
            {
                prgCountField.setText("Program ID Count: " + String.valueOf(prgList.size()));
            }

        private void populatePrgList(List<PrgState> prgList)
            {
                ObservableList<Integer> ids = FXCollections.observableArrayList();
                for (PrgState prg : prgList)
                    {
                        ids.add(prg.getId());
                    }
                prgIdList.setItems(ids);
            }

        private void populateSharedStructures(List<PrgState> prgList)
            {
                populateHeap(prgList);
                populateOut(prgList);
                populateFiles(prgList);
            }

        private void populateHeap(List<PrgState> prgList)
            {
                Map<Integer, Value> heap = prgList.getFirst().getHeapTable().getContent();
                ObservableList<Pair<Integer, Value>> data =
                        FXCollections.observableArrayList();
                for (Map.Entry<Integer, Value> entry : heap.entrySet())
                    {
                        data.add(new Pair<>(entry.getKey(), entry.getValue()));
                    }
                heapTable.setItems(data);
                heapTable.refresh();
            }

        private void populateOut(List<PrgState> prgList)
            {
                outList.setItems(
                        FXCollections.observableArrayList(prgList.getFirst().getOutput().getList())
                );
            }

        private void populateFiles(List<PrgState> prgList)
            {
                Set<StringValue> filesValues = prgList.getFirst().getFileTable().getContent().keySet();
                Set<String> files = new TreeSet<>();
                for (StringValue id : filesValues)
                    {
                        files.add(id.getVal());
                    }
                fileTable.setItems(FXCollections.observableArrayList(files));
            }

        private void populatePerPrgState(PrgState prg)
            {
                populateSymTable(prg);
                populateStack(prg);
            }

        private void populateSymTable(PrgState prg)
            {
                ObservableList<Pair<String, Value>> data =
                        FXCollections.observableArrayList();
                for (Map.Entry<String, Value> entry : prg.getSymTable().getContent().entrySet())
                    {
                        data.add(new Pair<>(entry.getKey(), entry.getValue()));
                    }
                symTable.setItems(data);
                symTable.refresh();
            }

        private void populateStack(PrgState prg)
            {

                String stackString = prg.stackToString();
                String[] elements = stackString.split("\\R");
                ObservableList<String> stackData = FXCollections.observableArrayList(elements);
                exeStackList.setItems(stackData);
            }


    }