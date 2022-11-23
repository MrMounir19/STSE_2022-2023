package Agents;

import Behaviours.RegistrationBrokerBehaviour;
import jade.core.Agent;
import jade.domain.introspection.AddedBehaviour;

import java.util.ArrayList;

public class RegistrationBrokerAgent extends Agent {
    ArrayList<String> robots = new ArrayList<String>();
    // TODO: Probably needs to access the agent container itself?
    // Needs to be available to other agents in the main container. Possibly through a "GET" request-esque system, if direct access is impossible.

    @Override
    protected void setup() {
        addBehaviour(new RegistrationBrokerBehaviour());
    }
}
