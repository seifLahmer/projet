// Updated header bar with logo and sign-out button

package Test;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    private boolean isMenuVisible = true; // Tracks the visibility of the sidebar

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Equipment Management System");

        // Create side menu
        VBox sideMenu = new VBox();
        sideMenu.setStyle("-fx-background-color: #393969; -fx-padding: 10;");
        sideMenu.setSpacing(10);

        // Create buttons for equipment management
        Button manageEquipmentButton = createToggleButton("Gérer équipements", "/barbell.png");
        VBox equipmentButtons = new VBox(
                createButtonWithIcon("Afficher équipements", "/barbell.png", "/AfficherEquipements.fxml"),
                createButtonWithIcon("Ajouter équipement", "/plus.png", "/AjouterEquipement.fxml"),
                createButtonWithIcon("Modifier équipement", "/pen.png", "/ModifyEquipment.fxml")
        );
        equipmentButtons.setVisible(false); // Initially hidden

        // Create buttons for maintenance management
        Button manageMaintenanceButton = createToggleButton("Gérer maintenances", "/maintenance.png");
        VBox maintenanceButtons = new VBox(
                createButtonWithIcon("Afficher maintenances", "/maintenance.png", "/AfficherMaintenances.fxml"),
                createButtonWithIcon("Ajouter maintenance", "/plus.png", "/AjouterMaintenance.fxml"),
                createButtonWithIcon("Modifier maintenance", "/pen.png", "/ModifyMaintenance.fxml")
        );
        maintenanceButtons.setVisible(false); // Initially hidden

        // Toggle visibility for equipment buttons
        manageEquipmentButton.setOnAction(e -> toggleVisibility(equipmentButtons));

        // Toggle visibility for maintenance buttons
        manageMaintenanceButton.setOnAction(e -> toggleVisibility(maintenanceButtons));

        // Add all buttons to the menu
        sideMenu.getChildren().addAll(
                manageEquipmentButton,
                equipmentButtons,
                manageMaintenanceButton,
                maintenanceButtons
        );

        // Create toggle button (burger menu)
        Button toggleButton = new Button("\u2630"); // Unicode for burger menu icon
        toggleButton.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-text-fill: white;");
        toggleButton.setOnAction(e -> toggleMenu(sideMenu));

        // Add logo
        ImageView logoView = new ImageView();
        try {
            Image logo = new Image(getClass().getResourceAsStream("/logo.png")); // Replace with your logo path
            logoView.setImage(logo);
            logoView.setFitWidth(40);
            logoView.setFitHeight(40);
        } catch (Exception e) {
            System.err.println("Logo not found: /logo.png");
        }

        // Create sign-out button
        Button signOutButton = new Button("Sign Out");
        signOutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 5 10; -fx-cursor: hand;");
        signOutButton.setOnMouseEntered(e -> signOutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #c0392b; -fx-text-fill: white; -fx-padding: 5 10;"));
        signOutButton.setOnMouseExited(e -> signOutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 5 10;"));
        signOutButton.setOnAction(e -> primaryStage.close()); // Close application on sign-out

        // Add toggle button, logo, and sign-out button to a header bar
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox headerBar = new HBox(10, logoView, toggleButton, spacer, signOutButton);
        headerBar.setStyle("-fx-background-color: #393969; -fx-padding: 10;");

        // Create main content area
        BorderPane layout = new BorderPane();
        layout.setTop(headerBar);
        layout.setLeft(sideMenu);

        // Placeholder center content
        Label placeholderContent = new Label("Welcome to the Equipment Management System");
        placeholderContent.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50;");
        layout.setCenter(placeholderContent);

        // Set initial scene
        mainScene = new Scene(layout, 800, 600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // Other methods remain unchanged
    private Button createButtonWithIcon(String text, String iconPath, String fxmlPath) {
        ImageView iconView = new ImageView();
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            iconView.setImage(icon);
            iconView.setFitWidth(20);
            iconView.setFitHeight(20);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
        }

        Button button = new Button(text, iconView);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #393969; -fx-text-fill: white; -fx-padding: 10; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #3c3c91; -fx-text-fill: white; -fx-padding: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #393969; -fx-text-fill: white; -fx-padding: 10;"));
        button.setOnAction(e -> loadView(fxmlPath));

        return button;
    }

    private Button createToggleButton(String text, String iconPath) {
        ImageView iconView = new ImageView();
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            iconView.setImage(icon);
            iconView.setFitWidth(20);
            iconView.setFitHeight(20);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
        }

        Button button = new Button(text, iconView);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #393969; -fx-text-fill: white; -fx-padding: 10; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #3c3c91; -fx-text-fill: white; -fx-padding: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #393969; -fx-text-fill: white; -fx-padding: 10;"));

        return button;
    }

    private void toggleVisibility(VBox vbox) {
        vbox.setVisible(!vbox.isVisible());
    }

    private void toggleMenu(VBox sideMenu) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), sideMenu);
        if (isMenuVisible) {
            transition.setToX(-200); // Slide out
            isMenuVisible = false;
        } else {
            transition.setToX(0); // Slide in
            isMenuVisible = true;
        }
        transition.play();
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newContent = loader.load();
            ((BorderPane) mainScene.getRoot()).setCenter(newContent);
        } catch (Exception e) {
            e.printStackTrace();
            Label errorLabel = new Label("Failed to load the view: " + fxmlPath);
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            ((BorderPane) mainScene.getRoot()).setCenter(errorLabel);
        }
    }
}
