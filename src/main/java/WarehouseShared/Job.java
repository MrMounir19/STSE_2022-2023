package WarehouseShared;

import Enums.JobType;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class Job {
    protected int id;
    protected JobType action;
    public ArrayList<Position> path = new ArrayList<Position>();
    protected Position sourcePosition;
    public LocalTime startTime;
    public LocalTime finishedTime;
    public Position currentGoal = null;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStartTime() {
        setStartTime(LocalTime.now());
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setFinishedTime() {
        setFinishedTime(LocalTime.now());
    }

    public void setFinishedTime(LocalTime finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Duration jobDuration() {
        return Duration.between(this.startTime, this.finishedTime);
    }

    public String getAction() {
        return action.toString();
    }

    public void setAction(String action) {
        this.setAction(JobType.valueOf(action));
    }

    public void setAction(JobType action) {
        this.action = action;
    }

    public Position getSourcePosition() {
        return this.sourcePosition;
    }

    public void setSourcePosition(Position position) {
        this.sourcePosition = position;
    }

    public ArrayList<Position> getPath() {
        return path;
    }

    public void setPath(ArrayList<Position> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return getAction();
    }

    //TO DO: Add payload information
    public String getJsonString() {
        return "";
    }

    public Position getCurrentGoal() {
        if (currentGoal == null) {
            if (path.size() > 0){
                currentGoal = path.get(0);
            }
            else {
                return null;
            }
        }
        return currentGoal;
    }

    public void advanceGoal(){
        /*
        Call this function when you have reached the current goal, this will make the next call of getCurrentGoal()
        return the next position of the path
         */
        path.remove(0);
        setCurrentGoal(null);
    }

    public Boolean GoalFinished(){
        return ((path.size() == 0) && (currentGoal == null));
    }

    public void setCurrentGoal(Position currentGoal) {
        this.currentGoal = currentGoal;
    }
}

