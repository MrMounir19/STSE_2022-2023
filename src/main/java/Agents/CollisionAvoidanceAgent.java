package Agents;

import Behaviours.CollisionAvoidanceBehaviour;
import WarehouseRobot.MotorControl;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 * This is a simple Agent which uses basic collision avoidance behaviour, as well as a cyclic behaviour to keep driving
 * forward as long as no collision is detected.
 *
 * @author Maxim
 * @author Anthony
 * @version 1.0
 * @see CollisionAvoidanceBehaviour
 * @since 26/10/2022
 */
public class CollisionAvoidanceAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CollisionAvoidanceBehaviour());
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MotorControl.setSpeed(MotorControl.mediumSpeed);
                MotorControl.moveForward();
                block(5000);
            }
        });
    }
}
