package Controllers;

import Entite.Etat;
import Entite.Maintenance;
import Services.MaintenanceService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ModifyMaintenanceController {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox<Etat> etatComboBox; // ComboBox for Etat
    @FXML
    private Button saveButton;

    private Maintenance currentMaintenance;

    // Method to set the data from the selected maintenance
    public void setMaintenanceData(Maintenance maintenance) {
        this.currentMaintenance = maintenance;

        // Populate the fields with the current maintenance data (excluding id)
        descriptionField.setText(maintenance.getDescription());
        dateField.setText(String.valueOf(maintenance.getDate())); // Adjust if you need a specific format

        // Set the 'etat' ComboBox with available Etat values
        etatComboBox.getItems().setAll(Etat.values());  // Add all Etat values to the ComboBox
        etatComboBox.setValue(maintenance.getEtat());    // Set the current Etat to the selected value
    }

    @FXML
    private void onSaveButtonClick() {
        if (currentMaintenance == null) {
            showErrorAlert("No Maintenance", "No maintenance selected for modification.");
            return;  // Stop further execution if no maintenance is set
        }

        // Validate description and date
        String description = descriptionField.getText();
        if (description.isEmpty()) {
            showErrorAlert("Invalid Description", "Please enter a valid description.");
            return;
        }

        // Validate etat
        Etat etat = etatComboBox.getValue();  // Get selected Etat
        if (etat == null) {
            showErrorAlert("Invalid Etat", "Please select a valid Etat.");
            return;
        }

        // Set the modified data to the current maintenance
        currentMaintenance.setDescription(description);
        currentMaintenance.setEtat(etat);

        // Save the updated data (this could involve updating a database or list)
        saveUpdatedMaintenance(currentMaintenance);

        // Close the modify window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveUpdatedMaintenance(Maintenance maintenance) {
        // Create an instance of ServiceMaintenance and call update
        MaintenanceService serviceMaintenance = new MaintenanceService();

        // Prepare the update data (map could be optional if not needed)
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("Description", maintenance.getDescription());
        updateData.put("Etat", maintenance.getEtat());
        // Add other fields if needed like Date or others

        serviceMaintenance.update(maintenance, updateData);  // Now it's called on the instance

        System.out.println("Maintenance saved: " + maintenance);
    }
}
