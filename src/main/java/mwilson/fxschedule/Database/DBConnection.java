package mwilson.fxschedule.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06YG0";

    private static final String jbdcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "U06YG0";
    private static Connection conn = null;

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jbdcURL, username, Password.getPassword());

            System.out.println("Connection Successful");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        }
        catch (Exception e) {

        }
    }
}
