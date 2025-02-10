package controlles;

import Entite.Payment;
import Services.ServicePayment;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import Utils.SMSUtil;

public class PaymentController {

    @FXML private TextField memberIdField;
    @FXML private TextField amountField;
    @FXML private DatePicker paymentDateField;
    @FXML private TextField paymentStatusField;


    @FXML private TableView<Payment> paymentTableView;
    @FXML private TableColumn<Payment, Integer> paymentIdColumn;
    @FXML private TableColumn<Payment, Integer> memberIdColumn;
    @FXML private TableColumn<Payment, Double> amountColumn;
    @FXML private TableColumn<Payment, Date> paymentDateColumn;
    @FXML private TableColumn<Payment, String> paymentStatusColumn;
    @FXML private TableColumn<Payment, Integer>adminIdColumn;

    private ServicePayment servicePayment = new ServicePayment();


    private ObservableList<Payment> paymentList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        // Configurer la TableView
        paymentIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaymentId()).asObject());
        memberIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMemberId()).asObject());
        amountColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
        paymentDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPaymentDate()));
        paymentStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentStatus()));

    }


    @FXML
    private void addPayment() {
        try {
            // Verify all fields are filled
            if (memberIdField.getText().isEmpty() || amountField.getText().isEmpty() ||
                    paymentDateField.getValue() == null || paymentStatusField.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill all fields!");
                return;
            }

            // Convert values safely
            int memberId = Integer.parseInt(memberIdField.getText());
            double amount = Double.parseDouble(amountField.getText());
            Date paymentDate = java.sql.Date.valueOf(paymentDateField.getValue());
            String paymentStatus = paymentStatusField.getText();

            // Create and add payment
            Payment newPayment = new Payment(memberId, amount, paymentDate, paymentStatus);
            servicePayment.ajouter(newPayment);

            // Send SMS confirmation
            String phoneNumber = "+21699999999"; // Replace with the actual member's phone number
            SMSUtil.sendPaymentConfirmation(phoneNumber, amount, String.valueOf(memberId));

            // Reload payments list
            loadPayments();

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment added successfully and SMS notification sent!");
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numeric values!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error adding payment: " + e.getMessage());
        }
    }


    @FXML
    private void updatePayment() {
        try {
            Payment selectedPayment = paymentTableView.getSelectionModel().getSelectedItem();

            if (selectedPayment == null) {
                showAlert(Alert.AlertType.WARNING, "Aucun paiement sélectionné", "Veuillez sélectionner un paiement à modifier !");
                return;
            }

            // Vérifier que tous les champs sont remplis
            if (memberIdField.getText().isEmpty() || amountField.getText().isEmpty() ||
                    paymentDateField.getValue() == null || paymentStatusField.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs avant de modifier !");
                return;
            }

            // Mise à jour des valeurs
            selectedPayment.setMemberId(Integer.parseInt(memberIdField.getText()));
            selectedPayment.setAmount(Double.parseDouble(amountField.getText()));
            selectedPayment.setPaymentDate(java.sql.Date.valueOf(paymentDateField.getValue()));
            selectedPayment.setPaymentStatus(paymentStatusField.getText());

            // Mise à jour dans la base de données
            servicePayment.update(selectedPayment);

            // Recharger la liste des paiements
            loadPayments();

            // Afficher une confirmation
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Paiement mis à jour avec succès !");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de format", "Veuillez entrer des valeurs numériques valides !");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Erreur lors de la mise à jour du paiement : " + e.getMessage());
        }
    }

    /**
     * Affiche une boîte de dialogue d'alerte avec un type donné.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void deletePayment() {
        Payment selectedPayment = paymentTableView.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            // Afficher une alerte si aucun paiement n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun paiement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un paiement à supprimer.");
            alert.showAndWait();
            return;
        }

        // Demande de confirmation avant suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Suppression du paiement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce paiement ?");

        // Si l'utilisateur confirme la suppression
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    servicePayment.supprimer(selectedPayment);
                    loadPayments();
                    System.out.println("Paiement supprimé avec succès !");
                } catch (SQLException e) {
                    // Affichage d'une alerte en cas d'erreur SQL
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur de suppression");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur s'est produite lors de la suppression du paiement : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        });
    }


    private void loadPayments() throws SQLException {
        paymentList.clear();
        paymentList.addAll(servicePayment.getAll());
        paymentTableView.setItems(paymentList);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        Payment selectedPayment = paymentTableView.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            memberIdField.setText(String.valueOf(selectedPayment.getMemberId()));
            amountField.setText(String.valueOf(selectedPayment.getAmount()));
            paymentDateField.setValue(selectedPayment.getPaymentDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            paymentStatusField.setText(selectedPayment.getPaymentStatus());
        }
    }
}
