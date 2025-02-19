package org.example;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.time.ZoneId;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;

public class Greyhound {
    //data retrieved from webscraping
    private String name;
    private ArrayList<Race> raceResults;

    //calculated data
    private double overallPlacePercentage;
    private double stamina;
    private String boxPreference;
    private boolean physicallyFit;

    //constructors
    public Greyhound(String name){
        this.name = name;
        this.raceResults = new ArrayList<>();
        this.overallPlacePercentage = calculateOverallPlacePercentage();
        this.stamina = calculateStamina();
        this.boxPreference = calculateBoxPreference();
        this.physicallyFit = isPhysicallyFit();
    }

    public Greyhound(String name, ArrayList<Race> raceResults){
        this.name = name;
        this.raceResults = raceResults;
        this.overallPlacePercentage = calculateOverallPlacePercentage();
        this.stamina = calculateStamina();
        this.boxPreference = calculateBoxPreference();
        this.physicallyFit = isPhysicallyFit();
    }

    //getters
    public String getName(){
        return name;
    }
    public double getStamina(){ return stamina; }
    public ArrayList<Race> getRaceResults() { return raceResults; }
    public double getOverallPlacePercentage() { return overallPlacePercentage; }
    public String getBoxPreference() { return boxPreference; }

    //prints all recorded greyhound racing info
    public void printResults(){
        for (Race race : raceResults){
            System.out.println(race.getTrack() + " - " + race.getFinish()+
                    " - " + race.getBox()+ " - " + race.getDistance()+
                    " - " + race.getGrade()+ " - " + race.getTime()+
                    " - " + race.getBestOfNight()+ " - " + race.getTimeAdjust()+ " - " + race.getNewTime()+
                    " - " + race.getTrakRtg()+ " - " + race.getMargin()+ " - " + race.getPositionInRace()+
                    " - " + race.getWinnerOrSecond()+ " - " + race.getOdd() + " - " +  race.getDate());
        }
    }

    //calculates the percentage of races in which the greyhound reached 3rd or better
    public double calculateOverallPlacePercentage(){
        int places = 0;
        for (Race race : raceResults){
            if (race.getFinish() <= 3) {
                places++;
            }
        }
        return places / (double)raceResults.size();
    }

    //calculates the average number of positions gained or lost during the race,
    // revealing his mid and end race profile. Specially important in longer races (>400m)
    public double calculateStamina(){
        double accumulator = 0;
        double weightAccumulator = 0;
        double dateRelevance = 1.0;
        for (Race race: raceResults){
            int positionFirstTurn = 0;
            for (int i = 0; i < race.getPositionInRace().length(); i++){
                if (race.getPositionInRace().charAt(i) <= 56 && race.getPositionInRace().charAt(i) >= 49){
                    positionFirstTurn = race.getPositionInRace().charAt(i) - 48;
                    i = race.getPositionInRace().length();
                }
            }
            if (positionFirstTurn != 0){

                if (race.getDistance() < 350){
                    accumulator += ((positionFirstTurn - race.getFinish()) * 0.5 * dateRelevance);
                    weightAccumulator += (0.5 * dateRelevance);
                }else if (race.getDistance() < 400){
                    accumulator += ((positionFirstTurn - race.getFinish()) * 0.7 * dateRelevance);
                    weightAccumulator += (0.7 * dateRelevance);
                }else if (race.getDistance() < 500){
                    accumulator += ((positionFirstTurn - race.getFinish()) * 1.0 * dateRelevance);
                    weightAccumulator += (1.0 * dateRelevance);
                } else {
                    accumulator += ((positionFirstTurn - race.getFinish()) * 1.2 * dateRelevance);
                    weightAccumulator += (1.2 * dateRelevance);
                }

                dateRelevance *= 0.986;
            }
        }
        return (accumulator / weightAccumulator);
    }

    //calculates the average position of the greyhound`s last 10 races,
    // using the relevance of each race as weight
    public double calculateWeightedPosition(String currentTrack, int currentDistance,
                                   String currentGrade, int currentBox){
        double weightSum = 0;
        double weightTimeSum = 0;
        int j = 10;
        if (raceResults.size() < 10){
            j = raceResults.size();
        }
        for (int i = 0; i < j; i++){
            Race race = raceResults.get(i);
            weightSum += race.calculateRelevance(currentTrack, currentDistance,currentGrade, currentBox);
            weightTimeSum += ((race.calculateRelevance(currentTrack, currentDistance,
                    currentGrade, currentBox)) * ((double)race.getFinish()));
        }
        return (weightTimeSum / weightSum);
    }

