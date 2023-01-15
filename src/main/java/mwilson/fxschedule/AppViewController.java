package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.Model.Appointment;
import mwilson.fxschedule.Model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppViewController implements Initializable {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker startDateBox;
    public DatePicker endDateBox;
    public TextArea descriptionArea;
    public ComboBox<Contact> contactBox;

    public static Appointment selectedAppointment;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSelectedAppointment(selectedAppointment);
    }

    public void setSelectedAppointment(Appointment selectedAppointment){
        idField.setText(Integer.toString(selectedAppointment.getAppointmentId()));
        titleField.setText(selectedAppointment.getTitle());
        locationField.setText(selectedAppointment.getLocation());
        typeField.setText(selectedAppointment.getType());
        descriptionArea.setText(selectedAppointment.getDescription());
    }
    public void OnCancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Directory");
            stage.setScene(scene);
            stage.show();
        }
    }

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
