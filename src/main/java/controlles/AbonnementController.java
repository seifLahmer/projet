package controlles;

import Entite.Abonnement;
import Services.ServiceAbonnement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AbonnementController {

    @FXML private Button PayerButton;
    @FXML private TextField abonnementIdField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TextField memberIdField;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private TableView<Abonnement> abonnementTable;
    @FXML private TableColumn<Abonnement, Integer> abonnementIdColumn;
    @FXML private TableColumn<Abonnement, LocalDate> startDateColumn;
    @FXML private TableColumn<Abonnement, LocalDate> endDateColumn;
    @FXML private TableColumn<Abonnement, Double> priceColumn;
    @FXML private TableColumn<Abonnement, String> statusColumn;
    @FXML private TableColumn<Abonnement, Integer> memberIdColumn;

    @FXML
    public void initialize() {
        // Initialize the ComboBox
        statusComboBox.getItems().addAll("Active", "Inactive", "Pending");
        
        // Configure table columns
        abonnementIdColumn.setCellValueFactory(new PropertyValueFactory<>("abonnementId"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        
        try {
            loadAbonnements();
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des abonnements: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadAbonnements() throws SQLException {
        List<Abonnement> abonnements = ServiceAbonnement.getAll();
        abonnementTable.getItems().setAll(abonnements);
    }

    @FXML
    private void handleAddAbonnement() {
        try {
            int abonnementId = Integer.parseInt(abonnementIdField.getText());
            LocalDate startLocalDate = startDatePicker.getValue();
            LocalDate endLocalDate = endDatePicker.getValue();
            double price = Double.parseDouble(priceField.getText());
            String status = statusComboBox.getValue();
            int memberId = Integer.parseInt(memberIdField.getText());

            Abonnement abonnement = new Abonnement(abonnementId, startLocalDate, endLocalDate, price, status, memberId);
            ServiceAbonnement.ajouter(abonnement);
            loadAbonnements();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez vérifier les données saisies.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'abonnement.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateAbonnement() {
        Abonnement selectedAbonnement = abonnementTable.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            try {
                int abonnementId = Integer.parseInt(abonnementIdField.getText());
                LocalDate startLocalDate = startDatePicker.getValue();
                LocalDate endLocalDate = endDatePicker.getValue();
                double price = Double.parseDouble(priceField.getText());
                String status = statusComboBox.getValue();
                int memberId = Integer.parseInt(memberIdField.getText());

                Abonnement updatedAbonnement = new Abonnement(abonnementId, startLocalDate, endLocalDate, price, status, memberId);
                ServiceAbonnement.update(updatedAbonnement);
                loadAbonnements();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Veuillez vérifier les données saisies.", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour de l'abonnement.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un abonnement à mettre à jour.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleDeleteAbonnement() {
        Abonnement selectedAbonnement = abonnementTable.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            try {
                ServiceAbonnement.supprimer(selectedAbonnement);
                loadAbonnements();
                clearFields();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression de l'abonnement.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un abonnement à supprimer.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleNavigate() {
        try {
            // Change the resource path to include the leading slash
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Layout.fxml"));
            Parent root = loader.load();

            // Get the current scene
            Scene currentScene = PayerButton.getScene();

            // Change to the new scene
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Payment Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Add this for debugging
            showAlert("Erreur", "Impossible de charger l'interface de paiement: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        abonnementIdField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        priceField.clear();
        statusComboBox.getSelectionModel().clearSelection();
        memberIdField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}