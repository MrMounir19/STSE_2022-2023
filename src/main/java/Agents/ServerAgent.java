package Agents;

import Behaviours.ServerMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import Enums.JobType;
import WarehouseServer.JobStorage;
import WarehouseServer.RobotStorage;
import WarehouseShared.Job;
import WarehouseShared.Position;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

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
    private final ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
    @Override
    protected void setup() {
        /*
        Create the very first job
        TODO :: Remove
         */
        Job job = new Job();
        job.setId(69);
        job.setAction(JobType.PickUp);
        ArrayList<Position> path =  new ArrayList<>();
        path.add(new Position(7170, 16050));
        path.add(new Position(8400, 15180));
        path.add(new Position(8700, 14100));
        path.add(new Position(8540, 15900));
        job.setPath(path);
        JobStorage.addToDoJob(job);

        // Now add the behaviours
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
//                JsonArray  goal = new JsonArray();
//                goal.add(7660);
//                goal.add(15800);
//                path.add(goal);
//                JsonArray goal2 = new JsonArray();
//                goal2.add(8400);
//                goal2.add(14345);
//                path.add(goal2);

                JsonArray  goal = new JsonArray();
                goal.add(7170);
                goal.add(16270);
                path.add(goal);
                JsonArray goal2 = new JsonArray();
                goal2.add(7130);
                goal2.add(15230);
                path.add(goal2);
                JsonArray goal3 = new JsonArray();
                goal3.add(8700);
                goal3.add(14100);
                path.add(goal3);
                JsonArray goal4 = new JsonArray();
                goal4.add(8540);
                goal4.add(15900);
                path.add(goal4);

                //8400 14345
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
