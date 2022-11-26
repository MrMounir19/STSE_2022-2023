package Agents;

import Behaviours.RegistrationBehaviour;
import Behaviours.RobotMessageParserBehaviour;
import jade.core.Agent;

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
    @Override
    protected void setup() {
        addBehaviour(new RegistrationBehaviour());
        addBehaviour(new RobotMessageParserBehaviour());
        //TODO: Add collision behaviour?
    }
}
