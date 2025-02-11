package Controllers;



import Services.Authentification;
import Test.Main;
import Utils.DataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

public class Login{





    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Connection conn = DataSource.getInstance().getCon();

    @FXML
    public void handleForgotPassword(MouseEvent event) {
        Main.loadScene("/Password_Recovery.fxml", "Récupération du mot de passe");
    }


    private void storeToken(String token) {
        try (FileWriter fileWriter = new FileWriter("jwt_token.txt")) {
            fileWriter.write(token);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la sauvegarde du token.");
        }
    }

    private String loadToken() {
        try {
            return new String(Files.readAllBytes(Paths.get("jwt_token.txt")));
        } catch (IOException e) {
            e.printStackTrace();
            return null; // ou gérer le cas où le fichier n'existe pas
        }
    }


    public void handleLogin() {
        Services.Authentification authService = new Authentification(conn);
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {

            String token = authService.login( email, password);

            if (token != null) {

                authService.saveToken(token);
                storeToken(token); ;
                showAlert("Succès", "Connexion réussie !");
                System.out.println("JWT: " + token);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MemberInterface.fxml"));
                Parent root = loader.load();

                // Obtenir la scène actuelle
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Member Interface");
                stage.show();
            } else {
                showAlert("Erreur", "Email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue.");
        }
    }
    public void redirectToSignup(javafx.event.ActionEvent event) throws IOException {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
        Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Signup");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load the Signup page.");
            alert.showAndWait();
        }
    }




private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
}


public void main() {
}



}



