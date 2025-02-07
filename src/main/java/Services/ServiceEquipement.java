package Services;

import Entite.Equipment;
import Entite.Etat;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Map;

public class ServiceEquipement {

    private Connection con;

    public ServiceEquipement() {
        con = DataSource.getInstance().getCon();
    }

    // Add new equipment
    public void ajouterEquipement(Equipment equipment) {
        String query = "INSERT INTO equipement (EquipementID, EquipementName, Category, Quantity, AchatDate, LastMaintenanceDate, etat) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, equipment.getEquipementID());
            ps.setString(2, equipment.getEquipementName());
            ps.setString(3, equipment.getCategory());
            ps.setInt(4, equipment.getQuantity());
            ps.setDate(5, new java.sql.Date(equipment.getAchatDate().getTime()));
            ps.setDate(6, new java.sql.Date(equipment.getLastMaintenanceDate().getTime()));
            ps.setString(7, equipment.getEtat().name()); // Store the enum value as string

            ps.executeUpdate();
            System.out.println("Equipement ajoute avec succes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all equipment
    public ObservableList<Equipment> getAll() {
        ObservableList<Equipment> equipments = FXCollections.observableArrayList(); // Use ObservableList
        String query = "SELECT * FROM equipement";

        try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Equipment equipment = new Equipment(
                        rs.getInt("EquipementID"),
                        rs.getString("EquipementName"),
                        rs.getString("Category"),
                        rs.getInt("Quantity"),
                        rs.getDate("AchatDate"),
                        rs.getDate("LastMaintenanceDate"),
                        Etat.valueOf(rs.getString("etat"))
                );
                equipments.add(equipment); // Add to ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipments; // Return the ObservableList
    }

    // Get equipment by ID
    public Equipment getById(int equipementID) {
        String query = "SELECT * FROM equipement WHERE EquipementID = ?";
        Equipment equipment = null;

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, equipementID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    equipment = new Equipment(
                            rs.getInt("EquipementID"),
                            rs.getString("EquipementName"),
                            rs.getString("Category"),
                            rs.getInt("Quantity"),
                            rs.getDate("AchatDate"),
                            rs.getDate("LastMaintenanceDate"),
                            Etat.valueOf(rs.getString("etat"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipment;
    }

    // Update equipment details
    public void update(Equipment newEquipment, Map<String, Object> updateData) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE equipement SET ");
        for (String key : updateData.keySet()) {
            queryBuilder.append(key).append(" = ?, ");
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Remove the trailing comma
        queryBuilder.append(" WHERE EquipementID = ?");

        try (PreparedStatement ps = con.prepareStatement(queryBuilder.toString())) {
            int index = 1;
            for (Object value : updateData.values()) {
                if (value instanceof String) {
                    ps.setString(index++, (String) value);
                } else if (value instanceof Integer) {
                    ps.setInt(index++, (Integer) value);
                } else if (value instanceof Date) {
                    ps.setDate(index++, (Date) value);
                } else if (value instanceof Etat) {
                    ps.setString(index++, ((Etat) value).name());
                }
            }
            ps.setInt(index, newEquipment.getEquipementID()); // Set the ID for the WHERE clause

            ps.executeUpdate();
            System.out.println("Equipement mis à jour avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete equipment
    public void supprimer(Equipment equipment) {
        String query = "DELETE FROM equipement WHERE EquipementID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, equipment.getEquipementID());
            ps.executeUpdate();
            System.out.println("Equipement supprimé avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other helper methods can be added as needed.
}
