package Controllers;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import Services.ServiceActivity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import Entite.Reservation;
import  Entite.Activity;
import Services.ServiceReservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActivitesReserveesController {

    @FXML
    private FlowPane activitesReserveesContainer;
    private ServiceActivity serviceActivity = new ServiceActivity();

    private ServiceReservation reservationService = new ServiceReservation();
    private int membreId = 1; // À remplacer dynamiquement si besoin

    @FXML
    public void initialize() {
        try {
            afficherReservations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherReservations() throws SQLException {
        activitesReserveesContainer.getChildren().clear();

        // Retrieve member reservations
        List<Reservation> reservationsMembre = reservationService.getReservationsParMembre(membreId);
        System.out.println("🔎 Reservations found: " + reservationsMembre.size());

        for (Reservation reservation : reservationsMembre) {
            // Retrieve the full activity details using the activityId from the reservation.
            Activity activity = serviceActivity.getById(reservation.getActivityId());

            // Create the card container
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(15));
            card.setMinWidth(220);

            // Style the card with a white background, rounded corners, a light border, and a drop shadow.
            card.setStyle(
                    "-fx-background-color: #ffffff; " +
                            "-fx-background-radius: 8; " +
                            "-fx-border-radius: 8; " +
                            "-fx-border-color: #e0e0e0; " +
                            "-fx-border-width: 1; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 2);"
            );

            // Create and style the label for the activity name.
            Label nameLabel = new Label("Activity: " + activity.getActivityName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

            // Create and style the label for the activity date.
            Label dateLabel = new Label("Date: " + activity.getDate().toString());
            dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

            // Create and style the label for the activity time.
            Label timeLabel = new Label("Time: " + activity.getHour().toString());
            timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

            // Create and style the label for the activity duration.
            Label durationLabel = new Label("Duration: " + activity.getDuration() + " min");
            durationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

            // Create and style the delete button.
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle(
                    "-fx-background-color: #e74c3c; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand;"
            );
            deleteButton.setOnAction(e -> {
                try {
                    supprimerReservation(reservation.getReservationId());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            // Add all components to the card
            card.getChildren().addAll(nameLabel, dateLabel, timeLabel, durationLabel, deleteButton);
            activitesReserveesContainer.getChildren().add(card);
        }
    }



    private void supprimerReservation(int reservationId) throws SQLException {
        System.out.println("🗑️ Suppression de la réservation ID : " + reservationId);
        reservationService.supprimer(new Reservation(reservationId, membreId, 0, null));
        afficherReservations(); // 🔄 Mise à jour de l'affichage après suppression
    }

    @FXML
    private void retourActivites() throws IOException {
        Stage stage = (Stage) activitesReserveesContainer.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Reservations.fxml"));
        stage.setScene(new Scene(root));
    }
    @FXML
    private void afficherActivites() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservations.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Les activités disponibles");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
