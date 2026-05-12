package com.example.charity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contribution {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty time;
    private final SimpleDoubleProperty amountGifted;
    private final SimpleStringProperty primaryObjective;

    Contribution(int id, String fullName, String time, double amountGifted, String primaryObjective) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.time = new SimpleStringProperty(time);
        this.amountGifted = new SimpleDoubleProperty(amountGifted);
        this.primaryObjective = new SimpleStringProperty(primaryObjective);
    }
    public SimpleIntegerProperty getId() {return id;}
    public SimpleStringProperty getFullName() {return fullName;}
    public SimpleStringProperty getTime() {return time;}
    public SimpleDoubleProperty getAmountGifted() {return amountGifted;}
    public SimpleStringProperty getPrimaryObjective() {return primaryObjective;}


}
