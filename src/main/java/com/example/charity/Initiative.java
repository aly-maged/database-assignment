package com.example.charity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;



public class Initiative {
    private final SimpleIntegerProperty InitiativeId;
    private final SimpleIntegerProperty sectorId;
    private final SimpleDoubleProperty fundingTarget;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty endDate;
    private final SimpleStringProperty primaryObjective;

    public Initiative(int id, int sectorId, double fundingTarget, String startDate, String endDate, String primaryObjective) {
        this.InitiativeId = new SimpleIntegerProperty(id);
        this.sectorId = new SimpleIntegerProperty(sectorId);
        this.fundingTarget = new SimpleDoubleProperty(fundingTarget);
        this.startDate=new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.primaryObjective = new SimpleStringProperty(primaryObjective);
    }

    public SimpleIntegerProperty idPropery() { return InitiativeId; }
    public SimpleIntegerProperty sectoIdProperty() { return sectorId; }
    public SimpleDoubleProperty fundingTargetProperty() { return fundingTarget; }
    public SimpleStringProperty startDate() { return startDate; }
    public SimpleStringProperty endDate() { return endDate; }
    public SimpleStringProperty primaryObjectiveProperty() { return primaryObjective; }


}
