package Controllers;

import Entite.Equipment;
import Entite.Etat;
import Services.ServiceEquipement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ModifyEquipmentController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<Etat> etatComboBox; // ComboBox for Etat
    @FXML
    private Button saveButton;

    private Equipment currentEquipment;

    // Method to set the data from the selected equipment
    public void setEquipmentData(Equipment equipment) {
        this.currentEquipment = equipment;

        // Populate the fields with the current equipment data (excluding id)
        nameField.setText(equipment.getEquipementName());
        categoryField.setText(equipment.getCategory());
        quantityField.setText(String.valueOf(equipment.getQuantity()));

        // Set the 'etat' ComboBox with available Etat values
        etatComboBox.getItems().setAll(Etat.values());  // Add all Etat values to the ComboBox
        etatComboBox.setValue(equipment.getEtat());    // Set the current Etat to the selected value
    }

    @FXML
    private void onSaveButtonClick() {
        if (currentEquipment == null) {
            showErrorAlert("No Equipment", "No equipment selected for modification.");
            return;  // Stop further execution if no equipment is set
        }

        // Validate quantity
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Quantity", "Please enter a valid number for the quantity.");
            return;
        }

        // Validate etat
        Etat etat = etatComboBox.getValue();  // Get selected Etat
        if (etat == null) {
            showErrorAlert("Invalid Etat", "Please select a valid Etat.");
            return;
        }

        // Set the modified data to the current equipment
        currentEquipment.setEquipementName(nameField.getText());
        currentEquipment.setCategory(categoryField.getText());
        currentEquipment.setQuantity(quantity);
        currentEquipment.setEtat(etat);

        // Save the updated data (this could involve updating a database or list)
        saveUpdatedEquipment(currentEquipment);

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

    private void saveUpdatedEquipment(Equipment equipment) {
        // Create an instance of ServiceEquipement
        ServiceEquipement serviceEquipement = new ServiceEquipement();

        // Now create the update data map
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("EquipementName", equipment.getEquipementName());
        updateData.put("Category", equipment.getCategory());
        updateData.put("Quantity", equipment.getQuantity());
        updateData.put("Etat", equipment.getEtat());

        // Now call update on the instance of ServiceEquipement
        serviceEquipement.update(equipment, updateData);  // Correct method call

        System.out.println("Equipment saved: " + equipment);
    }




}
