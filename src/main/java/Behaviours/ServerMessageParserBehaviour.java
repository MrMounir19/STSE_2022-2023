package Behaviours;


import Enums.MessageType;
import Utils.Messages;
import com.google.gson.JsonObject;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Parses messages received by the server and creates one-shot behaviours in response.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 26/11/2022
 */
public class ServerMessageParserBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null && message.getPerformative() == ACLMessage.INFORM) {
            handleMessage(message);
        }
        block();
    }

    private void handleMessage(ACLMessage message) {
        //TODO: check if robot that sent the message is in the system

        String content = message.getContent();
        JsonObject payload;

        try {
            payload = Messages.toJson(content);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        MessageType messageType = MessageType.valueOf(payload.get("messageType").getAsString());

        if (messageType == MessageType.Registration) {
            handleRegistrationMessage(message);
        } else if (messageType == MessageType.Collision) {
            handleCollisionMessage(message);
        } else if (messageType == MessageType.JobFinished) {
            handleJobFinishedMessage(message);
        } else if (messageType == MessageType.JobFailed) {
            handleJobFailedMessage(message);
        } else if (messageType == MessageType.JobRequest) {
            handleJobRequestMessage(message);
        } else if (messageType == MessageType.LocationRequest){
            handleLocationRequestMessage(message);
        } else {
            System.out.println("Received message type not valid for server.");
        }
    }

    private void handleLocationRequestMessage(ACLMessage message){

        myAgent.addBehaviour( new LocationRequestReplyBehaviour(message));
    }

    private void handleRegistrationMessage(ACLMessage message) {
        myAgent.addBehaviour(new RegistrationBrokerBehaviour(message));
    }
    private void handleCollisionMessage(ACLMessage message) {

    }
    private void handleJobFinishedMessage(ACLMessage message) {
        myAgent.addBehaviour(new JobFinishedBehaviour(message));
    }
    private void handleJobFailedMessage(ACLMessage message) {
        myAgent.addBehaviour(new JobFailedBehaviour(message));
    }
    private void handleJobRequestMessage(ACLMessage message) {
        myAgent.addBehaviour(new JobAssignBehaviour(message.getSender().getLocalName()));
    }
}
