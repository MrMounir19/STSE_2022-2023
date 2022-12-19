package Behaviours;

import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseRobot.SensorControl;
import WarehouseShared.Position;
import jade.core.behaviours.CyclicBehaviour;
import WarehouseRobot.MotorControl;
import lejos.utility.Delay;
import java.util.ArrayList;

/**
 * Handles the reoccurring standard behaviours of our physical robot.
 * Mainly consisting of getting jobs, handling them and reacting to collisions along the way.
 *
 * @author Anthony
 * @author Senne
 * @since 19/12/2022
 */
public class GeneralRobotBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        block(100);
        // Position p = RobotInformation.position;
        // initialize the robot, especially important for its correct orientation
        if (!RobotInformation.isInitialized) {
            initializeRobot();
        }
        if (RobotInformation.jobs.size() == 0) {
            myAgent.send(Messages.requestJobMessage());
            // TODO: Block for X ms to receive & store job(s)?
            // Potentially request multiple?
        }
        // If the robot does not have a job, take one from the queue
        if (RobotInformation.currentJob == null && RobotInformation.jobs.size() > 0) {
            System.out.println("Taking first job");
            RobotInformation.takeJobFromQueue();
            RobotInformation.currentJob.advanceGoal();  // to get the first goal from the new job
        }
        // When the robot has a job
        if (RobotInformation.currentJob != null) {
            Delay.msDelay(150);
            // Get the position of the job goal
            Position targetpos = RobotInformation.currentJob.getCurrentGoal();
            System.out.println("goal x: " + targetpos.x +" | goal y: " + targetpos.y);
            Position p = RobotInformation.position;

            // Calculate the angle to the target position
            float yaw = (float) Math.toDegrees(RobotInformation.yaw);
            float target_angle = (float) Math.toDegrees(Math.atan2(targetpos.y - p.y, targetpos.x - p.x));
            float diff_angle;
            diff_angle = correctAngle(target_angle - yaw);  // The diff_angle has to be in [-180, 180] degrees

            // initial rotation to the direction of our first target on the path
            while (diff_angle >= 1) {
                //TODO set left or right if angles are closer
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.fastSpeed);
                MotorControl.turnLeftInPlace();

                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = correctAngle(target_angle - yaw);
                System.out.println(diff_angle);
            }

            // ------------------ Go to goal ------------------
            while (!RobotInformation.currentJob.GoalFinished()) {
                Delay.msDelay(100);

                // Collision check
                int forward_distance = SensorControl.getFrontSensorDistance();
                int forward_dist_threshold = 6;
                if (forward_distance > 0 && forward_distance <= forward_dist_threshold) {
                    System.out.println("Collision detected at range: " + forward_distance);
                    handleCollision();
                    // myAgent.addBehaviour(new ObstacleAvoidanceBehaviour());
                    // Idk deze behaviour stop de rest ni
                    break;
                }

                // Move forward
                MotorControl.moveForward();
                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = correctAngle(target_angle - yaw);

                // Forward with slight rotation if we deviate
                int diff_angle_margin = 2;
                int diff_angle_div_margin = 180 - 10;   // a margin of 10 on the standard 180-degree angle
                if (diff_angle > diff_angle_margin) {
                    System.out.println("correcting");
                    MotorControl.moveForwardPrecise(MotorControl.fastSpeed, 1, 1 + diff_angle / diff_angle_div_margin);
                } else if (diff_angle < - diff_angle_margin) {
                    System.out.println("correcting");
                    MotorControl.moveForwardPrecise(MotorControl.fastSpeed, 1 + diff_angle / diff_angle_div_margin, 1);
                } else {
                    MotorControl.moveForward();
                }
                //TODO smoothen this since the location can jitter
                System.out.println("X dist: " + Math.abs(RobotInformation.position.x - targetpos.x) + " | y dist: " + Math.abs(RobotInformation.position.y - targetpos.y));
                System.out.println("x pos: " + RobotInformation.position.x + " | ypos: " + RobotInformation.position.y);
                System.out.println("target x pos: " + targetpos.x + " | targetypos: " + targetpos.y);

                if (Math.abs(RobotInformation.position.x - targetpos.x) < 200 && Math.abs(RobotInformation.position.y - targetpos.y) < 200) {
                    MotorControl.setSpeed(MotorControl.mediumSpeed);
                    if (Math.abs(RobotInformation.position.x - targetpos.x) < 100 && Math.abs(RobotInformation.position.y - targetpos.y) < 100) {
                        System.out.println("X dist: " + Math.abs(RobotInformation.position.x - targetpos.x) + " | y dist: " + Math.abs(RobotInformation.position.x - targetpos.y));
                        // Tell system to move on to the next goal
                        System.out.println("reach goal");
                        MotorControl.stopMotors();
                        Delay.msDelay(1000);
                        RobotInformation.currentJob.advanceGoal();
                        break;
                    }
                } else {
                    MotorControl.setSpeed(MotorControl.fastSpeed);
                }
            }
        }
    }

    /**
     * Fix for first time orientation:
     * Wait for 3000 ms, and take average of coordinates over this time.
     * This is necessary because the POZYX system is not entirely accurate.
     *
     * @author Anthony
     * @author Senne
     * @since 19/12/2022
     */
    private void initializeRobot(){
        System.out.println("Init");
        RobotInformation.clearHistory();
        Delay.msDelay(3000);
        Position p = new Position(0, 0);
        ArrayList<Position> historyCopy = RobotInformation.positionHistory;
        for (Position temppos : historyCopy) {
            p.x += temppos.x;
            p.y += temppos.y;
        }
        p.x /= historyCopy.size();
        p.y /= historyCopy.size();
        RobotInformation.isInitialized = true;
    }

    /**
     * Reforms the input angle to be between [-180, 180] degrees
     * @param angle: the input angle we want to correct
     * @return the new angle, that is in the [-180, 180] degree range
     */
    private float correctAngle(float angle) {
        if (angle < -180) {
            angle += 360;
        } else if (angle >= 180) {
            angle -=360;
        }
        return angle;
    }

    /**
     * Handles a collision.
     *
     * @author Anthony
     * @author Senne
     * @since 19/12/2022
     */
    private void handleCollision () {
        int forward_distance = 20;
        System.out.println("Entering collision avoidance.");
        int turn_delay = 0;
        // We stop the robot.
        MotorControl.stopMotors();
        // We set the robot's speed to 100, which should be fairly slow allowing for a controlled manoeuvre.
        MotorControl.setSpeed(MotorControl.mediumSpeed);
        // We start turning the robot to the right, allowing the left sensor to detect the object.
        MotorControl.turnLeftInPlace();
        // While we haven't yet detected an object with the left sensor, we keep turning.
        System.out.println("Initiating turn check.");
        int SensorDistanceTolerance = 3;
        int leftSensorDistance = SensorControl.getLeftSensorDistance();
        System.out.println("Distance: " + leftSensorDistance);
        while (leftSensorDistance >= forward_distance) {
            Delay.msDelay(100);
            leftSensorDistance = SensorControl.getLeftSensorDistance();
            System.out.println("Distance: " + leftSensorDistance);

            turn_delay += 100;
            if (turn_delay > 20000) {
                // If we've been turning for 20 seconds, we assume we've overturned and stop turning.
                // TODO: Might need tuning.
                System.out.println("Turning for too long");
                break;
            }
        }
        // If we get here, the avaoidance turn is complete
        System.out.println("Avoidance turn complete.");

        // We stop the robot.
        Delay.msDelay(100);
        MotorControl.stopMotors();

        // TODO needs tuning
        MotorControl.setSpeed(MotorControl.mediumSpeed);
        boolean didTurn = false;
        System.out.println("Start loop");
        while (true) {
            Delay.msDelay(100);
            int frontSens = SensorControl.getFrontSensorDistance();
            System.out.println("get front sensor");
            if (frontSens > 0 && frontSens <= forward_distance - 5) {
                handleCollision();
            }
            if (SensorControl.getLeftSensorDistance() > (forward_distance + SensorDistanceTolerance) && !didTurn) {
                didTurn = true;
                MotorControl.turnRightInPlace();
            } else if (SensorControl.getLeftSensorDistance() < (forward_distance - SensorDistanceTolerance) && !didTurn) {
                didTurn = true;
                MotorControl.turnLeftInPlace();
            } else {
                MotorControl.moveForward();
                didTurn = false;
                Delay.msDelay(200);
            }

            // Check if back on path
            int path_margin = 5;
            // Calculate distance of current position to line
            Position p1 = RobotInformation.currentJob.previousGoal;
            Position p2 = RobotInformation.currentJob.currentGoal;
            Position p0 = RobotInformation.position;

            double linedistance = Math.abs((p2.x-p1.x)*(p1.y-p0.y)-(p1.x-p0.x)*(p2.y-p1.y))/Math.sqrt(((p2.x-p1.x)*(p2.x-p1.x))+((p2.y-p1.y)*(p2.y-p1.y)));
            if (linedistance < path_margin) {
                break;
            }
        }
    }
}
