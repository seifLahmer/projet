package Controllers;

import Services.EmailService; // Assurez-vous d'importer EmailService
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PasswordRecoveryController {

    @FXML
    private TextField emailField;

    @FXML
    public void handleSendEmail(ActionEvent event) {
        String recipient = emailField.getText();

        // Vérifiez si l'email est valide (ajoutez votre propre logique de vérification ici)
        if (!isValidEmail(recipient)) {
            System.err.println("Adresse email invalide.");
            return;
        }

        String subject = "Récupération du Mot de Passe";
        String messageBody = "<h3>Bonjour !</h3><p>Voici votre lien de récupération de mot de passe.</p>";
        boolean isHtml = true;

        // Créez une instance de EmailService et envoyez l'email
        EmailService emailService = new EmailService();
        emailService.sendEmail(recipient, subject, messageBody, isHtml);
    }

    // Méthode pour vérifier si l'email est valide
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}