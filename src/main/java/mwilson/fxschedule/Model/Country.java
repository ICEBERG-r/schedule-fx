package mwilson.fxschedule.Model;

//import java.sql.Date;
//import java.sql.Timestamp;

public class Country {
    private int countryID;
    private String country;
    //private Date createDate;
    private String createdBy;
    //private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;
    }

    public int getCountryID(){
        return countryID;
    }

    public void setCountryID(int countryID){
        this.countryID = countryID;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

}
