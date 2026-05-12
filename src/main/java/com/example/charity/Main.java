package com.example.charity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;

import java.time.LocalDate;


public class Main extends Application{
    public ObservableList<Supporter> supporterData = FXCollections.observableArrayList();
    public ObservableList<Initiative> initiativesData = FXCollections.observableArrayList();

    public TableView<Supporter> supporterTableView = new TableView<>();
    public TableView<Initiative> initiativeTableView = new TableView<>();
    public Controller controller=new Controller();
    public static void main(String[] args) {
        launch(args);
    }
    public Supporter selectedSupporter=new Supporter(0,"","");
    public Initiative selectedInitiative=new Initiative(0,0,0,"","","");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Funding Management - Supporters");
        VBox editLayout = new VBox(10);
        VBox editInitiativeLayout = new VBox(10);

        Scene Initiativescene ;
        Scene editInitiativescene =new  Scene(editInitiativeLayout,600,400);
        Button backInitiativebtn = new Button("back");

        Scene editscene = new Scene(editLayout, 600, 400);
        Button backbtn = new Button("back");

        TextField nameInputedit = new TextField();
        nameInputedit.setPromptText("Full Name");
        TextField emailInputedit = new TextField();
        emailInputedit.setPromptText("Email Address");

        Button submitBtn = new Button("Submit edit");

        TextField Sector_id = new TextField();
        Sector_id.setPromptText("Sector ID");
        TextField funding_target = new TextField();
        funding_target.setPromptText("funding target");
        DatePicker start_datePicker = new DatePicker();
        DatePicker end_datePicker = new DatePicker();
        TextField primary_objective = new TextField();
        primary_objective.setPromptText("primary objective");

        editLayout.setPadding(new Insets(10, 0, 0, 0));
        editLayout.getChildren().addAll(backbtn,nameInputedit, emailInputedit, submitBtn);


