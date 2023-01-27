package Agents;

import Behaviours.CollisionAvoidanceCheckBehaviour;
import Behaviours.GeneralServerBehaviour;
import Behaviours.JobGeneratorBehaviour;
import Behaviours.ServerMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import WarehouseServer.JobStorage;
import WarehouseShared.Config;
import WarehouseShared.Job;
import WarehouseShared.Position;
import com.google.gson.JsonArray;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

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
        Behaviour generalServerBehaviour = new GeneralServerBehaviour();
        Behaviour collisionAvoidanceCheckBehaviour = new CollisionAvoidanceCheckBehaviour();
        addBehaviour(tbf.wrap(serverMessageParserBehaviour));
        addBehaviour(tbf.wrap(uwbReceivingBehaviour));
        addBehaviour(tbf.wrap(generalServerBehaviour));
        addBehaviour(tbf.wrap(collisionAvoidanceCheckBehaviour));

        if (Config.getConfig().get("useJobGenerator").getAsBoolean()) {
            Behaviour jobGeneratorBehaviour = new JobGeneratorBehaviour();
            addBehaviour(tbf.wrap(jobGeneratorBehaviour));
        } else {
//            Job newJob = new Job();
//            newJob.setDestination(new Position(7170, 16270));
//            JobStorage.addToDoJob(newJob);


//            Job newJob = new Job();
//            newJob.setDestination(new Position(12246, 15204));
//            JobStorage.addToDoJob(newJob);
//
//            Job newJob2 = new Job();
//            newJob2.setDestination(new Position(15047, 13681));
//            JobStorage.addToDoJob(newJob2);


            Job newJob = new Job();
            newJob.setDestination(new Position(13890, 14590));
            JobStorage.addToDoJob(newJob);

            Job newJob2 = new Job();
            newJob2.setDestination(new Position(14506, 12904));
            JobStorage.addToDoJob(newJob2);

            Job newJob3 = new Job();
            newJob3.setDestination(new Position(15911, 14646));
            JobStorage.addToDoJob(newJob3);

        }
    }
}
