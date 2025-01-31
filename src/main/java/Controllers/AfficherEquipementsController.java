package Controllers;

import Entite.Equipment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AfficherEquipementsController {

    @FXML
    private TableView<Equipment> equipmentTable;

    @FXML
    private TableColumn<Equipment, Integer> colID;
    @FXML
    private TableColumn<Equipment, String> colName;
    @FXML
    private TableColumn<Equipment, String> colCategory;
    @FXML
    private TableColumn<Equipment, Integer> colQuantity;
    @FXML
    private TableColumn<Equipment, String> colPurchaseDate;
    @FXML
    private TableColumn<Equipment, String> colMaintenanceDate;
    @FXML
    private TableColumn<Equipment, String> colState;

    private ObservableList<Equipment> equipmentList;

    // Informations de connexion à la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/projet_gym";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public void initialize() {
        // Initialisation des colonnes avec leurs propriétés correspondantes
        colID.setCellValueFactory(new PropertyValueFactory<>("equipementID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("equipementName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("achatDate"));
        colMaintenanceDate.setCellValueFactory(new PropertyValueFactory<>("lastMaintenanceDate"));
        colState.setCellValueFactory(new PropertyValueFactory<>("etat"));

        // Charger les données depuis la base de données
        loadInventoryData();
    }

    private void loadInventoryData() {
        equipmentList = FXCollections.observableArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formateur pour convertir la chaîne en Date

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM equipment"; // Requête pour récupérer tous les équipements
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int equipementID = rs.getInt("equipementID");
                String equipementName = rs.getString("equipementName");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                String achatDateStr = rs.getString("achatDate");
                String lastMaintenanceDateStr = rs.getString("lastMaintenanceDate");
                String etat = rs.getString("etat");

                // Convertir les chaînes en objets Date
                Date achatDate = dateFormat.parse(achatDateStr);
                Date lastMaintenanceDate = dateFormat.parse(lastMaintenanceDateStr);

                // Créer un objet Equipment pour chaque ligne de la base de données
                Equipment equipment = new Equipment(equipementID, equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);
                equipmentList.add(equipment);
            }

            // Ajouter les données à la table
            equipmentTable.setItems(equipmentList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