        TableColumn<Supporter, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Supporter, String> nameCol = new TableColumn<>("Full Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameCol.setMinWidth(200);

        TableColumn<Supporter, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailCol.setMinWidth(200);

        TableColumn<Supporter, Void> deleteCol = new TableColumn<>("Action");
        TableColumn<Supporter, Void> editCol = new TableColumn<>("Edit");


        /// ===========Initiative Table ===================== \\\
        TextField funding_target_edit = new TextField();
        funding_target_edit.setPromptText("funding target");
        DatePicker start_datePicker_edit = new DatePicker();
        DatePicker end_datePicker_edit = new DatePicker();
        TextField primary_objective_edit = new TextField();
        primary_objective_edit.setPromptText("primary objective");

        Button submitInitiativeBtn = new Button("Submit edit");

        editInitiativeLayout.setPadding(new Insets(10, 0, 0, 0));
        editInitiativeLayout.getChildren().addAll(backInitiativebtn,funding_target_edit, start_datePicker_edit, end_datePicker_edit, primary_objective_edit,submitInitiativeBtn);

        TableColumn<Initiative, Number> initiativeId = new TableColumn<>("ID");
        initiativeId.setCellValueFactory(cellData -> cellData.getValue().idPropery());

        TableColumn<Initiative, Number> sectorIdcol = new TableColumn<>("Sector ID");
        sectorIdcol.setCellValueFactory(cellData -> cellData.getValue().sectoIdProperty());

        TableColumn<Initiative, Number> fundingTargetcol = new TableColumn<>("target");
        fundingTargetcol.setCellValueFactory(cellData -> cellData.getValue().fundingTargetProperty());
        fundingTargetcol.setMinWidth(60);

        TableColumn<Initiative, String> startDatecol = new TableColumn<>("start date");
        startDatecol.setCellValueFactory(cellData -> cellData.getValue().startDate());
        startDatecol.setMinWidth(60);

        TableColumn<Initiative, String> endDatecol = new TableColumn<>("end date");
        endDatecol.setCellValueFactory(cellData -> cellData.getValue().endDate());
        endDatecol.setMinWidth(60);

        TableColumn<Initiative, String> primaryObjectivecol = new TableColumn<>("Primary Object ");
        primaryObjectivecol.setCellValueFactory(cellData -> cellData.getValue().primaryObjectiveProperty());
        primaryObjectivecol.setMinWidth(200);

        TableColumn<Initiative, Void> deleteInitiativeCol = new TableColumn<>("Action");
        TableColumn<Initiative, Void> editInitiativeCol = new TableColumn<>("Edit");

        initiativeTableView.getColumns().addAll(initiativeId,sectorIdcol,fundingTargetcol,startDatecol,endDatecol,primaryObjectivecol,deleteInitiativeCol,editInitiativeCol);
        initiativeTableView.setItems(initiativesData);


        supporterTableView.getColumns().addAll(idCol, nameCol, emailCol, deleteCol,editCol);
        supporterTableView.setItems(supporterData);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Full Name");
        TextField emailInput = new TextField();
        emailInput.setPromptText("Email Address");

        Button addButton = new Button("Add Supporter");
        addButton.setOnAction(e -> {
            controller.addSupporter(nameInput.getText(), emailInput.getText(),supporterData);
            nameInput.clear();
            emailInput.clear();
            controller.loadSupporters(supporterData);
        });

        deleteCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setOnAction(event -> {
                    selectedSupporter = getTableView().getItems().get(getIndex());

                    controller.deleteSupporter(selectedSupporter.idProperty().get());

                    supporterData.remove(selectedSupporter);
                });

                deleteBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
        editCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");

            {
                editBtn.setOnAction(event -> {

                    selectedSupporter = getTableView().getItems().get(getIndex());
                    nameInputedit.setText(selectedSupporter.nameProperty().get());
                    emailInputedit.setText(selectedSupporter.emailProperty().get());
                    primaryStage.setScene(editscene);
                    primaryStage.show();
                });

                editBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editBtn);
                }
            }
        });


        deleteInitiativeCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setOnAction(event -> {
                    selectedInitiative = getTableView().getItems().get(getIndex());

                   controller.deleteInitiative(selectedInitiative.idPropery().get());

                    initiativesData.remove(selectedInitiative);
                });

                deleteBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
        editInitiativeCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");

            {
                editBtn.setOnAction(event -> {

                    selectedInitiative = getTableView().getItems().get(getIndex());
//                    nameInputedit.setText(selectedSupporter.nameProperty().get());
//                    emailInputedit.setText(selectedSupporter.emailProperty().get());
                    funding_target_edit.setText(String.valueOf(selectedInitiative.fundingTargetProperty().get()));
                    start_datePicker_edit.setValue(LocalDate.parse(selectedInitiative.startDate().get()));
                    end_datePicker_edit.setValue(LocalDate.parse(selectedInitiative.endDate().get()));
                    primary_objective_edit.setText(selectedInitiative.primaryObjectiveProperty().get());
                    primaryStage.setScene(editInitiativescene);
                    primaryStage.show();
                });

                editBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editBtn);
                }
            }
        });



        HBox formLayout = new HBox(10);
        formLayout.setPadding(new Insets(10, 0, 0, 0));
        formLayout.getChildren().addAll(nameInput, emailInput, addButton);

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(new Label("Supporter Management"), supporterTableView, formLayout);


        ///Adding initiative scene content

        Button addInitiativeBtn = new Button("Add Initative");
        addInitiativeBtn.setOnAction(e -> {
            controller.addInitiative(Sector_id.getText(),funding_target.getText(),start_datePicker.getValue(),end_datePicker.getValue(),primary_objective.getText(),initiativesData);
            Sector_id.clear();
            funding_target.clear();
            start_datePicker.setValue(null);
            end_datePicker.setValue(null);
            primary_objective.clear();
            controller.loadInitiatives(initiativesData);

        });
        HBox initiativeformLayout = new HBox(10);
        initiativeformLayout.setPadding(new Insets(10, 0, 0, 0));
        initiativeformLayout.getChildren().addAll(Sector_id, funding_target, start_datePicker,end_datePicker,primary_objective,addInitiativeBtn);

        VBox InitiativeLayout = new VBox(10);
        InitiativeLayout.setPadding(new Insets(10));
        InitiativeLayout.getChildren().addAll(new Label("Initiative Management"), initiativeTableView,initiativeformLayout);


        controller.loadSupporters(supporterData);
        supporterTableView.setItems(supporterData);

        controller.loadInitiatives(initiativesData);
        initiativeTableView.setItems(initiativesData);
        Initiativescene = new Scene(InitiativeLayout);

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(Initiativescene);
        primaryStage.show();
        backbtn.setOnAction(e -> {
            primaryStage.setScene(scene);
        });
        submitBtn.setOnAction(e -> {
            if(nameInputedit.getText().isEmpty() || emailInputedit.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.setContentText("You must enter value at nameField and emailField");
                alert.showAndWait();

                return;
            }
            controller.updateSupporter(selectedSupporter.idProperty().get(),nameInputedit.getText(),emailInputedit.getText());
            selectedSupporter.setFullName(nameInputedit.getText());
            selectedSupporter.setEmail(emailInputedit.getText());
            nameInput.clear();
            emailInput.clear();
            primaryStage.setScene(scene);
        });

        backInitiativebtn.setOnAction(e -> {
            primaryStage.setScene(Initiativescene);
        });
        submitInitiativeBtn.setOnAction(e -> {
            if(funding_target_edit.getText().isEmpty() || primary_objective_edit.getText().isEmpty()||start_datePicker_edit==null||end_datePicker_edit==null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.setContentText("You must enter value at nameField and emailField");
                alert.showAndWait();
                return;
            }
            controller.updateInitiative(selectedInitiative.idPropery().get(),funding_target_edit.getText(),start_datePicker_edit.getValue(),end_datePicker_edit.getValue(),primary_objective_edit.getText());
            controller.loadInitiatives(initiativesData);
            funding_target_edit.clear();
            primary_objective_edit.clear();
            start_datePicker_edit.setValue(null);
            end_datePicker_edit.setValue(null);

            primaryStage.setScene(Initiativescene);
        });

    }
}

