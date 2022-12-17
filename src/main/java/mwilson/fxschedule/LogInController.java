package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LogInController implements Initializable {


    public PasswordField passwordLoginField;
    public TextField usernameLoginField;
    public Button loginButton;
    public Button exitButton;
    public Label zoneIDLabel;

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

    public void OnLoginButtonClicked(ActionEvent actionEvent) throws IOException {
        //if username and password combination is valid, move to next screen
        //else display incorrect login alert
        //Temporarily moves to directory view without valid login/pw
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Directory");
        stage.setScene(scene);
        stage.show();
    }
}