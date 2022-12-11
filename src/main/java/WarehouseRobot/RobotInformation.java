package WarehouseRobot;

import Utils.Job;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

//TODO Maybe merge with server robotobject but depends on separate functionality
public class RobotInformation {
    public static String robotId;
    public static float xPosition;
    public static float yPosition;

    public static float masterXPosition;
    public static float masterYPosition;
    public static String uwbID;
    public static float yaw;

    public static ArrayList<ArrayList<Float>> positionHistory;
    public static double orientation;
    public static ArrayList<Job> jobs;

    public static Job currentJob = null;

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
        ArrayList<Float> tuple = new ArrayList<>();
        tuple.add(x);
        tuple.add(y);
        positionHistory.add(tuple);
    }

    public static void setMasterPosition(float x, float y) {
        masterXPosition = x;
        masterYPosition = y;
    }
    public static void clearHistory() {
        positionHistory.clear();
    }

    public static void addJob(Job job) {
        jobs.add(job);
    }

    public static void takeJobFromQueue() {
        currentJob = jobs.remove(0);
        if (currentJob.getCurrentGoal() == null) {
            currentJob.setCurrentGoal(currentJob.path.remove(0));
        }
    }
}
