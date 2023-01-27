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
                MotorControl.moveForward();
            }
            if (message.getContent().equals("Left")) {
                MotorControl.stopMotors();
                MotorControl.turnLeftInPlace();
                MotorControl.stopMotors();
            }

        }
    }
}
