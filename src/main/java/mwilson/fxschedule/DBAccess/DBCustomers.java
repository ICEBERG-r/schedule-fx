package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBCustomers {


    public static String tableName = "customers";
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.phone, d.division, r.Country FROM customers c\n" +
                    "JOIN first_level_divisions d on c.Division_ID = d.Division_ID\n" +
                    "JOIN countries r on d.Country_ID = r.Country_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                Customer row = new Customer(customerID, customerName, address, postalCode, phone, division, country);
                clist.add(row);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }

    public static int insert(String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        return ps.executeUpdate();
    }

    public static int update(int customerID, String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = "UPDATE customers \nSET Customer_Name = ?, \nAddress = ?, \nPostal_Code = ?, \nPhone = ?, \nDivision_ID = ? " +
                "\nWHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        return ps.executeUpdate();
    }

    public static int delete(int customerID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();

        sql = "DELETE FROM customers WHERE Customer_ID = ?";
        ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);

        return ps.executeUpdate();
    }
}
