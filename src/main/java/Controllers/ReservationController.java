package Controllers;


import Entite.*;
import Services.ServiceActivity;
import Services.ServiceMember;
import Services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReservationController {

    @FXML
    private TableView<Reservation> tableID;
    @FXML
    private TableColumn<Reservation, String> activityColumnID;

    @FXML
    private TableColumn<Reservation, Date> dateColumnID;

    @FXML
    private DatePicker dateID;

    @FXML
    private TextField memberID;
    @FXML
    private ComboBox<String> activityID;



    ServiceActivity sa = new ServiceActivity();

    @FXML
    void add(ActionEvent event) throws SQLException {
        // Création d'une instance du service
        ServiceReservation sr = new ServiceReservation();


        // Récupération des données saisies
        int memberId = Integer.parseInt(memberID.getText());
        LocalDate date = dateID.getValue();

        // Création de l'activité
        Reservation reservation = new Reservation(
                memberId,
                onActivitySelected(),
                java.sql.Date.valueOf(date)
        );


            // Ajout de l'activité dans la base de données
            try {
                sr.ajouter(reservation);
                initialize();
                clearFields();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setHeaderText(null);
                success.setContentText("Activité ajoutée avec succès !");
                success.showAndWait();
            } catch (Exception e) {
                System.out.println(e);
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText(null);
                error.setContentText("Une erreur s'est produite lors de l'ajout de l'activité.");
                error.showAndWait();
            }



        // Retour utilisateur

    }

    @FXML
    void delete(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans la table
        Reservation selectedReservation = tableID.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Confirmation de suppression
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cette reservation ?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ServiceReservation sa = new ServiceReservation();

                    try {
                        // Supprimer l'activité de la base de données
                        sa.supprimer(selectedReservation);

                        // Mettre à jour la table
                        tableID.getItems().remove(selectedReservation);

                        // Afficher un message de succès
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Succès");
                        success.setHeaderText(null);
                        success.setContentText("Reservation supprimée avec succès !");
                        success.showAndWait();

                    } catch (SQLException e) {
                        // Gérer les erreurs de suppression
                        System.out.println(e);
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erreur");
                        error.setHeaderText(null);
                        error.setContentText("Une erreur s'est produite lors de la suppression de la reservation.");
                        error.showAndWait();
                    }
                }
            });

        } else {
            // Aucune sélection effectuée
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Attention");
            warning.setHeaderText(null);
            warning.setContentText("Veuillez sélectionner une activité à supprimer.");
            warning.showAndWait();
        }

    }

    @FXML
    void update(ActionEvent event) {
        // Récupérer l'activité sélectionnée
        Reservation selectedReservation = tableID.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            try {
                // Récupérer les données modifiées du formulaire
                int memberId = Integer.parseInt(memberID.getText());
                int activity = onActivitySelected();
                LocalDate date = dateID.getValue();


                // Mise à jour de l'objet activité

                selectedReservation.setActivityId(activity);
                selectedReservation.setReservationDate(java.sql.Date.valueOf(date));
                selectedReservation.setMemberId(memberId);


                // Appeler la méthode update simplifiée
                ServiceReservation sa = new ServiceReservation();
                sa.update(selectedReservation);

                // Rafraîchir la table
                tableID.refresh();

                clearFields();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setHeaderText(null);
                success.setContentText("Reservation mise à jour avec succès !");
                success.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText(null);
                error.setContentText("Une erreur s'est produite lors de la mise à jour de l'reservation.");
                error.showAndWait();
            }
        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Attention");
            warning.setHeaderText(null);
            warning.setContentText("Veuillez sélectionner une reservation à modifier.");
            warning.showAndWait();
        }
    }
    @FXML
    void initialize() throws SQLException {
        ServiceReservation sera = new ServiceReservation();
        ActivityList ml = new ActivityList(sa.getAll());

        try {
            // Fetch activity names
            List<Activity> activities = ml.getActivityList();
            if (activities.isEmpty()) {
                System.out.println("No activities found.");
            }
            List<String> activityNames = activities.stream()
                    .map(activity -> activity.getActivityName())
                    .toList();
            ObservableList<String> observableActivityList = FXCollections.observableList(activityNames);
            activityID.setItems(observableActivityList);// Assuming listID is a ListView<String>

        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Failed to load activities.");
            error.showAndWait();
        }

        try {
            // Fetch activities
            List<Reservation> listR = sera.getAll();
            ObservableList<Reservation> observableActivityList = FXCollections.observableList(listR);
            tableID.setItems(observableActivityList); // Assuming tableID is a TableView<Activity>
            //activityColumnID.setCellValueFactory(new PropertyValueFactory<>("activityName"));
            dateColumnID.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));

        } catch (SQLException e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Failed to load reservations.");
            error.showAndWait();
        }
    }

    public void loadDataToForm(MouseEvent mouseEvent) throws SQLException {
        // Récupérer l'activité sélectionnée dans la table

        Reservation selectedReservation = tableID.getSelectionModel().getSelectedItem();
        ActivityList al = new ActivityList(sa.getAll());
        if (selectedReservation != null) {
            // Charger les données dans les champs du formulaire
            // Charger les données dans les champs du formulaire
            memberID.setText(String.valueOf(selectedReservation.getReservationId()));
            //activityID.setValue(ml.getNameById(selectedActivity.getMemberId()));

            java.sql.Date sqlDate = new java.sql.Date(selectedReservation.getReservationDate().getTime());
            dateID.setValue(sqlDate.toLocalDate());


        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Attention");
            warning.setHeaderText(null);
            warning.setContentText("Veuillez sélectionner une activité à modifier.");
            warning.showAndWait();
        }
    }

    private void clearFields() {
        activityID.setValue("");
        memberID.clear();
        dateID.setValue(null);// Efface le coach ID
    }

    public int onActivitySelected() throws SQLException{

        ActivityList al = new ActivityList(sa.getAll());
        List<Activity> activities = al.getActivityList();
        String selectedActivity= activityID.getSelectionModel().getSelectedItem();

        if ( selectedActivity== null) {
            System.out.println("Aucun activité sélectionné.");
            return -1;
        }
        Optional<Activity> activityOpt = activities.stream()
                .filter(activity -> (activity.getActivityName().equals(selectedActivity))).findFirst();

        // Si une activité est trouvé, retourne son ID, sinon retourne -1
        return activityOpt.map(Activity::getMemberId).orElseGet(() -> {
            System.out.println("activité sélectionné non trouvé dans la liste.");
            return -1; // Retourne une valeur négative si le coach n'est pas trouvé
        });

    }
}


