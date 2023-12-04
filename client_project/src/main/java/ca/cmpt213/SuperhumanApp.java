package ca.cmpt213;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class SuperhumanApp extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        CheckBox idCheckBox = new CheckBox("Retrieve ID");
        idCheckBox.setSelected(true);
        CheckBox nameCheckBox = new CheckBox("Retrieve Name");
        nameCheckBox.setSelected(true);
        CheckBox weightCheckBox = new CheckBox("Retrieve Weight");
        CheckBox heightCheckBox = new CheckBox("Retrieve Height");
        CheckBox pictureURLCheckBox = new CheckBox("Retrieve Picture URL");
        pictureURLCheckBox.setSelected(true);
        CheckBox categoryCheckBox = new CheckBox("Retrieve Category");
        CheckBox overallAbilityCheckBox = new CheckBox("Retrieve Overall Ability");


        Button getButton = new Button("Retrieve all superhumans");

        Label findSuper = new Label("ID to find:");
        TextField findSuperField = new TextField();
        Button findButton = new Button("Find");

        Label deleteSuper = new Label("ID to delete:");
        TextField deleteSuperField = new TextField();
        Button deleteButton = new Button("Delete");

        Button addButton = new Button("Add a new superhuman");


        GridPane profile_grid = new GridPane();
        profile_grid.setHgap(10);
        profile_grid.setVgap(10);
        profile_grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        GridPane menu_grid = new GridPane();
        menu_grid.setHgap(10);
        menu_grid.setVgap(10);
        menu_grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));


        menu_grid.add(addButton, 0, 0, 2, 1);
        menu_grid.add(getButton, 0, 1, 2, 1);
        
        menu_grid.add(idCheckBox, 0, 2, 2, 1);
        menu_grid.add(nameCheckBox, 0, 3, 2, 1);
        menu_grid.add(weightCheckBox, 0, 4, 2, 1);
        menu_grid.add(heightCheckBox, 0, 5, 2, 1);
        menu_grid.add(pictureURLCheckBox, 0, 6, 2, 1);
        menu_grid.add(categoryCheckBox, 0, 7, 2, 1);
        menu_grid.add(overallAbilityCheckBox, 0, 8, 2, 1);
        menu_grid.add(findSuper, 0, 9, 2, 1);
        menu_grid.add(findSuperField, 0, 10, 2, 1);
        menu_grid.add(findButton, 0, 11, 2, 1);
        menu_grid.add(deleteSuper, 0, 12, 2, 1);
        menu_grid.add(deleteSuperField, 0, 13, 2, 1);
        menu_grid.add(deleteButton, 0, 14, 2, 1);
        

        HBox hbox = new HBox(20, menu_grid, profile_grid);
        Scene mainMenuScene = new Scene(hbox, 1000, 750);

        getButton.setOnAction(e -> {
            
            CheckBox[] checkBoxes = {pictureURLCheckBox, idCheckBox, nameCheckBox, weightCheckBox, heightCheckBox,  categoryCheckBox, overallAbilityCheckBox};
            boolean[] checkList = new boolean[checkBoxes.length];

            for (int i = 0; i < checkBoxes.length; i++) {
                checkList[i] = checkBoxes[i].isSelected();
            }
            GetAllSupers.setUpDisplayAllGrid(hbox, checkList);
        });


        findButton.setOnAction(e -> {
            FindSuper.setUpFindSuperBox(hbox, findSuperField);
        });

        addButton.setOnAction(e -> {
            AddSuperhuman.openAddScene(stage);
        });
        

        deleteButton.setOnAction(e -> {
            DeleteSuper.deleteSuperSetup(hbox, deleteSuperField);
        });

        stage.setScene(mainMenuScene);
        stage.setResizable(false);
        stage.setTitle("Superhuman Database Management System");
        stage.show();
    }
        
}
