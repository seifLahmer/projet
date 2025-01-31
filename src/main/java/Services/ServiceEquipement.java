package Services;

import Entite.Equipment;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceEquipement implements IService<Equipment> {

    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat = null;

    public ServiceEquipement() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Equipment equipment) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("INSERT INTO equipement (equipementName, category, quantity, achatDate, lastMaintenanceDate, etat) VALUES (?,?,?,?,?,?)");
        pre.setString(1, equipment.getEquipementName());
        pre.setString(2, equipment.getCategory());
        pre.setInt(3, equipment.getQuantity());
        pre.setDate(4, new java.sql.Date(equipment.getAchatDate().getTime()));
        pre.setDate(5, new java.sql.Date(equipment.getLastMaintenanceDate().getTime()));
        pre.setString(6, equipment.getEtat());

        pre.executeUpdate();
        System.out.println("Equipment added successfully!");
    }

    @Override
    public void supprimer(Equipment equipment) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM equipement WHERE equipementID = ?");
        pre.setInt(1, equipment.getEquipementID());
        pre.executeUpdate();
        System.out.println("Equipment deleted successfully!");
    }

    @Override
    public void update(Equipment equipment, Map<String, Object> data) throws SQLException {
        // Construct the SQL query
        String query = "UPDATE equipement SET ";
        query += String.join(" = ?, ", data.keySet()) + " = ? WHERE equipementID = ?";

        // Prepare the query
        PreparedStatement pre = conn.prepareStatement(query);

        // Add values to the PreparedStatement
        int index = 1;
        for (Object value : data.values()) {
            pre.setObject(index++, value); // setObject simplifies type management
        }

        // Add the equipment ID
        pre.setInt(index, equipment.getEquipementID());

        // Execute the update
        pre.executeUpdate();
        System.out.println("Equipment updated successfully!");
    }

    @Override
    public List<Equipment> getAll() throws SQLException {
        List<Equipment> list = new ArrayList<>();

        ResultSet reset = stat.executeQuery("SELECT * FROM equipement");
        while (reset.next()) {
            int equipementID = reset.getInt("equipementID");
            String equipementName = reset.getString("equipementName");
            String category = reset.getString("category");
            int quantity = reset.getInt("quantity");
            Date achatDate = reset.getDate("achatDate");
            Date lastMaintenanceDate = reset.getDate("lastMaintenanceDate");
            String etat = reset.getString("etat");

            Equipment e = new Equipment(equipementID, equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);
            list.add(e);
        }
        return list;
    }

    @Override
    public Equipment getById(int id) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM equipement WHERE equipementID = ?");
        pre.setInt(1, id);
        ResultSet reset = pre.executeQuery();

        Equipment e = null; // Initialize the Equipment variable

        if (reset.next()) {
            int equipementID = reset.getInt("equipementID");
            String equipementName = reset.getString("equipementName");
            String category = reset.getString("category");
            int quantity = reset.getInt("quantity");
            Date achatDate = reset.getDate("achatDate");
            Date lastMaintenanceDate = reset.getDate("lastMaintenanceDate");
            String etat = reset.getString("etat");

            e = new Equipment(equipementID, equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);
        } else {
            System.out.println("No equipment found with ID: " + id);
        }

        reset.close(); // Close the ResultSet
        pre.close();   // Close the PreparedStatement

        return e; // Return the Equipment object or null if not found
    }
}
