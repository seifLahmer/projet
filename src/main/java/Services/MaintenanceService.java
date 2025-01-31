package Services;

import Entite.Maintenance;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaintenanceService implements IService<Maintenance> {

    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat = null;

    public MaintenanceService() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Maintenance maintenance) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("INSERT INTO maintenance (EquipementId, MaintenanceDate) VALUES (?, ?)");
        pre.setInt(1, maintenance.getEquipementId());
        pre.setDate(2, maintenance.getMaintenanceDate());

        pre.executeUpdate();
        System.out.println("Maintenance added successfully!");
    }

    @Override
    public void supprimer(Maintenance maintenance) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM maintenance WHERE MaintenanceId = ?");
        pre.setInt(1, maintenance.getMaintenanceId());
        pre.executeUpdate();
        System.out.println("Maintenance deleted successfully!");
    }

    @Override
    public void update(Maintenance maintenance, Map<String, Object> data) throws SQLException {
        // Construct the SQL query
        String query = "UPDATE maintenance SET ";
        query += String.join(" = ?, ", data.keySet()) + " = ? WHERE MaintenanceId = ?";

        // Prepare the query
        PreparedStatement pre = conn.prepareStatement(query);

        // Add values to the PreparedStatement
        int index = 1;
        for (Object value : data.values()) {
            pre.setObject(index++, value); // setObject simplifies type management
        }

        // Add the MaintenanceId
        pre.setInt(index, maintenance.getMaintenanceId());

        // Execute the update
        pre.executeUpdate();
        System.out.println("Maintenance updated successfully!");
    }

    @Override
    public List<Maintenance> getAll() throws SQLException {
        List<Maintenance> list = new ArrayList<>();

        ResultSet reset = stat.executeQuery("SELECT * FROM maintenance");
        while (reset.next()) {
            int maintenanceId = reset.getInt("MaintenanceId");
            int equipementId = reset.getInt("EquipementId");
            Date maintenanceDate = reset.getDate("MaintenanceDate");

            Maintenance m = new Maintenance(maintenanceId, equipementId, maintenanceDate);
            list.add(m);
        }
        return list;
    }

    @Override
    public Maintenance getById(int id) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM maintenance WHERE MaintenanceId = ?");
        pre.setInt(1, id);
        ResultSet reset = pre.executeQuery();

        Maintenance m = null; // Initialize the Maintenance variable

        if (reset.next()) {
            int maintenanceId = reset.getInt("MaintenanceId");
            int equipementId = reset.getInt("EquipementId");
            Date maintenanceDate = reset.getDate("MaintenanceDate");

            m = new Maintenance(maintenanceId, equipementId, maintenanceDate);
        } else {
            System.out.println("No maintenance found with ID: " + id);
        }

        reset.close(); // Close the ResultSet
        pre.close();   // Close the PreparedStatement

        return m; // Return the Maintenance object or null if not found
    }
}
