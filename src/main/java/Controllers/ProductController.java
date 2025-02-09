package Controllers;

import Entite.Product;
import Services.ServiceProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.SQLException;
import java.util.List;

public class ProductController {

    @FXML
    private TableView<Product> tableID;
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
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField quantityField;

    private final ServiceProduct serviceProduct = new ServiceProduct();

    @FXML
    void addProduct(ActionEvent event) {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            String type = typeField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            Product product = new Product(name, description, price, type, quantity);
            serviceProduct.ajouter(product);
            refreshTable();
            clearFields();

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du produit.");
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Product selectedProduct = tableID.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceProduct.supprimer(selectedProduct);
                        refreshTable();
                        showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit supprimé avec succès !");
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression du produit.");
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un produit à supprimer.");
        }
    }


    @FXML
    void updateProduct(ActionEvent event) {
        Product selectedProduct = tableID.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                selectedProduct.setNom(nameField.getText());
                selectedProduct.setDescription(descriptionField.getText());
                selectedProduct.setPrix(Double.parseDouble(priceField.getText()));
                selectedProduct.setTypeProduit(typeField.getText());
                selectedProduct.setQuantiteDisponible(Integer.parseInt(quantityField.getText()));

                serviceProduct.update(selectedProduct);
                refreshTable();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit mis à jour avec succès !");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour du produit.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un produit à modifier.");
        }
    }

    @FXML
    void initialize() {
        // Configurer les colonnes du tableau pour afficher les données des produits
        idColumn.setCellValueFactory(new PropertyValueFactory<>("produitId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeProduit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteDisponible"));

        // Rafraîchir le tableau pour afficher les produits
        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Product> products = serviceProduct.getAll();
            ObservableList<Product> observableList = FXCollections.observableList(products);
            tableID.setItems(observableList);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("produitId"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeProduit"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteDisponible"));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les produits.");
        }
    }

    @FXML
    void loadDataToForm(MouseEvent event) {
        Product selectedProduct = tableID.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            nameField.setText(selectedProduct.getNom());
            descriptionField.setText(selectedProduct.getDescription());
            priceField.setText(String.valueOf(selectedProduct.getPrix()));
            typeField.setText(selectedProduct.getTypeProduit());
            quantityField.setText(String.valueOf(selectedProduct.getQuantiteDisponible()));
        }
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        typeField.clear();
        quantityField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
