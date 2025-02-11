package Controllers;


import Entite.Member;
import Services.ServiceMember;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AfficherPersonneController {

    @FXML
    private TableColumn<Member, Integer> MemberId;

    @FXML
    private TableColumn<Member, String> FirstName;

    @FXML
    private TableColumn<Member, String> LastName;

    @FXML
    private TableColumn<Member, String> Email;
    @FXML
    private TableColumn<Member, Character> Gender;

    @FXML
    private TableColumn<Member, String> PhoneNumber;

    @FXML
    private TableColumn<Member, String> Schedule;
    @FXML
    private TableColumn<Member, Date> StartDate;

    @FXML
    private TableColumn<Member, Date> EndDate;

    @FXML
    private TableColumn<Member, Float> Price;
    @FXML
    private TableColumn<Member, Boolean> Status;

    @FXML
    private TableColumn<Member, String> SubscriptionType;

    @FXML
    private TableColumn<Member, String> Role;
    @FXML
    private TableView<Member> tableMember;

    @FXML
    void initialize() {
        ServiceMember ser=new ServiceMember();
        try {
            List<Member> list= ser.findAll();
            System.out.println(list);

            ObservableList<Member> ober= FXCollections.observableList(list);
            tableMember.setItems(ober);

            MemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));
            FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            Gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
            Schedule.setCellValueFactory(new PropertyValueFactory<>("Schedule"));
            StartDate.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            EndDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
            SubscriptionType.setCellValueFactory(new PropertyValueFactory<>("SubscriptionType"));
            Role.setCellValueFactory(new PropertyValueFactory<>("Role"));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }



    @FXML
    private void handleUpdate() {
        Member selectedMember = tableMember.getSelectionModel().getSelectedItem();

        if (selectedMember != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateMember.fxml"));
                Parent root = loader.load();

                // Récupérer le contrôleur de la fenêtre UpdateMember
                UpdateMemberController controller = loader.getController();
                controller.initData(selectedMember); // Passer les données du membre

                Stage stage = new Stage();
                stage.setTitle("Modifier un membre");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Veuillez sélectionner un membre !");
        }
    }



    @FXML
    private void handleDelete() {
        System.out.println("test");
        ServiceMember sp = new ServiceMember();
        Member selectedMember = tableMember.getSelectionModel().getSelectedItem();

        if (selectedMember != null) {
            // Boîte de confirmation avant suppression
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce membre ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        sp.Supprimer(selectedMember);  // Suppression avec votre service
                        tableMember.getItems().remove(selectedMember); // Suppression de la TableView
                        System.out.println("Membre supprimé avec succès.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression du membre.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un membre à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un membre");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

