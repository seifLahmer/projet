package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static Stage primaryStage;  // Fenêtre principale

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Initialisation de la fenêtre principale
        loadScene("/Login.fxml", "Login");  // Chargement de l'interface de login
    }

    // Méthode pour charger n'importe quelle scène
    public static void loadScene(String fxmlPath, String title) {
        try {

            URL resource = Main.class.getResource(fxmlPath);
            if (resource == null) {
                System.err.println("Le fichier FXML '" + fxmlPath + "' est introuvable.");
                return; // Ou lancez une exception
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


