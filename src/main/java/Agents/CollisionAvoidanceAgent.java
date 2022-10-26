package Agents;

import jade.core.Agent;

import Behaviours.CollisionAvoidanceBehaviour;
import jade.core.behaviours.CyclicBehaviour;

public class CollisionAvoidanceAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CollisionAvoidanceBehaviour());
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                // Move forward
            }
        });
    }
}
