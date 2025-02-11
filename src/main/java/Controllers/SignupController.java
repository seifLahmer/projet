package Controllers;

import Entite.Member;
import Services.ServiceMember;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class SignupController {

    @FXML
    private TextField firstNameField; // Champ pour le prénom
    @FXML
    private TextField lastNameField; // Champ pour le nom de famille
    @FXML
    private TextField emailField; // Champ pour l'adresse email
    @FXML
    private PasswordField passwordField; // Champ pour le mot de passe
    @FXML
    private ComboBox<String> genderComboBox; // ComboBox pour le genre
    @FXML
    private TextField phoneNumberField; // Champ pour le numéro de téléphone
    @FXML
    private TextField subsTypeField; // Champ pour le type d'abonnement
    @FXML
    private TextField txtRole; // Champ pour le rôle (remplacé par un rôle par défaut)
    @FXML
    private TextField txtStartDate; // Champ pour la date de début
    @FXML
    private TextField txtEndDate; // Champ pour la date de fin
    @FXML
    private TextField txtSchedule; // Champ pour l'horaire
    @FXML
    private TextField txtPrice; // Champ pour le prix
    @FXML
    private TextField txtStatus; // Champ pour le statut

    private Connection conn; // Assurez-vous que la connexion à la base de données est initialisée

    @FXML


    private void redirectToLogin() {
        try {
            // Charger la page de connexion
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml")); // Assurez-vous que le chemin est correct
            Stage stage = (Stage) firstNameField.getScene().getWindow(); // Obtenir la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void signup(javafx.event.ActionEvent actionEvent) {
        System.out.println("test");
        ServiceMember sp = new ServiceMember();


        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();
        String subscriptionType = subsTypeField.getText();
        String role = "Adherent"; // Rôle par défaut
        String schedule = "default value"; // Schedule est null
        // Date d'aujourd'hui
        LocalDate today = LocalDate.now();
        java.sql.Date startDate = java.sql.Date.valueOf(today); // Date d'aujourd'hui en sql.Date
        java.sql.Date endDate = java.sql.Date.valueOf(today.plusDays(30)); // Date dans 30 jours
        Float price = 60.0F; // Prix est null
        Boolean status = Boolean.TRUE;

        // Détermination du genre à partir de la ComboBox
        String genderValue = genderComboBox.getValue();
        char gender = (genderValue != null) ? genderValue.charAt(0) : 'U'; // U pour indéfini si null

        // Création d'un nouvel objet Member
        Member newMember = new Member(
                firstName,
                lastName,
                email,
                password,
                gender,
                phoneNumber,
                schedule,
                startDate,
                endDate,
                price,
                status,
                subscriptionType,
                role // Rôle par défaut
        );

        System.out.println(newMember);

        try {
            sp.Ajouter(newMember); // Ajout du membre
            System.out.println("Membre ajouté");
            // Redirection ou affichage de la liste
            redirectToLogin();
        } catch (SQLException e) {
            System.out.println(e);

        }
    }
    }
