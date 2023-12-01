package ca.cmpt213;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        Label nameLabel = new Label("Name:");
        Label weightLabel = new Label("Weight:");
        Label heightLabel = new Label("Height:");
        Label pictureURLabel = new Label("Picture URL:");
        Label categoryLabel = new Label("Category:");
        Label overallAbility = new Label("Overall Ability:");

        Label outputLabel = new Label();

        TextField nameField = new TextField();
        TextField weightField = new TextField();
        TextField heightField = new TextField();
        TextField pictureURLField = new TextField();
        TextField categoryField = new TextField();
        TextField overallAbilityField = new TextField();

        Button addButton = new Button("Add");
        Button getButton = new Button("Get");

        Label findSuper = new Label("ID to find:");
        TextField findSuperField = new TextField();
        Button findButton = new Button("Find");

        Label deleteSuper = new Label("ID to delete:");
        TextField deleteSuperField = new TextField();
        Button deleteButton = new Button("Delete");


        getButton.setOnAction(e -> {
            try {
                URL url = new URI("http://localhost:8080/api/superhuman/all").toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                System.out.println(line);
                outputLabel.setText(line);
                System.out.println(connection.getResponseCode());
                connection.disconnect();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (URISyntaxException uriException) {
                uriException.printStackTrace();
            }
        });


        findButton.setOnAction(e -> {
            try {
                URL url = new URI("http://localhost:8080/api/superhuman/" + findSuperField.getText()).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                System.out.println(line);
                outputLabel.setText(line);
                }
                System.out.println(connection.getResponseCode());
                connection.disconnect();

            } catch (IOException ioException){
                ioException.printStackTrace();
            } catch (URISyntaxException uriException){
                uriException.printStackTrace();
            }
        });


        addButton.setOnAction(e -> {
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
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (URISyntaxException uriException) {
                uriException.printStackTrace();
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                URL url = new URI("http://localhost:8080/api/superhuman/" + deleteSuperField.getText()).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.connect();
                System.out.println(connection.getResponseCode());
                connection.disconnect();
                
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (URISyntaxException uriException) {
                uriException.printStackTrace();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(weightLabel, 0, 2);
        grid.add(weightField, 1, 2);
        grid.add(heightLabel, 0, 3);
        grid.add(heightField, 1, 3);
        grid.add(pictureURLabel, 0, 4);
        grid.add(pictureURLField, 1, 4);
        grid.add(categoryLabel, 0, 5);
        grid.add(categoryField, 1, 5);
        grid.add(overallAbility, 0, 6);
        grid.add(overallAbilityField, 1, 6);
        grid.add(addButton, 0, 7);
        grid.add(getButton, 0, 8);
        grid.add(findSuper, 0, 9);
        grid.add(findSuperField, 1, 9);
        grid.add(findButton, 0, 10);
        grid.add(deleteSuper, 0, 11);
        grid.add(deleteSuperField, 1, 11);
        grid.add(deleteButton, 0, 12);


        VBox vbox = new VBox(20, grid, outputLabel);
        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    

}