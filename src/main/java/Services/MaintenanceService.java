package Services;

import Entite.Maintenance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceService {

    private Connection connection;

    // Constructor to initialize the database connection
    public MaintenanceService(Connection connection) {
        this.connection = connection;
    }

    // Add a new maintenance record
    public boolean addMaintenance(Maintenance maintenance) {
        String query = "INSERT INTO maintenance (EquipementId, MaintenanceDate) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maintenance.getEquipementId());
            stmt.setDate(2, maintenance.getMaintenanceDate());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all maintenance records
    public List<Maintenance> getAllMaintenances() {
        List<Maintenance> maintenances = new ArrayList<>();
        String query = "SELECT * FROM maintenance";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int maintenanceId = rs.getInt("MaintenanceId");
                int equipementId = rs.getInt("EquipementId");
                Date maintenanceDate = rs.getDate("MaintenanceDate");
                maintenances.add(new Maintenance(maintenanceId, equipementId, maintenanceDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintenances;
    }

    // Get maintenance by id
    public Maintenance getMaintenanceById(int maintenanceId) {
        String query = "SELECT * FROM maintenance WHERE MaintenanceId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maintenanceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int equipementId = rs.getInt("EquipementId");
                    Date maintenanceDate = rs.getDate("MaintenanceDate");
                    return new Maintenance(maintenanceId, equipementId, maintenanceDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete maintenance record by id
    public boolean deleteMaintenance(int maintenanceId) {
        String query = "DELETE FROM maintenance WHERE MaintenanceId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maintenanceId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
