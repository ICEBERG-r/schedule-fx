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
    public void setUserID(int userID){
        this.userID = userID;
    }
    public int getUserID(){
        return userID;
    }
    public void setUserName(String username){
        this.username = username;
    }
    public String getUserName(){
        return username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}

