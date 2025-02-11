package Controllers;

import Entite.Activity;
import Entite.Members;
import Entite.MemberList;
import Services.ServiceActivity;
import Services.ServiceMembers;
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


    ServiceMembers sm = new ServiceMembers();

    private boolean validateInput() {
        String errorMessage = "";

        if (nameId.getText().trim().isEmpty()) {
            errorMessage += "Activity Name is required.\n";
        }
        if (descriptionId.getText().trim().isEmpty()) {
            errorMessage += "Description is required.\n";
        }
        if (dateId.getValue() == null) {
            errorMessage += "Date is required.\n";
        }
        if (hourId.getText().trim().isEmpty()) {
            errorMessage += "Hour is required (HH:mm format).\n";
        } else {
            try {
                LocalTime.parse(hourId.getText()); // Check time format
            } catch (Exception e) {
                errorMessage += "Invalid time format (expected HH:mm).\n";
            }
        }
        if (durationId.getText().trim().isEmpty()) {
            errorMessage += "Duration is required.\n";
        } else {
            try {
                int duration = Integer.parseInt(durationId.getText());
                if (duration <= 0) {
                    errorMessage += "Duration must be a positive number.\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Duration must be a valid number.\n";
            }
        }
        if (maxID.getText().trim().isEmpty()) {
            errorMessage += "Max Members is required.\n";
        } else {
            try {
                int maxMembers = Integer.parseInt(maxID.getText());
                if (maxMembers <= 0) {
                    errorMessage += "Max Members must be a positive number.\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Max Members must be a valid number.\n";
            }
        }
        if (listID.getValue() == null || listID.getValue().trim().isEmpty()) {
            errorMessage += "Coach selection is required.\n";
        }

        if (!errorMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Validation Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return true;
        }

        return false;
    }
    @FXML
    void add(ActionEvent event) throws SQLException {
        if (validateInput()) {
            return; // Stop if validation fails
        }

        // Create the service instance
        ServiceActivity sa = new ServiceActivity();

        // Retrieve input values
        String name = nameId.getText();
        String description = descriptionId.getText();
        LocalDate date = dateId.getValue();
        int duration = Integer.parseInt(durationId.getText());
        int maxParticipants = Integer.parseInt(maxID.getText());
        LocalTime hour = LocalTime.parse(hourId.getText());
        int coachId = onCoachSelected();

        // Check if the coach is available
        if (!sa.isCoachAvailable(coachId, java.sql.Date.valueOf(date), java.sql.Time.valueOf(hour), duration)) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Coach Unavailable");
            error.setHeaderText(null);
            error.setContentText("This coach already has an activity at the selected time.");
            error.showAndWait();
            return;
        }
        // Create Activity object
        Activity activity = new Activity(
                name, description, maxParticipants,
                java.sql.Date.valueOf(date),
                java.sql.Time.valueOf(hour),
                duration, coachId
        );
        sa.ajouter(activity);
        initialize();
        clearFields();
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setHeaderText(null);
        success.setContentText("Activity added successfully!");
        success.showAndWait();

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
        Activity selectedActivity = tableID.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            if (validateInput()) {
                return; // Stop if validation fails
            }

            try {
                // Retrieve updated values
                String name = nameId.getText();
                String description = descriptionId.getText();
                int coach = onCoachSelected();
                LocalDate date = dateId.getValue();
                int duration = Integer.parseInt(durationId.getText());
                int maxParticipants = Integer.parseInt(maxID.getText());
                LocalTime hour = LocalTime.parse(hourId.getText());

                // Update activity object
                selectedActivity.setActivityName(name);
                selectedActivity.setDescription(description);
                selectedActivity.setMemberId(coach);
                selectedActivity.setDate(Date.valueOf(date));
                selectedActivity.setDuration(duration);
                selectedActivity.setMaxMembers(maxParticipants);
                selectedActivity.setHour(Time.valueOf(hour));

                // Perform update
                ServiceActivity sa = new ServiceActivity();
                sa.update(selectedActivity);

                tableID.refresh();
                clearFields();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText(null);
                success.setContentText("Activity updated successfully!");
                success.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText(null);
                error.setContentText("An error occurred while updating the activity.");
                error.showAndWait();
            }
        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            warning.setContentText("Please select an activity to update.");
            warning.showAndWait();
        }
    }

    @FXML
    void initialize() throws SQLException {
        ServiceActivity sera = new ServiceActivity();
        MemberList ml = new MemberList(sm.getAll());

        try {
            // Fetch coach names
            List<Members> coaches = ml.getCoaches();
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
        List<Members> coaches = ml.getCoaches();
        String selectedCoach = listID.getSelectionModel().getSelectedItem();

        if (selectedCoach == null) {
            System.out.println("Aucun coach sélectionné.");
            return -1;
        }
        Optional<Members> coachOpt = coaches.stream()
                .filter(coach -> (coach.getFirstName() + " " + coach.getLastName()).equals(selectedCoach))
                .findFirst();

        // Si un coach est trouvé, retourne son ID, sinon retourne -1
        return coachOpt.map(Members::getMemberId).orElseGet(() -> {
            System.out.println("Coach sélectionné non trouvé dans la liste.");
            return -1; // Retourne une valeur négative si le coach n'est pas trouvé
        });

    }
}
