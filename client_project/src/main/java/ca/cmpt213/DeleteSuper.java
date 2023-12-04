package ca.cmpt213;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DeleteSuper {

    public static void deleteSuperSetup(HBox init_window, TextField deleteSuperField){
        boolean ID_correct = checkID(deleteSuperField);

        if (ID_correct){
            deleteSuperFromServer(init_window, deleteSuperField);
        }

    }

    
    private static void deleteSuperFromServer(HBox init_window, TextField deleteSuperField){
        try {
                URL url = new URI("http://localhost:8080/api/superhuman/" + deleteSuperField.getText()).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.connect();
                System.out.println(connection.getResponseCode());
                connection.disconnect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                    String error = "Requsted ID does not exist on server.";
                    AlertMessage.showAlert(error);
                }
                else if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                    String message = "Superhuman successfully deleted.";
                    AlertMessage.showComplete(message);
                }
                
            } catch (IOException ioException) {
                ioException.printStackTrace();
                String error = "Error connecting to the server.";
                AlertMessage.showAlert(error);
            } catch (URISyntaxException uriException) {
                uriException.printStackTrace();
                String error = "Incorrect server URL.";
                AlertMessage.showAlert(error);
            } finally {
                deleteSuperField.clear();
            }
    }


    private static boolean checkID(TextField findSuperField){
        if (findSuperField.getText().length() == 0) {
            String error = "Please enter a valid ID.";
            AlertMessage.showAlert(error);
            return false;
        } else {
            try {
                int id = Integer.parseInt(findSuperField.getText());
                if (id < 0) {
                    String error = "ID cannot be negative.";
                    AlertMessage.showAlert(error);
                    return false;
                }
            } catch (NumberFormatException nfe) {
                findSuperField.clear();
                String error = "Please enter a valid ID.";
                AlertMessage.showAlert(error);
                return false;
            }
            return true;
        }
    }
    
}