    //calculates the preferred boxes of the greyhound based on its results on each box
    public String calculateBoxPreference(){
        int[][] boxes = new int[8][2];
        int[][] boxRegions = new int[3][2];
        double[] boxRegionsPercentage = new double[3];
        int places = 0;

        for(Race race : raceResults){
            if(race.getBox() > 0) {
                boxes[(race.getBox() - 1)][1]++;
                if (race.getFinish() <= 3) {
                    places++;
                    boxes[(race.getBox() - 1)][0]++;
                }
            }
        }

        boxRegions[0][0] = boxes[0][0] + boxes [1][0];
        boxRegions[0][1] = boxes[0][1] + boxes [1][1];
        boxRegions[1][0] = boxes[2][0] + boxes [3][0] + boxes[4][0] + boxes[5][0];
        boxRegions[1][1] = boxes[2][1] + boxes [3][1] + boxes[4][1] + boxes[5][1];
        boxRegions[2][0] = boxes[6][0] + boxes [7][0];
        boxRegions[2][1] = boxes[6][1] + boxes [7][1];

        boxRegionsPercentage[0] = (double) boxRegions[0][0] / boxRegions[0][1];
        boxRegionsPercentage[1] = (double) boxRegions[1][0] / boxRegions[1][1];
        boxRegionsPercentage[2] = (double) boxRegions[2][0] / boxRegions[2][1];

        for(int i = 0; i < boxRegionsPercentage.length; i++){
            if (boxRegions[i][1] < 5){
                return "undefined";
            }
        }

        if((boxRegionsPercentage[0] - boxRegionsPercentage[1] > 0.10) &&
                (boxRegionsPercentage[0] - boxRegionsPercentage[2] > 0.10)){
            return "inner";
        }

        if((boxRegionsPercentage[2] - boxRegionsPercentage[1] > 0.10) &&
                (boxRegionsPercentage[2] - boxRegionsPercentage[0] > 0.10)){
            return "outer";
        }

        if((boxRegionsPercentage[1] - boxRegionsPercentage[0] > 0.10) &&
                (boxRegionsPercentage[1] - boxRegionsPercentage[2] > 0.10)){
            return "middle";
        }

        if((boxRegionsPercentage[0] - boxRegionsPercentage[1] > 0.15) &&
                (boxRegionsPercentage[2] - boxRegionsPercentage[1] > 0.15)){
            return "notmiddle";
        }

        return "undefined";
    }

    //checks if the greyhound has raced in the last two months, to avoid trusting on a
    //greyhound recently coming from an injury
    public boolean isPhysicallyFit(){

        Race mostRecentRace = raceResults.get(0);

        LocalDate raceDate = LocalDate.ofInstant(mostRecentRace.getDate().toInstant(),
                                                    ZoneId.systemDefault());
        LocalDate currentDate = LocalDate.now();

        long monthsBetween = ChronoUnit.MONTHS.between(raceDate, currentDate);
        if(monthsBetween >= 2){
            return false;
        }
        return true;
    }

    //checks if the greyhound has already raced at least 3 times in the track
    //and compares its track performance with its overall performance
    public boolean trackAffinity(String currentTrack){
        int racesInTrack = 0;
        int placesInTrack = 0;
        double trackPlacePercentage;
        for (Race race : raceResults){
            if (race.getTrack().equals(currentTrack)){
                racesInTrack++;
                if(race.getFinish() <= 3){
                    placesInTrack++;
                }
            }
        }
        if(racesInTrack < 3){
            return false;
        }
        trackPlacePercentage = (double)placesInTrack / racesInTrack;
        if (trackPlacePercentage < this.overallPlacePercentage * 0.75){
            return false;
        }
        return true;
    }

    //calculates the average time in the exact track and distance of the current race
    public double trackAndDistanceAverageTime(String currentTrack, int currentDistance){
        double timeAccumulator = 0.0;
        int racesInTrackAndDistance = 0;
        for (Race race : raceResults){
            if(race.getTrack().equals(currentTrack) && race.getDistance() == currentDistance
            && race.getTime() != 0.0){
                racesInTrackAndDistance++;
                timeAccumulator += race.getTime();
            }
        }
        if (racesInTrackAndDistance == 0){
            return 0.0;
        }
        return (double) timeAccumulator / racesInTrackAndDistance;
    }

