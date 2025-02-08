package Controllers;

import Entite.Food;
import Entite.UserDietPlan;
import Services.DietPlanService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;

public class DietPlanController {

    @FXML
    private TextField txtMemberID;
    @FXML
    private ComboBox<Food> comboFood;
    @FXML
    private TextField txtServings;
    @FXML
    private ComboBox<String> comboMealTime;
    @FXML
    private TableView<UserDietPlan> dietPlanTable;

    private DietPlanService dietPlanService;

    public DietPlanController() {
        dietPlanService = new DietPlanService();
    }

    @FXML
    private void initialize() {
        // Initialize ComboBox
        try {
            List<Food> foods = dietPlanService.getAllFoods();
            comboFood.getItems().addAll(foods);
            comboMealTime.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack");
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les aliments : " + e.getMessage());
        }

        // Initialize TableView
        dietPlanTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("dietPlanID"));
        dietPlanTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberName"));
        dietPlanTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("foodName"));
        dietPlanTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("servings"));
        dietPlanTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("mealTime"));

        // Create a new column for actions (delete button)
        TableColumn<UserDietPlan, Void> colBtn = new TableColumn<>("Actions");
        Callback<TableColumn<UserDietPlan, Void>, TableCell<UserDietPlan, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<UserDietPlan, Void> call(final TableColumn<UserDietPlan, Void> param) {
                final TableCell<UserDietPlan, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction(event -> {
                            UserDietPlan dietPlan = getTableView().getItems().get(getIndex());
                            deleteDietPlan(dietPlan);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        dietPlanTable.getColumns().add(colBtn);
    }

    @FXML
    private void addDietPlan() {
        try {
            int memberID = Integer.parseInt(txtMemberID.getText());
            Food selectedFood = comboFood.getValue();
            double servings = Double.parseDouble(txtServings.getText());
            String mealTime = comboMealTime.getValue();

            if (selectedFood == null || mealTime == null) {
                showAlert("Erreur", "Veuillez sélectionner un aliment et un moment de repas.");
                return;
            }

            String memberName = dietPlanService.getMemberName(memberID);
            UserDietPlan dietPlan = new UserDietPlan(0, memberID, memberName, selectedFood.getFoodID(), selectedFood.getFoodName(), servings, mealTime);
            dietPlanService.addDietPlan(dietPlan);

            // Refresh TableView
            loadDietPlans(memberID);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides pour MemberID et Servings.");
        } catch (SQLException e) {
            showAlert("Erreur de Base de Données", "Impossible d'ajouter le plan de régime : " + e.getMessage());
        }
    }

    private void loadDietPlans(int memberID) {
        try {
            List<UserDietPlan> dietPlans = dietPlanService.getDietPlansByMember(memberID);
            dietPlanTable.getItems().clear();
            dietPlanTable.getItems().addAll(dietPlans);
        } catch (SQLException e) {
            showAlert("Erreur de Base de Données", "Impossible de charger les plans de régime : " + e.getMessage());
        }
    }

    private void deleteDietPlan(UserDietPlan dietPlan) {
        try {
            dietPlanService.deleteDietPlan(dietPlan.getDietPlanID());
            loadDietPlans(Integer.parseInt(txtMemberID.getText())); // Refresh the table
        } catch (SQLException e) {
            showAlert("Erreur de Base de Données", "Impossible de supprimer le plan de régime : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}