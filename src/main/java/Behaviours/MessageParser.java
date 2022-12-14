package Behaviours;

import WarehouseRobot.MotorControl;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class MessageParser extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message= getAgent().receive();
        if (message!=null){
            System.out.println("Motor agent: " + message.getContent());

            if (message.getContent().equals("Forward")) {
                System.out.println("Forward movement");
                MotorControl.moveForward();
            }
            if (message.getContent().equals("Left")) {
                System.out.println("Left movement");
                MotorControl.stopMotors();
                MotorControl.turnLeftInPlace();
                MotorControl.stopMotors();
            }

        }
    }
}
