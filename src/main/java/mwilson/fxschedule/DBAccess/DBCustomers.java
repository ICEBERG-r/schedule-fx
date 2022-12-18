package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBCustomers {
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from Customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Customer C = new Customer(customerID, customerName, address, postalCode, phone);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }
}
