package Agents;

import Behaviours.CollisionAvoidanceBehaviour;
import Behaviours.CollisionAvoidanceBehaviour2;
import Behaviours.CommandBehaviour;
import WarehouseRobot.MotorControl;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class RobotAgent extends Agent {
    // TODO: Initialise RobComponents before the agent container starts. (In the container start file)
    @Override
    protected void setup() {
        addBehaviour(new CollisionAvoidanceBehaviour2());
        addBehaviour(new CommandBehaviour());
    }
}
