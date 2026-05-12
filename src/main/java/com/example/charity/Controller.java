package com.example.charity;


import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Controller {
    DB db = new DB();

    public void loadSupporters(ObservableList<Supporter> supporterData) {
        supporterData.clear();
        supporterData = db.loadSupporters(supporterData);
    }
    public void addSupporter(String fullName, String email, ObservableList<Supporter> supporterData) {
        if (fullName.isEmpty() || email.isEmpty()) return;
        db.addSupporter(fullName, email);
    }

    public void deleteSupporter(int id) {
        db.deleteSupporter(id);
    }

    public void updateSupporter(int id, String fullName, String email) {
        db.updateSupporter(id, fullName, email);
    }
    public void addInitiative(String sectorId, String target, LocalDate start, LocalDate end, String objective, ObservableList<Initiative> initiativeData) {
        db.addInitiative(sectorId, target, start, end, objective);
    }


    public void loadInitiatives(ObservableList<Initiative> initiativeData) {
        initiativeData.clear();
        initiativeData=db.loadInitiatives(initiativeData);
    }
    public void updateInitiative(int id, String newTarget, LocalDate newStart, LocalDate newEnd, String newObjective)  {
        db.updateInitiative(id, newTarget, newStart, newEnd, newObjective);
    }
    public void deleteInitiative(int id) {
        db.deleteInitiative(id);
    }
}