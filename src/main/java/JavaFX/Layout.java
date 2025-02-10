package JavaFX;


import javafx.application.Application;  // Importation de la classe Application
import javafx.scene.Scene;             // Importation de la classe Scene
import javafx.scene.control.Label;     // Importation de la classe Label
import javafx.stage.Stage;             // Importation de la classe Stage

import java.sql.SQLException;

public class Layout extends Application { // La classe Main étend Application

        @Override
        public void start(Stage stage) {  // Méthode start() qui est appelée lorsque l'application démarre
            stage.setTitle("JavaFX Test");  // Définir le titre de la fenêtre principale
            Label label = new Label("Hello, JavaFX!"); // Créer un label avec du texte
            Scene scene = new Scene(label, 400, 200);  // Créer une scène contenant le label
            stage.setScene(scene);  // Ajouter la scène à la fenêtre
            stage.show();  // Afficher la fenêtre à l'écran
        }

        public static void main(String[] args) {
            launch(args);  // Lancer l'application JavaFX
        }
        public void initialize() throws SQLException {
            System.out.println("Controller initialized"); // Vérification
            loadPayments();
        }

    private void loadPayments() {
    }

}
    



