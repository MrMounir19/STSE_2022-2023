package WarehouseServer;

import java.util.ArrayList;
/**
 * Class that keeps track of all the registered robots
 *
 * @author Thimoty
 * @version 1.0
 * @since 26/11/2022
 */
public class RobotRegistrationStorage {

    public static ArrayList<RobotObject> registeredRobots = new ArrayList<>();

    public static void addRobot(String robotID) {
        //Adds the robot to the list
        System.out.println("Registered robot with id: " + robotID);
        registeredRobots.add(new RobotObject(robotID));
        System.out.println("Registered robots: " + registeredRobots);
    }

    public static boolean checkRobot(String robotID){
        //Returns true if robot id is in the list
        for (RobotObject robot : registeredRobots) {
            if (robot.getRobotId().equals(robotID)) {
                return true;
            }
        }
        return false;
    }

    public static void updateRobotPosition(String uwbID, float x, float y, float orientation) {
        for (RobotObject robot : registeredRobots) {
            if (robot.getUwbID().equals(uwbID)) {
                robot.setRobotPosition(x,y,orientation);
            }
        }
    }
}
