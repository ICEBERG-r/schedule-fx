package mwilson.fxschedule.Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Helper {

    public static void DisplayInfoAlert(String title, String elaboration){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(elaboration);
        alert.showAndWait();
    }

    public static void ExitProgramPrompt(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(( response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        }));

    }

    public static void ExitProgramPromptFrench(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter?");
        alert.showAndWait().ifPresent(( response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        }));
    }

}
