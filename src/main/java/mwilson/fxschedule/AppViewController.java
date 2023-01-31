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
import java.util.Optional;
import java.util.ResourceBundle;

public class AppViewController implements Initializable {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField typeField;
    public TextArea descriptionArea;
    public ComboBox<Contact> contactBox;

    public static Appointment selectedAppointment;
    public DatePicker datePicker;
    public ComboBox<LocalTime> startTimeBox;
    public ComboBox<LocalTime> endTimeBox;
    public ComboBox<Customer> customerBox;
    public ComboBox<User> userBox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactBox.setItems(DBContacts.getAllContacts());
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,45);

        while (!start.equals(end)){
            startTimeBox.getItems().add(start);
            endTimeBox.getItems().add(start);
            start = start.plusMinutes(15);
        }

        startTimeBox.getItems().add(LocalTime.of(23,45));
        endTimeBox.getItems().add(LocalTime.of(23,45));

        setSelectedAppointment();
    }

    public void setSelectedAppointment(){
        ObservableList<Contact> contactList = DBContacts.getAllContacts();
        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
        ObservableList<User> userList = DBUsers.getAllUsers();
        contactBox.setItems(contactList);
        customerBox.setItems(customerList);
        userBox.setItems(userList);
        contactList.forEach(contact -> {
            if(Objects.equals(contact.toString(), selectedAppointment.getContact())){
                contactBox.setValue(contact);
            }
        });
        customerList.forEach(customer -> {
            if(Objects.equals(customer.getCustomerID(), selectedAppointment.getCustomerID())){
                customerBox.setValue(customer);
            }
        });
        userList.forEach(user -> {
            if(Objects.equals(user.getUserID(), selectedAppointment.getUserID())){
                userBox.setValue(user);
            }
        });
        idField.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        titleField.setText(selectedAppointment.getTitle());
        locationField.setText(selectedAppointment.getLocation());
        typeField.setText(selectedAppointment.getType());
        descriptionArea.setText(selectedAppointment.getDescription());
        startTimeBox.setValue(selectedAppointment.getStart().toLocalTime());
        endTimeBox.setValue(selectedAppointment.getEnd().toLocalTime());
        datePicker.setValue(selectedAppointment.getStart().toLocalDate());
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
            Instant instantStart = Timestamp.valueOf(start).toInstant();
            Instant instantEnd = Timestamp.valueOf(end).toInstant();
            ZoneId zoneId = ZoneId.of(ZoneId.systemDefault().toString());
            ZonedDateTime startZDT = ZonedDateTime.ofInstant(instantStart, zoneId);
            ZonedDateTime endZDT = ZonedDateTime.ofInstant(instantEnd, zoneId);
            ZonedDateTime startEST = startZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime endEST = endZDT.withZoneSameInstant(ZoneId.of("America/New_York"));


            if ((Objects.equals(startEST.getDayOfWeek(), DayOfWeek.SATURDAY)) ||
                    (Objects.equals(startEST.getDayOfWeek(), DayOfWeek.SUNDAY))){
                Helper.DisplayInfoAlert("Error!", "Appointments can only be " +
                        "scheduled on Monday through Friday.");
            }
            else if (startEST.toLocalTime().isBefore(LocalTime.of(8,0)) ||
                    endEST.toLocalTime().isAfter(LocalTime.of(22,0))){
                Helper.DisplayInfoAlert("Error!", "Appointments must occur during business hours - " +
                        "8:00 - 22:00 EST");
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
