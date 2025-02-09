package Controllers;

import Entite.Equipment;
import Services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.SimpleDateFormat;

public class AfficherEquipementsController {

    @FXML
    private TableView<Equipment> equipementsTable;

    @FXML
    private TableColumn<Equipment, Integer> idColumn;

    @FXML
    private TableColumn<Equipment, String> nameColumn;

    @FXML
    private TableColumn<Equipment, String> categoryColumn;

    @FXML
    private TableColumn<Equipment, Integer> quantityColumn;

    @FXML
    private TableColumn<Equipment, String> achatDateColumn; // New column for Achat Date

    @FXML
    private TableColumn<Equipment, String> maintenanceDateColumn;
    @FXML
    private TableColumn<Equipment, String> etatColumn;

    @FXML
    private TableColumn<Equipment, Void> actionColumn;

    private final ServiceEquipement serviceEquipement = new ServiceEquipement();

    @FXML
    public void initialize() {
        // Bind columns to Equipment properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("equipementID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("equipementName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
// Bind the Achat Date and Maintenance Date columns
        achatDateColumn.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return new javafx.beans.property.SimpleStringProperty(dateFormat.format(cellData.getValue().getAchatDate()));
        });

        maintenanceDateColumn.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return new javafx.beans.property.SimpleStringProperty(dateFormat.format(cellData.getValue().getLastMaintenanceDate()));
        });
        // Set up the cell factory for the action column (buttons)
        actionColumn.setCellFactory(new Callback<TableColumn<Equipment, Void>, TableCell<Equipment, Void>>() {
            @Override
            public TableCell<Equipment, Void> call(TableColumn<Equipment, Void> param) {
                return new TableCell<Equipment, Void>() {
                    private final Button modifyButton = new Button("Modify");
                    private final Button deleteButton = new Button("Delete");

                    {
                        // Modify button action
                        modifyButton.setOnAction((ActionEvent event) -> {
                            Equipment equipment = getTableView().getItems().get(getIndex());
                            modifyEquipement(equipment);
                        });

                        // Delete button action
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Equipment equipment = getTableView().getItems().get(getIndex());
                            deleteEquipement(equipment);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, modifyButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        // Populate the table with data from the service
        equipementsTable.setItems(serviceEquipement.getAll());
    }

    private void modifyEquipement(Equipment equipment) {
        try {
            // Load the ModifyEquipment FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyEquipment.fxml"));
            AnchorPane modifyPane = loader.load();

            // Get the controller of the modify interface
            ModifyEquipmentController controller = loader.getController();
            controller.initialize(equipment);  // Pass the selected equipment to the controller

            // Create a new stage for the modify interface
            Stage modifyStage = new Stage();
            modifyStage.setTitle("Modify Equipment");
            modifyStage.setScene(new Scene(modifyPane));
            modifyStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteEquipement(Equipment equipment) {
        // Call the delete method from the service
        serviceEquipement.supprimer(equipment);

        // After deletion, refresh the table
        equipementsTable.setItems(serviceEquipement.getAll());
    }


    public void refreshEquipements(ActionEvent actionEvent) {
        // Fetch the latest data from the service
        equipementsTable.setItems(serviceEquipement.getAll());
    }

}
