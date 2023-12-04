package ca.cmpt213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FindSuper {

    public static void setUpFindSuperBox(HBox init_scene, TextField findSuperField){
        if (init_scene.getChildren().size() > 1) {
            init_scene.getChildren().remove(1);
        }

        boolean ID_correct = checkID(findSuperField);
        if (ID_correct) {
            getExactSuperFromServer(init_scene, findSuperField);
        }
        
    }


    private static void getExactSuperFromServer(HBox init_window, TextField findSuperField){

        try {
                URL url = new URI("http://localhost:8080/api/superhuman/" + findSuperField.getText()).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                Gson gson = new Gson();
                Superhuman superhuman = gson.fromJson(line, new TypeToken<Superhuman>() {}.getType());
                displaySuperhuman(superhuman, init_window);
                }
                System.out.println(connection.getResponseCode());
                connection.disconnect();

            } catch (IOException ioException){
                ioException.printStackTrace();
                String error = "Error connecting to the server.";
                AlertMessage.showAlert(error);
            } catch (URISyntaxException uriException){
                uriException.printStackTrace();
                String error = "Incorrect server URL.";
                AlertMessage.showAlert(error);
            } finally{
                findSuperField.clear();
            }
    }


    private static void displaySuperhuman(Superhuman superhuman, HBox hbox) {
        ImageView sImage = new ImageView();
        Label sID = new Label();
        Label sName = new Label();
        Label sWeight = new Label();
        Label sHeight = new Label();
        Label sCategory = new Label();
        Label sOverallAbility = new Label();

        sImage.setImage(new Image(superhuman.getPictureURL()));
        sImage.setFitHeight(500);
        sImage.setFitWidth(350);
        sName.setText(superhuman.getName());
        sID.setText(String.valueOf(superhuman.getId()));
        sWeight.setText(String.valueOf(superhuman.getWeight()));
        sHeight.setText(String.valueOf(superhuman.getHeight()));
        sCategory.setText(superhuman.getCategory());
        sOverallAbility.setText(String.valueOf(superhuman.getOverallAbility()));
        VBox superhumanCard = new VBox(5);

        superhumanCard.getChildren().addAll(sImage, sName, sID, sWeight, sHeight, sCategory, sOverallAbility);
        hbox.getChildren().add(superhumanCard);
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
