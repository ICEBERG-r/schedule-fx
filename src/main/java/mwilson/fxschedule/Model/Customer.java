package mwilson.fxschedule.Model;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;


    public Customer(int customerID, String customerName, String address, String postalCode, String phone){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getCustomerID(){
        return customerID;
    }
    public String getCustomerName(){
        return customerName;
    }
    public String getAddress(){
        return address;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public String getPhoneNumber(){
        return phone;
    }
}
