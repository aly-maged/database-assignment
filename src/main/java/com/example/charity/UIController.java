package com.example.charity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

public class UIController {


    @FXML private TableView<Supporter>          supporterTableView;
    @FXML private TableColumn<Supporter, Number> idCol;
    @FXML private TableColumn<Supporter, String> nameCol;
    @FXML private TableColumn<Supporter, String> emailCol;
    @FXML private TableColumn<Supporter, Void>   actionCol;
    @FXML private TableColumn<Supporter, Void>   editCol;
    @FXML private TextField nameInput;
    @FXML private TextField emailInput;


    @FXML private TextField nameField;
    @FXML private TextField emailField;


    @FXML private TableView<Initiative>           initiativeTableView;
    @FXML private TableColumn<Initiative, Number> initiativeIdCol;
    @FXML private TableColumn<Initiative, Number> sectorIdCol;
    @FXML private TableColumn<Initiative, Number> fundingTargetCol;
    @FXML private TableColumn<Initiative, String> startDateCol;
    @FXML private TableColumn<Initiative, String> endDateCol;
    @FXML private TableColumn<Initiative, String> primaryObjectiveCol;
    @FXML private TableColumn<Initiative, Void>   actionInitiativeCol;
    @FXML private TableColumn<Initiative, Void>   editInitiativeCol;
    @FXML private TextField   sectorIdInput;
    @FXML private TextField   primaryObjectInput;
    @FXML private DatePicker  startDatePicker;
    @FXML private DatePicker  endDatePicker;


    @FXML private TextField  targetField;
    @FXML private TextField  primaryObjectField;
    @FXML private DatePicker startDatePickerEdit;
    @FXML private DatePicker endDatePickerEdit;


    @FXML private TableView<Contribution>           contributionsTableView;
    @FXML private TableColumn<Contribution, String> fullNameCol;
    @FXML private TableColumn<Contribution, Number> amountCol;
    @FXML private TableColumn<Contribution, String> timeCol;
    @FXML private TableColumn<Contribution, String> primaryObjectContribCol;


    private final ObservableList<Supporter>   supporterData    = FXCollections.observableArrayList();
    private final ObservableList<Initiative>  initiativesData  = FXCollections.observableArrayList();
    private final ObservableList<Contribution> contributionData = FXCollections.observableArrayList();
    private final Controller controller = new Controller();

    private Supporter  selectedSupporter;
    private Initiative selectedInitiative;


    @FXML
    public void initialize() {
        if (supporterTableView != null)   initSupporterTable();
        if (initiativeTableView != null)  initInitiativeTable();
        if (contributionsTableView != null) initContributionsTable();
    }


    @FXML
    private void handleSupporters() {
        switchTo("Supporter table.fxml");
    }

    @FXML
    private void handleInitiative() {
        switchTo("Initiative Table.fxml");
    }

    @FXML
    private void handleContributions() {
        switchTo("Contributions Table.fxml");

    }


    private void initSupporterTable() {
        idCol.setCellValueFactory(cell -> cell.getValue().idProperty());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        emailCol.setCellValueFactory(cell -> cell.getValue().emailProperty());
        setupSupporterDeleteColumn();
        setupSupporterEditColumn();
        supporterTableView.setItems(supporterData);
        controller.loadSupporters(supporterData);
    }

    @FXML
    private void handleAddSupporter() {
        String name  = nameInput.getText();
        String email = emailInput.getText();
        if (name.isEmpty() || email.isEmpty()) return;
        controller.addSupporter(name, email, supporterData);
        nameInput.clear();
        emailInput.clear();
        controller.loadSupporters(supporterData);
    }

    @FXML
    private void handleBackFromSupporter() {
        switchTo("homePage.fxml");
    }

