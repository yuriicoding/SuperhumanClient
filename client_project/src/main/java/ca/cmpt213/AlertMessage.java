package ca.cmpt213;

import javafx.scene.control.Alert;

public class AlertMessage {
    public static void showAlert(String error_message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Action was not completed!");
        alert.setContentText(error_message);
        alert.showAndWait();
    }

    public static void showComplete(String complete_message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action completed");
        alert.setHeaderText("Server succesfully performed the action.");
        alert.setContentText(complete_message);
        alert.showAndWait();
    }
}
