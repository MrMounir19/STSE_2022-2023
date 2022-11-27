package Behaviours;

import Utils.Messages;
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

    }

}
