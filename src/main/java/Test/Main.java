package Test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            URL fxmlUrl = getClass().getResource("/AbonnementLayout.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find AbonnementLayout.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Gestion des Abonnements");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
