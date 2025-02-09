package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        // Load the first interface (AjouterEquipement.fxml)
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ajouterEquipement.fxml"));
        Parent root1 = loader1.load();
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
        stage1.setTitle("Ajouter Equipement");
        stage1.setScene(scene1);
        stage1.show();

        // Load the second interface (AfficherEquipements.fxml)
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/AfficherEquipements.fxml"));
        Parent root2 = loader2.load();
        Scene scene2 = new Scene(root2);
        Stage stage2 = new Stage();
        stage2.setTitle("Afficher Equipements");
        stage2.setScene(scene2);
        stage2.show();

        // Optionally, load a third interface (ModifyEquipment.fxml) if needed
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/ModifyEquipment.fxml"));
        Parent root3 = loader3.load();
        Scene scene3 = new Scene(root3);
        Stage stage3 = new Stage();
        stage3.setTitle("Modifier Equipement");
        stage3.setScene(scene3);
        stage3.show();
    }
}
