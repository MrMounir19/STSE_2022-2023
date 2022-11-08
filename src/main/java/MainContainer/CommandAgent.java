package MainContainer;
import Behaviours.CommanderBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CommandAgent extends Agent{
    @Override
    public void setup() {
        addBehaviour(new CommanderBehaviour());
    }
}
