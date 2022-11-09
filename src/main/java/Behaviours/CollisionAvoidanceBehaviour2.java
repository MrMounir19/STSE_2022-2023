package Behaviours;

import WarehouseRobot.MotorControl;
import WarehouseRobot.SensorControl;
import jade.core.behaviours.CyclicBehaviour;
import lejos.utility.Delay;

public class CollisionAvoidanceBehaviour2  extends CyclicBehaviour {
    static private final int collisionDistance = 15;

    @Override
    public void action() {
        int forward_distance = SensorControl.getFrontSensorDistance();
        if (forward_distance > 0 && forward_distance <= collisionDistance - 5) {
            System.out.println("Collision detected at range: " + forward_distance);
            handleCollision(collisionDistance);
        }

        block(100);
    }

    /**
     * @param forward_distance Distance to the object in front of the robot before collision avoidance starts.
     */
    private void handleCollision(int forward_distance) {
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
        int SensorDistanceTolerance = 3;
        int distance = SensorControl.getLeftSensorDistance();
        while (distance >= forward_distance) {
            Delay.msDelay(100);
            distance = SensorControl.getLeftSensorDistance();
            System.out.println(distance);

            turn_delay += 100;
            if (turn_delay > 10000) {
                // If we've been turning for 3 seconds, we assume we've overturned and stop turning.
                // TODO: Might need tuning.
                System.out.println("Turning for too long");
                break;
            }
        }

        System.out.println("Avoidance turn complete.");

        // We stop the robot.
        Delay.msDelay(100);
        MotorControl.stopMotors();

        // TODO needs tuning
        MotorControl.setSpeed(MotorControl.mediumSpeed);
        boolean didTurn = false;
        while (true) {
            Delay.msDelay(100);
            int forward = SensorControl.getFrontSensorDistance();
            if (forward > 0 && forward <= forward_distance - 5) {
                handleCollision(forward_distance);
            }
            if (SensorControl.getLeftSensorDistance() > (forward_distance + SensorDistanceTolerance) && !didTurn) {
                didTurn = true;
                MotorControl.turnLeftInPlace();
            } else if (SensorControl.getLeftSensorDistance() < (forward_distance - SensorDistanceTolerance) && !didTurn) {
                didTurn = true;
                MotorControl.turnRightInPlace();
            } else {
                MotorControl.moveForward();
                didTurn = false;
                Delay.msDelay(200);
            }

        }
    }
}
