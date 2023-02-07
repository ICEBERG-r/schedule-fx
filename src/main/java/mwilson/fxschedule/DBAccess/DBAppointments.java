package mwilson.fxschedule.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBAppointments {
    public static class AppointmentByMonthType{
        String month;
        String type;
        int count;

        AppointmentByMonthType(String month, String type, int count){
            this.month = month;
            this.type = type;
            this.count = count;
        }

        public String getMonth(){ return month;}
        public String getType() { return type; }
        public int getCount() { return count; }
        public void setMonth(String month) { this.month = month; }
        public String toString(){
            return month + "   " + type + "    " + count;
        }
    }
    public static class AppointmentByContact{
        String contact;
        int appointmentId;
        String title;
        String type;
        String description;
        LocalDateTime start;
        LocalDateTime end;
        int customerId;

        AppointmentByContact(String contact, int appointmentId, String title, String type, String description,
                             LocalDateTime start, LocalDateTime end, int customerId){
            this.contact = contact;
            this.appointmentId = appointmentId;
            this.title = title;
            this.type = type;
            this.description = description;
            this.start = start;
            this.end = end;
            this.customerId = customerId;
        }

        public String getContact(){
            return contact;
        }
        public int getAppointmentId(){
            return appointmentId;
        }
        public String getTitle(){
            return title;
        }
        public String getType(){
            return type;
        }
        public String getDescription(){
            return description;
        }
        public LocalDateTime getStart(){
            return start;
        }
        public LocalDateTime getEnd(){
            return end;
        }
        public int getCustomerId(){
            return customerId;
        }
        public String toString(){
            return contact + "  Appointment ID:" + appointmentId + "  " + title + "  " + type + "  " + description + "  Start:" +
                    start + "  End:" + end + "  Customer ID:" + customerId;
        }
    }


    private static final String[] MONTHS_INDEX = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End," +
                    " a.Customer_ID, a.User_ID, a.Contact_ID, c.Contact_Name from appointments a\n" +
                    "JOIN contacts c on a.Contact_ID = c.Contact_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");


                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();

                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment A = new Appointment(appointmentID, title, description, location, type,
                        start, end, contactID, contact, customerId, userId);

                alist.add(A);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return alist;
    }

    public static int insert(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        return ps.executeUpdate();
    }
    public static int update(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments \nSET Title = ?, \nDescription = ?, \nLocation = ?, \nType = ?, \nStart = ?, \nEnd = ?, " +
                "\nCustomer_ID = ?, \nUser_ID = ?, \nContact_ID = ? \nWHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, appointmentID);
        return ps.executeUpdate();
    }
    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentID);
        return ps.executeUpdate();
    }

    public static List<AppointmentByMonthType> getAppointmentsByMonthAndType(){
        var alist = new ArrayList<AppointmentByMonthType>();

        try {
            String sql = "SELECT MONTH(Start) AS Month, Type, COUNT(*) as Count FROM appointments\n" +
                    "GROUP BY Month, Type\n" +
                    "ORDER BY Month, Type";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                alist.add(new AppointmentByMonthType((MONTHS_INDEX[rs.getInt("Month") -1]),
                        rs.getString("Type"), rs.getInt("Count")));

            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return alist;
    }

    public static List<AppointmentByContact> getAppointmentsByContact(){
        var alist = new ArrayList<AppointmentByContact>();

        try {
            String sql = "SELECT * FROM appointments\n" +
                    "NATURAL JOIN contacts\n" +
                    "ORDER BY Contact_Name";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                alist.add(new AppointmentByContact(rs.getString("Contact_Name"),
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Type"),
                        rs.getString("Description"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID")));

            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return alist;
    }
}
