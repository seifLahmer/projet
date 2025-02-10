package Controllers;

import Entite.Maintenance;
import Services.MaintenanceService;
import Services.PDFGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.List;

public class AfficherMaintenance {

    @FXML
    private TableView<Maintenance> maintenanceTable;

    @FXML
    private TableColumn<Maintenance, Integer> maintenanceIdColumn;

    @FXML
    private TableColumn<Maintenance, Integer> equipementIdColumn;

    @FXML
    private TableColumn<Maintenance, String> maintenanceDateColumn;

    @FXML
    private TableColumn<Maintenance, String> descriptionColumn;

    @FXML
    private TableColumn<Maintenance, Double> coutColumn;

    @FXML
    private TableColumn<Maintenance, String> effectueParColumn;

    @FXML
    private TableColumn<Maintenance, Void> actionColumn;

    private final MaintenanceService maintenanceService = new MaintenanceService();

    @FXML
    public void initialize() {
        // Bind columns to Maintenance properties
        maintenanceIdColumn.setCellValueFactory(cellData -> cellData.getValue().maintenanceIdProperty().asObject());

        // FIX: Use getEquipementId() directly without SimpleIntegerProperty
        equipementIdColumn.setCellValueFactory(cellData -> cellData.getValue().equipementIdProperty().asObject());

        // Bind the Maintenance Date column
        maintenanceDateColumn.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return new javafx.beans.property.SimpleStringProperty(dateFormat.format(cellData.getValue().getMaintenanceDate()));
        });

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        coutColumn.setCellValueFactory(cellData -> cellData.getValue().coutProperty().asObject());
        effectueParColumn.setCellValueFactory(cellData -> cellData.getValue().effectueParProperty());

        // Set up the cell factory for the action column (buttons)
        actionColumn.setCellFactory(new Callback<TableColumn<Maintenance, Void>, TableCell<Maintenance, Void>>() {
            @Override
            public TableCell<Maintenance, Void> call(TableColumn<Maintenance, Void> param) {
                return new TableCell<Maintenance, Void>() {
                    private final Button modifyButton = new Button("Modify");
                    private final Button deleteButton = new Button("Delete");

                    {
                        // Modify button action
                        modifyButton.setOnAction((ActionEvent event) -> {
                            Maintenance maintenance = getTableView().getItems().get(getIndex());
                            modifyMaintenance(maintenance);
                        });

                        // Delete button action
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Maintenance maintenance = getTableView().getItems().get(getIndex());
                            deleteMaintenance(maintenance);
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
        maintenanceTable.setItems(maintenanceService.getAll());
    }

    private void modifyMaintenance(Maintenance maintenance) {
        try {
            // Load the ModifyMaintenance FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyMaintenance.fxml"));
            VBox modifyPane = loader.load();


            // Create a new stage for the modify interface
            Stage modifyStage = new Stage();
            modifyStage.setTitle("Modify Maintenance");
            modifyStage.setScene(new Scene(modifyPane));
            modifyStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMaintenance(Maintenance maintenance) {
        // Call the delete method from the service
        maintenanceService.delete(maintenance);

        // After deletion, refresh the table
        refreshMaintenances();
    }

    public void refreshMaintenances() {
        // Fetch the latest data from the service and update the table
        maintenanceTable.setItems(maintenanceService.getAll());
    }

    @FXML
    private void onDownloadMaintenancePdf() {
        // Fetch the list of all maintenance records
        List<Maintenance> maintenances = maintenanceService.getAll();

        // Generate the PDF for maintenances
        PDFGenerator.generateMaintenancePdf(maintenances);
    }
}