package mwilson.fxschedule.Model;

//import java.sql.Date;
//import java.sql.Timestamp;

public class FirstLevelDivision {
    private int divisionID;
    private String division;
    //private Date createDate;
    private String createdBy;
    //private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public FirstLevelDivision(int divisionID, String division){
        this.divisionID = divisionID;
        this.division = division;
    }

    public int getDivisionID(){
        return divisionID;
    }
    public String getDivision(){
        return division;
    }

    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }

    public void setDivision(String division){
        this.division = division;
    }
}
