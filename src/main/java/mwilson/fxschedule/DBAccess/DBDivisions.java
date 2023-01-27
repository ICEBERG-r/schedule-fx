package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBDivisions {

    public static String tableName = "first_level_divisions";
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

    public static String DivisionIDtoDivisionName(int divisionId) throws SQLException {
        ResultSet rs = null;
        String divisionName = null;
        try {
            String sql = "SELECT Division FROM " + tableName + " WHERE Division_ID = " + divisionId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                divisionName = rs.getString("Division");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionName;
    }
    public static int DivisionIDtoCountryID(int divisionId) throws SQLException {
        ResultSet rs = null;
        int countryId = 0;
        try {
            String sql = "SELECT Division FROM " + tableName + " WHERE Division_ID = " + divisionId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                countryId = rs.getInt("Country_ID");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryId;
    }
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
