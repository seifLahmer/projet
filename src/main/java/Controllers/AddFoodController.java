package Controllers;

import Entite.Food;
import Services.DietPlanService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class AddFoodController {

    @FXML
    private TextField txtFoodName;
    @FXML
    private TextField txtCalories;
    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private TableView<Food> foodTable;
    @FXML
    private TableColumn<Food, String> foodNameCol;
    @FXML
    private TableColumn<Food, Double> caloriesCol;
    @FXML
    private TableColumn<Food, String> categoryCol;
    @FXML
    private TableColumn<Food, Void> actionCol;

    private DietPlanService dietPlanService;

    public AddFoodController() {
        dietPlanService = new DietPlanService();
    }

    @FXML
    public void initialize() {
        foodNameCol.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("caloriesPerServing"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        // Add a button in the action column for deleting items
        actionCol.setCellFactory(col -> new TableCell<Food, Void>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setOnAction(e -> {
                    Food selectedFood = getTableView().getItems().get(getIndex());
                    deleteFood(selectedFood);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        loadFoodItems(); // Load existing food items into the table
    }

    @FXML
    private void addFood() {
        String foodName = txtFoodName.getText();
        String caloriesStr = txtCalories.getText();
        String category = comboCategory.getValue();

        if (foodName.isEmpty() || caloriesStr.isEmpty() || category == null) {
            // Show error message
            return;
        }

        try {
            double caloriesPerServing = Double.parseDouble(caloriesStr);
            Food newFood = new Food(0, foodName, caloriesPerServing, category);
            dietPlanService.addFood(newFood);
            loadFoodItems(); // Refresh the table
            clearFields(); // Clear input fields
        } catch (NumberFormatException e) {
            // Show error message
        } catch (SQLException e) {
            // Show error message
        }
    }

    private void deleteFood(Food food) {
        try {
            dietPlanService.deleteFood(food.getFoodID()); // Ensure this method exists in DietPlanService
            loadFoodItems(); // Refresh the table
        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
        }
    }

    private void loadFoodItems() {
        try {
            List<Food> foods = dietPlanService.getAllFoods();
            foodTable.getItems().clear();
            foodTable.getItems().addAll(foods);
        } catch (SQLException e) {
            // Show error message
        }
    }

    private void clearFields() {
        txtFoodName.clear();
        txtCalories.clear();
        comboCategory.setValue(null);
    }
}