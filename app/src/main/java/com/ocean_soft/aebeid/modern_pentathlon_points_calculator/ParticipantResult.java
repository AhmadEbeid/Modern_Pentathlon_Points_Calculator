package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

/**
 * Created by aebei on 18-Nov-16.
 */

public class ParticipantResult {

    private String ParticipantName;
    private int ID;
    private int SwimmingMin;
    private int SwimmingSec;
    private int SwimmingPoints;
    private String SwimmingTime;
    private int RunningMin;
    private int RunningSec;
    private String RunningTime;
    private int RunningPoints;
    private int TotalPoints;

    public ParticipantResult(){};

    public ParticipantResult(String participantName, int swimmingMin,
                             int swimmingSec, int swimmingPoints,
                             int runningMin, int runningSec,
                             int runningPoints, int totalPoints) {
        ParticipantName = participantName;
        SwimmingMin = swimmingMin;
        SwimmingSec = swimmingSec;
        SwimmingPoints = swimmingPoints;
        RunningMin = runningMin;
        RunningSec = runningSec;
        RunningPoints = runningPoints;
        TotalPoints = totalPoints;
        SwimmingTime = swimmingMin + "." + swimmingSec;
        RunningTime = runningMin + "." + runningSec;

    }

    public String getParticipantName() {
        return ParticipantName;
    }

    public void setParticipantName(String participantName) {
        ParticipantName = participantName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSwimmingMin() {
        return SwimmingMin;
    }

    public void setSwimmingMin(int swimmingMin) {
        SwimmingMin = swimmingMin;
    }

    public int getSwimmingSec() {
        return SwimmingSec;
    }

    public void setSwimmingSec(int swimmingSec) {
        SwimmingSec = swimmingSec;
    }

    public int getSwimmingPoints() {
        return SwimmingPoints;
    }

    public void setSwimmingPoints(int swimmingPoints) {
        SwimmingPoints = swimmingPoints;
    }

    public String getSwimmingTime() {
        return SwimmingTime;
    }

    public void setSwimmingTime(String swimmingTime) {
        SwimmingTime = swimmingTime;
    }

    public void setSwimmingTime() {
        SwimmingTime = SwimmingMin + "." + SwimmingSec;
    }

    public int getRunningMin() {
        return RunningMin;
    }

    public void setRunningMin(int runningMin) {
        RunningMin = runningMin;
    }

    public int getRunningSec() {
        return RunningSec;
    }

    public void setRunningSec(int runningSec) {
        RunningSec = runningSec;
    }

    public String getRunningTime() {
        return RunningTime;
    }

    public void setRunningTime(String runningTime) {
        RunningTime = runningTime;
    }

    public void setRunningTime() {
        RunningTime = RunningMin + "." + RunningSec;
    }

    public int getRunningPoints() {
        return RunningPoints;
    }

    public void setRunningPoints(int runningPoints) {
        RunningPoints = runningPoints;
    }

    public int getTotalPoints() {
        return TotalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        TotalPoints = totalPoints;
    }

}
