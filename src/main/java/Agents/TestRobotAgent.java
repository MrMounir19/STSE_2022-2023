package Agents;

import Behaviours.*;
import WarehouseRobot.RobotInformation;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

/**
 * This agent is used for the testing.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @see RegistrationBehaviour
 * @see RobotMessageParserBehaviour
 * @since 19/12/2022
 */
public class TestRobotAgent extends Agent {
    private final ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
    @Override
    protected void setup() {
        Behaviour registrationBehaviour = new RegistrationBehaviour();
        Behaviour robotmessageParserBehaviour = new RobotMessageParserBehaviour();
        Behaviour finishJob = new CyclicBehaviour() {
            @Override
            public void action() {
                if (RobotInformation.currentJob != null) {
                    addBehaviour(new FinishJobBehaviour());
                }
                block(100);
            }
        };

        addBehaviour(tbf.wrap(registrationBehaviour));
        addBehaviour(tbf.wrap(robotmessageParserBehaviour));
        addBehaviour(tbf.wrap(finishJob));
    }
}
