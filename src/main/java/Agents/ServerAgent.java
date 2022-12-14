package Agents;

import Behaviours.ServerMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import WarehouseServer.RobotStorage;
import WarehouseShared.Job;
import WarehouseShared.Position;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This agent is used for the server.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @see ServerMessageParserBehaviour
 * @since 26/11/2022
 */
public class ServerAgent extends Agent {
    private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
    @Override
    protected void setup() {
        Behaviour serverMessageParserBehaviour = new ServerMessageParserBehaviour();
        Behaviour uwbReceivingBehaviour = new UWBReceivingBehaviour();
        addBehaviour(tbf.wrap(serverMessageParserBehaviour));
        addBehaviour(tbf.wrap(uwbReceivingBehaviour));
        addBehaviour(tbf.wrap(new OneShotBehaviour() {
            @Override
            public void action() {
                Delay.msDelay(25000);
                System.out.println("REEEE");
                String id = RobotStorage.robots.get(0).robotId;
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(new AID(id, AID.ISLOCALNAME));
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("messageType", "Job");
                JsonObject data = new JsonObject();
                data.addProperty("action", "pickup");
                JsonArray path = new JsonArray();
                JsonArray  goal = new JsonArray();
                goal.add(7758);
                goal.add(15886);
                path.add(goal);
                data.add("path", path);
                jsonObject.add("data", data);

                String output = jsonObject.toString();
                message.setContent(output);
                myAgent.send(message);
                System.out.println("Sent message");
            }
        }));

    }
}
