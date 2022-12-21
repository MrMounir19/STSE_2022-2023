package WarehouseRobot;

import WarehouseShared.Job;
import WarehouseShared.Position;

import java.util.ArrayList;

// TODO Maybe merge with server robotobject but depends on separate functionality
// From @Maxim:
// Could use a RobotObject instead of most of this, and keep jobs? (Or reuse WarehouseServer.JobStorage)
public class RobotInformation {
    public static String robotId;
    public static Position masterPosition;
    public static String uwbID;
    public static Position position = new Position(0, 0);
    public static float yaw = 0;
    public static boolean isInitialized = false;

    public static ArrayList<Position> positionHistory = new ArrayList<>();
    public static ArrayList<Job> jobs = new ArrayList<>();

    public static Job currentJob = null;

    public  static String getRobotId() {
        return robotId;
    }

    public static String getUwbID() {
        return uwbID;
    }

    public static void setRobotPosition(float x, float y, float orientation) {
        position = new Position(x, y);
        yaw = orientation;
        positionHistory.add(position);
    }

    public static void setMasterPosition(float x, float y) {
        masterPosition = new Position(x, y);
    }

    public static void clearHistory() {
        positionHistory.clear();
    }

    public static void addJob(Job job) {
        jobs.add(job);
    }

    public static void takeJobFromQueue() {
        currentJob = jobs.remove(0);
        if (currentJob.currentGoal == null) {
            currentJob.setCurrentGoal(currentJob.path.get(0));
            System.out.println(RobotInformation.position.x + " " + RobotInformation.position.y);
            currentJob.previousGoal = RobotInformation.position;
        }
    }

    public static Float getYaw() {
        return yaw;
    }
}
