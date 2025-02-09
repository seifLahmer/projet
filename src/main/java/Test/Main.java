package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charger la première interface (AjouterCalorie.fxml)
        FXMLLoader calorieLoader = new FXMLLoader(getClass().getResource("/AjouterCalorie.fxml"));
        Parent calorieRoot = calorieLoader.load();
        Scene calorieScene = new Scene(calorieRoot);
        primaryStage.setTitle("Ajouter Calorie");
        primaryStage.setScene(calorieScene);
        primaryStage.show();

        // Charger la deuxième interface (diet_plan.fxml) dans une nouvelle fenêtre
        FXMLLoader dietPlanLoader = new FXMLLoader(getClass().getResource("/diet_plan.fxml"));
        Parent dietPlanRoot = dietPlanLoader.load();
        Scene dietPlanScene = new Scene(dietPlanRoot);
        Stage dietPlanStage = new Stage();
        dietPlanStage.setTitle("Plan de Régime");
        dietPlanStage.setScene(dietPlanScene);
        dietPlanStage.show();

        // Charger la troisième interface (AddFood.fxml) dans une nouvelle fenêtre
        FXMLLoader addFoodLoader = new FXMLLoader(getClass().getResource("/AddFood.fxml"));
        Parent addFoodRoot = addFoodLoader.load();
        Scene addFoodScene = new Scene(addFoodRoot);
        Stage addFoodStage = new Stage();
        addFoodStage.setTitle("Ajouter Aliment");
        addFoodStage.setScene(addFoodScene);
        addFoodStage.show();
    }
}