package Behaviours;

import WarehouseRobot.MotorControl;
import WarehouseRobot.RobComponents;
import WarehouseRobot.SensorControl;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

import static jade.lang.acl.ACLMessage.INFORM;

public class CollisionAvoidanceBehaviour extends CyclicBehaviour {
    static private final int collisionDistance = 10;

    @Override
    public void action() {
        int forward_distance = SensorControl.getFrontSensorDistance();
        if (forward_distance > 0 && forward_distance <= collisionDistance) {
            System.out.println("Collision detected at range: " + forward_distance);
            handleCollision(forward_distance);
        }

        block(100);
    }

    /**
     * @param forward_distance Distance to the object in front of the robot before collision avoidance starts.
     */
    private void handleCollision(int forward_distance) {
        // TODO: handle if distance == 0.
        System.out.println("Entering collision avoidance.");
        int turn_delay = 0;
        // We stop the robot.
        MotorControl.stopMotors();
        // We set the robot's speed to 100, which should be fairly slow allowing for a controlled manoeuvre.
        MotorControl.setSpeed(MotorControl.slowSpeed);
        // We start turning the robot to the right, allowing the left sensor to detect the object.
        MotorControl.turnRightInPlace();
        // While we haven't yet detected an object with the left sensor, we keep turning.
        System.out.println("Initiating turn check.");
        while (SensorControl.getLeftSensorDistance() >= forward_distance) {
            Delay.msDelay(100);
            turn_delay += 100;
            if (turn_delay > 3000) {
                // If we've been turning for 3 seconds, we assume we've overturned and stop turning.
                // TODO: Might need tuning.
                break;
            }
        }

        System.out.println("Avoidance turn complete.");

        // We stop the robot.
        MotorControl.stopMotors();
        // Check forward distance again.
        forward_distance = Math.max(1, SensorControl.getFrontSensorDistance());
        System.out.println("New forward distance: " + forward_distance);
        if (forward_distance <= collisionDistance) {
            System.out.println("New collision detected, recursively calling handleCollision.");
            handleCollision(forward_distance);
        }

        System.out.println("Exiting turn loop.");

        int total_delay = 0;

        // Nothing detected ahead. The robot will move forward to avoid the object and then continue its path.
        MotorControl.setSpeed(MotorControl.mediumSpeed);
        MotorControl.moveForward();
        System.out.println("Moving forward past collision object.");
        while (SensorControl.getLeftSensorDistance() <= (forward_distance * 1.2)) {
            Delay.msDelay(100);
            total_delay += 100;
            if (total_delay > 5000) {
                // If we've been turning for 5 seconds, we assume we've passed / are stuck and stop turning.
                // TODO: Might need tuning.
                break;
            }
        }

        total_delay = 0;

        int extra_delay = 2000;
        while (total_delay < extra_delay) {
            total_delay += 100;
            Delay.msDelay(100);
        }

        // We turn it back into the original direction.
        System.out.println("Correcting back to original direction.");
        MotorControl.stopMotors();
        MotorControl.setSpeed(MotorControl.slowSpeed);
        while (turn_delay > 0) {
            MotorControl.turnLeftInPlace();
            Delay.msDelay(100);
            turn_delay -= 100;
        }

        // We stop the motor so another behaviour can take over.
        MotorControl.stopMotors();
        System.out.println("Collision handling finished giving control to other behaviour");
    }
}
