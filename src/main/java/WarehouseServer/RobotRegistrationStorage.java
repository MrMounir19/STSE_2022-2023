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

    public static ArrayList<String> registeredRobots = new ArrayList<>();

    public static void addRobot(String robotID) {
        //Adds the robot to the list
        System.out.println("Registered robot with id: " + robotID);
        registeredRobots.add(robotID);
        System.out.println("Registered robots: " + registeredRobots);
    }

    public static boolean checkRobot(String robotID){
        //Returns true if robot id is in the list
        return registeredRobots.contains(robotID);
    }

}
