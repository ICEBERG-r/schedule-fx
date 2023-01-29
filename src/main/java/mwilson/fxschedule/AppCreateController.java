package mwilson.fxschedule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBAppointments;
import mwilson.fxschedule.DBAccess.DBContacts;
import mwilson.fxschedule.DBAccess.DBCustomers;
import mwilson.fxschedule.DBAccess.DBUsers;
import mwilson.fxschedule.Model.Contact;
import mwilson.fxschedule.Model.Customer;
import mwilson.fxschedule.Model.User;
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppCreateController implements Initializable {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField typeField;
    public TextArea descriptionArea;
    public ComboBox<Contact> contactBox;
    public DatePicker datePicker;
    public ComboBox<LocalTime> startTimeBox;
    public ComboBox<LocalTime> endTimeBox;
    public ComboBox<Customer> customerBox;
    public ComboBox<User> userBox;

    public void initialize(URL url, ResourceBundle resourceBundle){
        contactBox.setItems(DBContacts.getAllContacts());
        customerBox.setItems(DBCustomers.getAllCustomers());
        userBox.setItems(DBUsers.getAllUsers());
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,45);

        while (!start.equals(end)){
            startTimeBox.getItems().add(start);
            endTimeBox.getItems().add(start);
            start = start.plusMinutes(15);
        }

        startTimeBox.getItems().add(LocalTime.of(23,45));
        endTimeBox.getItems().add(LocalTime.of(23,45));


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

    public void OnSaveButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save");
            alert.setHeaderText("Are you sure you want to save?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(ButtonType.OK)){
                LocalDateTime start = LocalDateTime.of(datePicker.getValue(),startTimeBox.getValue());
                LocalDateTime end = LocalDateTime.of(datePicker.getValue(), endTimeBox.getValue());
                LocalTime startTime = startTimeBox.getValue();
                LocalTime endTime = endTimeBox.getValue();

                if ((Objects.equals(datePicker.getValue().getDayOfWeek(), DayOfWeek.SATURDAY)) ||
                        (Objects.equals(datePicker.getValue().getDayOfWeek(), DayOfWeek.SUNDAY))){
                    Helper.DisplayInfoAlert("Error!", "Appointments can only be " +
                            "scheduled on Monday through Friday.");
                }
                else if (startTime.atOffset(ZoneOffset.of("-5"))
                        .isBefore(OffsetTime.of(8,0,0,0,ZoneOffset.of("-5")))
                || endTime.atOffset(ZoneOffset.of("-5"))
                        .isAfter(OffsetTime.of(22,0,0,0,ZoneOffset.of("-5")))){
                    Helper.DisplayInfoAlert("Error!", "Appointments must occur during business hours - " +
                            "8:00 AM - 10:00 PM EST");
                } else {
                    DBAppointments.insert(titleField.getText(),descriptionArea.getText(), locationField.getText(),
                            typeField.getText(), start, end, customerBox.getValue().getCustomerID(), userBox.getValue().getUserID(),
                            contactBox.getValue().getContactID());
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
                    Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Directory");
                    stage.setScene(scene);
                    stage.show();
                }



            }
        }
    }

