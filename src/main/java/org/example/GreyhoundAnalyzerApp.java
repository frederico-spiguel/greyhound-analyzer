package org.example;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GreyhoundAnalyzerApp extends Application {

    //create the starting screen
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Greyhound Race Analyzer");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);

        Label lblGreyhound1 = new Label("Greyhound 1 Name:");
        lblGreyhound1.setStyle("-fx-text-fill: white;");
        TextField txtGreyhound1 = new TextField();
        Label lblGreyhound2 = new Label("Greyhound 2 Name:");
        lblGreyhound2.setStyle("-fx-text-fill: white;");
        TextField txtGreyhound2 = new TextField();
        Label lblDistance = new Label("Distance:");
        lblDistance.setStyle("-fx-text-fill: white;");
        TextField txtDistance = new TextField();
        Label lblTrack = new Label("Track Name:");
        lblTrack.setStyle("-fx-text-fill: white;");
        TextField txtTrack = new TextField();
        Label lblBox1 = new Label("Starting Box:");
        lblBox1.setStyle("-fx-text-fill: white;");
        TextField txtBox1 = new TextField();
        Label lblBox2 = new Label("Starting Box:");
        lblBox2.setStyle("-fx-text-fill: white;");
        TextField txtBox2 = new TextField();
        Label lblGrade = new Label("Race Grade:");
        lblGrade.setStyle("-fx-text-fill: white;");
        TextField txtGrade = new TextField();

        Image titleGif = new Image(getClass().getResource("/titleGif.gif").toExternalForm());
        ImageView title = new ImageView(titleGif);

        VBox titleContainer = new VBox();
        titleContainer.setAlignment((Pos.CENTER));
        titleContainer.setSpacing(10);
        titleContainer.getChildren().add(title);

        Image greyhoundGif = new Image(getClass().getResource("/loadingGif.gif").toExternalForm());
        ImageView loadingGif = new ImageView(greyhoundGif);
        loadingGif.setFitWidth(400);
        loadingGif.setPreserveRatio(true);
        grid.add(loadingGif, 1, 7, 2, 1);
        loadingGif.setVisible(false);

        Button btnCompare = new Button("Compare");
        btnCompare.setOnAction(e -> {
            String greyhound1Name = txtGreyhound1.getText();
            String greyhound2Name = txtGreyhound2.getText();
            int currentDistance = Integer.parseInt(txtDistance.getText());
            String currentTrack = txtTrack.getText();
            int currentBoxG1 = Integer.parseInt(txtBox1.getText());
            int currentBoxG2 = Integer.parseInt(txtBox2.getText());
            String currentGrade = txtGrade.getText();

            loadingGif.setVisible(true);

            Task<Void> task = new Task<>(){

                @Override
                protected Void call(){
                    Greyhound greyhound1 = Scraper.collectGreyhoundData(greyhound1Name);
                    Greyhound greyhound2 = Scraper.collectGreyhoundData(greyhound2Name);

                    if (greyhound1 != null && greyhound2 != null) {
                        javafx.application.Platform.runLater(() -> {
                            loadingGif.setVisible(false);

                            ComparisonScreen.show(primaryStage, greyhound1, greyhound2, currentTrack,
                                    currentDistance, currentBoxG1, currentBoxG2, currentGrade);
                        });

                    } else {
                        javafx.application.Platform.runLater(() -> {
                            System.out.println("Error scraping greyhound info");
                        });
                    }
                    return null;
                }
            };

            new Thread(task).start();

        });

        grid.add(lblGreyhound1, 0, 1);
        grid.add(txtGreyhound1, 1, 1);
        grid.add(lblGreyhound2, 2, 1);
        grid.add(txtGreyhound2, 3, 1);
        grid.add(lblBox1, 0, 2);
        grid.add(txtBox1, 1, 2);
        grid.add(lblBox2, 2, 2);
        grid.add(txtBox2, 3, 2);
        grid.add(lblDistance, 0, 3);
        grid.add(txtDistance, 1, 3, 3, 1);
        grid.add(lblTrack, 0, 4);
        grid.add(txtTrack, 1, 4, 3, 1);
        grid.add(lblGrade, 0, 5);
        grid.add(txtGrade, 1, 5,3,1);
        grid.add(btnCompare, 1, 6,3,1);

        VBox mainContainer = new VBox();
        mainContainer.getChildren().add(titleContainer);
        mainContainer.getChildren().add(grid);

        Scene scene = new Scene(mainContainer, 800, 700);
        scene.getRoot().setStyle("-fx-background-color:black;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}