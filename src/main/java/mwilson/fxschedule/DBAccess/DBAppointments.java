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

/**
 * Contains classes and methods that interact with the "appointments" table in the MySQL database.
 */
public class DBAppointments {
    /**
     * Defines an object to be used when running the "Appointments by Month and Type" report.
     *
     */
    public static class AppointmentByMonthType{
        String month;
        String type;
        int count;

        AppointmentByMonthType(String month, String type, int count){
            this.month = month;
            this.type = type;
            this.count = count;
        }

        public String toString(){
            return month + "   " + type + "    " + count;
        }
    }

    /**
     * Defines an object to be used when running the "Appointment by Contact" report.
     */
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

        public LocalDateTime getStart(){
            return start;
        }

        public String toString(){
            return contact + "  Appointment ID:" + appointmentId + "  " + title + "  " + type + "  " + description + "  Start:" +
                    start + "  End:" + end + "  Customer ID:" + customerId;
        }
    }

    /**
     * Defines an object to be used with the "Appointment Count by User" report.
     */
    public static class AppointmentCountByUser{
        String user;
        int count;
        AppointmentCountByUser(String user, int count){
            this.user = user;
            this.count = count;
        }

        public String toString(){
            return user + " " + count;
        }
    }

    /**
     * An array that holds each month as a string value.
     */
    private static final String[] MONTHS_INDEX = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    /**
     * Returns an ObservableList of all appointments in the MySQL database.
     * Converts the timestamp value to a LocalDateTime object.
     * @return an ObservableList of all Appointments in the MySQL database.
     */
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

    /**
     * Inserts a new appointment into the MySQL Database
     * @param title the title of the appointment
     * @param description a description of the appointment
     * @param location the location in which the appointment will take place
     * @param type the type of appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param customerID the customer ID of the customer related to the appointment
     * @param userID the user ID of the user related to the appointment
     * @param contactID the contact ID of the contact related to the appointment
     * @throws SQLException if the appointment is unable to be inserted due to an SQL error
     */
    public static void insert(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
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
        ps.executeUpdate();
    }

    /**
     * Updates the values of an existing appointment in the MySQL database.
     * @param appointmentID the ID of the appointment being updated
     * @param title the title of the appointment
     * @param description a description of the appointment
     * @param location the location in which the appointment will take place
     * @param type the type of appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param customerID the customer ID of the customer related to the appointment
     * @param userID the user ID of the user related to the appointment
     * @param contactID the contact ID of the contact related to the appointment
     * @throws SQLException if the appointment is unable to be updated due to an SQL error
     */
    public static void update(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        String sql = """
                UPDATE appointments\s
                SET Title = ?,\s
                Description = ?,\s
                Location = ?,\s
                Type = ?,\s
                Start = ?,\s
                End = ?,\s
                Customer_ID = ?,\s
                User_ID = ?,\s
                Contact_ID = ?\s
                WHERE Appointment_ID = ?""";
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
        ps.executeUpdate();
    }

    /**
     *
     * @param appointmentID the ID of the appointment being deleted
     * @throws SQLException if the appointment is unable to be deleted due to an SQL error
     */
    public static void delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    /**
     * Returns a List of appointments sorted by month and type along with the count
     * @return a List of appointments sorted by month and type along with the count
     */
    public static List<AppointmentByMonthType> getAppointmentsByMonthAndType(){
        var alist = new ArrayList<AppointmentByMonthType>();

        try {
            String sql = """
                    SELECT MONTH(Start) AS Month, Type, COUNT(*) as Count FROM appointments
                    GROUP BY Month, Type
                    ORDER BY Month, Type""";

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

    /**
     * Returns a schedule of appointments for each contact
     * @return a List of appointments sorted by contact
     */
    public static List<AppointmentByContact> getAppointmentsByContact(){
        ArrayList<AppointmentByContact> alist = new ArrayList<>();

        try {
            String sql = """
                    SELECT * FROM appointments
                    NATURAL JOIN contacts
                    ORDER BY Contact_Name""";

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

    /**
     * Returns a list of usernames and the number of appointments associated with that user
     * @return a List of usernames and the number of appointments associated with that user
     */
    public static List<AppointmentCountByUser> getAppointmentCountByUser(){
        ArrayList<AppointmentCountByUser> alist = new ArrayList<>();

        try {
            String sql = """
                    SELECT u.User_Name, COUNT(*) as Count FROM appointments a
                    JOIN users u on a.User_ID = u.User_ID
                    GROUP BY User_Name
                    ORDER BY User_Name""";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                alist.add(new AppointmentCountByUser(rs.getString("User_Name"), rs.getInt("Count")));

            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return alist;
    }
}
