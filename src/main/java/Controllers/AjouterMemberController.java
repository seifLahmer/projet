package Controllers;

import Entite.Member;

import Services.ServiceMember;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterMemberController {

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private CheckBox txtGenderH;
    @FXML
    private CheckBox txtGenderF;
    @FXML
    private TextField txtSchedule;

    @FXML
    private TextField txtStartDate ;

    @FXML
    private TextField txtEndDate ;

    @FXML
    private TextField txtPrice ;
    @FXML
    private CheckBox txtStatus ;

    @FXML
    private TextField txtSubsType ;

    @FXML
    private CheckBox txtRole1 ;
    @FXML
    private CheckBox txtRole2 ;


    @FXML
    void ajouter(ActionEvent event) throws IOException {
        System.out.println("test");
        ServiceMember sp = new ServiceMember();
        char Gender;
        if (txtGenderH.getText().equals("H")) {
            Gender = txtGenderH.getText().charAt(0);
        } else {
            Gender = txtGenderF.getText().charAt(0);
        }
        String Role;
        if (txtRole1.getText().equals("Adherent")) {
            Role = txtRole1.getText();
        } else {
            Role = txtRole2.getText();
        }
        Boolean Stat;
        if (txtStatus.getText().equals("1")) {
            Stat = true;
        } else {
            Stat = false;
        }


        Member p1 = new Member(Integer.parseInt(txtMemberId.getText()), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPassword.getText(), Gender, txtPhoneNumber.getText(), txtSchedule.getText(), Date.valueOf(txtStartDate.getText()), Date.valueOf(txtEndDate.getText()), Float.parseFloat(txtPrice.getText()), Stat, txtSubsType.getText(), Role);
        System.out.println(p1);
        try {
            sp.Ajouter(p1);
            System.out.println("personne ajoutée");
            Afficher(event);

        } catch (SQLException e) {
            System.out.println(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MemberInterface.fxml"));


        Parent root = loader.load();
        txtMemberId.getScene().setRoot(root);

    }
}

