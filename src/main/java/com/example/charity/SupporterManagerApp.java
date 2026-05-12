//import javafx.application.Application;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.sql.*;
//
//public class SupporterManagerApp extends Application {
//
//    // Database Connection Details (Update these with your MySQL credentials)
//
//    // UI Components
//    private TableView<Supporter> table = new TableView<>();
//    private ObservableList<Supporter> supporterData = FXCollections.observableArrayList();
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Funding Management - Supporters");
//
//        // 1. Setup Table Columns
//        TableColumn<Supporter, Number> idCol = new TableColumn<>("ID");
//        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
//
//        TableColumn<Supporter, String> nameCol = new TableColumn<>("Full Name");
//        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//        nameCol.setMinWidth(200);
//
//        TableColumn<Supporter, String> emailCol = new TableColumn<>("Email");
//        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
//        emailCol.setMinWidth(200);
//
//        table.getColumns().addAll(idCol, nameCol, emailCol);
//        table.setItems(supporterData);
//
//        // 2. Setup Input Form
//        TextField nameInput = new TextField();
//        nameInput.setPromptText("Full Name");
//        TextField emailInput = new TextField();
//        emailInput.setPromptText("Email Address");
//
//        Button addButton = new Button("Add Supporter");
//        addButton.setOnAction(e -> {
//            addSupporter(nameInput.getText(), emailInput.getText());
//            nameInput.clear();
//            emailInput.clear();
//        });
//
//        HBox formLayout = new HBox(10);
//        formLayout.setPadding(new Insets(10, 0, 0, 0));
//        formLayout.getChildren().addAll(nameInput, emailInput, addButton);
//
//        // 3. Main Layout
//        VBox mainLayout = new VBox(10);
//        mainLayout.setPadding(new Insets(10));
//        mainLayout.getChildren().addAll(new Label("Supporter Management"), table, formLayout);
//
//        // Load initial data from the database
//        loadSupporters();
//
//        Scene scene = new Scene(mainLayout, 600, 400);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    // --- JDBC DATABASE METHODS ---
//
//
//    private void loadSupporters() {
//        supporterData.clear();
//        String query = "SELECT supporter_ID, full_name, email FROM Supporter";
//
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            while (rs.next()) {
//                supporterData.add(new Supporter(
//                        rs.getInt("supporter_ID"),
//                        rs.getString("full_name"),
//                        rs.getString("email")
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error loading data: " + e.getMessage());
//        }
//    }
//
//    private void addSupporter(String fullName, String email) {
//        if (fullName.isEmpty() || email.isEmpty()) return;
//
//        // Using PreparedStatement to prevent SQL injection and handle strings safely
//        String sql = "INSERT INTO Supporter (full_name, email) VALUES (?, ?)";
//
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, fullName);
//            pstmt.setString(2, email);
//            pstmt.executeUpdate();
//
//            // Refresh the table to show the new record
//            loadSupporters();
//
//        } catch (SQLException e) {
//            System.err.println("Error inserting data: " + e.getMessage());
//        }
//    }
//
//    // --- DATA MODEL CLASS ---
//
//    public static class Supporter {
//        private final SimpleIntegerProperty id;
//        private final SimpleStringProperty fullName;
//        private final SimpleStringProperty email;
//
//        public Supporter(int id, String fullName, String email) {
//            this.id = new SimpleIntegerProperty(id);
//            this.fullName = new SimpleStringProperty(fullName);
//            this.email = new SimpleStringProperty(email);
//        }
//
//        public SimpleIntegerProperty idProperty() { return id; }
//        public SimpleStringProperty nameProperty() { return fullName; }
//        public SimpleStringProperty emailProperty() { return email; }
//    }
//}