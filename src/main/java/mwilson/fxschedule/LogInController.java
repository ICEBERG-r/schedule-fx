package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {


    public PasswordField passwordLoginField;
    public TextField usernameLoginField;
    public Button loginButton;
    public Button exitButton;
    public Label zoneIDLabel;
    public Button dbTest;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label titleLabel;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIDLabel.setText(ZoneId.systemDefault().toString());
        System.out.println(Locale.getDefault());

        if (Locale.getDefault().toString().equals("fr_FR")){
            exitButton.setText("Quitter");
            loginButton.setText("Connexion");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("le mot de passe");
            titleLabel.setText("assistante de planification");
        }
    }

    public void OnExitClicked(ActionEvent actionEvent) {

        //check for language using a switch statement. only EN or FR for this assignment
        if (Locale.getDefault().toString().equals("fr_FR")){
            Helper.ExitProgramPromptFrench();
        }
        else {
            Helper.ExitProgramPrompt();
        }
    }

    public void OnLoginButtonClicked(ActionEvent actionEvent) {

        try {
        String uname = usernameLoginField.getText();
        String pw = passwordLoginField.getText();
        String actualpw = null;

        String sql = "SELECT Password FROM Users WHERE User_Name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, uname);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            actualpw = rs.getString("Password");
        }

        if (pw.equals(actualpw)) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Directory");
                stage.setScene(scene);
                stage.show();
            }
            else {
                if (Locale.getDefault().toString().equals("fr_FR")){
                    Helper.DisplayInfoAlert("Erreur d'identification", "La combinaison du nom d'utilisateur et du mot de passe est incorrecte");
                }
                else {
                    Helper.DisplayInfoAlert("Log-in Error", "Username and Password combination is incorrect");
                }
        }
        } catch (Exception e){
            if (Locale.getDefault().toString().equals("fr_FR")){
                Helper.DisplayInfoAlert("Erreur d'identification", "La combinaison du nom d'utilisateur et du mot de passe est incorrecte");
            }
            else {
                Helper.DisplayInfoAlert("Log-in Error", "Username and Password combination is incorrect");
            }
        }





    }

    public void OnTestClicked(ActionEvent actionEvent) throws IOException {

        //Bypasses the login
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Directory");
        stage.setScene(scene);
        stage.show();
    }
}