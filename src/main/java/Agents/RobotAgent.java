package Agents;

import Behaviours.GeneralRobotBehaviour;
import Behaviours.RegistrationBehaviour;
import Behaviours.RobotMessageParserBehaviour;
import Behaviours.UWBReceivingBehaviour;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

/**
 * This agent is used for the robot.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @see RegistrationBehaviour
 * @see RobotMessageParserBehaviour
 * @since 26/11/2022
 */
public class RobotAgent extends Agent {
    private final ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
    @Override
    protected void setup() {
        Behaviour registrationBehaviour = new RegistrationBehaviour();
        Behaviour robotmessageParserBehaviour = new RobotMessageParserBehaviour();
        Behaviour uwbReceivingBehaviour = new UWBReceivingBehaviour();
        Behaviour generalRobotBehaviour = new GeneralRobotBehaviour();

        addBehaviour(tbf.wrap(registrationBehaviour));
        addBehaviour(tbf.wrap(robotmessageParserBehaviour));
        addBehaviour(tbf.wrap(uwbReceivingBehaviour));
        addBehaviour(tbf.wrap(generalRobotBehaviour));
        //TODO: Add collision behaviour?
    }
}
