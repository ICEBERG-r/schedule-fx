package mwilson.fxschedule;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.*;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Country;
import mwilson.fxschedule.Model.Customer;
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
        for (Customer C: customerList){
            System.out.println("ID: " + C.getCustomerID() + " Name: " + C.getCustomerName());
        }
    }

    public void OnExitClicked(ActionEvent actionEvent) {

        //check for language using a switch statement. only EN or FR for this assignment
        Helper.ExitProgramPrompt();

    }

    public void OnLoginButtonClicked(ActionEvent actionEvent) throws IOException {

        //log in not working
        //probably need another method of reading the pw from DB
        //Password is "text" in the DB, and not varchar as expected?
        //can the "text" not be converted to a string?
        try {
        String uname = usernameLoginField.getText();
        String pw = passwordLoginField.getText();

        String sql = "SELECT Password FROM Users WHERE User_Name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, uname);
        ResultSet rs = ps.executeQuery();


        String actualPw = (String) rs.getObject("Password");
            System.out.println(uname);
            System.out.println(pw);
            System.out.println(actualPw);
        if (pw.equals(actualPw)) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Directory");
                stage.setScene(scene);
                stage.show();
            }
            else {
                Helper.DisplayInfoAlert("Log-in Error", "Username and Password combination is incorrect");
        }
        } catch (Exception e){
            Helper.DisplayInfoAlert("Log-in Error", "Username and Password combination is incorrect");
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