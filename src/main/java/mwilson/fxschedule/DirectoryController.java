package mwilson.fxschedule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBAppointments;
import mwilson.fxschedule.DBAccess.DBCustomers;
import mwilson.fxschedule.DBAccess.DBUsers;
import mwilson.fxschedule.Model.Appointment;
import mwilson.fxschedule.Model.Customer;
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class DirectoryController implements Initializable {
    public Button newCustomerButton;
    public Button viewCustomerButton;
    public Button newAppointmentButton;
    public Button viewAppointmentButton;
    public Button exitButton;
    public Button deleteCustomerButton;
    public Button deleteAppointmentButton;
    public Button reportsButton;

    public TableView<Customer> customerTable;

    public TableColumn<Customer, String> cColCustomerID;
    public TableColumn<Customer, String> cColCustomerName;
    public TableColumn<Customer, String> cColAddress;
    public TableColumn<Customer, String> cColPostalCode;
    public TableColumn<Customer, String> cColPhone;
    public TableColumn<Customer, String> cColDivision;
    public TableColumn<Customer, String> cColCountry;
    public TableView<Appointment> appointmentTable;
    public RadioButton allAppointmentsRadio;
    public ToggleGroup appointments;
    public RadioButton thisWeekRadio;
    public RadioButton thisMonthRadio;
    public TableColumn<Appointment, String> aColAppointmentID;
    public TableColumn<Appointment, String> aColTitle;
    public TableColumn<Appointment, String> aColDescription;
    public TableColumn<Appointment, String> aColLocation;
    public TableColumn<Appointment, String> aColType;
    public TableColumn<Appointment, String> aColStart;
    public TableColumn<Appointment, String> aColEnd;
    public TableColumn<Appointment, String> aColContact;
    public TableColumn<Appointment, String> aColCustomerID;
    public TableColumn<Appointment, String> aColUserID;
    public int userID;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {


        DBUsers.getAllUsers().forEach(user -> {
            if (Objects.equals(user.getUserName(), LogInController.userName)){
                userID = user.getUserID();
            }
        });
        if (LogInController.firstLogin) {
            ObservableList<Appointment> appointmentsSoon = FXCollections.observableArrayList();
            DBAppointments.getAllAppointments().forEach(appointment -> {
                if ((appointment.getUserID() == userID) && appointment.getStart().isAfter(LocalDateTime.now()) &&
                        appointment.getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                    appointmentsSoon.add(appointment);
                    Helper.DisplayInfoAlert("Appointment in the next fifteen minutes", "Appointment " + appointment.getAppointmentID() +
                            " begins at " + appointment.getStart().toLocalTime() + " on " + appointment.getStart().toLocalDate() +
                            ".");

                }
            });
            if (appointmentsSoon.isEmpty()) {
                Helper.DisplayInfoAlert("Alert!", "There are no appointments scheduled in the next fifteen minutes.");
            }
        }

        LogInController.firstLogin = false;


        cColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cColCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cColPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cColDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        cColCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

        aColAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        aColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        aColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        aColLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        aColType.setCellValueFactory(new PropertyValueFactory<>("type"));
        aColStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        aColEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        aColContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        aColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        aColUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        customerTable.setItems(DBCustomers.getAllCustomers());
        appointmentTable.setItems(DBAppointments.getAllAppointments());


    }

    public void OnNewCustomerButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerCreate.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("New Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void OnViewCustomerButtonClicked(ActionEvent actionEvent) throws IOException {
        CustViewController.selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Directory");
        stage.setScene(scene);
        stage.show();
    }

    public void OnNewAppointmentButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AppointmentCreate.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Directory");
        stage.setScene(scene);
        stage.show();
    }

    public void OnViewAppointmentButtonClicked(ActionEvent actionEvent) throws IOException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            AppViewController.selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AppointmentView.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Directory");
            stage.setScene(scene);
            stage.show();
        } else {
            Helper.DisplayInfoAlert("No appointment selected", "An appointment must be selected from the table.");
        }
    }

    public void OnExitButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)){
            System.exit(0);
        }
    }

    public void OnDeleteCustomerButtonClicked(ActionEvent actionEvent) throws SQLException {
        if (!customerTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(ButtonType.OK)) {
                Customer c = customerTable.getSelectionModel().getSelectedItem();
                DBCustomers.delete(c.getCustomerID());
                customerTable.setItems(DBCustomers.getAllCustomers());
                appointmentTable.setItems(DBAppointments.getAllAppointments());
                Helper.DisplayInfoAlert("Customer deleted", c.getCustomerName() + " has been deleted.");
            }
        } else {
            Helper.DisplayInfoAlert("No customer selected", "A customer must be selected from the table.");
        }
    }

    public void OnDeleteAppointmentButtonClicked(ActionEvent actionEvent) throws SQLException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Appointment");
            alert.setHeaderText("Are you sure you want to cancel this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(ButtonType.OK)) {
                Appointment a = appointmentTable.getSelectionModel().getSelectedItem();
                DBAppointments.delete(a.getAppointmentID());
                customerTable.setItems(DBCustomers.getAllCustomers());
                appointmentTable.setItems(DBAppointments.getAllAppointments());
                Helper.DisplayInfoAlert("Appointment Cancelled", "Appointment " + a.getAppointmentID()
                        + " has been cancelled.");
            }
        } else {
            Helper.DisplayInfoAlert("No appointment selected", "An appointment must be selected from the " +
                    "table.");
        }
    }

    public void OnReportsClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Reports.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void onAllAppointmentsSelected(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointments.getAllAppointments());
    }

    public void OnThisWeekSelected(ActionEvent actionEvent) {
        ObservableList<Appointment> all = DBAppointments.getAllAppointments();
        ObservableList<Appointment> thisWeek =FXCollections.observableArrayList();
        all.forEach(appointment -> {
            if (appointment.getStart().isAfter(LocalDateTime.now()) &&
                    appointment.getStart().isBefore(LocalDateTime.now().plusWeeks(1))){
                thisWeek.add(appointment);
            }
        });

        appointmentTable.setItems(thisWeek);
    }

    public void OnThisMonthSelected(ActionEvent actionEvent) {
        ObservableList<Appointment> all = DBAppointments.getAllAppointments();
        ObservableList<Appointment> thisMonth = FXCollections.observableArrayList();

        all.forEach(appointment -> {
            if (appointment.getStart().isAfter(LocalDateTime.now()) &&
                    appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1))){
                thisMonth.add(appointment);
            }
        });

        appointmentTable.setItems(thisMonth);
    }
}
