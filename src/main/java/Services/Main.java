package Services; // src/main/java/Main.java

import Entite.Calorie;
import Services.ServiceCalorie;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServiceCalorie serviceCalorie = new ServiceCalorie();

        // Create a new Calorie instance to add
        Calorie newCalorie = new Calorie(1, "Homme", 180.0, 75.0, 30, "Très actif", "Maintien");

        try {
            // Attempt to add the Calorie record to the database
            serviceCalorie.ajouter(newCalorie);
            System.out.println("Calorie entry added successfully with calorie needs: " +
                    serviceCalorie.calculateCalorieNeeds(newCalorie.getGender(),
                            newCalorie.getHeight(),
                            newCalorie.getWeight(),
                            newCalorie.getAge(),
                            newCalorie.getActivityLevel(),
                            newCalorie.getGoal()));

            // Retrieve and print all Calorie entries
            System.out.println("All Calorie entries:");
            for (Calorie c : serviceCalorie.getAll()) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Test with different goals
        Calorie calorieToGain = new Calorie(2, "Homme", 180.0, 75.0, 30, "Très actif", "Gain de poids");
        Calorie calorieToLose = new Calorie(3, "Homme", 180.0, 75.0, 30, "Très actif", "Perte de poids");

        try {
            serviceCalorie.ajouter(calorieToGain);
            serviceCalorie.ajouter(calorieToLose);

            System.out.println("Calorie entry for gaining added successfully with calorie needs: " +
                    serviceCalorie.calculateCalorieNeeds(calorieToGain.getGender(),
                            calorieToGain.getHeight(),
                            calorieToGain.getWeight(),
                            calorieToGain.getAge(),
                            calorieToGain.getActivityLevel(),
                            calorieToGain.getGoal()));

            System.out.println("Calorie entry for losing added successfully with calorie needs: " +
                    serviceCalorie.calculateCalorieNeeds(calorieToLose.getGender(),
                            calorieToLose.getHeight(),
                            calorieToLose.getWeight(),
                            calorieToLose.getAge(),
                            calorieToLose.getActivityLevel(),
                            calorieToLose.getGoal()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}