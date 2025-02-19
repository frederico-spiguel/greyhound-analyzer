package org.example;

import java.time.ZoneId;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Race {
    //data retrieved via webscraping
    private Date date;
    private String track;
    private int finish;
    private int box;
    private int distance;
    private String grade;
    private double time;
    private double bestOfNight;
    private double timeAdjust;
    private double newTime;
    private String trakRtg;
    private double margin;
    private String positionInRace;
    private String winnerOrSecond;
    private double odd;


    //constructor
    public Race(Date date, String track, int finish, int box, int distance,
                String grade, double time, double bestOfNight,
                double timeAdjust, double newTime, String trakRtg,
                double margin, String positionInRace, String winnerOrSecond,
                double odd){
        this.date = date;
        this.track = track;
        if(finish == 0) this.finish = 8;
        else this.finish = finish;
        this.box = box;
        this.distance = distance;
        this.grade = grade;
        this.time = time;
        this.bestOfNight = bestOfNight;
        this.timeAdjust = timeAdjust;
        this.newTime = newTime;
        this.trakRtg = trakRtg;
        this.margin = margin;
        this.positionInRace = positionInRace;
        this.winnerOrSecond = winnerOrSecond;
        this.odd = odd;
    }

    //getters and setters
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getTrack() {
        return track;
    }
    public void setTrack(String track) {
        this.track = track;
    }
    public int getFinish() {
        return finish;
    }
    public void setFinish(int finish) {
        this.finish = finish;
    }
    public int getBox() {
        return box;
    }
    public void setBox(int box) {
        this.box = box;
    }
    public int getDistance(){ return distance; }
    public void setDistance(int distance) { this.distance = distance; }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public double getTime() {
        return time;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public double getBestOfNight() {
        return bestOfNight;
    }
    public void setBestOfNight(double bestOfNight) {
        this.bestOfNight = bestOfNight;
    }
    public double getTimeAdjust() {
        return timeAdjust;
    }
    public void setTimeAdjust(double timeAdjust) {
        this.timeAdjust = timeAdjust;
    }
    public double getNewTime() {
        return newTime;
    }
    public void setNewTime(double newTime) {
        this.newTime = newTime;
    }
    public String getTrakRtg() {
        return trakRtg;
    }
    public void setTrakRtg(String trakRtg) {
        this.trakRtg = trakRtg;
    }
    public double getMargin() {
        return margin;
    }
    public void setMargin(double margin) {
        this.margin = margin;
    }
    public String getPositionInRace() {
        return positionInRace;
    }
    public void setPositionInRace(String positionInRace) {
        this.positionInRace = positionInRace;
    }
    public String getWinnerOrSecond() {
        return winnerOrSecond;
    }
    public void setWinnerOrSecond(String winnerOrSecond) {
        this.winnerOrSecond = winnerOrSecond;
    }
    public double getOdd() {
        return odd;
    }
    public void setOdd(double odd) {
        this.odd = odd;
    }

    //calculates the relevance of this race in comparison to the current race
    public double calculateRelevance(String currentTrack, int currentDistance, String currentGrade, int currentBox){

        double relevance;

        double dateRelevance = calculateDateRelevance();
        double boxRelevance = calculateBoxRelevance(currentBox);
        double trackRelevance = calculateTrackRelevance(currentTrack);
        double oddRelevance = calculateOddRelevance();
        double distanceRelevance = calculateDistanceRelevance(currentDistance);
        double gradeRelevance = calculateGradeRelevance(currentGrade);

        relevance = (dateRelevance * boxRelevance * trackRelevance * oddRelevance *
                     distanceRelevance * gradeRelevance);

        return relevance;

    }

    private double calculateDateRelevance(){
        LocalDate raceDate = LocalDate.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
        LocalDate currentDate = LocalDate.now();

        long monthsBetween = ChronoUnit.MONTHS.between(raceDate, currentDate);
        if(monthsBetween < 1){
            return 1.4;
        } else if (monthsBetween < 6) {
            return 1.0;
        } else if (monthsBetween < 12) {
            return 0.7;
        } else {
            return 0.4;
        }
    }

    private double calculateBoxRelevance(int currentBox){
        int bigger = currentBox;
        int smaller = this.box;
        if (this.box > currentBox){
            bigger = this.box;
            smaller = currentBox;
        }
        if (this.box == currentBox){
            return 1.5;
        } else if((bigger - smaller) == 1){
            return 1.1;
        } else if((bigger - smaller) == 2){
            return 0.9;
        } else if((bigger - smaller) <= 4){
            return 0.8;
        } else {
            return 0.7;
        }

    }

    private double calculateTrackRelevance(String currentTrack){
        if (currentTrack == null){
            return 1.0;
        }
        if (currentTrack.equals(this.track)){
            return 1.3;
        }
        else {
            return 1.0;
        }
    }

    private double calculateOddRelevance(){
        if (this.odd <= 1.20){
            return 0.7;
        } else if(this.odd <= 1.50){
            return 0.8;
        } else if (this.odd <= 2.50){
            return 1.0;
        } else if (this.odd <= 5){
            return 1.2;
        } else if (this.odd <= 10){
            return 1.0;
        } else if (this.odd <= 15) {
            return 0.9;
        } else{
            return 0.7;
        }
    }

    private double calculateDistanceRelevance(int currentDistance){
        int bigger = currentDistance;
        int smaller = this.distance;
        if (this.distance > currentDistance){
            bigger = this.distance;
            smaller = currentDistance;
        }
        if ((bigger - smaller) <= 50){
            return 1.20;
        } else if ((bigger - smaller) <= 100) {
            return 0.8;
        } else {
            return 0.6;
        }
    }

    private double calculateGradeRelevance(String currentGrade){
        int dbRaceGrade = gradeToInt(this.grade);
        int dbCurrentGrade = gradeToInt(currentGrade);

        int bigger = dbRaceGrade;
        int smaller = dbCurrentGrade;
        if (dbRaceGrade < dbCurrentGrade){
            bigger = dbCurrentGrade;
            smaller = dbRaceGrade;
        }
        if (bigger == smaller){
            return 1.30;
        } else if((bigger - smaller) <= 1){
            return 1.00;
        } else {
            return 0.7;
        }
    }

    //this function ranks all possible greyhound racing grades into 6 levels to make grade comparison possible
    private static int gradeToInt(String currentGrade){
        switch (currentGrade.toUpperCase()){
            case "T3-M", "M", "MH", "MSF", "C0", "JM", "OM":
                return 0;
            case "N", "NH", "MF", "5NP", "5NPH", "T3-5", "T3-6", "T3-7", "T3-RW", "C1", "J", "NMW", "NNP":
                return 1;
            case "NF", "RW", "RWH", "6", "6H", "7", "7H", "X67", "X67H", "VET", "C2", "7F", "6G", "XM5", "SHNP":
                return 2;
            case "5", "5H", "RWF", "6F", "5F", "X67F", "X45", "X45H", "C3":
                return 3;
            case "X45F", "X34", "X345", "FFA", "C4", "4", "I", "B8", "O", "MAO", "MA45":
                return 4;
            case "SH", "GRP3", "GRP2", "GRP1", "C5", "S", "GL", "X12", "X23", "3", "2" :
                return 5;
            default:
                return 3;
        }

    }
}
