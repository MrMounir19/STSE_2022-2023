package Utils;

import Enums.CollisionAction;
import Enums.JobType;
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
    private static final String serverAgent = "ServerAgent";

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

        String payload = "{'messageType': 'registration'}";

        message.setContent(payload);

        return message;
    }

    /**
     * Message used by the server to confirm the registration of a robot
     */
    public static ACLMessage registrationConfirmationMessage(String targetAgent) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(targetAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': 'registrationConfirmation', 'data': {'serverAgentName': '" + serverAgent + "'}}";

        message.setContent(payload);

        return message;
    }

    /**
     * TODO: What is this message for?
     */
    public static ACLMessage jobMessage(String target_agent, JobType job_type, ArrayList<int[]> path) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(target_agent, AID.ISLOCALNAME));

        String payload = "{'messageType': 'job', 'data': {'action': '" + job_type.toString() + "', 'path': " + path.toString() + "}}";

        message.setContent(payload);

        return message;
    }


    /**
     * TODO: What is this message for?
     */
    public static ACLMessage collisionMessage(CollisionAction action) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(serverAgent, AID.ISLOCALNAME));

        String payload = "{'messageType': 'collision', 'data': {'action': " + action.toString() + "}}";

        message.setContent(payload);

        return message;
    }
}
