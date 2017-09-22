package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import java.util.List;

public class Athlete{

    private int Athlete_ID;
    private String Athlete_Name;

    private int yearOfBirth;

    private float bestSwimmingTime;
    private float bestRunningTime;

    private float currentSwimmingTime;
    private float currentRunningTime;

    private List<Float> swimmingTime;
    private List<Float> runningTime;


    Athlete(){}

    Athlete(int ID, String Name, int year, float swimmingT, float runningT){
        Athlete_ID = ID;
        Athlete_Name = Name;
        yearOfBirth = year;
        currentSwimmingTime = swimmingT;
        currentRunningTime = runningT;
        //swimmingTime = new ArrayList<Float>();
        //swimmingTime.add(swimmingT);
        //runningTime = new ArrayList<Float>();
        //runningTime.add(runningT);
    }

    Athlete(String Name, int year, float swimmingT, float runningT){
        Athlete_Name = Name;
        yearOfBirth = year;
        currentSwimmingTime = swimmingT;
        currentRunningTime = runningT;
    }

    Athlete(String Name, int year){
        Athlete_Name = Name;
        yearOfBirth = year;
    }

    public int getAthlete_ID() {
        return Athlete_ID;
    }

    public void setAthlete_ID(int athlete_ID) {
        Athlete_ID = athlete_ID;
    }

    public String getAthlete_Name() {
        return Athlete_Name;
    }

    public void setAthlete_Name(String athlete_Name) {
        Athlete_Name = athlete_Name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public float getCurrentSwimmingTime() {
        return currentSwimmingTime;
    }

    public void setCurrentSwimmingTime(float currentSwimmingTime) {
        this.currentSwimmingTime = currentSwimmingTime;
    }

    public float getCurrentRunningTime() {
        return currentRunningTime;
    }

    public void setCurrentRunningTime(float currentRunningTime) {
        this.currentRunningTime = currentRunningTime;
    }

    public float getBestSwimmingTime() {
        return bestSwimmingTime;
    }

    public void setBestSwimmingTime(float bestSwimmingTime) {
        this.bestSwimmingTime = bestSwimmingTime;
    }

    public float getBestRunningTime() {
        return bestRunningTime;
    }

    public void setBestRunningTime(float bestRunningTime) {
        this.bestRunningTime = bestRunningTime;
    }

    public List<Float> getSwimmingTime() {
        return swimmingTime;
    }

    public void setSwimmingTime(List<Float> swimmingTime) {
        this.swimmingTime = swimmingTime;
    }

    public List<Float> getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(List<Float> runningTime) {
        this.runningTime = runningTime;
    }
}
