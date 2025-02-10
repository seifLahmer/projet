package Services;

import Entite.Maintenance;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Map;

public class MaintenanceService {

    private Connection con;

    public MaintenanceService() {
        con = DataSource.getInstance().getCon();
    }
    // Get all maintenance records from the database
    public ObservableList<Maintenance> getAll() {
        ObservableList<Maintenance> maintenances = FXCollections.observableArrayList();
        String query = "SELECT * FROM maintenance";

        try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Maintenance maintenance = new Maintenance(
                        rs.getInt("MaintenanceID"),
                        rs.getInt("EquipementID"),
                        rs.getDate("MaintenanceDate"),
                        rs.getString("Description"),
                        rs.getDouble("Cout"),
                        rs.getString("EffectuePar")
                );
                maintenances.add(maintenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintenances;
    }

    // Delete a maintenance record from the database
    public void delete(Maintenance maintenance) {
        String query = "DELETE FROM maintenance WHERE MaintenanceID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, maintenance.getMaintenanceID());
            ps.executeUpdate();
            System.out.println("Maintenance deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update maintenance details
    public void update(Maintenance newMaintenance, Map<String, Object> updateData) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE maintenance SET ");
        for (String key : updateData.keySet()) {
            queryBuilder.append(key).append(" = ?, ");
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Remove the trailing comma
        queryBuilder.append(" WHERE MaintenanceID = ?");

        try (PreparedStatement ps = con.prepareStatement(queryBuilder.toString())) {
            int index = 1;
            for (Object value : updateData.values()) {
                if (value instanceof String) {
                    ps.setString(index++, (String) value);
                } else if (value instanceof Date) {
                    ps.setDate(index++, (Date) value);
                } else if (value instanceof Double) {
                    ps.setDouble(index++, (Double) value);
                }
            }
            ps.setInt(index, newMaintenance.getMaintenanceID()); // Set the ID for the WHERE clause

            ps.executeUpdate();
            System.out.println("Maintenance updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods...
}
