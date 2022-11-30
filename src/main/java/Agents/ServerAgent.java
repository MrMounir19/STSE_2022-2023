package Agents;

import Behaviours.ServerMessageParserBehaviour;
import jade.core.Agent;

/**
 * This agent is used for the server.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @see ServerMessageParserBehaviour
 * @since 26/11/2022
 */
public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new ServerMessageParserBehaviour());
    }
}
