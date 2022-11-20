package Agents;

import Behaviours.CommanderBehaviour;
import jade.core.Agent;

/**
 * This is a simple Agent which uses Commander behaviour
 *
 * @author Senne
 * @author Anthony
 * @version 1.0
 * @see CommanderBehaviour
 * @since 02/11/2022
 */
public class CommandAgent extends Agent {
    @Override
    public void setup() {
        addBehaviour(new CommanderBehaviour());
    }
}
