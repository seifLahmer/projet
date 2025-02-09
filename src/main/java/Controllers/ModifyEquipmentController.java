package Controllers;
import Entite.Equipment;
import Entite.Etat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.text.ParseException;
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
        // Validate quantity
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Quantity", "Please enter a valid number for the quantity.");
            return;
        }

        // Validate dates
        Date achatDate = parseDate(achatDateField.getText());
        Date lastMaintenanceDate = parseDate(lastMaintenanceDateField.getText());
        if (achatDate == null || lastMaintenanceDate == null) {
            return;
        }

        // Validate etat
        String etatStr = etatField.getText();
        Etat etat = parseEtat(etatStr);
        if (etat == null) {
            return;
        }

        // Set the modified data to the current equipment
        currentEquipment.setEquipementName(nameField.getText());
        currentEquipment.setCategory(categoryField.getText());
        currentEquipment.setQuantity(quantity);
        currentEquipment.setAchatDate(achatDate);
        currentEquipment.setLastMaintenanceDate(lastMaintenanceDate);
        currentEquipment.setEtat(etat);

        // Save the updated data (this could involve updating a database or list)
        saveUpdatedEquipment(currentEquipment);

        // Close the modify window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            showErrorAlert("Invalid Date", "Please enter a valid date in the format yyyy-MM-dd.");
            return null;
        }
    }

    private Etat parseEtat(String etatStr) {
        try {
            return Etat.valueOf(etatStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showErrorAlert("Invalid Etat", "Please enter a valid Etat (Available, In Maintenance, Out of Service).");
            return null;
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveUpdatedEquipment(Equipment equipment) {
        // Logic to save the modified equipment (could update the equipment in a database or list)
        System.out.println("Equipment saved: " + equipment);
    }

    public void initialize(Equipment equipment) {
    }
}
