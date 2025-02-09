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

    // Add new maintenance record
    public void ajouterMaintenance(Maintenance maintenance) {
        String query = "INSERT INTO maintenance (EquipementID, MaintenanceDate, Description, Cout, EffectuePar) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, maintenance.getEquipementId());
            ps.setDate(2, new java.sql.Date(maintenance.getMaintenanceDate().getTime()));
            ps.setString(3, maintenance.getDescription());
            ps.setDouble(4, maintenance.getCout());
            ps.setString(5, maintenance.getEffectuePar());

            ps.executeUpdate();
            System.out.println("Maintenance added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all maintenance records
    public ObservableList<Maintenance> getAll() {
        ObservableList<Maintenance> maintenances = FXCollections.observableArrayList();
        String query = "SELECT * FROM maintenance";

        try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Retrieve additional data from ResultSet
                double cout = rs.getDouble("Cout");
                String effectuePar = rs.getString("EffectuePar");

                Maintenance maintenance = new Maintenance(
                        rs.getInt("MaintenanceID"),
                        rs.getInt("EquipementID"),
                        rs.getDate("MaintenanceDate"),
                        rs.getString("Description"),
                        cout, // Pass the 'cout'
                        effectuePar // Pass the 'effectuePar'
                );
                maintenances.add(maintenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintenances;
    }

    // Get maintenance by ID
    public Maintenance getById(int maintenanceID) {
        String query = "SELECT * FROM maintenance WHERE MaintenanceID = ?";
        Maintenance maintenance = null;

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, maintenanceID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double cout = rs.getDouble("Cout");
                    String effectuePar = rs.getString("EffectuePar");

                    maintenance = new Maintenance(
                            rs.getInt("MaintenanceID"),
                            rs.getInt("EquipementID"),
                            rs.getDate("MaintenanceDate"),
                            rs.getString("Description"),
                            cout, // Pass the 'cout'
                            effectuePar // Pass the 'effectuePar'
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintenance;
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

    // Delete maintenance record
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
}
