package Entite;

public class Food {
    private int foodID;
    private String foodName;
    private double caloriesPerServing;
    private String category;

    // Constructeur
    public Food(int foodID, String foodName, double caloriesPerServing, String category) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.caloriesPerServing = caloriesPerServing;
        this.category = category;
    }

    // Getters et Setters
    public int getFoodID() { return foodID; }
    public void setFoodID(int foodID) { this.foodID = foodID; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public double getCaloriesPerServing() { return caloriesPerServing; }
    public void setCaloriesPerServing(double caloriesPerServing) { this.caloriesPerServing = caloriesPerServing; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return foodName + " (" + caloriesPerServing + " kcal)";
    }
}