package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReservationController {

    @FXML
    private TableColumn<?, ?> activityColumnID;

    @FXML
    private TextField activityId;

    @FXML
    private TableColumn<?, ?> dateColumnID;

    @FXML
    private DatePicker dateID;

    @FXML
    private TableColumn<?, ?> memberColumnID;

    @FXML
    private TextField memberID;

    @FXML
    private TableView<?> tableID;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }

}
