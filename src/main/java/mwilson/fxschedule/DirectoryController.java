package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBCustomers;
import mwilson.fxschedule.Model.Appointment;
import mwilson.fxschedule.Model.Customer;

import java.io.IOException;
import java.net.URL;
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



    @Override public void initialize(URL url, ResourceBundle resourceBundle) {


        cColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cColCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cColPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cColDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        cColCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

        customerTable.setItems(DBCustomers.getAllCustomers());
        System.out.println(DBCustomers.getAllCustomers());


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
        System.out.println(customerTable.getSelectionModel().getSelectedItem());
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
        System.out.println(customerTable.getSelectionModel().getSelectedItem().getPhone());
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
