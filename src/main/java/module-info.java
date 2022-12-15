module com.example.schedulefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.schedulefx to javafx.fxml;
    exports com.schedulefx;
    exports Model;
    opens Model to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
}