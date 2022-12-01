package Behaviours;

import Utils.Job;
import Utils.Messages;
import WarehouseRobot.RobotInformation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
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

        String content = message.getContent();
        JsonObject payload;

        try {
            payload = Messages.toJson(content);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String messageType = payload.get("messageType").getAsString();

        if (Objects.equals(messageType, "registrationConfirmation")) {
            handleRegistrationConfirmationMessage(message);

        } else if(Objects.equals(messageType, "job")){
            handleJobMessage(message);
        }else {
            System.out.println("Received message type not valid for robot.");
        }
    }

    private void handleRegistrationConfirmationMessage(ACLMessage message) {
        serverAgentName = message.getSender().getLocalName();
        System.out.println("Received registration confirmation. Server agent name is: " + serverAgentName);
    }

    private void handleJobMessage(ACLMessage message) {
        JsonParser j = new JsonParser();
        Gson gson = new Gson();
        JsonObject payload = j.parse(message.getContent()).getAsJsonObject();
        JsonObject data = payload.getAsJsonObject("data");
        Job job =  gson.fromJson(data, Job.class);
        RobotInformation.addJob(job);
    }

}
