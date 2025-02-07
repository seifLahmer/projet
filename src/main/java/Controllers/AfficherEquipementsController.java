package Controllers;

import Entite.Equipment;
import Services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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
        // Logic to open modify interface or dialog
        System.out.println("Modify: " + equipment);
    }

    private void deleteEquipement(Equipment equipment) {

    }

    public void refreshEquipements(ActionEvent actionEvent) {
    }
}
