package Entite;

public class UserDietPlan {
    private int dietPlanID;
    private int memberID;
    private String memberName; // New field
    private int foodID;
    private String foodName; // New field
    private double servings;
    private String mealTime;

    // Updated constructor
    public UserDietPlan(int dietPlanID, int memberID, String memberName, int foodID, String foodName, double servings, String mealTime) {
        this.dietPlanID = dietPlanID;
        this.memberID = memberID;
        this.memberName = memberName;
        this.foodID = foodID;
        this.foodName = foodName;
        this.servings = servings;
        this.mealTime = mealTime;
    }

    // Getters and Setters
    public int getDietPlanID() { return dietPlanID; }
    public void setDietPlanID(int dietPlanID) { this.dietPlanID = dietPlanID; }

    public int getMemberID() { return memberID; }
    public void setMemberID(int memberID) { this.memberID = memberID; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public int getFoodID() { return foodID; }
    public void setFoodID(int foodID) { this.foodID = foodID; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public double getServings() { return servings; }
    public void setServings(double servings) { this.servings = servings; }

    public String getMealTime() { return mealTime; }
    public void setMealTime(String mealTime) { this.mealTime = mealTime; }

    @Override
    public String toString() {
        return "DietPlanID: " + dietPlanID + ", MemberID: " + memberID + ", MemberName: " + memberName +
                ", FoodID: " + foodID + ", FoodName: " + foodName + ", Servings: " + servings + ", MealTime: " + mealTime;
    }
}