package mwilson.fxschedule;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBCountries;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Country;

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
    public Button dbTest;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnExitClicked(ActionEvent actionEvent) {

        //check for language using a switch statement. only EN or FR for this assignment
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
        //else check for language and display incorrect login alert for that language
        //Temporarily moves to directory view without valid login/pw
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Directory");
        stage.setScene(scene);
        stage.show();
    }

    public void OnTestClicked(ActionEvent actionEvent) {

        ObservableList<Country> countryList = DBCountries.getAllCountries();
        for (Country C: countryList){
            System.out.println("ID: " + C.getCountryID() + "  Name: " + C.getCountry());
        }
    }
}