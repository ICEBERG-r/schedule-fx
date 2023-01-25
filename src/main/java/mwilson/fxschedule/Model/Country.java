package mwilson.fxschedule.Model;


public class Country {
    private final int countryID;
    private final String country;

    public Country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;
    }

    public int getCountryID(){
        return countryID;
    }

    public String toString() {return country;}

}
