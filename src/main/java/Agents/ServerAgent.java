package Agents;

import Behaviours.ServerMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import Enums.JobType;
import WarehouseServer.JobStorage;
import WarehouseShared.Job;
import WarehouseShared.Position;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

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
        path.add(new Position(7170, 16270));
        path.add(new Position(7130, 15230));
        path.add(new Position(8700, 14100));
        path.add(new Position(8540, 15900));
        job.setPath(path);
        JobStorage.addToDoJob(job);

        // Now add the behaviours
        Behaviour serverMessageParserBehaviour = new ServerMessageParserBehaviour();
        Behaviour uwbReceivingBehaviour = new UWBReceivingBehaviour();
        addBehaviour(tbf.wrap(serverMessageParserBehaviour));
        addBehaviour(tbf.wrap(uwbReceivingBehaviour));
    }
}
