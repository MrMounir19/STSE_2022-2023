package Agents;

import Behaviours.*;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import lejos.utility.Delay;

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
        Behaviour requestJob = new OneShotBehaviour() {
            @Override
            public void action() {
                Delay.msDelay(5000);
                System.out.println("Requesting job!");
                addBehaviour(new RequestJobBehaviour());
            }
        };
        Behaviour finishJob = new OneShotBehaviour() {
            @Override
            public void action() {
                Delay.msDelay(10000);
                System.out.println("Finishing job!");
                addBehaviour(new FinishJobBehaviour());
            }
        };

        addBehaviour(tbf.wrap(registrationBehaviour));
        addBehaviour(tbf.wrap(robotmessageParserBehaviour));
        addBehaviour(tbf.wrap(requestJob));
        addBehaviour(tbf.wrap(finishJob));
    }
}
