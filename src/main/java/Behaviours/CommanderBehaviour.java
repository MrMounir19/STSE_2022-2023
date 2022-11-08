package Behaviours;

import WarehouseRobot.MotorControl;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

import java.util.List;
import java.util.Random;

public class CommanderBehaviour extends CyclicBehaviour {
    //static List<String> commands = List.of("forward", "left", "right", "backward", "stop");
    static List<String> commands = List.of("forward");
    Random random = new Random();

    @Override
    public void action() {
        String command = getRandomCommand();
        sendCommand(command);
        Delay.msDelay(5000);
        block(5000);
    }

    private String getRandomCommand() {
        return commands.get(random.nextInt(commands.size()));
    }

    private void sendCommand(String command) {
        System.out.println("Sending command: " + command);

        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("RobotAgent", AID.ISLOCALNAME));
        message.setContent(command);
        myAgent.send(message);
    }
}
