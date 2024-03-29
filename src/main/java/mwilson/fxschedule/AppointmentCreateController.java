package mwilson.fxschedule;

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
import mwilson.fxschedule.Model.Appointment;
import mwilson.fxschedule.Model.Contact;
import mwilson.fxschedule.Model.Customer;
import mwilson.fxschedule.Model.User;
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppointmentCreateController implements Initializable {
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

    public boolean isOverlapping;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactBox.setItems(DBContacts.getAllContacts());
        customerBox.setItems(DBCustomers.getAllCustomers());
        userBox.setItems(DBUsers.getAllUsers());

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 45);

        while (!startTime.equals(endTime)) {
            startTimeBox.getItems().add(startTime);
            endTimeBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        startTimeBox.getItems().add(LocalTime.of(23, 45));
        endTimeBox.getItems().add(LocalTime.of(23, 45));


    }

    /**
     * When the cancel button is clicked, an alert appears to confirm cancellation of the appointment creation.
     * If the user selects OK, the program navigates back to the Directory scene.
     * LAMBDA - This Event handler lambda slightly reduces the amount of code needed to execute the event.
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
     * When the save button is clicked, an alert appears to confirm saving the appointment.
     * If OK is clicked, the appointment is added to the database and the program navigates back to the
     * Directory scene.
     * LAMBDA - This Event handler lambda slightly reduces the amount of code needed to execute the event.
     * LAMBDA - The "For Each" lambda reduces the lines of code necessary to execute the loop.
     */
    public void OnSaveButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save");
        alert.setHeaderText("Are you sure you want to save?");
        alert.showAndWait().ifPresent((response -> {
            if (response == ButtonType.OK){
                try {
                    LocalDateTime start = LocalDateTime.of(datePicker.getValue(), startTimeBox.getValue());
                    LocalDateTime end = LocalDateTime.of(datePicker.getValue(), endTimeBox.getValue());
                    Instant instantStart = Timestamp.valueOf(start).toInstant();
                    Instant instantEnd = Timestamp.valueOf(end).toInstant();
                    ZoneId zoneId = ZoneId.of(ZoneId.systemDefault().toString());
                    ZonedDateTime startZDT = ZonedDateTime.ofInstant(instantStart, zoneId);
                    ZonedDateTime endZDT = ZonedDateTime.ofInstant(instantEnd, zoneId);
                    ZonedDateTime startEST = startZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
                    ZonedDateTime endEST = endZDT.withZoneSameInstant(ZoneId.of("America/New_York"));

                    isOverlapping = false;
                    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
                    appointments.forEach(appointment -> {
                        if (Objects.equals(customerBox.getValue().getCustomerID(), appointment.getCustomerID())){
                            if ((start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) ||
                                    (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) ||
                                    (start.isAfter(appointment.getStart())) && end.isBefore(appointment.getEnd()) ||
                                    (start.isBefore(appointment.getStart()) && end.isAfter(appointment.getEnd())) ||
                                    (start.equals(appointment.getStart())) || (end.equals(appointment.getEnd()))) {
                                isOverlapping = true;
                            }
                        }});

                    if (typeField.getText().isEmpty() || descriptionArea.getText().isEmpty() || locationField.getText().isEmpty()
                            || typeField.getText().isEmpty() || customerBox.getValue() == null || userBox.getValue() == null ||
                            contactBox.getValue() == null) {
                        Helper.DisplayInfoAlert("Error!","All fields must be filled.");
                    } else if ((Objects.equals(startEST.getDayOfWeek(), DayOfWeek.SATURDAY)) ||
                            (Objects.equals(startEST.getDayOfWeek(), DayOfWeek.SUNDAY))) {
                        Helper.DisplayInfoAlert("Error!", "Appointments can only be " +
                                "scheduled on Monday through Friday.");
                    } else if (startEST.toLocalTime().isBefore(LocalTime.of(8, 0)) ||
                            endEST.toLocalTime().isAfter(LocalTime.of(22, 0))) {
                        Helper.DisplayInfoAlert("Error!", "Appointments must occur during business hours - " +
                                "8:00 - 22:00 EST");
                    } else if (start.isAfter(end)) {
                        Helper.DisplayInfoAlert("Error!", "Meeting must start before it can end.");
                    } else if (isOverlapping) {
                        Helper.DisplayInfoAlert("Error!", "Customer has another meeting at this time.");
                    } else {

                        DBAppointments.insert(titleField.getText(), descriptionArea.getText(), locationField.getText(),
                                typeField.getText(), start, end, customerBox.getValue().getCustomerID(), userBox.getValue().getUserID(),
                                contactBox.getValue().getContactID());
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Directory");
                        stage.setScene(scene);
                        stage.show();

                    }
                } catch (SQLException e) {
                    Helper.DisplayInfoAlert("Error!", "SQL Error!");
                } catch (Exception e) {
                    Helper.DisplayInfoAlert("Error!", "All fields must be filled.");
                }
            }
        }));
    }
}

