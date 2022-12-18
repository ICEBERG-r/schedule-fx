package mwilson.fxschedule.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.DateTimeStringConverter;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Appointment;

import java.sql.*;

public class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from Appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Date start = rs.getDate("Start");
                Date end = rs.getDate("End");
                Appointment A = new Appointment(appointmentID, title, description, location, type, start, end);
                alist.add(A);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return alist;
    }
}
