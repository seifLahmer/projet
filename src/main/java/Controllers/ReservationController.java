package Controllers;

import Entite.Activity;
import Services.ServiceActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import Entite.Reservation;
import Services.ServiceReservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReservationController {

    private ServiceReservation serviceReservation = new ServiceReservation();
    private int membreId = 1;
    private ServiceActivity serviceActivity = new ServiceActivity();
    @FXML
    private FlowPane activitesDispoContainer;

    @FXML
    public void initialize() {
        afficherActivitesDisponibles();
    }

    @FXML
    private ComboBox<String> activityFilter;
    @FXML
    private DatePicker dateFilter;

    @FXML
    private void afficherActivitesDisponibles() {
        activitesDispoContainer.getChildren().clear();

        try {
            List<Activity> activites = serviceActivity.getAll();
            List<Integer> activitesReserveesIds = serviceReservation.getActivitesReserveesIds(membreId);

            for (Activity activite : activites) {
                if (!activitesReserveesIds.contains(activite.getActivityId())) {
                    HBox card = new HBox(15);
                    card.setStyle("-fx-border-color: black; -fx-padding: 15px; -fx-background-color: #f9f9f9;");
                    card.setAlignment(Pos.CENTER_LEFT);

                    // Image de l'activité
                    ImageView imageView = new ImageView(new Image("file:images/" + activite.getActivityName() + ".jpg"));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);

                    // Informations de l'activité
                    VBox details = new VBox(5);
                    details.getChildren().addAll(
                            new Label("Activité : " + activite.getActivityName()),
                            new Label("Description : " + activite.getDescription()),
                            new Label("Date : " + activite.getDate()),
                            new Label("Heure : " + activite.getHour()),
                            new Label("Durée : " + activite.getDuration() + " min"),
                            new Label("Places disponibles : " + activite.getMaxMembers())
                    );

                    // Bouton Réserver stylisé
                    Button reserverButton = new Button("Réserver");
                    reserverButton.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-font-size: 14px;");
                    reserverButton.setOnAction(e -> {
                        try {
                            serviceReservation.ajouterReservation(membreId, activite.getActivityId());
                            afficherActivitesDisponibles(); // Mise à jour après réservation
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    });

                    // Ajouter éléments à la carte
                    card.getChildren().addAll(imageView, details, reserverButton);
                    activitesDispoContainer.getChildren().add(card);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








}



