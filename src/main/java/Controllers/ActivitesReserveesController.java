package Controllers;

import Entite.Reservation;
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

        // 🔎 Récupération des réservations du membre
        List<Reservation> reservationsMembre = reservationService.getReservationsParMembre(membreId);
        System.out.println("🔎 Réservations trouvées : " + reservationsMembre.size());

        for (Reservation reservation : reservationsMembre) {
            VBox card = new VBox(10);
            card.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f0f0f0;");
            card.setMinWidth(200);

            Label nomLabel = new Label("Activité: " + reservation.getActivityId()); // Utilisation de l'ID pour l'instant
            Label dateLabel = new Label("Date: " + reservation.getReservationDate());
            Button supprimerButton = new Button("Annuler");

            // 🗑️ Suppression de la réservation
            supprimerButton.setOnAction(e -> {
                try {
                    supprimerReservation(reservation.getReservationId());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            card.getChildren().addAll(nomLabel, dateLabel, supprimerButton);
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
        Parent root = FXMLLoader.load(getClass().getResource("/resources/activites_disponibles.fxml"));
        stage.setScene(new Scene(root));
    }
}
