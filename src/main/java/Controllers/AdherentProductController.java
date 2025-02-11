package Controllers;

import Entite.Product;
import Services.ServiceProduct;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Services.StripePaymentService;

import javax.swing.*;

public class AdherentProductController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TableView<Product> cartTable;
    @FXML
    private TableColumn<Product, String> cartNameColumn;
    @FXML
    private TableColumn<Product, Double> cartPriceColumn;
    @FXML
    private TableColumn<Product, Integer> cartQuantityColumn;

    @FXML
    private Label totalLabel;

    private final ServiceProduct serviceProduct = new ServiceProduct();
    private final ObservableList<Product> cartItems = FXCollections.observableArrayList();
    private double totalAmount = 0.0;

    @FXML
    void initialize() {
        // Configurer les colonnes du tableau des produits
        idColumn.setCellValueFactory(new PropertyValueFactory<>("produitId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeProduit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteDisponible"));

        // Configurer les colonnes du tableau du panier
        cartNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        cartQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteDisponible"));

        // Rafraîchir le tableau des produits
        refreshProductTable();

        // Lier le panier à la table
        cartTable.setItems(cartItems);
    }

    private void refreshProductTable() {
        try {
            List<Product> products = serviceProduct.getAll();
            ObservableList<Product> observableList = FXCollections.observableList(products);
            productTable.setItems(observableList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les produits.");
        }
    }

    @FXML
    void addToCart() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Vérifier si le produit est déjà dans le panier
            boolean found = false;
            for (Product item : cartItems) {
                if (item.getProduitId() == selectedProduct.getProduitId()) {
                    item.setQuantiteDisponible(item.getQuantiteDisponible() + 1); // Augmenter la quantité
                    found = true;
                    break;
                }
            }

            if (!found) {
                // Ajouter une copie du produit au panier
                Product cartProduct = new Product(
                        selectedProduct.getProduitId(),
                        selectedProduct.getNom(),
                        selectedProduct.getDescription(),
                        selectedProduct.getPrix(),
                        selectedProduct.getTypeProduit(),
                        1 // Quantité initiale
                );
                cartItems.add(cartProduct);
            }

            // Mettre à jour le total
            totalAmount += selectedProduct.getPrix();
            totalLabel.setText(String.format("%.2f", totalAmount));

            // Rafraîchir le panier
            cartTable.refresh();
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un produit à ajouter au panier.");
        }
    }



    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
    @FXML
    void pay() {
        if (totalAmount > 0) {
            StripePaymentService paymentService = new StripePaymentService();
            try {
                PaymentIntent paymentIntent = paymentService.createPayment(totalAmount);
                showAlert(Alert.AlertType.INFORMATION, "Paiement réussi",
                        "Paiement de " + totalAmount + "€ effectué avec succès.\nID de transaction : " + paymentIntent.getId());

                // Réinitialiser le panier
                cartItems.clear();
                totalAmount = 0.0;
                totalLabel.setText("0.0");
                cartTable.refresh();
            } catch (StripeException e) {
                showAlert(Alert.AlertType.ERROR, "Échec du paiement", "Erreur : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Aucun produit dans le panier.");
        }
    }


    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField cvcField;

}