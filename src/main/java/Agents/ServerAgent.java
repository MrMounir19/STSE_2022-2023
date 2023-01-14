package Agents;

import Behaviours.GeneralServerBehaviour;
import Behaviours.JobGeneratorBehaviour;
import Behaviours.ServerMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import WarehouseShared.Config;
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
        addBehaviour(tbf.wrap(serverMessageParserBehaviour));
        addBehaviour(tbf.wrap(uwbReceivingBehaviour));
        addBehaviour(tbf.wrap(generalServerBehaviour));

        if (Config.getConfig().get("useJobGenerator").getAsBoolean()) {
            Behaviour jobGeneratorBehaviour = new JobGeneratorBehaviour();
            addBehaviour(tbf.wrap(jobGeneratorBehaviour));
        }
    }
}
