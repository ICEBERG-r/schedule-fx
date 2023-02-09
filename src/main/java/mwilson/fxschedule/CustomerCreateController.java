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
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerCreateController implements Initializable {
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

    /**
     * When the cancel button is clicked, an alert appears to confirm cancellation of the customer creation.
     * If the user selects OK, the program navigates back to the Directory scene.
     * LAMBDA COMMENTS
     */
    public void OnCancelButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.showAndWait().ifPresent(( response -> {
            if (response == ButtonType.OK){
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Directory");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }));

        }

    /**
     * When the save button is clicked, an alert appears to confirm saving the customer.
     * If OK is clicked, the customer is created in the database and the program navigates back to the
     * Directory scene.
     */
    public void OnSaveButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save");
        alert.setHeaderText("Are you sure you want to save?");
        alert.showAndWait().ifPresent(( response -> {
            if (response == ButtonType.OK) {
                if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || postalField.getText().isEmpty() ||
                        phoneField.getText().isEmpty() || countryCombo.getValue() == null || divisionCombo.getValue() == null) {
                    Helper.DisplayInfoAlert("Error!", "All fields must be filled");
                } else {
                    try {
                        int divisionID = divisionCombo.getValue().getDivisionID();
                        DBCustomers.insert(nameField.getText(), addressField.getText(), postalField.getText(), phoneField.getText(),
                                divisionID);
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Directory");
                        stage.setScene(scene);
                        stage.show();
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }));
    }

    /**
     * Filters the contents of the division box based on the country that was selected by the user in the country box.
     */
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
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
