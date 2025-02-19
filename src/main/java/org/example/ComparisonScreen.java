package org.example;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.geometry.HPos;

import java.awt.*;


public class ComparisonScreen {

    //create the comparison screen with 6 comparison parameters
    public static void show(Stage stage, Greyhound greyhound1, Greyhound greyhound2, String track,
                            int distance, int box1, int box2, String grade) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        col1.setHgrow(Priority.ALWAYS);
        col1.setHalignment(HPos.RIGHT);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        col2.setHgrow(Priority.ALWAYS);
        col2.setHalignment(HPos.CENTER);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        col3.setHgrow(Priority.ALWAYS);


        grid.getColumnConstraints().addAll(col1, col2, col3);

        Label title = new Label("Comparison Results                    " + track + " - " +
                distance + "m - Grade " + grade);
        title.setStyle("-fx-text-fill: white;");
        title.setFont(new Font(20));
        title.setAlignment(Pos.CENTER);
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setColumnSpan(title, GridPane.REMAINING);

        grid.add(title, 0, 0, 2, 1);

        Label greyhound1Name = new Label(greyhound1.getName().toUpperCase());
        greyhound1Name.setStyle("-fx-text-fill: white;");
        greyhound1Name.setFont(new Font(16));
        Label greyhound2Name = new Label(greyhound2.getName().toUpperCase());
        greyhound2Name.setStyle("-fx-text-fill: white;");
        greyhound2Name.setFont(new Font(16));

        grid.add(greyhound1Name, 0, 1);
        grid.add(greyhound2Name, 2, 1);

        addRugImageRow(grid, box1, box2, 2);

        addComparisonRow(grid, "Stamina", String.valueOf(greyhound1.getStamina()),
                String.valueOf(greyhound2.getStamina()), greyhound1.getStamina(),
                greyhound2.getStamina(), true, 3);
        addComparisonRow(grid, "Weighted Position",
                String.valueOf(greyhound1.calculateWeightedPosition(track, distance, grade, box1)),
                String.valueOf(greyhound2.calculateWeightedPosition(track, distance, grade, box2)),
                greyhound1.calculateWeightedPosition(track, distance, grade, box1),
                greyhound2.calculateWeightedPosition(track, distance, grade, box2),
                false, 4);
        addComparisonRow(grid, "Track & Distance Avg Time",
                String.valueOf(greyhound1.trackAndDistanceAverageTime(track, distance)),
                String.valueOf(greyhound2.trackAndDistanceAverageTime(track, distance)),
                greyhound1.trackAndDistanceAverageTime(track, distance),
                greyhound2.trackAndDistanceAverageTime(track, distance), false,
                greyhound1.calculateRacesInTrackAndDistance(track, distance),
                greyhound2.calculateRacesInTrackAndDistance(track, distance), 5);

        addStringComparisonRow(grid, "Box Suitability",
                greyhound1.calculateBoxSuitability(box1), greyhound2.calculateBoxSuitability(box2), 6);

        addBooleanComparisonRow(grid, "Physically Fit",
                greyhound1.isPhysicallyFit(), greyhound2.isPhysicallyFit(), 7);
        addBooleanComparisonRow(grid, "Track Affinity",
                greyhound1.trackAffinity(track), greyhound2.trackAffinity(track), 8);

        addAnalysisConclusionRow(grid, greyhound1, greyhound2, track, distance, box1, box2, grade);

        Button btnNewComparison = new Button("New Comparison");
        btnNewComparison.setOnAction(e -> {
            GreyhoundAnalyzerApp mainApp = new GreyhoundAnalyzerApp();
            mainApp.start(stage);
        });
        grid.add(btnNewComparison, 0, 15, 2, 1);

