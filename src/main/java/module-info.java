module mwilson.fxschedule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens mwilson.fxschedule to javafx.fxml;
    exports mwilson.fxschedule;
    exports mwilson.fxschedule.Model;
}