package ca.cmpt213;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddSuperhuman {

    public static  void openAddScene(Stage primaryStage) {

        Stage addStage = new Stage();
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add New Superhuman");

        GridPane addGridPane = new GridPane();
        addGridPane.setHgap(10);
        addGridPane.setVgap(10);
        addGridPane.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        addGridPane.setAlignment(javafx.geometry.Pos.CENTER);

        Label addSuperSection = new Label("Fill out all the fields below to add a new superhuman:");
        addSuperSection.setStyle("-fx-font-weight: bold;");

        Label nameLabel = new Label("Name:");
        Label weightLabel = new Label("Weight:");
        Label heightLabel = new Label("Height:");
        Label pictureURLabel = new Label("Picture URL:");
        Label categoryLabel = new Label("Category:");
        Label overallAbility = new Label("Overall Ability:");

        TextField nameField = new TextField();
        TextField weightField = new TextField();
        TextField heightField = new TextField();
        TextField pictureURLField = new TextField();
        TextField categoryField = new TextField();
        TextField overallAbilityField = new TextField();

        Button addButton = new Button("Add");
        
        addGridPane.add(addSuperSection, 0, 0, 2, 1);
        addGridPane.add(nameLabel, 0, 1);
        addGridPane.add(nameField, 1, 1);
        addGridPane.add(weightLabel, 0, 2);
        addGridPane.add(weightField, 1, 2);
        addGridPane.add(heightLabel, 0, 3);
        addGridPane.add(heightField, 1, 3);
        addGridPane.add(pictureURLabel, 0, 4);
        addGridPane.add(pictureURLField, 1, 4);
        addGridPane.add(categoryLabel, 0, 5);
        addGridPane.add(categoryField, 1, 5);
        addGridPane.add(overallAbility, 0, 6);
        addGridPane.add(overallAbilityField, 1, 6);
        addGridPane.add(addButton, 1, 7);


        addButton.setOnAction(e -> {
            boolean correctInput = checkUserInput(weightField, heightField, pictureURLField, overallAbilityField);
            if (correctInput) {
                boolean data_sent = addSuperToServer(nameField, weightField, heightField, pictureURLField, categoryField, overallAbilityField);
                if (data_sent) {
                    addStage.close();
                    primaryStage.toFront();
                } else {
                    System.out.println("Incorrect input for new superhuman");
                    nameField.clear();
                    weightField.clear();
                    heightField.clear();
                    pictureURLField.clear();
                    categoryField.clear();
                    overallAbilityField.clear();
                }
            } else {
                System.out.println("Incorrect input for new superhuman");
                nameField.clear();
                weightField.clear();
                heightField.clear();
                pictureURLField.clear();
                categoryField.clear();
                overallAbilityField.clear();
            }
        });
        

        Scene addScene = new Scene(addGridPane, 450, 350);
        addStage.setResizable(false);
        addStage.setScene(addScene);

        addStage.setOnCloseRequest(event -> primaryStage.toFront());
        addStage.showAndWait();
    }


    private static boolean addSuperToServer(TextField nameField, TextField weightField, TextField heightField, TextField pictureURLField, TextField categoryField, TextField overallAbilityField){
        boolean data_sent = false;
        try {
                URL url = new URI("http://localhost:8080/api/superhuman/add").toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write("{\"name\":\"" + nameField.getText() + "\",\"weight\":\"" + weightField.getText() + 
                "\",\"height\":\"" + heightField.getText() + "\",\"pictureURL\":\"" + pictureURLField.getText() + "\",\"category\":\"" + categoryField.getText() + 
                "\",\"overallAbility\":\"" + overallAbilityField.getText() + "\"}");
                writer.flush();
                writer.close();
                connection.connect();
                System.out.println(connection.getResponseCode());
                connection.disconnect();
                data_sent = true;
                if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                    String message = "Successfully added new superhuman.";
                    AlertMessage.showComplete(message);
                }
            } catch (IOException ioException) {
                data_sent = false;
                ioException.printStackTrace();
                String error = "Error connecting to the server.";
                AlertMessage.showAlert(error);
            } catch (URISyntaxException uriException) {
                data_sent = false;
                uriException.printStackTrace();
                String error = "Incorrect server URL.";
                AlertMessage.showAlert(error);
            }
            return data_sent;
    }

    private static boolean checkUserInput(TextField weightField, TextField heightField, TextField pictureURLField, TextField overallAbilityField){
        if (weightField.getText().isEmpty() || heightField.getText().isEmpty() || pictureURLField.getText().isEmpty() || overallAbilityField.getText().isEmpty()){
            String error = "Please fill out all the fields.";
            AlertMessage.showAlert(error);
            return false;
        } else {
            try {
                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                Image image = new Image(pictureURLField.getText());
                int overallAbility = Integer.parseInt(overallAbilityField.getText());

                if (weight < 0 || height < 0 || overallAbility < 0){
                    String error = "Make sure weight, height and overallAbility are positive.";
                    AlertMessage.showAlert(error);
                    return false;
                }
                
            } catch (Exception exception){
                String error = "Incorrect input for some of the fields.";
                AlertMessage.showAlert(error);
                return false;
            }
            return true;
        }
    }
}
