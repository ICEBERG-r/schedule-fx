package mwilson.fxschedule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import mwilson.fxschedule.Database.DBConnection;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Schedule FX 3000");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBConnection.startConnection();
        launch();
        DBConnection.closeConnection();
    }
}