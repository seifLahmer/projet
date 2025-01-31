package Controllers;

import Entite.Equipment;
import Services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EquipmentController {

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
    private TableColumn<Equipment, Date> colAchatDate;

    @FXML
    private TableColumn<Equipment, Date> colMaintenanceDate;

    @FXML
    private TableColumn<Equipment, String> colEtat;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtQuantity;

    @FXML
    private DatePicker dateAchat;

    @FXML
    private DatePicker dateMaintenance;

    @FXML
    private TextField txtEtat;

    private final ServiceEquipement serviceEquipement = new ServiceEquipement();

    private ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        initializeTableColumns();
        loadEquipmentData();
    }

    private void initializeTableColumns() {
        colID.setCellValueFactory(new PropertyValueFactory<>("equipementID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("equipementName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAchatDate.setCellValueFactory(new PropertyValueFactory<>("achatDate"));
        colMaintenanceDate.setCellValueFactory(new PropertyValueFactory<>("lastMaintenanceDate"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        equipmentTable.setItems(equipmentList);
    }

    private void loadEquipmentData() {
        try {
            List<Equipment> equipmentFromDB = serviceEquipement.getAll();
            equipmentList.setAll(equipmentFromDB);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger les données de l'équipement : " + e.getMessage());
        }
    }

    @FXML
    private void handleAddEquipment(ActionEvent event) {
        if (!validateInputs()) return;

        try {
            Equipment newEquipment = new Equipment(
                    0, // ID is auto-incremented by the database
                    txtName.getText(),
                    txtCategory.getText(),
                    Integer.parseInt(txtQuantity.getText()),
                    java.sql.Date.valueOf(dateAchat.getValue()),
                    java.sql.Date.valueOf(dateMaintenance.getValue()),
                    txtEtat.getText()
            );

            serviceEquipement.ajouter(newEquipment);
            equipmentList.add(newEquipment);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'équipement a été ajouté avec succès !");
            clearInputs();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'ajout", "Impossible d'ajouter l'équipement : " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteEquipment(ActionEvent event) {
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (selectedEquipment == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun équipement sélectionné", "Veuillez sélectionner un équipement à supprimer.");
            return;
        }

        try {
            serviceEquipement.supprimer(selectedEquipment);
            equipmentList.remove(selectedEquipment);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'équipement a été supprimé avec succès !");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "Impossible de supprimer l'équipement : " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        if (txtName.getText().isEmpty() || txtCategory.getText().isEmpty() || txtQuantity.getText().isEmpty() ||
                dateAchat.getValue() == null || dateMaintenance.getValue() == null || txtEtat.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs manquants", "Veuillez remplir tous les champs avant de continuer.");
            return false;
        }

        try {
            int quantity = Integer.parseInt(txtQuantity.getText());
            if (quantity <= 0) {
                showAlert(Alert.AlertType.ERROR, "Valeur invalide", "La quantité doit être un nombre positif.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de format", "Veuillez entrer une quantité valide (numérique).");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputs() {
        txtName.clear();
        txtCategory.clear();
        txtQuantity.clear();
        dateAchat.setValue(null);
        dateMaintenance.setValue(null);
        txtEtat.clear();
    }

    public void handleAdd(ActionEvent actionEvent) {
    }
}
