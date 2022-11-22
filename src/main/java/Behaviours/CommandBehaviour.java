package Behaviours;

import WarehouseRobot.MotorControl;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Objects;

/**
 * This is a simple command parsing behaviour.
 * It parses received messages and, in case of a valid command, it performs the appropriate action.
 *
 * @author Maxim
 * @author Senne
 * @version 1.0
 * @since 08/11/2022
 */
public class CommandBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            handleMessage(message);
        } else {
            block();
        }
    }

    /**
     * This method handles the received message, and performs the appropriate action (if any).
     *
     * @param message The received message. (ACLMessage)
     * @see ACLMessage
     */
    private void handleMessage(ACLMessage message) {
        String content = message.getContent();

        System.out.println("CommandAgent: " + content);

        if (Objects.equals(content, "forward")) {
            MotorControl.setSpeed(MotorControl.fastSpeed);
            MotorControl.moveForward();

        } else if (Objects.equals(content, "left")) {
            MotorControl.setSpeed(MotorControl.slowSpeed);
            MotorControl.turnLeftInPlace();

        } else if (Objects.equals(content, "right")) {
            MotorControl.setSpeed(MotorControl.slowSpeed);
            MotorControl.turnRightInPlace();

        } else if (Objects.equals(content, "backward")) {
            MotorControl.setSpeed(MotorControl.mediumSpeed);
            MotorControl.moveBackward();

        } else if (Objects.equals(content, "stop")) {
            MotorControl.setSpeed(MotorControl.mediumSpeed);
            MotorControl.stopMotors();

        } else {
            System.out.println("CommandAgent: Unknown command: " + content);
        }
    }
}
