package Controllers;

import Entite.Equipment;
import Entite.Etat;
import Services.ServiceEquipement;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;

import java.util.Date;

public class AjouterEquipementController {

    @FXML
    private TextField equipementNameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<Etat> etatComboBox; // ComboBox for Etat

    @FXML
    private DatePicker achatDatePicker; // Date Picker for AchatDate

    @FXML
    private DatePicker lastMaintenanceDatePicker; // Date Picker for LastMaintenanceDate

    private ServiceEquipement serviceEquipement;

    public AjouterEquipementController() {
        serviceEquipement = new ServiceEquipement();
    }

    @FXML
    private void initialize() {
        // Populate ComboBox with enum values for Etat
        etatComboBox.getItems().setAll(Etat.values());
    }

    @FXML
    private void ajouterEquipement(MouseEvent event) {
        // Get input values from the form
        String equipementName = equipementNameField.getText().trim();
        String category = categoryField.getText().trim();
        int quantity;
        Date achatDate = java.sql.Date.valueOf(achatDatePicker.getValue());  // Use Date from DatePicker
        Date lastMaintenanceDate = java.sql.Date.valueOf(lastMaintenanceDatePicker.getValue()); // Use Date from DatePicker
        Etat etat = etatComboBox.getValue();

        // Check for empty fields
        if (equipementName.isEmpty() || category.isEmpty() || quantityField.getText().isEmpty() || etat == null) {
            System.out.println("Please fill in all fields");
            return;
        }

        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity input.");
            return;
        }

        // Create a new Equipment object
        Equipment newEquipment = new Equipment(equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);

        // Call the service to add the equipment
        serviceEquipement.ajouterEquipement(newEquipment);
    }
}
