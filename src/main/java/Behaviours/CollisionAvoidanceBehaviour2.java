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
        // TODO needs tuning
        int SensorDistanceTolerance = 3;
        while (true) {
            Delay.msDelay(100);
            if (SensorControl.getLeftSensorDistance() > (forward_distance + SensorDistanceTolerance)) {
                MotorControl.turnLeftInPlace();
            } else if (SensorControl.getLeftSensorDistance() < (forward_distance - SensorDistanceTolerance)) {
                MotorControl.turnRightInPlace();
            } else {
                MotorControl.moveForward();
                Delay.msDelay(100);
            }

        }
    }
}
