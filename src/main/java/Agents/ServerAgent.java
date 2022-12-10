package Agents;

import Behaviours.ServerMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import WarehouseShared.Job;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

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
                Delay.msDelay(10000);
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(new AID("RobotAgent", AID.ISLOCALNAME));
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("messageType", new JsonPrimitive("JobAction"));
                JsonObject data = new JsonObject();
                data.add("action", new JsonPrimitive("pickup"));
                JsonArray path = new JsonArray();
                JsonArray  goal = new JsonArray();
                goal.add(5);
                goal.add(6);
                path.add(goal);
                data.add("path", path);
                jsonObject.add("data", data);
                String output = data.toString();
                message.setContent(output);
                myAgent.send(message);
                Job job =  gson.fromJson(data, Job.class);
                System.out.println();
            }
        }));

    }
}
