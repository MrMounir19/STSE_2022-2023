package WarehouseServer;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
/**
 * Class that keeps track of all the registered robots
 *
 * @author Thimoty
 * @version 1.0
 * @since 26/11/2022
 */
public class RobotStorage {
    public static ArrayList<RobotObject> robots = new ArrayList<>();

    public static void addRobot(String robotID, String uwb_id) {
        // Adds the robot to the list
        System.out.println("Registered robot with id: " + robotID);
        robots.add(new RobotObject(robotID, uwb_id));
        System.out.println("Registered robots: " + robots);
    }

    public static ArrayList<RobotObject> getRobots() {
        return robots;
    }

    public static boolean checkRobot(String robotID) {
        return getFromID(robotID) != null;
    }

    public static void updateRobotPosition(String uwbID, float x, float y, float orientation) {
        for (RobotObject robot : robots) {
            if (robot.getUwbID().equals(uwbID)) {
                robot.setRobotPosition(x,y,orientation);
            }
        }
    }

    public static RobotObject getFromACLMessage(ACLMessage message) {
        AID sender = message.getSender();
        String robotId = sender.getLocalName();

        return getFromID(robotId);
    }

    public static RobotObject getFromID(String robotId) {
        for (RobotObject robot : robots) {
            if (robot.getRobotId().equals(robotId)) {
                return robot;
            }
        }

        return null;
    }
}
