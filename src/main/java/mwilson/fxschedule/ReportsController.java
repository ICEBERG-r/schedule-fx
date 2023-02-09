package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBAppointments;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public Button exitButton;
    public TextArea reportArea;
    public Button generateReportButton;
    public ComboBox<String> reportSelectionBox;

    public void initialize(URL url, ResourceBundle rs) {
        reportSelectionBox.getItems().add("Appointments by type and month");
        reportSelectionBox.getItems().add("Contact Schedule");
        reportSelectionBox.getItems().add("Appointment Count by User");
    }

    public void onExitClicked(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Directory.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Directory");
            stage.setScene(scene);
            stage.show();
    }

    public void onGenerateClicked(ActionEvent actionEvent) {
        if (Objects.equals(reportSelectionBox.getSelectionModel().getSelectedItem(), "Appointments by type and month")) {
            StringBuilder output = new StringBuilder();
            output.append("MONTH   TYPE   COUNT\n");
            for (DBAppointments.AppointmentByMonthType appointmentByMonthType : DBAppointments.getAppointmentsByMonthAndType()) {
                assert false;
                output.append(appointmentByMonthType).append("\n");
            }
            reportArea.setText(output.toString());
        } else if (Objects.equals(reportSelectionBox.getSelectionModel().getSelectedItem(), "Contact Schedule")) {
            StringBuilder output = new StringBuilder();
            for (DBAppointments.AppointmentByContact appointmentByContact : DBAppointments.getAppointmentsByContact()){
                assert false;
                output.append(appointmentByContact).append("\n");
            }
            reportArea.setText(output.toString());
        } else if (Objects.equals(reportSelectionBox.getSelectionModel().getSelectedItem(), "Appointment Count by User")) {
            StringBuilder output = new StringBuilder();
            for (DBAppointments.AppointmentCountByUser appointmentCountByUser : DBAppointments.getAppointmentCountByUser()){
                assert false;
                output.append(appointmentCountByUser).append("\n");
            }
            reportArea.setText(output.toString());
        } else {
            reportArea.setText("Select a report before clicking generate!");
        }
    }

}
