package ca.cmpt213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GetAllSupers {

    public static void setUpDisplayAllGrid(HBox init_scene, boolean[] displayList){
        GridPane gallerySupers = new GridPane();
        gallerySupers.setHgap(10);
        gallerySupers.setVgap(10);
        gallerySupers.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gallerySupers);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent;");

        if (init_scene.getChildren().size() > 2) {
            init_scene.getChildren().remove(2);
        }
        init_scene.getChildren().add(scrollPane);
        getAllSupersFromServer(gallerySupers, displayList);

    }

    private static void getAllSupersFromServer(GridPane gallerySupers, boolean[] displayList){
        try {
                URL url = new URI("http://localhost:8080/api/superhuman/all").toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();

                Gson gson = new Gson();
                List<Superhuman> superhumanList = gson.fromJson(line, new TypeToken<List<Superhuman>>() {}.getType());
                displayAllSuperhumans(superhumanList, gallerySupers, displayList);

                System.out.println(connection.getResponseCode());
                connection.disconnect();

            } catch (IOException ioException) {
                ioException.printStackTrace();
                String error = "Error connecting to the server.";
                AlertMessage.showAlert(error);
            } catch (URISyntaxException uriException) {
                uriException.printStackTrace();
                String error = "Incorrect server URL.";
                AlertMessage.showAlert(error);
            }
    }
    
    private static void displayAllSuperhumans(List<Superhuman> superhumanList, GridPane grid, boolean[] displayList){

        int horizontal_counter = 0;
        int vertical_counter = 0;
        for (Superhuman superhuman : superhumanList) {

            ImageView sImage = new ImageView();
            Label sID = new Label("ID");
            Label sName = new Label();
            Label sWeight = new Label();
            Label sHeight = new Label();
            Label sCategory = new Label();
            Label sOverallAbility = new Label();

            sImage.setImage(new Image(superhuman.getPictureURL()));
            sImage.setFitHeight(200);
            sImage.setFitWidth(140);
            sName.setText("Name: " + superhuman.getName());
            sID.setText(String.valueOf("ID: " + superhuman.getId()));
            sWeight.setText(String.valueOf("Weight: " + superhuman.getWeight()));
            sHeight.setText(String.valueOf("Height: " + superhuman.getHeight()));
            sCategory.setText("Category: " + superhuman.getCategory());
            sOverallAbility.setText(String.valueOf("Ability: " + superhuman.getOverallAbility()));
            VBox superhumanCard = new VBox(5);
            superhumanCard.setMinHeight(190);
            superhumanCard.setMinWidth(140);
            superhumanCard.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
            superhumanCard.setStyle("-fx-background-color: lightgrey;");

            if (displayList[0]) {
                superhumanCard.getChildren().add(sImage);
            }
            Label[] superhumanData = {sID, sName, sWeight, sHeight, sCategory, sOverallAbility};
            for (int i = 0; i < superhumanData.length && i+1 < displayList.length; i++) {
                if (displayList[i+1]) {
                    superhumanCard.getChildren().add(superhumanData[i]);
                    superhumanData[i].setStyle("-fx-text-fill: black;");
                }
            }
            if (horizontal_counter == 4){
                vertical_counter++;
                horizontal_counter = 0;
            }
            grid.add(superhumanCard, horizontal_counter, vertical_counter);
            horizontal_counter++;
        }
    }
}
