package Controllers;

import Entite.Food;
import Entite.UserDietPlan;
import Services.DietPlanService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
    @FXML
    private Label lblMemberName;
    @FXML
    private Label lblCalorieNeeds;
    @FXML
    private Label lblTotalCalories; // Add label for displaying total calories

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

        // Add listener for Member ID TextField
        txtMemberID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    try {
                        int memberID = Integer.parseInt(newValue);
                        String memberName = dietPlanService.getMemberName(memberID);
                        lblMemberName.setText(memberName != null ? memberName : "Member not found");

                        // Fetch and display the last calorie need for the member
                        double calorieNeeds = dietPlanService.getLastCalorieNeeds(memberID);
                        lblCalorieNeeds.setText(String.valueOf(calorieNeeds));

                        // Calculate total calories whenever member ID changes
                        updateTotalCalories(memberID);
                    } catch (NumberFormatException e) {
                        lblMemberName.setText(""); // Clear if not a valid number
                        lblCalorieNeeds.setText(""); // Clear calorie needs
                    } catch (SQLException e) {
                        showAlert("Erreur", "Unable to fetch member name or calorie needs: " + e.getMessage());
                    }
                } else {
                    lblMemberName.setText(""); // Clear if input is empty
                    lblCalorieNeeds.setText(""); // Clear calorie needs
                    lblTotalCalories.setText("0"); // Reset total calories
                }
            }
        });

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

    private void updateTotalCalories(int memberID) {
        try {
            double totalCalories = dietPlanService.getTotalCaloriesByMember(memberID);
            lblTotalCalories.setText(String.valueOf(totalCalories));
        } catch (SQLException e) {
            showAlert("Erreur", "Unable to calculate total calories: " + e.getMessage());
        }
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

            // Refresh TableView and update total calories
            loadDietPlans(memberID);
            updateTotalCalories(memberID); // Update total calories after adding a new diet plan

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
            updateTotalCalories(Integer.parseInt(txtMemberID.getText())); // Update total calories after deletion
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