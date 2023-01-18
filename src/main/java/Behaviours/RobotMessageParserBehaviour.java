package Behaviours;

import Enums.CollisionAction;
import Enums.MessageType;
import Enums.CollisionAction;
import WarehouseShared.Job;
import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseShared.Position;
import com.google.gson.JsonObject;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.awt.*;
import java.util.Objects;

/**
 * Parses messages received by the robot and creates one-shot behaviours in response.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 26/11/2022
 */
public class RobotMessageParserBehaviour extends CyclicBehaviour {
    static String serverAgentName = null;

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            handleMessage(message);
        }
        block();
    }

    private void handleMessage(ACLMessage message) {
        if (serverAgentName != null) {
            if (!Objects.equals(message.getSender().getLocalName(), serverAgentName)) {
                System.out.println("Server agent name mismatch. Discarding message.");
                return;
            }
        }
        String content = message.getContent();
        JsonObject payload;

        try {
            System.out.println(content);
            payload = Messages.toJson(content);
        } catch (Exception e) {
            e.printStackTrace();
            block(10000);
            return;
        }
        MessageType messageType = MessageType.valueOf(payload.get("messageType").getAsString());

        if (messageType == MessageType.RegistrationConfirmation) {
            handleRegistrationConfirmationMessage(message);
        } else if (messageType == MessageType.Job) {
            handleJobMessage(message);
        } else if (messageType == MessageType.Collision) {
            handleCollisionMessage(message);
        } else if (messageType == MessageType.LocationRequest) {
            handleLocationRequestReply(message);
        } else {
            System.out.println("Received message type not valid for robot.");
        }
    }

    private void handleRegistrationConfirmationMessage(ACLMessage message) {
        serverAgentName = message.getSender().getLocalName();
        Messages.serverAgent = serverAgentName;
        System.out.println("Received registration confirmation. Server agent name is: " + serverAgentName);
    }

    private void handleJobMessage(ACLMessage message) {
        JsonObject payload = Messages.toJson(message.getContent());
        JsonObject data = payload.getAsJsonObject("data");

        Job job = new Job();
        job.fromString(data.toString());

        RobotInformation.addJob(job);
    }

    private void handleCollisionMessage(ACLMessage message) {
        // TODO
        // Will need a state of some kind that can be toggled to force the robot to stop. @Anthony @Senne

        JsonObject payload = Messages.toJson(message.getContent());
        String collisionActionStr = payload.getAsJsonObject("data").getAsJsonObject("action").getAsString();
        RobotInformation.collisionStatus = CollisionAction.valueOf(collisionActionStr);

        // If Continue: force stop is off
        // If Stop: force stop is on
    }

    private void handleLocationRequestReply(ACLMessage message) {
        JsonObject payload = Messages.toJson(message.getContent());
        JsonObject jsonData = payload.getAsJsonObject("data");
        Position destinationPos = new Position(jsonData.get("x").getAsFloat(), jsonData.get("y").getAsFloat());
        RobotInformation.currentDestination = destinationPos;
    }
}
