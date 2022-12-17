module mwilson.fxschedule {
    requires javafx.controls;
    requires javafx.fxml;


    opens mwilson.fxschedule to javafx.fxml;
    exports mwilson.fxschedule;
}