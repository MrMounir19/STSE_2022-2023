package Agents;

import WarehouseRobot.MotorControl;
import jade.core.Agent;

import Behaviours.CollisionAvoidanceBehaviour;
import jade.core.behaviours.CyclicBehaviour;

public class CollisionAvoidanceAgent extends Agent {
    // TODO: Initialise RobComponents before the agent container starts. (In the container start file)
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
