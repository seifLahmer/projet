package Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class AdminController {

    @FXML
    private VBox equipmentButtons;
    @FXML
    private VBox maintenanceButtons;

    @FXML
    private Button manageEquipmentButton;
    @FXML
    private Button manageMaintenanceButton;

    // Toggle Equipment Menu
    @FXML
    private void handleManageEquipment() {
        if (equipmentButtons.isVisible()) {
            equipmentButtons.setVisible(false);
        } else {
            equipmentButtons.setVisible(true);
            maintenanceButtons.setVisible(false);  // Hide maintenance menu if open
        }
    }

    // Toggle Maintenance Menu
    @FXML
    private void handleManageMaintenance() {
        if (maintenanceButtons.isVisible()) {
            maintenanceButtons.setVisible(false);
        } else {
            maintenanceButtons.setVisible(true);
            equipmentButtons.setVisible(false);  // Hide equipment menu if open
        }
    }

    // Optional: Sign out button handler
    @FXML
    private void handleSignOut() {
        // Sign out logic here
        System.out.println("Signing out...");
    }

    // Optional: Toggle button handler for opening/closing the sidebar menu
    @FXML
    private void handleToggleButton() {
        // Toggle sidebar logic here
        System.out.println("Toggling sidebar...");
    }
}
