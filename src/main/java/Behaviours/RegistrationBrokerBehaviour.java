package Behaviours;

import com.google.gson.JsonObject;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import Utils.RobotMessage;

import java.util.ArrayList;


/**
 * This is a simple robot registration broker behaviour.
 * It receives a ping from a new robot on the network and registers it.
 *
 * @author Maxim
 * @version 1.0
 * @since 23/11/2022
 */
public class RegistrationBrokerBehaviour extends CyclicBehaviour {

    ArrayList<String> tempArray = new ArrayList<>(); // TODO: Get List of registered Robots

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            handleMessage(message);
        } else {
            block();
        }
    }

    /**
     * This method handles the received message, and registers the robot if it is a valid registration message.
     *
     * @param message The received message. (ACLMessage)
     * @see ACLMessage
     */
    private void handleMessage(ACLMessage message) {
        String content = message.getContent();
        AID sender = message.getSender();
        String robot_id = sender.getName(); // TODO: Might be getLocalName() instead? (If not unique due to Agent names)

        JsonObject jsonContent = RobotMessage.toJson(content);
        String messageType = jsonContent.get("messageType").getAsString();

        System.out.println("RegistrationBroker: " + content);

        if (!messageType.equals("registration")) {
            System.out.println("MessageType is not a registration");
            return;
        }

        if (checkRobot(robot_id)) {
            System.out.println("Robot already registered");
            return;
        }

        addRobot(robot_id);
    }

    public void addRobot(String robotID) {
        //Adds the robot to the list
        System.out.println("Registered robot with id: " + robotID);
        tempArray.add(robotID);
    }

    public boolean checkRobot(String robotID){
        //Returns true if robot id is in the list
        return tempArray.contains(robotID);
    }
}
