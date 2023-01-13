package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBUsers {

    public static String tableName = "users";
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> ulist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User U = new User(userID, userName, password);
                ulist.add(U);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return ulist;
    }
}
