package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField userNameField;
    public TextField passwordField;
    public Button logInButton;
    public Button exitButton;


    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnExitClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)){
            System.exit(0);
        }

    }

    public void OnLogInClicked(ActionEvent actionEvent) {
        //if username and password combination is valid, move to next screen
        //else display incorrect login alert
    }
}