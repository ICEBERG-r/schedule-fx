package mwilson.fxschedule.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection connection = null;


    public static void startConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
            } catch(ClassNotFoundException | SQLException e) {
                System.out.println("Error:" + e.getMessage());
            }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
