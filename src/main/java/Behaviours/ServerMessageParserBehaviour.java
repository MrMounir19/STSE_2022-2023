package Behaviours;


import Utils.Messages;
import com.google.gson.JsonObject;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Objects;
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
        if (message != null) {
            handleMessage(message);
        }
        block();
    }

    private void handleMessage(ACLMessage message) {
        //TODO: check if robot that sent the message is in the system

        String content = message.getContent();
        JsonObject payload;

        // TODO: Catch MalformedJsonException (Can occur when receiving error message (for example when sending message to container/agent that no longer exists))
        payload = Messages.toJson(content);

        String messageType = payload.get("messageType").getAsString();

        if (Objects.equals(messageType, "registration")) {
            handleRegistrationMessage(message);
        } else if (Objects.equals(messageType, "collision")){
            handleCollisionMessage(message);
        } else {
            System.out.println("Received message type not valid for server.");
        }
    }

    private void handleRegistrationMessage(ACLMessage message) {
        myAgent.addBehaviour(new RegistrationBrokerBehaviour(message));
    }
    private void handleCollisionMessage(ACLMessage message) {

    }


}
