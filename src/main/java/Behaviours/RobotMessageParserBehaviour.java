package Behaviours;

import Enums.MessageType;
import WarehouseShared.Job;
import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseShared.Position;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

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
    String serverAgentName = null;

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            handleMessage(message);
        }
        block();
    }

    private void handleMessage(ACLMessage message) {
        if (this.serverAgentName != null) {
            if (!Objects.equals(message.getSender().getLocalName(), this.serverAgentName)) {
                System.out.println("Server agent name mismatch. Discarding message.");
                return;
            }
        }
        System.out.println("received message");
        String content = message.getContent();
        JsonObject payload;

        try {
            payload = Messages.toJson(content);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Parsing message type");
        System.out.println(payload);
        MessageType messageType = MessageType.valueOf(payload.get("messageType").getAsString());
        System.out.println("Done parsing message type");

        if (messageType == MessageType.RegistrationConfirmation) {
            handleRegistrationConfirmationMessage(message);

        } else if (messageType == MessageType.Job) {
            System.out.println("Start check");
            handleJobMessage(message);
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
        // TODO: Handle if already working on a job.
        JsonObject payload = Messages.toJson(message.getContent());
        JsonObject data = payload.getAsJsonObject("data");

        Job job = new Job();
        job.fromString(data.toString());

        RobotInformation.addJob(job);
    }

}
