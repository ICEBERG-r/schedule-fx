package mwilson.fxschedule.Model;

public class User{
    private int userID;
    private String username;
    private String password;

    public User(int userID, String username, String password){
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    public int getUserID(){
        return userID;
    }
    public String getUserName(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String toString() {
        return username;
    }
}

