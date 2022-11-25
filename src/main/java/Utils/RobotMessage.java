package Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class RobotMessage {
    private static final JsonParser parser = new JsonParser();
    private static final String registration_broker_agent_id = "RegistrationBrokerAgent";
    private static final String job_broker_id = "JobBrokerAgent";

    public static JsonObject toJson(String message) {
        return parser.parse(message).getAsJsonObject();
    }

    public static String fromJson(JsonObject jsonObject) {
        return jsonObject.getAsString();
    }

    public static ACLMessage registrationMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(registration_broker_agent_id, AID.ISLOCALNAME));

        String payload = "{'messageType': 'registration'}";

        message.setContent(payload);

        return message;
    }

    public static ACLMessage jobMessage(Job job_type, ArrayList<int[]> path) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(job_broker_id, AID.ISLOCALNAME));

        String payload = "{'messageType': 'job', 'data': {'action': '" + job_type.toString() + "', 'path': " + path.toString() + "}}";

        message.setContent(payload);

        return message;
    }

    public static ACLMessage collisionMessage(CollisionAction action) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(registration_broker_agent_id, AID.ISLOCALNAME)); // TODO correct agent

        String payload = "{'messageType': 'collision', 'data': {'action': " + action.toString() + "}}";

        message.setContent(payload);

        return message;
    }
}
