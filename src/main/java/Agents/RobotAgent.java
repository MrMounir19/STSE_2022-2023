package Agents;

import Behaviours.CollisionAvoidanceBehaviour2;
import Behaviours.CommandBehaviour;
import jade.core.Agent;

/**
 * This is a simple standard robot agent that is meant to control a robot on its own. (Without other agents)
 * It uses the second version of our Collision Avoidance behaviour, as well as command (parsing) behaviour.
 *
 * @author Maxim
 * @author Anthony
 * @author Senne
 * @version 1.0
 * @see CollisionAvoidanceBehaviour2
 * @see CommandBehaviour
 * @since 08/11/2022
 */
public class RobotAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CollisionAvoidanceBehaviour2());
        addBehaviour(new CommandBehaviour());
    }
}
