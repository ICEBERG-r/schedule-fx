package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class contains methods that interact with the customers table in the MySQL database.
 */
public class DBCustomers {
    /**
     * Returns an ObservableList of all Customers in the MySQL database
     * @return an ObservableList of all Customers in the MySQL database
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> clist = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.phone, d.division, r.Country FROM customers c
                    JOIN first_level_divisions d on c.Division_ID = d.Division_ID
                    JOIN countries r on d.Country_ID = r.Country_ID;""";

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

    /**
     * Inserts a new customer into the MySQL database.
     * @param name the name of the customer
     * @param address the address of the customer. Does not include postal code, division, or country information
     * @param postalCode the postal code of the customer
     * @param phone the customer's phone number
     * @param divisionID the ID of the division in which the customer is located
     * @throws SQLException if the customer is unable to be inserted due to an SQL error
     */
    public static void insert(String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.executeUpdate();
    }

    /**
     * Updates the values for an existing customer in the database
     * @param customerID the ID associated with the customer
     * @param name the name of the customer
     * @param address the address of the customer. Does not include postal code, division, or country information
     * @param postalCode the postal code of the customer
     * @param phone the customer's phone number
     * @param divisionID the ID of the division in which the customer is located
     * @throws SQLException if the customer is unable to be updated due to an SQL error
     */
    public static void update(int customerID, String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = """
                UPDATE customers\s
                SET Customer_Name = ?,\s
                Address = ?,\s
                Postal_Code = ?,\s
                Phone = ?,\s
                Division_ID = ?\s
                WHERE Customer_ID = ?""";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        ps.executeUpdate();
    }

    /**
     * Deletes an existing customer from the database. Also deletes all associated appointments in order to avoid
     * Foreign key errors.
     * @param customerID the ID associated with the customer
     * @throws SQLException if the customer is unable to be deleted due to an SQL error
     */
    public static void delete(int customerID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();

        sql = "DELETE FROM customers WHERE Customer_ID = ?";
        ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);

        ps.executeUpdate();
    }
}
