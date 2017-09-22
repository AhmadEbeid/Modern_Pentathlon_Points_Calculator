package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import android.provider.Telephony;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Competition {

    private String competitionName = "";
    private String formattedDate = "";
    private int ID;
    private int numberOfParticipants;
    private ArrayList<String> participants = new ArrayList<String>();
    private int participantsListID;
    private String participantsString  = "";
    private String swimmingMode = "";
    private String runningMode = "";
    private String result = "";

    public Competition(){}

    public Competition(String competitionName, ArrayList<String> participants,
                       String SMode, String RMode){
        this.competitionName = competitionName;
        this.participants = participants;
        swimmingMode = SMode;
        runningMode = RMode;
        numberOfParticipants = participants.size();
        participantsString = MainActivity.serialize(participants);
        formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getParticipantsString() {
        return participantsString;
    }

    public void setParticipantsString(String participantsString) {
        this.participantsString = participantsString;
        participants = new ArrayList<>();
        participants = MainActivity.deserialize(participantsString);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getParticipantsListID() {
        return participantsListID;
    }

    public void setParticipantsListID(int participantsListID) {
        this.participantsListID = participantsListID;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public String getSwimmingMode() {
        return swimmingMode;
    }

    public void setSwimmingMode(String swimmingMode) {
        this.swimmingMode = swimmingMode;
    }

    public String getRunningMode() {
        return runningMode;
    }

    public void setRunningMode(String runningMode) {
        this.runningMode = runningMode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
