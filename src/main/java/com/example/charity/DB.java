package com.example.charity;

import javafx.collections.ObservableList;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.*;

public class DB {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=AssignDatabase;encrypt=true;trustServerCertificate=true;user=sharaf;password=12345;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public ObservableList<Supporter> loadSupporters(ObservableList<Supporter> supporterData) {

        String query = "SELECT supporter_ID, full_name, email FROM Supporter";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                supporterData.add(new Supporter(
                        rs.getInt("supporter_ID"),
                        rs.getString("full_name"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return supporterData;
    }

    public void updateSupporter(int id, String fullName, String email) {

        String sql = "UPDATE Supporter SET full_name = ?, email = ? WHERE supporter_ID = ?";

        try (Connection conn = getConnection(); // Use your connection method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Supporter updated successfully in the database!");
            }

        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
    }

    public void addSupporter(String fullName, String email) {
        if (fullName.isEmpty() || email.isEmpty()) return;

        String sql = "INSERT INTO Supporter (full_name, email) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void deleteSupporter(int id) {
        String sql = "DELETE FROM Supporter WHERE supporter_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Supporter " + id + " successfully deleted from database.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting supporter: " + e.getMessage());
        }

    }



    public ObservableList<Initiative>  loadInitiatives(ObservableList<Initiative> initiativeData) {
        String sql = "SELECT initiative_ID, Sector_ID, funding_target, start_date, end_date, primary_objective FROM Initiative";

        try (Connection conn = getConnection(); // Use your connection class
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("initiative_ID");
                int sectorId = rs.getInt("Sector_ID");
                double target = rs.getDouble("funding_target");
                String objective = rs.getString("primary_objective");

                java.sql.Date sqlStartDate = rs.getDate("start_date");
                String startStr = (sqlStartDate != null) ? sqlStartDate.toString() : "No Date";
                java.sql.Date sqlEndDate = rs.getDate("end_date");
                String endStr = (sqlEndDate != null) ? sqlEndDate.toString() : "No Date";
                initiativeData.add(new Initiative(id, sectorId, target, startStr, endStr, objective));
            }
            System.out.println("Initiatives loaded successfully!");
        } catch (SQLException e) {
            System.out.println("Error loading Initiatives: " + e.getMessage());
        }
        return initiativeData;
    }

    public void updateInitiative(int id, String newTarget, LocalDate newStart, LocalDate newEnd, String newObjective) {

        String sql = "UPDATE Initiative SET funding_target = ?, start_date = ?, end_date = ?, primary_objective = ? WHERE initiative_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, Double.parseDouble(newTarget));

            pstmt.setDate(2, Date.valueOf(newStart));
            pstmt.setDate(3, Date.valueOf(newEnd));

            pstmt.setString(4, newObjective);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error updating Initiative: " + e.getMessage());
        }
    }

    public void addInitiative(String sectorId, String target, LocalDate start, LocalDate end, String objective) {

        String sql = "INSERT INTO Initiative (Sector_ID, funding_target, start_date, end_date, primary_objective) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(sectorId));
            pstmt.setDouble(2, Double.parseDouble(target));

            if (start != null) {
                pstmt.setDate(3, Date.valueOf(start));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }
            if (end != null) {
                pstmt.setDate(4, Date.valueOf(end));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setString(5, objective);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New Initiative successfully added to the database!");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error adding Initiative: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Format Error: Make sure Sector ID and Funding Target are valid numbers.");
        }
    }

    public void deleteInitiative(int id) {
        String sql = "DELETE FROM Initiative WHERE initiative_ID= ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Initaitive " + id + " successfully deleted from database.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting supporter: " + e.getMessage());
        }
    }


    public ObservableList<Contribution>loadContributions(ObservableList<Contribution> contributionData) {
        String sql = "SELECT id, full_name, amount_gifted, timestamp , primary_objective FROM ContributionView";

        try (Connection conn = getConnection(); // Use your connection class
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String full_name = rs.getString("full_name");
                double amountGifted = rs.getDouble("amount_gifted");
                String objective = rs.getString("primary_objective");

                java.sql.Date sqlStartDate = rs.getDate("timestamp");
                String timestr = (sqlStartDate != null) ? sqlStartDate.toString() : "No Date";

                contributionData.add(new Contribution(id,full_name , timestr, amountGifted, objective));
            }
            System.out.println("Contributions loaded successfully!");
        } catch (SQLException e) {
            System.out.println("Error loading Contribution: " + e.getMessage());
        }
        return contributionData;
    }
}
