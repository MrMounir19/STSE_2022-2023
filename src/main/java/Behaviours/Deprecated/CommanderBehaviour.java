package Behaviours.Deprecated;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

import java.util.List;
import java.util.Random;

/**
 * This is a simple command sending behaviour.
 * It can send a command to an agent with a given name.
 *
 * @author Maxim
 * @author Senne
 * @version 1.0
 * @since 08/11/2022
 */
public class CommanderBehaviour extends CyclicBehaviour {
    static List<String> commands = List.of("forward", "left", "right", "backward", "stop");
    Random random = new Random();

    @Override
    public void action() {
        String command = getRandomCommand();
        sendCommand(command);
        Delay.msDelay(5000);
        block(5000);
    }

    /**
     * This method selects a random command from the list of commands.
     *
     * @return The selected command. (String)
     * @implNote The list of commands is defined in the static variable 'commands'.
     */
    private String getRandomCommand() {
        return commands.get(random.nextInt(commands.size()));
    }

    /**
     * This method sends a command to an agent with a given name.
     *
     * @param command The command to send. (String)
     * @implNote The name of the agent is currently hardcoded as "RobotAgent".
     */
    private void sendCommand(String command) {
        System.out.println("Sending command: " + command);

        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("RobotAgent", AID.ISLOCALNAME));
        message.setContent(command);
        myAgent.send(message);
    }
}
