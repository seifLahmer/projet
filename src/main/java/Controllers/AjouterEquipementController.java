package Controllers;

import Entite.Equipment;
import Entite.Etat;
import Services.ServiceEquipement;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.util.Date;

public class AjouterEquipementController {

    @FXML
    private TextField equipementIDField;

    @FXML
    private TextField equipementNameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<Etat> etatComboBox; // ComboBox for Etat

    private ServiceEquipement serviceEquipement;

    public AjouterEquipementController() {
        serviceEquipement = new ServiceEquipement();
    }

    @FXML
    private void initialize() {
        // Populate ComboBox with enum values
        etatComboBox.getItems().setAll(Etat.values());
    }

    @FXML
    private void ajouterEquipement(MouseEvent event) {
        int equipementID = Integer.parseInt(equipementIDField.getText());
        String equipementName = equipementNameField.getText();
        String category = categoryField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        Date achatDate = new Date();  // Current date for demonstration
        Date lastMaintenanceDate = new Date();  // Current date for demonstration
        Etat etat = etatComboBox.getValue(); // Get selected Etat

        Equipment newEquipment = new Equipment(equipementID, equipementName, category, quantity, achatDate, lastMaintenanceDate, etat);
        serviceEquipement.ajouterEquipement(newEquipment);
    }
}
