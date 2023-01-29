package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBCountries;
import mwilson.fxschedule.DBAccess.DBCustomers;
import mwilson.fxschedule.DBAccess.DBDivisions;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Country;
import mwilson.fxschedule.Model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustCreateController implements Initializable {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public ComboBox<Country> countryCombo;
    public ComboBox<FirstLevelDivision> divisionCombo;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(DBCountries.getAllCountries());


    }

    public void OnCancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Directory");
            stage.setScene(scene);
            stage.show();
        }
    }

    //Does not save info yet. Returns to Directory.
    public void OnSaveButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save");
        alert.setHeaderText("Are you sure you want to save?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)){
            int divisionID = divisionCombo.getValue().getDivisionID();
            DBCustomers.insert(nameField.getText(), addressField.getText(), postalField.getText(), phoneField.getText(),
                    divisionID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Directory");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void OnCountrySelected(ActionEvent event) {
        divisionCombo.valueProperty().set(null);
        divisionCombo.getItems().removeAll(divisionCombo.getItems());
        Country selectedCountry = countryCombo.getValue();

        try {
            String sql = "SELECT * from " + DBDivisions.tableName + " WHERE COUNTRY_ID = " + selectedCountry.getCountryID();

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                FirstLevelDivision D = new FirstLevelDivision(divisionID, division);
                divisionCombo.getItems().add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
