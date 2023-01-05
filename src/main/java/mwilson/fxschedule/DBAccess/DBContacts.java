package mwilson.fxschedule.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyPermission;

public class DBContacts {
    public static ObservableList<Contact> getAllCustomers(){
        ObservableList<Contact> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from Contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact C = new Contact(contactID, contactName, email);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }

    public static int insert(String contactName, String email) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES(?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ps.setString(2, email);
        return ps.executeUpdate();
    }

    public static int update(int contactID, String contactName, String email) throws SQLException {
        String sql = "UPDATE contacts SET Contact_Name = ? Email = ? WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ps.setString(2, email);
        ps.setInt(3, contactID);
        return ps.executeUpdate();
    }

    public static int delete(int contactID) throws SQLException {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, contactID);
        return ps.executeUpdate();
    }
}
