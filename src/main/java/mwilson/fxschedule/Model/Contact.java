package mwilson.fxschedule.Model;

/**
 * This class contains the model for a contact object
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactID(){
        return contactID;
    }

    public String toString(){
        return contactName;
    }
}
