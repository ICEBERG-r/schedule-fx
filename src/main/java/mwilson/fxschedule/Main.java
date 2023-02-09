package mwilson.fxschedule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mwilson.fxschedule.Database.DBConnection;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Locale.setDefault(new Locale("fr", "FR"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Schedule FX 3000");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entrance point of the application.
     * Starts the connection with the MySQL database and launches the application.
     * Terminates the connection upon the program's exit.
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch();
        DBConnection.closeConnection();
    }
}