package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBDivisions {
    public static ObservableList<FirstLevelDivision> getAllDivisions(){
        ObservableList<FirstLevelDivision> dlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from FIRST-LEVEL DIVISIONS";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                FirstLevelDivision D = new FirstLevelDivision(divisionID, divisionName);
                dlist.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return dlist;
    }
}
