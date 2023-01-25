package mwilson.fxschedule.Model;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;




    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String division, String country) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    public int getCustomerID(){
        return customerID;
    }
    public void setCustomerID(int id){
        this.customerID = id;
    }
    public String getCustomerName(){
        return customerName;
    }
    public void setCustomerName(String name){
        this.customerName = customerName;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String a){
        this.address = address;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public void setPostalCode(String c){
        this.postalCode = c;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String pno){
       this.phone = pno;
    }
    public String getDivision() {
        return division;
    }
    public void setDivision(String d){
        this.division = d;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String c){
        this.country = c;
    }

    public String toString(){
        return customerName;
    }
}
