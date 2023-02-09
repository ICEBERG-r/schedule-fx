package mwilson.fxschedule.Model;

/**
 * This class contains the model for a FirstLevelDivision object
 */
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

    public String toString() { return division; }

}