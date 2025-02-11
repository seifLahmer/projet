package Controllers;

import Entite.Maintenance;
import Services.MaintenanceService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.sql.Date;

public class AjouterMaintenanceController {

    @FXML
    private TextField equipementIdField;

    @FXML
    private DatePicker maintenanceDatePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField coutField;

    @FXML
    private TextField effectueParField;

    @FXML
    private VBox root;

    private MaintenanceService maintenanceService;

    public AjouterMaintenanceController() {
        this.maintenanceService = new MaintenanceService();
    }

    @FXML
    public void handleAddMaintenance() {
        try {
            // Récupérer les données des champs
            int equipementId = Integer.parseInt(equipementIdField.getText());
            LocalDate maintenanceDate = maintenanceDatePicker.getValue();
            String description = descriptionField.getText();
            double cout = Double.parseDouble(coutField.getText());
            String effectuePar = effectueParField.getText();

            // Validation des données
            if (description.isEmpty() || effectuePar.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Données invalides", "Veuillez remplir tous les champs.");
                return;
            }

            // Créer un nouvel objet Maintenance
            Maintenance newMaintenance = new Maintenance(
                    0, // L'ID sera généré automatiquement
                    equipementId,
                    Date.valueOf(maintenanceDate),
                    description,
                    cout,
                    effectuePar
            );

            // Ajouter la maintenance dans la base de données
            maintenanceService.add(newMaintenance);

            // Confirmation
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Maintenance ajoutée avec succès.");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs valides pour l'ID Équipement et le coût.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout de la maintenance.");
        }
    }

    private void clearFields() {
        equipementIdField.clear();
        maintenanceDatePicker.setValue(null);
        descriptionField.clear();
        coutField.clear();
        effectueParField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