        Scene scene = new Scene(grid, 800, 600);
        scene.getRoot().setStyle("-fx-background-color:black;");
        stage.setScene(scene);
        stage.show();
    }

    //displays a greyhound drawing with the colour and number of the box draw for both greyhounds
    private static void addRugImageRow(GridPane grid, int box1, int box2, int row){
        ComparisonScreen instance = new ComparisonScreen();
        switch (box1){
            case 1:
                Image leftBox1 = new Image(instance.getClass().getResource("/rugs/1-left.png").toExternalForm());
                ImageView leftBox1View = new ImageView(leftBox1);
                leftBox1View.setFitWidth(150);
                leftBox1View.setPreserveRatio(true);
                grid.add(leftBox1View, 0, row);
                break;
            case 2:
                Image leftBox2 = new Image(instance.getClass().getResource("/rugs/2-left.png").toExternalForm());
                ImageView leftBox2View = new ImageView(leftBox2);
                leftBox2View.setFitWidth(150);
                leftBox2View.setPreserveRatio(true);
                grid.add(leftBox2View, 0, row);
                break;
            case 3:
                Image leftBox3 = new Image(instance.getClass().getResource("/rugs/3-left.png").toExternalForm());
                ImageView leftBox3View = new ImageView(leftBox3);
                leftBox3View.setFitWidth(150);
                leftBox3View.setPreserveRatio(true);
                grid.add(leftBox3View, 0, row);
                break;
            case 4:
                Image leftBox4 = new Image(instance.getClass().getResource("/rugs/4-left.png").toExternalForm());
                ImageView leftBox4View = new ImageView(leftBox4);
                leftBox4View.setFitWidth(150);
                leftBox4View.setPreserveRatio(true);
                grid.add(leftBox4View, 0, row);
                break;
            case 5:
                Image leftBox5 = new Image(instance.getClass().getResource("/rugs/5-left.png").toExternalForm());
                ImageView leftBox5View = new ImageView(leftBox5);
                leftBox5View.setFitWidth(150);
                leftBox5View.setPreserveRatio(true);
                grid.add(leftBox5View, 0, row);
                break;
            case 6:
                Image leftBox6 = new Image(instance.getClass().getResource("/rugs/6-left.png").toExternalForm());
                ImageView leftBox6View = new ImageView(leftBox6);
                leftBox6View.setFitWidth(150);
                leftBox6View.setPreserveRatio(true);
                grid.add(leftBox6View, 0, row);
                break;
            case 7:
                Image leftBox7 = new Image(instance.getClass().getResource("/rugs/7-left.png").toExternalForm());
                ImageView leftBox7View = new ImageView(leftBox7);
                leftBox7View.setFitWidth(150);
                leftBox7View.setPreserveRatio(true);
                grid.add(leftBox7View, 0, row);
                break;
            case 8:
                Image leftBox8 = new Image(instance.getClass().getResource("/rugs/8-left.png").toExternalForm());
                ImageView leftBox8View = new ImageView(leftBox8);
                leftBox8View.setFitWidth(150);
                leftBox8View.setPreserveRatio(true);
                grid.add(leftBox8View, 0, row);
                break;

        }
        switch (box2){
            case 1:
                Image rightBox1 = new Image(instance.getClass().getResource("/rugs/1-right.png").toExternalForm());
                ImageView rightBox1View = new ImageView(rightBox1);
                rightBox1View.setFitWidth(150);
                rightBox1View.setPreserveRatio(true);
                grid.add(rightBox1View, 2, row);
                break;
            case 2:
                Image rightBox2 = new Image(instance.getClass().getResource("/rugs/2-right.png").toExternalForm());
                ImageView rightBox2View = new ImageView(rightBox2);
                rightBox2View.setFitWidth(150);
                rightBox2View.setPreserveRatio(true);
                grid.add(rightBox2View, 2, row);
                break;
            case 3:
                Image rightBox3 = new Image(instance.getClass().getResource("/rugs/3-right.png").toExternalForm());
                ImageView rightBox3View = new ImageView(rightBox3);
                rightBox3View.setFitWidth(150);
                rightBox3View.setPreserveRatio(true);
                grid.add(rightBox3View, 2, row);
                break;
            case 4:
                Image rightBox4 = new Image(instance.getClass().getResource("/rugs/4-right.png").toExternalForm());
                ImageView rightBox4View = new ImageView(rightBox4);
                rightBox4View.setFitWidth(150);
                rightBox4View.setPreserveRatio(true);
                grid.add(rightBox4View, 2, row);
                break;
            case 5:
                Image rightBox5 = new Image(instance.getClass().getResource("/rugs/5-right.png").toExternalForm());
                ImageView rightBox5View = new ImageView(rightBox5);
                rightBox5View.setFitWidth(150);
                rightBox5View.setPreserveRatio(true);
                grid.add(rightBox5View, 2, row);
                break;
            case 6:
                Image rightBox6 = new Image(instance.getClass().getResource("/rugs/6-right.png").toExternalForm());
                ImageView rightBox6View = new ImageView(rightBox6);
                rightBox6View.setFitWidth(150);
                rightBox6View.setPreserveRatio(true);
                grid.add(rightBox6View, 2, row);
                break;
            case 7:
                Image rightBox7 = new Image(instance.getClass().getResource("/rugs/7-right.png").toExternalForm());
                ImageView rightBox7View = new ImageView(rightBox7);
                rightBox7View.setFitWidth(150);
                rightBox7View.setPreserveRatio(true);
                grid.add(rightBox7View, 2, row);
                break;
            case 8:
                Image rightBox8 = new Image(instance.getClass().getResource("/rugs/8-right.png").toExternalForm());
                ImageView rightBox8View = new ImageView(rightBox8);
                rightBox8View.setFitWidth(150);
                rightBox8View.setPreserveRatio(true);
                grid.add(rightBox8View, 2, row);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + box2);
        }
        Label versus = new Label("VERSUS");
        versus.setFont(new Font(15));
        versus.setStyle("-fx-text-fill: white;");
        grid.add(versus, 1,2);
    }

    //displays the Stamina and Weighted Position values of each greyhound, styling green for the
    //better suited greyhound and red for the other
    private static void addComparisonRow(GridPane grid, String label, String val1, String val2,
                                         double num1, double num2, boolean higherIsBetter, int row) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: white;");
        lbl.setAlignment(Pos.CENTER);

        String formattedNum1 = String.format("%.2f", num1);
        String formattedNum2 = String.format("%.2f", num2);

        Label valLabel1 = new Label(formattedNum1);
        Label valLabel2 = new Label(formattedNum2);

        if ((higherIsBetter && num1 > num2) || (!higherIsBetter && num1 < num2)) {
            valLabel1.setStyle("-fx-text-fill: green; ");
            valLabel2.setStyle("-fx-text-fill: red; ");
        } else if ((higherIsBetter && num1 < num2) || (!higherIsBetter && num1 > num2)) {
            valLabel2.setStyle("-fx-text-fill: green; ");
            valLabel1.setStyle("-fx-text-fill: red; ");
        }

        grid.add(lbl, 1, row);
        grid.add(valLabel1, 0, row);
        grid.add(valLabel2, 2, row);
    }

    //displays the "Track & Distance average time" values of each greyhound, styling green for the
    //better suited greyhound and red for the other, along with the numbers of races in white
    private static void addComparisonRow(GridPane grid, String label, String val1, String val2,
                                         double num1, double num2, boolean higherIsBetter,
                                         int num3, int num4, int row) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: white;");

        String formattedNum1 = String.format("%.2f", num1);
        String formattedNum2 = String.format("%.2f", num2);

        Label valLabel1 = new Label(formattedNum1);
        Label valLabel2 = new Label(formattedNum2);

        Label raceCountLabel1 = new Label(" (" + num3 + ")");
        Label raceCountLabel2 = new Label(" (" + num4 + ")");

        raceCountLabel1.setStyle("-fx-text-fill: white;");
        raceCountLabel2.setStyle("-fx-text-fill: white;");

        if ((higherIsBetter && num1 > num2) || (!higherIsBetter && num1 < num2)) {
            valLabel1.setStyle("-fx-text-fill: green; ");
            valLabel2.setStyle("-fx-text-fill: red; ");
        } else if ((higherIsBetter && num1 < num2) || (!higherIsBetter && num1 > num2)) {
            valLabel2.setStyle("-fx-text-fill: green; ");
            valLabel1.setStyle("-fx-text-fill: red; ");
        } else{
            valLabel2.setStyle("-fx-text-fill: yellow; ");
            valLabel1.setStyle("-fx-text-fill: yellow; ");
        }

        HBox hbox1 = new HBox(5, valLabel1, raceCountLabel1);
        HBox hbox2 = new HBox(5, valLabel2, raceCountLabel2);


        grid.add(lbl, 1, row);
        hbox1.setAlignment(Pos.BASELINE_RIGHT);
        grid.add(hbox1, 0, row);
        grid.add(hbox2, 2, row);
    }

    //displays physicallyFit and trackAffinity attributes, showcasing green if true and red
    //if false
    private static void addBooleanComparisonRow(GridPane grid, String label,
                                                boolean val1, boolean val2, int row) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: white;");
        Label valLabel1 = new Label(String.valueOf(val1));
        Label valLabel2 = new Label(String.valueOf(val2));

        if (val1){
            valLabel1.setStyle("-fx-text-fill: green; ");
        }
        else{
            valLabel1.setStyle("-fx-text-fill: red; ");
        }
        if (val2){
            valLabel2.setStyle("-fx-text-fill: green; ");
        }
        else{
            valLabel2.setStyle("-fx-text-fill: red; ");
        }

        grid.add(lbl, 1, row);
        grid.add(valLabel1, 0, row);
        grid.add(valLabel2, 2, row);
    }

    //displays boxSuitability attribute of each greyhound. if a greyhound is better suited than
    //the other, it turns green as the worst turns red. In a tie, both become yellow
    private static void addStringComparisonRow(GridPane grid, String label,
                                               String val1, String val2, int row) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: white;");
        Label valLabel1 = new Label(val1);
        Label valLabel2 = new Label(val2);


        if (val1.equals("perfect")){
            valLabel1.setStyle("-fx-text-fill: green; ");
        }
        if (val2.equals("perfect")){
            valLabel2.setStyle("-fx-text-fill: green; ");
        }
        if (val1.equals("neutral")){
            valLabel1.setStyle("-fx-text-fill: yellow; ");
        }
        if (val2.equals("neutral")){
            valLabel2.setStyle("-fx-text-fill: yellow; ");
        }
        if (val1.equals("poor")){
            valLabel1.setStyle("-fx-text-fill: red; ");
        }
        if (val2.equals("poor")){
            valLabel2.setStyle("-fx-text-fill: red; ");
        }


        grid.add(lbl, 1, row);
        grid.add(valLabel1, 0, row);
        grid.add(valLabel2, 2, row);
    }

    //compares all 6 parameters to determine if a greyhound is likely to end in a better position
    //there are 4 cases in which a greyhounds can be declared likely to win over the other,
    //all based on the difference in the greyhound`s values
    private static void addAnalysisConclusionRow(GridPane grid, Greyhound greyhound1, Greyhound greyhound2, String track,
                                                 int distance, int box1, int box2, String grade) {
        String recommendation = "";

        double staminaDiff = (greyhound1.getStamina()) - (greyhound2.getStamina());
        double weightedPositionDiff = (greyhound1.calculateWeightedPosition(track, distance, grade, box1)) -
                (greyhound2.calculateWeightedPosition(track, distance, grade, box2));
        double avgTime1 = greyhound1.trackAndDistanceAverageTime(track, distance);
        double avgTime2 = greyhound2.trackAndDistanceAverageTime(track, distance);
        String boxSuitability1 = greyhound1.calculateBoxSuitability(box1);
        String boxSuitability2 = greyhound2.calculateBoxSuitability(box2);
        boolean fit1 = greyhound1.isPhysicallyFit();
        boolean fit2 = greyhound2.isPhysicallyFit();
        boolean trackAffinity1 = greyhound1.trackAffinity(track);
        boolean trackAffinity2 = greyhound2.trackAffinity(track);

        boolean case1 = (distance >= 400) && (greyhound1.getStamina() > 0) && (staminaDiff >= 0.75)
                && (weightedPositionDiff >= -1.5) && (avgTime1 <= avgTime2) &&
                (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) &&
                fit1 && (trackAffinity1 || !trackAffinity2);

        boolean case2 = (weightedPositionDiff <= -1.5) && (staminaDiff >= -0.5) &&
                (avgTime1 <= avgTime2) && (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) &&
                fit1 && (trackAffinity1 || !trackAffinity2);

        boolean case3 = (staminaDiff >= 0.25) && (weightedPositionDiff <= 0.5) &&
                (avgTime1 <= avgTime2) && (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) && fit1 &&
                (trackAffinity1 || !trackAffinity2);

        boolean case4 = (staminaDiff > 0.0) && (weightedPositionDiff < 0) &&
                (avgTime1 <= avgTime2) && (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) && fit1 &&
                trackAffinity1 && (!trackAffinity2|| !fit2 || boxSuitability2.equals("poor"));


        boolean case5 = (distance >= 400) && (greyhound2.getStamina()>0) && (staminaDiff <= 0.75)
                && (weightedPositionDiff <= 1.5) && (avgTime1 >= avgTime2) &&
                (!boxSuitability2.equals("poor")) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) &&
                fit2 && (trackAffinity2 || !trackAffinity1);

        boolean case6 = (weightedPositionDiff >= 1.5) && (staminaDiff <= 0.5) &&
                (!boxSuitability2.equals("poor")) && (avgTime1 >= avgTime2) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) &&
                fit2 && (trackAffinity2 || !trackAffinity1);

        boolean case7 = (staminaDiff <= 0.25) && (weightedPositionDiff >= 0.5) &&
                (avgTime1 >= avgTime2) && (!boxSuitability2.equals("poor")) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) && fit2 &&
                (trackAffinity2 || !trackAffinity1);

        boolean case8 = (staminaDiff <= 0.0) && (weightedPositionDiff >= 0) &&
                (avgTime1 >= avgTime2) && (!boxSuitability2.equals("poor")) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                        || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) && fit2 &&
                trackAffinity2 && (!trackAffinity1|| !fit1 || boxSuitability1.equals("poor"));

        if (case1 || case2 || case3 || case4) {
            recommendation = "Recommended Bet: " + greyhound1.getName().toUpperCase() +"      ";
        }else {if (case5 || case6 || case7 || case8) {
            recommendation = "Recommended Bet: " + greyhound2.getName().toUpperCase() +"      ";
        }      else{
            recommendation = "No solid betting recommendation.";
        }}

        Label conclusionLabel = new Label(recommendation);
        conclusionLabel.setStyle("-fx-text-fill: yellow;");
        conclusionLabel.setFont(new Font(16));
        grid.add(conclusionLabel, 0, 11, 3, 1);
    }
}