package mwilson.fxschedule.Model;

public class FirstLevelDivision {
    private int divisionID;
    private String division;

    public FirstLevelDivision(int divisionID, String division) {
        this.divisionID = divisionID;
        this.division = division;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public String toString() { return division; }

}