package Controllers;

import Entite.Equipment;
import Entite.Etat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyEquipmentController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField achatDateField;
    @FXML
    private TextField lastMaintenanceDateField;
    @FXML
    private TextField etatField;
    @FXML
    private Button saveButton;

    private Equipment currentEquipment;

    // Method to set the data from the selected equipment
    public void setEquipmentData(Equipment equipment) {
        this.currentEquipment = equipment;

        // Populate the fields with the current equipment data
        idField.setText(String.valueOf(equipment.getEquipementID())); // EquipementID is not editable
        nameField.setText(equipment.getEquipementName());
        categoryField.setText(equipment.getCategory());
        quantityField.setText(String.valueOf(equipment.getQuantity()));

        // Format and set the dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        achatDateField.setText(dateFormat.format(equipment.getAchatDate()));
        lastMaintenanceDateField.setText(dateFormat.format(equipment.getLastMaintenanceDate()));

        // Set the 'etat'
        etatField.setText(equipment.getEtat().toString());
    }

    @FXML
    private void onSaveButtonClick() {
        // Get the modified data from the fields
        currentEquipment.setEquipementName(nameField.getText());
        currentEquipment.setCategory(categoryField.getText());
        currentEquipment.setQuantity(Integer.parseInt(quantityField.getText()));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date achatDate = dateFormat.parse(achatDateField.getText());
            Date lastMaintenanceDate = dateFormat.parse(lastMaintenanceDateField.getText());
            currentEquipment.setAchatDate(achatDate);
            currentEquipment.setLastMaintenanceDate(lastMaintenanceDate);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle date format error (you can add a dialog here for the user)
        }

        // Set the 'etat' (assuming Etat is an enum, modify accordingly)
        String etatStr = etatField.getText();
        try {
            currentEquipment.setEtat(Etat.valueOf(etatStr)); // Assuming Etat is an enum
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Handle invalid etat input
        }

        // Save the updated data (this could involve updating a database or list)
        saveUpdatedEquipment(currentEquipment);

        // Close the modify window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void saveUpdatedEquipment(Equipment equipment) {
        // Logic to save the modified equipment (could update the equipment in a database or list)
        System.out.println("Equipment saved: " + equipment);
    }
}
