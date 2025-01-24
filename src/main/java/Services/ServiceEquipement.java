package Services;

import Entite.Equipment;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipement {
    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat = null;

    public ServiceEquipement() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Add a new equipment
    public void ajouter(Equipment equipement) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("INSERT INTO equipement (EquipementName, Category, Quantity, AchatDate, LastMaintenanceDate, etat) VALUES (?, ?, ?, ?, ?, ?)");
        pre.setString(1, equipement.getEquipementName());
        pre.setString(2, equipement.getCategory());
        pre.setInt(3, equipement.getQuantity());
        pre.setDate(4, new java.sql.Date(equipement.getAchatDate().getTime()));
        pre.setDate(5, new java.sql.Date(equipement.getLastMaintenanceDate().getTime()));
        pre.setString(6, equipement.getEtat());
        pre.executeUpdate();
        System.out.println("Équipement ajouté");
    }

    // Get all equipment
    public List<Equipment> getAll() throws SQLException {
        List<Equipment> list = new ArrayList<>();
        ResultSet reset = stat.executeQuery("SELECT * FROM equipement");
        while (reset.next()) {
            int equipementID = reset.getInt("EquipementID");
            String equipementName = reset.getString("EquipementName");
            String category = reset.getString("Category");
            int quantity = reset.getInt("Quantity");
            Date achatDate = reset.getDate("AchatDate");
            Date lastMaintenanceDate = reset.getDate("LastMaintenanceDate");
            String etat = reset.getString("etat");

            Equipment equipement = new Equipment(equipementID, equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);
            list.add(equipement);
        }
        return list;
    }

    // Get equipment by ID
    public Equipment getById(int id) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM equipement WHERE EquipementID = ?");
        pre.setInt(1, id);
        ResultSet reset = pre.executeQuery();

        if (reset.next()) {
            int equipementID = reset.getInt("EquipementID");
            String equipementName = reset.getString("EquipementName");
            String category = reset.getString("Category");
            int quantity = reset.getInt("Quantity");
            Date achatDate = reset.getDate("AchatDate");
            Date lastMaintenanceDate = reset.getDate("LastMaintenanceDate");
            String etat = reset.getString("etat");

            return new Equipment(equipementID, equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);
        } else {
            return null;
        }
    }

    // Update equipment
    public void update(Equipment equipement) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("UPDATE equipement SET EquipementName = ?, Category = ?, Quantity = ?, AchatDate = ?, LastMaintenanceDate = ?, etat = ? WHERE EquipementID = ?");
        pre.setString(1, equipement.getEquipementName());
        pre.setString(2, equipement.getCategory());
        pre.setInt(3, equipement.getQuantity());
        pre.setDate(4, new java.sql.Date(equipement.getAchatDate().getTime()));
        pre.setDate(5, new java.sql.Date(equipement.getLastMaintenanceDate().getTime()));
        pre.setString(6, equipement.getEtat());
        pre.setInt(7, equipement.getEquipementID());
        pre.executeUpdate();
        System.out.println("Équipement mis à jour");
    }

    // Delete equipment
    public void supprimer(Equipment equipement) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM equipement WHERE EquipementID = ?");
        pre.setInt(1, equipement.getEquipementID());
        pre.executeUpdate();
        System.out.println("Équipement supprimé");
    }
}
