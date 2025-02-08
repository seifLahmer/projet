package Controllers;

import Entite.Calorie;
import Services.ServiceCalorie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;

import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.List;

public class AjouterCalorie {

    @FXML
    private TextField txtid;
    @FXML
    private TextField txtHeight;
    @FXML
    private TextField txtWeight;
    @FXML
    private TextField txtAge;
    @FXML
    private ComboBox<String> comboGender;
    @FXML
    private ComboBox<String> comboActivity;
    @FXML
    private ComboBox<String> comboGoal;
    @FXML
    private TableView<Calorie> calorieTable;
    @FXML
    private TableColumn<Calorie, Integer> memberIdCol;
    @FXML
    private TableColumn<Calorie, String> genderCol;
    @FXML
    private TableColumn<Calorie, Double> heightCol;
    @FXML
    private TableColumn<Calorie, Double> weightCol;
    @FXML
    private TableColumn<Calorie, Integer> ageCol;
    @FXML
    private TableColumn<Calorie, String> activityLevelCol;
    @FXML
    private TableColumn<Calorie, String> goalCol;
    @FXML
    private TableColumn<Calorie, Double> calorieNeedsCol;

    private ServiceCalorie serviceCalorie;

    public AjouterCalorie() {
        serviceCalorie = new ServiceCalorie();
    }

    @FXML
    private void initialize() {
        // Initialiser les colonnes du TableView
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        activityLevelCol.setCellValueFactory(new PropertyValueFactory<>("activityLevel"));
        goalCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        calorieNeedsCol.setCellValueFactory(new PropertyValueFactory<>("calorieNeeds"));

        // Initialiser les ComboBox
        comboGender.setItems(FXCollections.observableArrayList("Homme", "Femme"));
        comboActivity.setItems(FXCollections.observableArrayList("Sédentaire", "Activité légère", "Modérément actif", "Très actif", "Extrêmement actif"));
        comboGoal.setItems(FXCollections.observableArrayList("Perte de poids", "Maintien", "Gain de poids"));

        // Charger les entrées existantes
        loadCalorieEntries();
    }

    @FXML
    private void ajouter() {
        try {
            // Récupérer les valeurs des champs
            int memberId = Integer.parseInt(txtid.getText());
            double height = Double.parseDouble(txtHeight.getText());
            double weight = Double.parseDouble(txtWeight.getText());
            int age = Integer.parseInt(txtAge.getText());
            String gender = comboGender.getValue();
            String activityLevel = comboActivity.getValue();
            String goal = comboGoal.getValue();

            if (gender == null || activityLevel == null || goal == null) {
                showAlert("Erreur", "Veuillez sélectionner une option pour Genre, Activité et Objectif.");
                return;
            }

            // Créer un nouvel objet Calorie
            Calorie newCalorie = new Calorie(memberId, gender, height, weight, age, activityLevel, goal);

            // Calculer les besoins caloriques
            double calorieNeeds = serviceCalorie.calculateCalorieNeeds(gender, height, weight, age, activityLevel, goal);

            // Afficher les besoins caloriques
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Besoins Caloriques Calculés");
            alert.setHeaderText("Vos besoins caloriques journaliers");
            alert.setContentText("Vos besoins caloriques estimés sont : " + calorieNeeds + " kcal.");
            alert.showAndWait();

            // Ajouter l'entrée en base de données
            serviceCalorie.ajouter(newCalorie);

            // Rafraîchir la TableView
            loadCalorieEntries();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides pour ID, Taille, Poids et Âge.");
        } catch (SQLException e) {
            showAlert("Erreur de Base de Données", "Impossible d'ajouter l'entrée : " + e.getMessage());
        }
    }

    private void loadCalorieEntries() {
        try {
            List<Calorie> calorieList = serviceCalorie.getAll();
            calorieTable.getItems().clear();
            calorieTable.getItems().addAll(calorieList);
        } catch (SQLException e) {
            showAlert("Erreur de Base de Données", "Impossible de charger les entrées : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
