package com.example.charity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supporter {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty email;

    public Supporter(int id, String fullName, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
    }

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nameProperty() { return fullName; }
    public SimpleStringProperty emailProperty() { return email; }
    public void setFullName(String fullName) {this.fullName.set(fullName);}
    public void setEmail(String email) {this.email.set(email);}
}

