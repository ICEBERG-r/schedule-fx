package mwilson.fxschedule;

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
import mwilson.fxschedule.Model.Appointment;
import mwilson.fxschedule.Model.Customer;
import mwilson.fxschedule.Utilities.DynamicTable;
import mwilson.fxschedule.Utilities.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public TableView<Appointment> appointmentTable;
    public RadioButton allAppointmentsRadio;
    public ToggleGroup appointments;
    public RadioButton thisWeekRadio;
    public RadioButton thisMonthRadio;

    public TableColumn<Object, Object> appointmentIdColumn;
    public TableColumn<Object, Object> appointmentTitleColumn;
    public TableColumn<Object, Object> appointmentDescriptionColumn;
    public TableColumn<Object, Object> appointmentLocationColumn;
    public TableColumn<Object, Object> appointmentContactColumn;
    public TableColumn<Object, Object> appointmentTypeColumn;
    public TableColumn<Object, Object> appointmentStartColumn;
    public TableColumn<Object, Object> appointmentEndColumn;
    public TableColumn<Object, Object> appointmentCustomerIdColumn;
    public TableColumn<Object, Object> appointmentUserIdColumn;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            customerTable.setItems(DynamicTable.buildData(DBCustomers.tableName, customerTable));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            appointmentTable.setItems(DynamicTable.buildData(DBAppointments.tableName, appointmentTable));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AppointmentView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Directory");
        stage.setScene(scene);
        stage.show();
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

    public void OnDeleteCustomerButtonClicked(ActionEvent actionEvent) {
    }

    public void OnDeleteAppointmentButtonClicked(ActionEvent actionEvent) {
    }

    public void OnReportsClicked(ActionEvent actionEvent) {
    }

    public void onAllAppointmentsSelected(ActionEvent actionEvent) {
    }

    public void OnThisWeekSelected(ActionEvent actionEvent) {
    }

    public void OnThisMonthSelected(ActionEvent actionEvent) {
    }
}
