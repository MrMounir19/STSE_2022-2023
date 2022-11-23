package Agents;

import Behaviours.RegistrationBehaviour;
import Behaviours.RegistrationBrokerBehaviour;
import jade.core.Agent;

import java.util.ArrayList;

public class RegistrationAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new RegistrationBehaviour());
    }
}
