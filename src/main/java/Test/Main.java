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

        // Create buttons with icons
        Button equipmentButton = createButtonWithIcon("Liste des equipements", "/barbell.png");
        Button memberButton = createButtonWithIcon("Liste des maintenances", "/maintenance.png");
        Button attendanceButton = createButtonWithIcon("Ajouter un equipement", "/plus.png");
        Button reportsButton = createButtonWithIcon("Modifier un equipement", "/pen.png");

        sideMenu.getChildren().addAll(
                equipmentButton,
                memberButton,
                attendanceButton,
                reportsButton
        );

        // Create toggle button (burger menu)
        Button toggleButton = new Button("\u2630"); // Unicode for burger menu icon
        toggleButton.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-text-fill: white;");
        toggleButton.setOnAction(e -> toggleMenu(sideMenu));

        // Add toggle button to a header bar
        HBox headerBar = new HBox(toggleButton);
        headerBar.setStyle("-fx-background-color: #393969; -fx-padding: 10;");
        headerBar.setSpacing(10);

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

        // Button actions to load FXML
        equipmentButton.setOnAction(e -> loadView("/AfficherEquipements.fxml"));
        memberButton.setOnAction(e -> loadView("/AfficherMaintenances.fxml"));
        attendanceButton.setOnAction(e -> loadView("/AjouterEquipement.fxml"));
        reportsButton.setOnAction(e -> loadView("/ModifyEquipment.fxml"));
    }

    /**
     * Creates a button with an icon and text.
     *
     * @param text     The text for the button.
     * @param iconPath The path to the icon image file.
     * @return A styled button with an icon.
     */
    private Button createButtonWithIcon(String text, String iconPath) {
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
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #DFDBD7; -fx-text-fill: white; -fx-padding: 10; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #DFDBD7; -fx-text-fill: white; -fx-padding: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #393969; -fx-text-fill: white; -fx-padding: 10;"));

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
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            ((BorderPane) mainScene.getRoot()).setCenter(errorLabel);
        }
    }
}