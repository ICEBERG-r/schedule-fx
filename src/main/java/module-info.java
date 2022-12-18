module mwilson.fxschedule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mwilson.fxschedule to javafx.fxml;
    exports mwilson.fxschedule;
}