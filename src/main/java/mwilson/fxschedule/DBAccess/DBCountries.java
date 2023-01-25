package mwilson.fxschedule.DBAccess;

import mwilson.fxschedule.Database.DBConnection;
import mwilson.fxschedule.Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBCountries {

    public static String tableName = "countries";
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryID, countryName);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }
    public static String CountryIDtoCountryName(int countryId) throws SQLException {
        ResultSet rs;
        String countryName = null;
        try {
            String sql = "SELECT Country FROM " + tableName + " WHERE Country_ID = " + countryId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                countryName = rs.getString("Country");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryName;
    }
}
