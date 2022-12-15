package Model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }
    public void setContactID(int contactID){
        this.contactID = contactID;
    }
    public int getContactID(){
        return contactID;
    }
    public void setContactName(String contactName){
        this.contactName = contactName;
    }
    public String getContactName(){
        return contactName;
    }
    public void setContactEmail(String email){
        this.email = email;
    }
    public String getContactEmail(){
        return email;
    }
}
