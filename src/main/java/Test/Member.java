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

public class Member extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    private boolean isMenuVisible = true; // Tracks the visibility of the sidebar

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Member Interface");

        // Create side menu
        VBox sideMenu = new VBox();
        sideMenu.setStyle("-fx-background-color: #393969; -fx-padding: 10;");
        sideMenu.setSpacing(10);

        // Create buttons for activity reservation
        Button reserveActivityButton = createButtonWithIcon("Reserve An Activity", "/activity.png", "/Reservation.fxml");
        Button managePaymentButton = createButtonWithIcon("Gérer le paiement", "/payment.png", "/GererPaiement.fxml");
        Button calculateCaloriesButton = createButtonWithIcon("Calcul calories", "/calories.png", "/CalculCalories.fxml");
        Button reservedActivitiesButton = createButtonWithIcon("Planning","/activity.png","activites_reservees.fxml");

        // Add all buttons to the menu
        sideMenu.getChildren().addAll(
                reserveActivityButton,
                managePaymentButton,
                calculateCaloriesButton,
                reservedActivitiesButton
        );

        // Create toggle button (burger menu)
        Button toggleButton = new Button("\u2630"); // Unicode for burger menu icon
        toggleButton.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-text-fill: white;");
        toggleButton.setOnAction(e -> toggleMenu(sideMenu));

        // Add logo
        ImageView logoView = new ImageView();
        try {
            Image logo = new Image(getClass().getResourceAsStream("/logo.png"));
            logoView.setImage(logo);
            logoView.setFitWidth(40);
            logoView.setFitHeight(40);
        } catch (Exception e) {
            System.err.println("Logo not found: /logo.png");
        }

        // Create sign-out button
        Button signOutButton = new Button("Sign Out");
        signOutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        signOutButton.setOnMouseEntered(e -> signOutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #c0392b;"));
        signOutButton.setOnMouseExited(e -> signOutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e74c3c;"));
        signOutButton.setOnAction(e -> primaryStage.close()); // Close application on sign-out

        // Add toggle button, logo, and sign-out button to a header bar
        Region spacer = new Region();
        HBox headerBar = new HBox(10, logoView, toggleButton, spacer, signOutButton);
        headerBar.setStyle("-fx-background-color: #393969; -fx-padding: 10;");

        // Create main content area
        BorderPane layout = new BorderPane();
        layout.setTop(headerBar);
        layout.setLeft(sideMenu);

        // Placeholder center content
        Label placeholderContent = new Label("Welcome to the Member Interface");
        placeholderContent.setStyle("-fx-font-size: 16px;");
        layout.setCenter(placeholderContent);

        // Set initial scene
        mainScene = new Scene(layout, 800, 600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // Helper methods to create buttons and toggle menu
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
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #393969; -fx-text-fill: white;");
        button.setOnAction(e -> loadView(fxmlPath));
        return button;
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
            errorLabel.setStyle("-fx-text-fill: red;");
            ((BorderPane) mainScene.getRoot()).setCenter(errorLabel);
        }
    }

    // Method to open Member Interface from Main
    public static void openMemberInterface() throws Exception {
        Member memberInterface = new Member();
        Stage stage = new Stage();
        memberInterface.start(stage); // Start Member interface
    }
}
