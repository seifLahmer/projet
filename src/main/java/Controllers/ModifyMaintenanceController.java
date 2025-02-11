package Controllers;

import Entite.Maintenance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import Services.MaintenanceService;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ModifyMaintenanceController {

    @FXML private TextField descriptionField;
    @FXML private TextField coutField;
    @FXML private TextField effectueParField;
    @FXML private DatePicker maintenanceDateField;

    private Maintenance selectedMaintenance;

    // Initialize view with the selected maintenance data
    @FXML
    public void initialize() {
        if (selectedMaintenance != null) {
            descriptionField.setText(selectedMaintenance.getDescription());

            // Convert Date to LocalDate without using toLocalDate()
            if (selectedMaintenance.getMaintenanceDate() != null) {
                maintenanceDateField.setValue(
                        Instant.ofEpochMilli(selectedMaintenance.getMaintenanceDate().getTime())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                );
            }

            coutField.setText(String.valueOf(selectedMaintenance.getCout()));
            effectueParField.setText(selectedMaintenance.getEffectuePar());
        }
    }


    // Set the selected maintenance object
    public void setSelectedMaintenance(Maintenance maintenance) {
        this.selectedMaintenance = maintenance;
    }

    // Save button action
    @FXML
    public void onSaveButtonClick() {
        if (selectedMaintenance != null) {
            // Update the fields of selectedMaintenance
            selectedMaintenance.setDescription(descriptionField.getText());
            selectedMaintenance.setCout(Double.parseDouble(coutField.getText()));
            selectedMaintenance.setEffectuePar(effectueParField.getText());
            selectedMaintenance.setMaintenanceDate(java.sql.Date.valueOf(maintenanceDateField.getValue()));

            // Prepare additional update data
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("updatedBy", "Admin"); // Example key-value pair
            updateData.put("updateTimestamp", System.currentTimeMillis()); // Example: Current timestamp
            MaintenanceService MaintenanceService = new MaintenanceService();
            // Call the update method
            MaintenanceService.update(selectedMaintenance, updateData);

            // Notify user or refresh the UI
            System.out.println("Maintenance updated successfully!");
        } else {
            System.out.println("No maintenance selected for update.");
        }
    }



    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success messages
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
