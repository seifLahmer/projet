package Services;

import Entite.Calorie;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCalorie {
    private final Connection con;

    public ServiceCalorie() {
        con = DataSource.getInstance().getCon();
    }

    public boolean userExists(int memberId) throws SQLException {
        String query = "SELECT COUNT(*) FROM member WHERE MemberId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if user exists
            }
        }
        return false; // User does not exist
    }

    public void ajouter(Calorie calorie) throws SQLException {
        if (userExists(calorie.getMemberId())) {
            double calorieNeeds = calculateCalorieNeeds(
                    calorie.getGender(),
                    calorie.getHeight(),
                    calorie.getWeight(),
                    calorie.getAge(),
                    calorie.getActivityLevel(),
                    calorie.getGoal()
            );

            String req = "INSERT INTO usercalorieprofiles (MemberId, gender, height, weight, age, activity_level, goal, calorie_needs, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
            try (PreparedStatement pstmt = con.prepareStatement(req)) {
                pstmt.setInt(1, calorie.getMemberId());
                pstmt.setString(2, calorie.getGender());
                pstmt.setDouble(3, calorie.getHeight());
                pstmt.setDouble(4, calorie.getWeight());
                pstmt.setInt(5, calorie.getAge());
                pstmt.setString(6, calorie.getActivityLevel());
                pstmt.setString(7, calorie.getGoal());
                pstmt.setDouble(8, calorieNeeds);
                pstmt.executeUpdate();
            }
        } else {
            System.out.println("User with MemberId " + calorie.getMemberId() + " does not exist. Cannot add Calorie entry.");
        }
    }

    public double calculateCalorieNeeds(String gender, double height, double weight, int age, String activityLevel, String goal) {
        double bmr;

        if (gender.equalsIgnoreCase("Homme")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        double activityFactor;
        switch (activityLevel) {
            case "Sédentaire":
                activityFactor = 1.2;
                break;
            case "Activité légère":
                activityFactor = 1.375;
                break;
            case "Modérément actif":
                activityFactor = 1.55;
                break;
            case "Très actif":
                activityFactor = 1.725;
                break;
            case "Extrêmement actif":
                activityFactor = 1.9;
                break;
            default:
                activityFactor = 1.2; // Default to sedentary if not found
                break;
        }

        double totalCalories = bmr * activityFactor;

        // Adjust calories based on the user's goal
        switch (goal) {
            case "Perte de poids":
                totalCalories -= 500; // Subtract for weight loss
                break;
            case "Gain de poids":
                totalCalories += 500; // Add for weight gain
                break;
            case "Maintien":
                // No adjustment needed for maintenance
                break;
            default:
                System.out.println("Unknown goal: " + goal + ". Defaulting to maintenance.");
                break;
        }

        return totalCalories; // Return adjusted total daily calorie needs
    }

    public List<Calorie> getAll() throws SQLException {
        List<Calorie> list = new ArrayList<>();
        String query = "SELECT * FROM usercalorieprofiles ORDER BY created_at DESC ";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int memberId = rs.getInt("MemberId");
                String gender = rs.getString("gender");
                double height = rs.getDouble("height");
                double weight = rs.getDouble("weight");
                int age = rs.getInt("age");
                String activityLevel = rs.getString("activity_level");
                String goal = rs.getString("goal");
                double calorieNeeds = rs.getDouble("calorie_needs");
                String createdAt = rs.getString("created_at");
                String updatedAt = rs.getString("updated_at");

                Calorie calorie = new Calorie(id, memberId, gender, height, weight, age, activityLevel, goal, calorieNeeds, createdAt, updatedAt);
                list.add(calorie);
            }
        }
        return list;
    }
}