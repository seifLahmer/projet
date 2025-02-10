package Controllers;

import Services.MaintenanceService;
import Entite.Maintenance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModifyMaintenanceController {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField coutField;
    @FXML
    private TextField effectueParField;
    @FXML
    private Button saveButton;

    private Maintenance currentMaintenance;

    // Method to set the data from the selected maintenance
    public void setMaintenanceData(Maintenance maintenance) {
        this.currentMaintenance = maintenance;

        // Populate the fields with the current maintenance data
        descriptionField.setText(maintenance.getDescription());
        dateField.setText(String.valueOf(maintenance.getMaintenanceDate())); // Adjust as needed
        coutField.setText(String.valueOf(maintenance.getCout()));
        effectueParField.setText(maintenance.getEffectuePar());
    }

    @FXML
    private void onSaveButtonClick() {
        if (currentMaintenance == null) {
            showErrorAlert("No Maintenance", "No maintenance selected for modification.");
            return;
        }

        // Validate fields
        String description = descriptionField.getText();
        String coutStr = coutField.getText();
        String effectuePar = effectueParField.getText();

        if (description.isEmpty() || coutStr.isEmpty() || effectuePar.isEmpty()) {
            showErrorAlert("Invalid Input", "All fields must be filled.");
            return;
        }

        // Convert cout to a double
        double cout;
        try {
            cout = Double.parseDouble(coutStr);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Cost", "Please enter a valid number for the cost.");
            return;
        }

        // Convert date to Date object (assuming simple date format "YYYY-MM-DD")
        Date maintenanceDate;
        try {
            maintenanceDate = java.sql.Date.valueOf(dateField.getText());  // Adjust the format as per your requirement
        } catch (IllegalArgumentException e) {
            showErrorAlert("Invalid Date", "Please enter a valid date (YYYY-MM-DD).");
            return;
        }

        // Update the maintenance object with new values
        currentMaintenance.setDescription(description);
        currentMaintenance.setMaintenanceDate(maintenanceDate);
        currentMaintenance.setCout(cout);
        currentMaintenance.setEffectuePar(effectuePar);

        // Prepare data to be updated
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("Description", currentMaintenance.getDescription());
        updateData.put("MaintenanceDate", currentMaintenance.getMaintenanceDate());
        updateData.put("Cout", currentMaintenance.getCout());
        updateData.put("EffectuePar", currentMaintenance.getEffectuePar());

        // Call the service to update the database
        MaintenanceService maintenanceService = new MaintenanceService();
        maintenanceService.update(currentMaintenance, updateData);

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
}
