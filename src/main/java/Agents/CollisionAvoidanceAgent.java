package Agents;

import Behaviours.MessageParser;
import WarehouseRobot.MotorControl;
import jade.core.AID;
import jade.core.Agent;

import Behaviours.CollisionAvoidanceBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

public class CollisionAvoidanceAgent extends Agent {
    // TODO: Initialise RobComponents before the agent container starts. (In the container start file)
    @Override
    protected void setup() {
        restartread();

        addBehaviour(new MessageParser());
        addBehaviour(new CollisionAvoidanceBehaviour());
    }

    public  void restartread() {
        Delay.msDelay(1500);
        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
        msg1.addReceiver(new AID("CommandAgent", AID.ISLOCALNAME));
        msg1.setContent("Init");
        send(msg1);
    }
}
