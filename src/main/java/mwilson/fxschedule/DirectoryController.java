package mwilson.fxschedule;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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
    public TableView<ObservableList> customerTable;
    public TableView<ObservableList> appointmentTable;
    public RadioButton allAppointmentsRadio;
    public ToggleGroup appointments;
    public RadioButton thisWeekRadio;
    public RadioButton thisMonthRadio;


    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection c;
        ObservableList<ObservableList> customerData = FXCollections.observableArrayList();
        ObservableList<ObservableList> appointmentData = FXCollections.observableArrayList();


        try {
            c = DBConnection.getConnection();
            String SQL = "SELECT * FROM customers";

            ResultSet rs = c.createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                customerTable.getColumns().addAll(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                customerData.add(row);
            }

            customerTable.setItems(customerData);
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
        CustViewController.selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
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
        System.out.println(appointmentTable.getSelectionModel().getSelectedItem().toString());
        //AppViewController.selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
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