    //calculates the numbers of times the greyhound race in the exact track and distance
    // of the current race
    public int calculateRacesInTrackAndDistance(String currentTrack, int currentDistance){
        int racesInTrackAndDistance = 0;
        for (Race race : raceResults){
            if(race.getTrack().equals(currentTrack) && race.getDistance() == currentDistance
                    && race.getNewTime() != 0.0){
                racesInTrackAndDistance++;
            }
        }
        return racesInTrackAndDistance;
    }

    //compares the greyhound box preference with its current box draw
    public String calculateBoxSuitability(int currentBox){
        if (this.boxPreference.equals("inner")){

            if(currentBox <= 2){return "perfect";}

            else if(currentBox >= 5){return "poor";}

            else {return "neutral";}
        }

        if (this.boxPreference.equals("outer")){

            if(currentBox >= 7){ return "perfect"; }

            else if (currentBox <= 4){ return "poor"; }

            else { return "neutral"; }
        }

        if (this.boxPreference.equals("middle")){

            if(currentBox >= 3 && currentBox <= 6){ return "perfect"; }

            else if (currentBox == 2 || currentBox == 7){ return "neutral"; }

            else { return "poor"; }

        }

        if (this.boxPreference.equals("notmiddle")){

            if (currentBox <= 2 || currentBox >= 7){ return "perfect"; }

            else { return "poor";}

        }
        return "neutral";
    }

    //extra method made in order to debug
    public static String addAnalysisConclusionRow(Greyhound greyhound1, Greyhound greyhound2, String track,
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

        boolean case1 = (distance >= 400) && (greyhound1.getStamina() > 0) && (staminaDiff >= 0.75) && (weightedPositionDiff >= -1.5) &&
                (avgTime1 <= avgTime2) &&
                (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                        || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) &&
                fit1 && (trackAffinity1 || !trackAffinity2);

        boolean case2 = (weightedPositionDiff <= -1.5) && (staminaDiff >= -0.5) &&
                (avgTime1 <= avgTime2) && (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                        || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) &&
                fit1 && (trackAffinity1 || !trackAffinity2);

        boolean case3 = (distance >= 400) && (greyhound2.getStamina() > 0) && (staminaDiff <= 0.75) && (weightedPositionDiff <= 1.5) &&
                (avgTime1 >= avgTime2) &&
                (!boxSuitability2.equals("poor")) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                        || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) &&
                fit2 && (trackAffinity1 || !trackAffinity2);

        boolean case4 = (weightedPositionDiff >= 1.5) && (staminaDiff <= 0.5) &&
                (!boxSuitability2.equals("poor")) && (avgTime1 >= avgTime2) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                        || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) &&
                fit2 && (trackAffinity1 || !trackAffinity2);

        boolean case5 = (staminaDiff >= 0.25) && (weightedPositionDiff <= 0.5) &&
                (avgTime1 <= avgTime2) && (!boxSuitability1.equals("poor")) &&
                (boxSuitability1.equals(boxSuitability2) || boxSuitability1.equals("perfect")
                        || (boxSuitability1.equals("neutral") && boxSuitability2.equals("poor"))) && fit1 &&
                (trackAffinity1 || !trackAffinity2);

        boolean case6 = (staminaDiff <= 0.25) && (weightedPositionDiff >= 0.5) &&
                (avgTime1 >= avgTime2) && (!boxSuitability2.equals("poor")) &&
                (boxSuitability2.equals(boxSuitability1) || boxSuitability2.equals("perfect")
                        || (boxSuitability2.equals("neutral") && boxSuitability1.equals("poor"))) && fit2 &&
                (trackAffinity2 || !trackAffinity1);

        if (case1 || case2 ||case5) {
            recommendation = "Recomendação de aposta: " + greyhound1.getName();
        }else {if (case3 || case4 || case6) {
            recommendation = "Recomendação de aposta: " + greyhound2.getName();
        }      else{
            recommendation = "Nenhuma recomendação de aposta.";
        }}
        return recommendation;
    }
}
