package Behaviours;

import WarehouseServer.RobotStorage;
import com.google.gson.JsonObject;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import Utils.Messages;



/**
 * This is a simple robot registration broker behaviour.
 * It receives a ping from a new robot on the network and registers it.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 23/11/2022
 */
public class RegistrationBrokerBehaviour extends OneShotBehaviour {
    ACLMessage message;

    RegistrationBrokerBehaviour (ACLMessage message) {
        this.message = message;
    }

    @Override
    public void action() {
        handleMessage();
    }

    /**
     * This method handles the received message, and registers the robot with the server.
     */
    private void handleMessage() {
        AID sender = this.message.getSender();
        JsonObject payload = Messages.toJson(message.getContent());

        String robot_id = sender.getLocalName();

        if (RobotStorage.checkRobot(robot_id)) {
            System.out.println(robot_id +" is already registered");
            return;
        }
        String uwb_id = payload.getAsJsonObject("data").get("uwb_id").getAsString();

        RobotStorage.addRobot(robot_id, uwb_id);
        myAgent.send(Messages.registrationConfirmationMessage(robot_id));
    }
}
