package mwilson.fxschedule.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
