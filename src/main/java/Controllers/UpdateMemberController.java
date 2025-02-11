package Controllers;

import Entite.Member;
import Services.ServiceMember;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UpdateMemberController {
    private Member member;
    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhoneNumber;
    public void initData(Member member) {
        this.member = member;
        txtFirstName.setText(member.getFirstName());
        txtLastName.setText(member.getLastName());
        txtEmail.setText(member.getEmail());
        txtPhoneNumber.setText(member.getPhoneNumber());
    }

    @FXML
    private void handleSave() {

        System.out.println("test");
        ServiceMember sp = new ServiceMember();
        try {
            // Créer une map des modifications
            Map<String, Object> updatedFields = new HashMap<>();

            // Comparer les valeurs et ajouter uniquement celles modifiées
            if (!txtFirstName.getText().equals(member.getFirstName())) {
                updatedFields.put("FirstName", txtFirstName.getText());
            }
            if (!txtLastName.getText().equals(member.getLastName())) {
                updatedFields.put("LastName", txtLastName.getText());
            }
            if (!txtEmail.getText().equals(member.getEmail())) {
                updatedFields.put("Email", txtEmail.getText());
            }
            if (!txtPhoneNumber.getText().equals(member.getPhoneNumber())) {
                updatedFields.put("PhoneNumber", txtPhoneNumber.getText());
            }

            // Vérifier s'il y a des modifications
            if (!updatedFields.isEmpty()) {
                sp.Update(member, updatedFields); // Passer l'ID du membre stocké
                System.out.println("Member updated successfully!");
            } else {
                System.out.println("No changes detected.");
            }

            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void handleCancel() {
        closeWindow();
    }

    // Fermer la fenêtre
    private void closeWindow() {
        Stage stage = (Stage) txtFirstName.getScene().getWindow();
        stage.close();
    }
}
