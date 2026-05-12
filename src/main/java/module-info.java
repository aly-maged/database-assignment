module com.example.charity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.charity to javafx.fxml;
    exports com.example.charity;
}