package WarehouseShared;

import Enums.JobType;
import Utils.Messages;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class Job {
    protected int id;
    protected JobType action;
    public ArrayList<Position> path = new ArrayList<>();
    protected Position sourcePosition;
    public LocalTime startTime;
    public LocalTime finishedTime;
    public Position currentGoal = null;
    public Position previousGoal = null;

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
        return getAction() + "(" + getId() + ")";
    }

    public void fromString(String jsonString){
        JsonObject jsonData = Messages.toJson(jsonString);
        this.setId(jsonData.get("id").getAsInt());
        this.setAction(jsonData.get("action").getAsString());
        JsonObject sourcePosition = jsonData.get("sourcePosition").getAsJsonObject();

        JsonArray path = jsonData.get("path").getAsJsonArray();
        ArrayList<Position> pathArray = new ArrayList<>();

        for(int pathIndex = 0; pathIndex < path.size(); pathIndex++){
            pathArray.add(new Gson().fromJson(path.get(pathIndex), Position.class));
        }

        this.setPath(pathArray);
        Position sP = new Position(sourcePosition.get("x").getAsFloat(), sourcePosition.get("y").getAsFloat());
        this.setSourcePosition(sP);

    }

    public String toJsonString() {
        String path_string = "[";
        int pos_counter = 0;
        for(Position pos: path){
            String pos_string =   "{" +
                                "'x':" + pos.x +"," +
                                "'y':" + pos.y +
                            "}";
            if (pos_counter < path.size()) pos_string += ",";
            path_string += pos_string;
            pos_counter +=1;
        }
        path_string += "]";

        return "{" +
                "'id':" + id + "," +
                "'action':" + action + "," +
                "'path':" + path_string + "," +
                "'sourcePosition':" +
                    "{" +
                        "'x':" + sourcePosition.x + "," +
                        "'y':" + sourcePosition.y +
                    "}"  +
                "}";
    }

    public Position getCurrentGoal() {
        return currentGoal;
    }

    public void advanceGoal() {
        /*
         * Call this function when you have reached the current goal, this will give the next goal as current
         */
        System.out.println("Current Goal finished: (" + path.get(0).x + ", " + path.get(0).y + ")");
        previousGoal = path.remove(0);
        if (path.size() > 0) {
            setCurrentGoal(path.get(0));
            return;
        }
        setCurrentGoal(null);
    }

    public Boolean GoalFinished(){
        return ((path.size() == 0) && (currentGoal == null));
    }

    public void setCurrentGoal(Position currentGoal) {
        this.currentGoal = currentGoal;
    }
}

