package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.Model.Country;
import mwilson.fxschedule.Model.FirstLevelDivision;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class CustCreateController {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public ComboBox<Country> countryCombo;
    public ComboBox<FirstLevelDivision> divisionCombo;

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
    public void OnSaveButtonClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save");
        alert.setHeaderText("Are you sure you want to save?");
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
}
