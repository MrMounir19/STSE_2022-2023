package WarehouseRobot;

import Utils.Job;

import java.util.ArrayList;

//TODO Maybe merge with server robotobject but depends on separate functionality
public class RobotInformation {
    public static String robotId;
    public static float xPosition;
    public static float yPosition;
    public static String uwbID;
    public static float yaw;

    public static ArrayList<Job> jobs;

    public  static String getRobotId() {
        return robotId;
    }

    public static String getUwbID() {
        return uwbID;
    }

    public static void setRobotPosition(float x, float y, float orientation) {
        xPosition = x;
        yPosition = y;
        yaw = orientation;
    }

    public static void addJob(Job job) {
        jobs.add(job);
    }
}
