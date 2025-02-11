// Updated header bar with logo and sign-out button

package Test;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Main UI setup (before opening Admin interface)
        //Admin.openAdminInterface();  // Call the Admin interface from Main
        //Member.openMemberInterface();
        Coach.openCoachInterface();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
