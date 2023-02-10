package mwilson.fxschedule.Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * This class contains various methods that are used throughout the application
 */
public class Helper {
    /**
     * Displays an informational alert box
     * @param title The title of the alert. Displays prominently in the alert window.
     * @param elaboration a further elaboration on the alert
     */
    public static void DisplayInfoAlert(String title, String elaboration){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(elaboration);
        alert.showAndWait();
    }

    /**
     * Displays an alert window confirming that the user wishes to exit the program.
     * Terminates the program when OK is clicked.
     * LAMBDA - This Event handler lambda slightly reduces the amount of code needed to execute the event.
     */
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

    /**
     * Displays an alert window in French confirming that the user wishes to exit the program.
     * Terminates the program when OK is clicked.
     * LAMBDA - This Event handler lambda slightly reduces the amount of code needed to execute the event.
     */
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
