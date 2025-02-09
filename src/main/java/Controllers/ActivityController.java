package Controllers;

import Entite.Activity;
import Entite.MapCoachActivities;
import Entite.Member;
import Entite.MemberList;
import Services.ServiceActivity;
import Services.ServiceMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ActivityController {

    @FXML
    private TableView<Activity> tableID;
    @FXML
    private TableColumn<Activity, Date> dateColumnId;
    @FXML
    private TableColumn<Activity, Integer> maxColumnId;
    @FXML
    private TableColumn<Activity, String> nameColumnID;
    @FXML
    private TextField coachId;

    @FXML
    private DatePicker dateId;

    @FXML
    private TextArea descriptionId;

    @FXML
    private TextField durationId;

    @FXML
    private TextField hourId;

    @FXML
    private TextField maxID;

    @FXML
    private TextField nameId;
    @FXML
    private ComboBox<String> listID;


    ServiceMember sm = new ServiceMember();
    MapCoachActivities mapCoachActivities = new MapCoachActivities();

    @FXML
    void add(ActionEvent event) throws SQLException {

        // Création d'une instance du service
        ServiceActivity sa = new ServiceActivity();


            // Récupération des données saisies
            String name = nameId.getText();
            String description = descriptionId.getText();
            LocalDate date = dateId.getValue();
            int duration = Integer.parseInt(durationId.getText());
            int maxParticipants = Integer.parseInt(maxID.getText());
            LocalTime hour = LocalTime.parse(hourId.getText()); // Format attendu : HH:mm

            // Création de l'activité
            Activity activity = new Activity(
                    name,
                    description,
                    maxParticipants,

                    java.sql.Date.valueOf(date),
                    java.sql.Time.valueOf(hour),
                    duration,
                    onCoachSelected()
            );

        if(!mapCoachActivities.isConflit(activity)){
                // Ajout de l'activité dans la base de données
            try {
                sa.ajouter(activity);
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
        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur");
            error.setHeaderText(null);
            error.setContentText("This Coach has another activity at this time");
            error.showAndWait();
        }

        // Retour utilisateur


    }

    @FXML
    void delete(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans la table
        Activity selectedActivity = tableID.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            // Confirmation de suppression
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cette activité ?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ServiceActivity sa = new ServiceActivity();

                    try {
                        // Supprimer l'activité de la base de données
                        sa.supprimer(selectedActivity);

                        // Mettre à jour la table
                        tableID.getItems().remove(selectedActivity);

                        // Afficher un message de succès
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Succès");
                        success.setHeaderText(null);
                        success.setContentText("Activité supprimée avec succès !");
                        success.showAndWait();

                    } catch (SQLException e) {
                        // Gérer les erreurs de suppression
                        System.out.println(e);
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erreur");
                        error.setHeaderText(null);
                        error.setContentText("Une erreur s'est produite lors de la suppression de l'activité.");
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
        Activity selectedActivity = tableID.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            try {
                // Récupérer les données modifiées du formulaire
                String name = nameId.getText();
                String description = descriptionId.getText();
                int coach = onCoachSelected();
                LocalDate date = dateId.getValue();
                int duration = Integer.parseInt(durationId.getText());
                int maxParticipants = Integer.parseInt(maxID.getText());
                LocalTime hour = LocalTime.parse(hourId.getText());

                // Mise à jour de l'objet activité
                selectedActivity.setActivityName(name);
                selectedActivity.setDescription(description);
                selectedActivity.setMemberId(coach);
                selectedActivity.setDate(Date.valueOf(date));
                selectedActivity.setDuration(duration);
                selectedActivity.setMaxMembers(maxParticipants);
                selectedActivity.setHour(Time.valueOf(hour));

                // Appeler la méthode update simplifiée
                ServiceActivity sa = new ServiceActivity();
                sa.update(selectedActivity);

                // Rafraîchir la table
                tableID.refresh();

                clearFields();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setHeaderText(null);
                success.setContentText("Activité mise à jour avec succès !");
                success.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText(null);
                error.setContentText("Une erreur s'est produite lors de la mise à jour de l'activité.");
                error.showAndWait();
            }
        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Attention");
            warning.setHeaderText(null);
            warning.setContentText("Veuillez sélectionner une activité à modifier.");
            warning.showAndWait();
        }
    }
    @FXML
    void initialize() throws SQLException {
        ServiceActivity sera = new ServiceActivity();
        MemberList ml = new MemberList(sm.getAll());

        try {
            // Fetch coach names
            List<Member> coaches = ml.getCoaches();
            if (coaches.isEmpty()) {
                System.out.println("No coaches found.");
            }
            List<String> coachNames = coaches.stream()
                    .map(coach -> coach.getFirstName() + " " + coach.getLastName())
                    .toList();
            ObservableList<String> observableCoachList = FXCollections.observableList(coachNames);
            listID.setItems(observableCoachList);// Assuming listID is a ListView<String>
        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Failed to load coaches.");
            error.showAndWait();
        }

        try {
            // Fetch activities
            List<Activity> listA = sera.getAll();
            ObservableList<Activity> observableActivityList = FXCollections.observableList(listA);
            tableID.setItems(observableActivityList); // Assuming tableID is a TableView<Activity>
            nameColumnID.setCellValueFactory(new PropertyValueFactory<>("activityName"));
            dateColumnId.setCellValueFactory(new PropertyValueFactory<>("date"));
            maxColumnId.setCellValueFactory(new PropertyValueFactory<>("maxMembers"));
        } catch (SQLException e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Failed to load activities.");
            error.showAndWait();
        }
    }
    public void loadDataToForm(MouseEvent mouseEvent) throws SQLException {
        // Récupérer l'activité sélectionnée dans la table
        Activity selectedActivity = tableID.getSelectionModel().getSelectedItem();
        MemberList ml = new MemberList(sm.getAll());
        if (selectedActivity != null) {
            // Charger les données dans les champs du formulaire
            nameId.setText(selectedActivity.getActivityName());
            descriptionId.setText(selectedActivity.getDescription());

            java.sql.Date sqlDate = new java.sql.Date(selectedActivity.getDate().getTime());
            dateId.setValue(sqlDate.toLocalDate());
            durationId.setText(String.valueOf(selectedActivity.getDuration()));
            maxID.setText(String.valueOf(selectedActivity.getMaxMembers()));
            hourId.setText(selectedActivity.getHour().toString());
            listID.setValue(ml.getNameById(selectedActivity.getMemberId()));
        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Attention");
            warning.setHeaderText(null);
            warning.setContentText("Veuillez sélectionner une activité à modifier.");
            warning.showAndWait();
        }
    }
    private void clearFields() {
        nameId.clear();            // Efface le texte
        descriptionId.clear();     // Efface la zone de texte
        dateId.setValue(null);     // Réinitialise le DatePicker
        hourId.clear();            // Efface l'heure
        durationId.clear();        // Efface la durée
        maxID.clear();             // Efface le nombre max de membres
        listID.setValue("");           // Efface le coach ID
    }
    @FXML
    public int onCoachSelected() throws SQLException {

        MemberList ml = new MemberList(sm.getAll());
        List<Member> coaches = ml.getCoaches();
        String selectedCoach = listID.getSelectionModel().getSelectedItem();

        if (selectedCoach == null) {
            System.out.println("Aucun coach sélectionné.");
            return -1;
        }
        Optional<Member> coachOpt = coaches.stream()
                .filter(coach -> (coach.getFirstName() + " " + coach.getLastName()).equals(selectedCoach))
                .findFirst();

        // Si un coach est trouvé, retourne son ID, sinon retourne -1
        return coachOpt.map(Member::getMemberId).orElseGet(() -> {
            System.out.println("Coach sélectionné non trouvé dans la liste.");
            return -1; // Retourne une valeur négative si le coach n'est pas trouvé
        });

    }
}
