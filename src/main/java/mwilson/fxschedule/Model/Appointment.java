package mwilson.fxschedule.Model;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;

    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end){
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
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public String getType(){
        return type;
    }
    public Timestamp getStartTime(){
        return start;
    }
    public Timestamp getEndTime(){
        return end;
    }
}