    private void setupSupporterDeleteColumn() {
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            {
                deleteBtn.getStyleClass().add("delete-button");
                deleteBtn.setOnAction(e -> {
                    Supporter s = getTableView().getItems().get(getIndex());
                    controller.deleteSupporter(s.idProperty().get());
                    supporterData.remove(s);
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    private void setupSupporterEditColumn() {
        editCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            {
                editBtn.getStyleClass().add("edit-button");
                editBtn.setOnAction(e -> {
                    selectedSupporter = getTableView().getItems().get(getIndex());
                    switchTo("editSupporter.fxml");
                    // after switch, the new controller instance has nameField/emailField
                    // we pass data via static reference
                    UIController.pendingSupporter = selectedSupporter;
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editBtn);
            }
        });
    }




    static Supporter  pendingSupporter;
    static Initiative pendingInitiative;

    @FXML
    private void initEditSupporter() {

    }


    private void prefillEditSupporter() {
        if (pendingSupporter != null && nameField != null) {
            nameField.setText(pendingSupporter.nameProperty().get());
            emailField.setText(pendingSupporter.emailProperty().get());
        }
    }

    @FXML
    private void handleSubmitEditSupporter() {
        String name  = nameField.getText();
        String email = emailField.getText();
        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Name and Email fields cannot be empty.");
            return;
        }
        controller.updateSupporter(pendingSupporter.idProperty().get(), name, email);
        pendingSupporter.setFullName(name);
        pendingSupporter.setEmail(email);
        pendingSupporter = null;
        switchTo("Supporter table.fxml");
    }

    @FXML
    private void handleBackFromEditSupporter() {
        switchTo("Supporter table.fxml");
    }


    private void initInitiativeTable() {
        initiativeIdCol.setCellValueFactory(cell -> cell.getValue().idPropery());
        initiativeIdCol.setStyle("-fx-alignment: CENTER;");

        sectorIdCol.setCellValueFactory(cell -> cell.getValue().sectoIdProperty());
        fundingTargetCol.setCellValueFactory(cell -> cell.getValue().fundingTargetProperty());
        startDateCol.setCellValueFactory(cell -> cell.getValue().startDate());
        endDateCol.setCellValueFactory(cell -> cell.getValue().endDate());
        primaryObjectiveCol.setCellValueFactory(cell -> cell.getValue().primaryObjectiveProperty());
        setupInitiativeDeleteColumn();
        setupInitiativeEditColumn();
        initiativeTableView.setItems(initiativesData);
        controller.loadInitiatives(initiativesData);
    }

    @FXML
    private void handleAddInitiative() {
        String sectorId  = sectorIdInput.getText();
        String fund=targetField.getText();
        String objective = primaryObjectInput.getText();
        LocalDate start  = startDatePicker.getValue();
        LocalDate end    = endDatePicker.getValue();
        controller.addInitiative(sectorId,fund, start, end, objective, initiativesData);
        sectorIdInput.clear();
        primaryObjectInput.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        initiativesData.clear();
        controller.loadInitiatives(initiativesData);
    }

    @FXML
    private void handleBackFromInitiative() {
        switchTo("homePage.fxml");
    }

    private void setupInitiativeDeleteColumn() {
        actionInitiativeCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            {
                deleteBtn.getStyleClass().add("delete-button");

                deleteBtn.setOnAction(e -> {
                    Initiative ini = getTableView().getItems().get(getIndex());
                    controller.deleteInitiative(ini.idPropery().get());
                    initiativesData.remove(ini);
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    private void setupInitiativeEditColumn() {
        editInitiativeCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            {
                editBtn.getStyleClass().add("edit-button");
                editBtn.setOnAction(e -> {
                    pendingInitiative = getTableView().getItems().get(getIndex());
                    switchTo("Edit_Initivative.fxml");
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editBtn);
            }
        });
    }


    private void prefillEditInitiative() {
        if (pendingInitiative != null && targetField != null) {
            targetField.setText(String.valueOf(pendingInitiative.fundingTargetProperty().get()));
            primaryObjectField.setText(pendingInitiative.primaryObjectiveProperty().get());
            String start = pendingInitiative.startDate().get();
            String end   = pendingInitiative.endDate().get();
            if (start != null && !start.equals("No Date")) startDatePickerEdit.setValue(LocalDate.parse(start));
            if (end   != null && !end.equals("No Date"))   endDatePickerEdit.setValue(LocalDate.parse(end));
        }
    }

    @FXML
    private void handleSubmitEditInitiative() {
        String target    = targetField.getText();
        String objective = primaryObjectField.getText();
        LocalDate start  = startDatePickerEdit.getValue();
        LocalDate end    = endDatePickerEdit.getValue();
        if (target.isEmpty() || objective.isEmpty() || start == null || end == null) {
            showAlert("All fields are required.");
            return;
        }
        controller.updateInitiative(pendingInitiative.idPropery().get(), target, start, end, objective);
        pendingInitiative = null;
        switchTo("Initiative Table.fxml");
    }

    @FXML
    private void handleBackFromEditInitiative() {
        switchTo("Initiative Table.fxml");
    }


    private void initContributionsTable() {

        fullNameCol.setCellValueFactory(cell -> cell.getValue().getFullName());
        amountCol.setCellValueFactory(cell -> cell.getValue().getAmountGifted());
        timeCol.setCellValueFactory(cell -> cell.getValue().getTime());
        primaryObjectContribCol.setCellValueFactory(cell -> cell.getValue().getPrimaryObjective());
        contributionsTableView.setItems(contributionData);
        // Uncomment once loadContributions() is implemented in DB.java
         controller.loadContributions(contributionData);
    }

    @FXML
    private void handleBackFromContributions() {
        switchTo("homePage.fxml");
    }


    private void switchTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            HelloApplication.primaryStage.setScene(scene);
            HelloApplication.primaryStage.show();


            UIController newCtrl = loader.getController();
            if (fxml.equals("editSupporter.fxml"))   newCtrl.prefillEditSupporter();
            if (fxml.equals("Edit_Initivative.fxml")) newCtrl.prefillEditInitiative();

        } catch (IOException e) {
            System.err.println("Failed to load: " + fxml);
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
