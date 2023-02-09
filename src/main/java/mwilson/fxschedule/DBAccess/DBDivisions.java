package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class contains methods that interact with the first_level_divisions table in the MySQL database.
 */
public class DBDivisions {

    public static String tableName = "first_level_divisions";

    /**
     * Returns an ObservableList of all first level divisions in the database
     * @return an ObservableList of all first level divisions in the database
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions(){
        ObservableList<FirstLevelDivision> dlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";

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

    /**
     * Takes the name of a division and returns the ID
     * @param division the name of a first level division
     * @return the ID of the division
     */
    public static int GetIDFromDivision(String division){
        int divisionId = 0;
        try {
            ResultSet rs;
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, division);
            rs = ps.executeQuery();
            if (rs.next()){
                divisionId = rs.getInt("Division_ID");
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return divisionId;
    }
}
