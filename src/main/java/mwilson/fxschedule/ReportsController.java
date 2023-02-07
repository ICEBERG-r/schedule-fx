package mwilson.fxschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mwilson.fxschedule.DBAccess.DBAppointments;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public Button exitButton;
    public TextArea reportArea;
    public Button generateReportButton;
    public ComboBox<String> reportSelectionBox;

    public void initialize(URL url, ResourceBundle rs) {
        reportSelectionBox.getItems().add("Appointments by type and month");
        reportSelectionBox.getItems().add("Contact Schedule");
        reportSelectionBox.getItems().add("TBD");
    }

    public void onExitClicked(ActionEvent actionEvent) throws IOException {
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
        } else if (Objects.equals(reportSelectionBox.getSelectionModel().getSelectedItem(), "TBD")) {
            reportArea.setText("TBD");
        } else {
            reportArea.setText("Select a report before clicking generate!");
        }
    }

}
