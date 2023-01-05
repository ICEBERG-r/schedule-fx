package mwilson.fxschedule.Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date start;
    private Date end;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointment(int appointmentID, String title, String description, String location, String type, Date start, Date end){
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    public int getAppointmentId(){
        return appointmentID;
    }
    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    /* public Date getStartTime(){
        return start;
    }
    public void setStartTime(Date start){
        this.start = start;
    }
    public Date getEndTime(){
        return end;
    }
    public void setEndTime(Date end){
        this.end = end;
    }*/
}
