package Utils;

import Enums.CollisionAction;
import Enums.JobType;
import Enums.MessageType;
import WarehouseShared.Job;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

/**
 * Util class to create messages of the system
 *
 * @author Maxim
 * @author Thimoty
 * @since 23/11/2022
 */
public class Messages {
    public static String serverAgent = "ServerAgent";

    private static final JsonParser parser = new JsonParser();

    public static JsonObject toJson(String message) {
        return parser.parse(message).getAsJsonObject();
    }

    public static String fromJson(JsonObject jsonObject) {
        return jsonObject.getAsString();
    }

    /**
     * Message used by robots to register themselves to the system
     */
    public static ACLMessage registrationMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(serverAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': '" + MessageType.Registration + "'}";

        message.setContent(payload);

        return message;
    }

    /**
     * Message used by the server to confirm the registration of a robot
     */
    public static ACLMessage registrationConfirmationMessage(String targetAgent) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(targetAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': '" + MessageType.RegistrationConfirmation + "', 'data': {'serverAgentName': '" + serverAgent + "'}}";

        message.setContent(payload);

        return message;
    }

    /**
     * TODO: What is this message for?
     */
    public static ACLMessage collisionMessage(CollisionAction action) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(serverAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': '" + MessageType.Collision + "', 'data': {'action': " + action.toString() + "}}";

        message.setContent(payload);

        return message;
    }

    public static ACLMessage requestJobMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(serverAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': '" + MessageType.JobRequest + "'}";

        message.setContent(payload);

        return message;
    }

    public static ACLMessage assignJobMessage(String targetAgent, Job job) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(targetAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': '" + MessageType.Job + "', 'data': " + job.getJsonString() + "}";

        message.setContent(payload);

        return message;
    }

    public static ACLMessage finishedJobMessage(Job job) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(serverAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': '" + MessageType.Job + "', 'data': " + job.getAction() + "}";

        message.setContent(payload);

        return message;
    }
}
