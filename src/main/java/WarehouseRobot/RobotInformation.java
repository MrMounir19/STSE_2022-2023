package WarehouseRobot;

import Enums.ActivityState;
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

    public static Position currentDestination;  // Can be the current job's pickup, the dropoff, a triage point, or a charging point.
    public static Position jobStartPosition;    // Needs to be set when a new job is accepted.
    public static ActivityState activityState = ActivityState.Idle; // Will change depending on current activity.

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
        if (currentJob == null) {
            takeJobFromQueue();
        }
    }

    public static void takeJobFromQueue() {
        if (jobs.size() > 0) {
            currentJob = jobs.remove(0);
            jobStartPosition = position;    // TODO: Needs to be a copy?
        }
        else {
            currentJob = null;
            jobStartPosition = null;    // TODO: Safe to set this to null?
        }
    }

    public static Float getYaw() {
        return yaw;
    }

    public static Position getCurrentDestination() {
        return currentDestination;
    }

    public static void setCurrentDestination(Position currentDestination) {
        RobotInformation.currentDestination = currentDestination;
    }
}
