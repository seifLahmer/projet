package Services;

import Entite.Food;
import Entite.UserDietPlan;
import Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DietPlanService {
    private final Connection connection;

    public DietPlanService() {
        connection = DataSource.getInstance().getCon();
    }

    // Retrieve all foods
    public List<Food> getAllFoods() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String query = "SELECT * FROM foods";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Food food = new Food(
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getDouble("CaloriesPerServing"),
                        rs.getString("Category")
                );
                foods.add(food);
            }
        }
        return foods;
    }

    // Add a diet plan
    public void addDietPlan(UserDietPlan dietPlan) throws SQLException {
        String query = "INSERT INTO user_diet_plans (MemberID, FoodID, Servings, MealTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, dietPlan.getMemberID());
            pstmt.setInt(2, dietPlan.getFoodID());
            pstmt.setDouble(3, dietPlan.getServings());
            pstmt.setString(4, dietPlan.getMealTime());
            pstmt.executeUpdate();
        }
    }

    // Delete a diet plan
    public void deleteDietPlan(int dietPlanID) throws SQLException {
        String query = "DELETE FROM user_diet_plans WHERE DietPlanID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, dietPlanID);
            pstmt.executeUpdate();
        }
    }

    // Retrieve diet plans for a member
    public List<UserDietPlan> getDietPlansByMember(int memberID) throws SQLException {
        List<UserDietPlan> dietPlans = new ArrayList<>();
        String query = "SELECT * FROM user_diet_plans WHERE MemberID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserDietPlan dietPlan = new UserDietPlan(
                            rs.getInt("DietPlanID"),
                            rs.getInt("MemberID"),
                            "", // Placeholder for member name
                            rs.getInt("FoodID"),
                            "", // Placeholder for food name
                            rs.getDouble("Servings"),
                            rs.getString("MealTime")
                    );
                    dietPlans.add(dietPlan);
                }
            }
        }
        // Here you would need to fetch the member name and food name for each dietPlan
        for (UserDietPlan dietPlan : dietPlans) {
            dietPlan.setMemberName(getMemberName(dietPlan.getMemberID()));
            dietPlan.setFoodName(getFoodName(dietPlan.getFoodID()));
        }
        return dietPlans;
    }

    // Get member name
    public String getMemberName(int memberID) throws SQLException {
        String query = "SELECT FirstName, LastName FROM member WHERE MemberID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    return firstName + " " + lastName; // Concatenate first and last names
                }
            }
        }
        return null; // Handle case where memberID does not exist
    }

    // Get food name
    public String getFoodName(int foodID) throws SQLException {
        String query = "SELECT FoodName FROM foods WHERE FoodID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, foodID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("FoodName");
                }
            }
        }
        return null; // or handle accordingly
    }
}