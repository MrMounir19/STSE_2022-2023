package Behaviours;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

import static jade.lang.acl.ACLMessage.INFORM;

public class CollisionAvoidanceBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message=this.myAgent.receive();
        if (message!=null) {  /* Receive Messages */
            System.out.println("CABehaviour message rec: " + message.getContent());
        }
        else {
            block();
        }
        // Message is not correct sensor:
        block();
        // Else:
        // If sensor is in front:
        if (true) {
            // Stop both motors.
            Delay.msDelay(1);
        }
    }
}
