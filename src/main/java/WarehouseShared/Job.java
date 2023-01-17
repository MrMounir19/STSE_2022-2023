package WarehouseShared;

import Utils.Messages;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.LocalTime;

public class Job {
    protected int id = -1;
    protected Position destination; // Pickup destination
    public LocalTime startTime;
    public LocalTime finishedTime;

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

    @Override
    public String toString() {
        return destination.toString() + "[" + getId() + "]";
    }

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public void fromString(String jsonString){
        JsonObject jsonData = Messages.toJson(jsonString);

        this.setId(jsonData.get("id").getAsInt());
        JsonObject destinationPosJson = jsonData.get("destination").getAsJsonObject();
        Position destinationPos = new Position(destinationPosJson.get("x").getAsFloat(), destinationPosJson.get("y").getAsFloat());
        this.setDestination(destinationPos);
    }

    public String toJsonString() {
        return "{" +
                "'id':" + id + "," +
                "'destination':" +
                    "{" +
                        "'x':" + destination.x + "," +
                        "'y':" + destination.y +
                    "}"  +
                "}";
    }
}

