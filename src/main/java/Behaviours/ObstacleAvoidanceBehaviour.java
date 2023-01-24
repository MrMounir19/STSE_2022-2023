package Behaviours;

import WarehouseRobot.MotorControl;
import WarehouseRobot.RobotInformation;
import WarehouseRobot.SensorControl;
import jade.core.behaviours.OneShotBehaviour;
import lejos.utility.Delay;

public class ObstacleAvoidanceBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        handleCollision();
    }

    private void handleCollision () {
        int forward_distance = 20;
        System.out.println("Entering collision avoidance.");
        int turn_delay = 0;
        // We stop the robot.
        MotorControl.stopMotors();
        // We set the robot's speed to 100, which should be fairly slow allowing for a controlled manoeuvre.
        MotorControl.setSpeed(MotorControl.mediumSpeed);
        // We start turning the robot to the right, allowing the left sensor to detect the object.
        MotorControl.turnRightInPlace();
        // While we haven't yet detected an object with the left sensor, we keep turning.
        System.out.println("Initiating turn check.");
        int SensorDistanceTolerance = 3;
        int distance = SensorControl.getLeftSensorDistance();
        System.out.println("Distance: " + distance);
        while (distance >= forward_distance) {
            Delay.msDelay(100);
            distance = SensorControl.getLeftSensorDistance();
            System.out.println("Distance: " + distance);

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
            // TODO: This never exits the loop? @Anthony @Senne
            Delay.msDelay(100);
            int forward = SensorControl.getFrontSensorDistance();
            if (forward > 0 && forward <= forward_distance - 5) {
                handleCollision();
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

            //Check if back on path
            //calculate distance of current position to line
            int x1 = (int) RobotInformation.jobStartPosition.x;
            int y1 = (int) RobotInformation.jobStartPosition.y;

            int x2 = (int) RobotInformation.currentJob.getDestination().x;
            int y2 = (int) RobotInformation.currentJob.getDestination().y;

            int x0 = (int) RobotInformation.position.x;
            int y0 = (int) RobotInformation.position.y;

            double linedistance = Math.abs((x2-x1)*(y1-y0)-(x1-x0)*(y2-y1))/Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
            if (linedistance < 5) {
                break;
            }
        }
    }
}
