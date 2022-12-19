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
    private final ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
    @Override
    protected void setup() {
        Behaviour serverMessageParserBehaviour = new ServerMessageParserBehaviour();
        Behaviour uwbReceivingBehaviour = new UWBReceivingBehaviour();
        addBehaviour(tbf.wrap(serverMessageParserBehaviour));
        addBehaviour(tbf.wrap(uwbReceivingBehaviour));
    }
}
