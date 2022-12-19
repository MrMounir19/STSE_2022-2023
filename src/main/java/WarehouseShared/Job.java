package WarehouseShared;

import Enums.JobType;
import Utils.Messages;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class Job {
    protected int id;
    protected JobType action;
    public ArrayList<Position> path;
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

    public void fromString(String jsonString){
        JsonObject jsonData = Messages.toJson(jsonString);
        this.setId(jsonData.get("id").getAsInt());
        this.setAction(jsonData.get("action").getAsString());
        JsonObject sourcePosition = jsonData.get("sourcePosition").getAsJsonObject();
        JsonArray path = jsonData.get("path").getAsJsonArray();
        ArrayList<Position> pathArray = new ArrayList<>();

        for(int pathIndex = 0; pathIndex < path.size(); pathIndex++){
            JsonObject jsonPathPos = path.get(pathIndex).getAsJsonObject();
            Position pathPos = new Position(jsonPathPos.get("x").getAsFloat(), jsonPathPos.get("y").getAsFloat());
            pathArray.add(pathPos);
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
                "'sourcePosition:':" +
                    "{" +
                        "'x':" + sourcePosition.x + "," +
                        "'y':" + sourcePosition.y +
                    "}"  +
                "}";
    }

    public Position getCurrentGoal() {
        if (currentGoal == null) {
            currentGoal = path.remove(0);
        }
        return currentGoal;

    }

    public void setCurrentGoal(Position currentGoal) {
        this.currentGoal = currentGoal;
    }
}

