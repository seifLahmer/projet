package Controllers;
import Entite.Maintenance;
import Services.MaintenanceService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ModifyMaintenanceController {

    @FXML private TextField descriptionField;
    @FXML private TextField dateField;
    @FXML private TextField coutField;
    @FXML private TextField effectueParField;

    private Maintenance selectedMaintenance;

    // Method to initialize the view with the selected maintenance data
    public void initialize() {
        if (selectedMaintenance != null) {
            // Populate the fields with the selected maintenance data
            descriptionField.setText(selectedMaintenance.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateField.setText(sdf.format(selectedMaintenance.getMaintenanceDate()));  // Format the date
            coutField.setText(String.valueOf(selectedMaintenance.getCout()));
            effectueParField.setText(selectedMaintenance.getEffectuePar());
        }
    }

    // Method to set the selected maintenance object
    public void setSelectedMaintenance(Maintenance maintenance) {
        this.selectedMaintenance = maintenance;
    }

    // Save button action
    @FXML
    private void onSaveButtonClick() {
        if (selectedMaintenance == null) {
            showError("No maintenance selected for modification.");
            return;
        }

        // Get the data from the input fields
        String description = descriptionField.getText();
        String date = dateField.getText();
        String costText = coutField.getText();
        String technician = effectueParField.getText();

        // Validate inputs
        if (description.isEmpty() || date.isEmpty() || costText.isEmpty() || technician.isEmpty()) {
            showError("All fields must be filled.");
            return;
        }

        try {
            // Parse the cost value
            double cost = Double.parseDouble(costText);

            // Convert the date string to a Date object
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(date);

            // Update the maintenance record
            selectedMaintenance.setDescription(description);
            selectedMaintenance.setMaintenanceDate(parsedDate);
            selectedMaintenance.setCout(cost);
            selectedMaintenance.setEffectuePar(technician);

            // Create a map to pass to the update method (if needed)
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("maintenance", selectedMaintenance);

            // Create an instance of MaintenanceService and call the update method
            MaintenanceService maintenanceService = new MaintenanceService();
            maintenanceService.update(selectedMaintenance, updateData); // Assuming your update method takes the selectedMaintenance and a Map

            // Show success message
            showSuccess("Maintenance record updated successfully.");
        } catch (Exception e) {
            showError("An error occurred while saving the data: " + e.getMessage());
        }
    }

    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success messages
    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
