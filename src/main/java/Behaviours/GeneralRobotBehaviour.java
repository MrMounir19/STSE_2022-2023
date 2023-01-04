package Behaviours;

import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseRobot.SensorControl;
import WarehouseShared.Position;
import jade.core.behaviours.CyclicBehaviour;
import WarehouseRobot.MotorControl;
import lejos.utility.Delay;

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
            int delay = 0;
            while ((int)RobotInformation.position.x == 0 && (int)RobotInformation.position.y == 0 && delay < 10) {
                delay += 1;
                Delay.msDelay(300);
            }
            RobotInformation.takeJobFromQueue();
            //RobotInformation.currentJob.advanceGoal();  // to get the first goal from the new job
        }
        // When the robot has a job
        if (RobotInformation.currentJob != null) {
            Delay.msDelay(150);
            // Get the position of the job goal
            Position targetpos = RobotInformation.currentJob.getCurrentGoal();
            System.out.println("robot-x: " + RobotInformation.position.x + " | robot-y: " + RobotInformation.position.y);
            System.out.println("goal x: " + targetpos.x +" | goal y: " + targetpos.y);
            Position p = getAccuratePosition();

            // Calculate the angle to the target position
            float yaw = (float) Math.toDegrees(RobotInformation.yaw);
            float target_angle = (float) Math.toDegrees(Math.atan2(targetpos.y - p.y, targetpos.x - p.x));
            float diff_angle;
            diff_angle = correctAngle(target_angle - yaw);  // The diff_angle has to be in [-180, 180] degrees

            // initial rotation to the direction of our first target on the path
            while (Math.abs(diff_angle) >= 1) {
                //TODO set left or right if angles are closer
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.slowSpeed);
                // If negative angle -> turn left
                // if positive angle -> turn right
                if (diff_angle < 0) {
                    MotorControl.turnLeftInPlace();
                }
                else {
                    MotorControl.turnRightInPlace();
                }

                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = correctAngle(target_angle - yaw);
                System.out.println("Angle: " + diff_angle);
            }

            // ------------------ Go to goal ------------------
            while (!RobotInformation.currentJob.GoalFinished()) {
                Delay.msDelay(100);

                // Collision check
                int forward_distance = SensorControl.getFrontSensorDistance();
                int forward_dist_threshold = 10;
                System.out.println("forward_distance: " +  forward_distance);
                if (forward_distance > 0 && forward_distance <= forward_dist_threshold) {
                    System.out.println("Collision detected at range: " + forward_distance);
                    handleCollision();
                    // myAgent.addBehaviour(new ObstacleAvoidanceBehaviour());
                    // Idk deze behaviour stop de rest ni
                    break;
                }

                // Move forward
                MotorControl.setSpeed(MotorControl.mediumSpeed);
                MotorControl.moveForward();
                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = correctAngle(target_angle - yaw);

                // Forward with slight rotation if we deviate
                int diff_angle_margin = 2;
                int diff_angle_div_margin = 180 - 100;   // a margin of 10 on the standard 180-degree angle
                System.out.println("diff_angle: " + diff_angle);
                if (diff_angle > diff_angle_margin) {
                    System.out.println("correcting left");
                    MotorControl.moveForwardPrecise(MotorControl.mediumSpeed, 1 + diff_angle / diff_angle_div_margin, 1);
                } else if (diff_angle < - diff_angle_margin) {
                    System.out.println("correcting right");
                    MotorControl.moveForwardPrecise(MotorControl.mediumSpeed, 1, 1 + diff_angle / diff_angle_div_margin);
                } else {
                    MotorControl.moveForward();
                }
                //TODO smoothen this since the location can jitter
                System.out.println("X dist: " + Math.abs(RobotInformation.position.x - targetpos.x) + " | y dist: " + Math.abs(RobotInformation.position.y - targetpos.y));
                System.out.println("x pos: " + RobotInformation.position.x + " | y pos: " + RobotInformation.position.y);
                System.out.println("target x pos: " + targetpos.x + " | targetypos: " + targetpos.y);

                if (Math.abs(RobotInformation.position.x - targetpos.x) < 300 && Math.abs(RobotInformation.position.y - targetpos.y) < 300) {
                    MotorControl.setSpeed(MotorControl.mediumSpeed);
                    if (Math.abs(RobotInformation.position.x - targetpos.x) < 200 && Math.abs(RobotInformation.position.y - targetpos.y) < 200) {
                        System.out.println("X dist: " + Math.abs(RobotInformation.position.x - targetpos.x) + " | y dist: " + Math.abs(RobotInformation.position.x - targetpos.y));
                        // Tell system to move on to the next goal
                        System.out.println("reached goal");
                        MotorControl.stopMotors();
                        Delay.msDelay(1000);
                        RobotInformation.currentJob.advanceGoal();
                        break;
                    }
                } else {
                    MotorControl.setSpeed(MotorControl.mediumSpeed);
                }
            }
            // TODO: Send finish (or failure) job message and remove current job.
        }
    }

    /**
     * Fix for first time orientation:
     * Wait for 3000 ms, and take average of coordinates over this time.
     * This is necessary because the POZYX system is not entirely accurate.
     * TODO: DEPRECATED
     *
     * @author Anthony
     * @author Senne
     * @since 19/12/2022
     */
    private void initializeRobot(){
        System.out.println("Init");
        RobotInformation.isInitialized = true;
    }

    /**
     * Take average of last 5 positions to calculate accurate position.
     * This is necessary because the POZYX system is not entirely accurate.
     *
     * @author Anthony
     * @author Senne
     * @since 19/12/2022
     */
    private Position getAccuratePosition() {
        RobotInformation.clearHistory();    // TODO: Don't wipe, but just fetch last 5 positions?
        while (RobotInformation.positionHistory.size() < 5) {
            Delay.msDelay(100);
        }
        Position p = new Position(0, 0);
        for (Position temppos : RobotInformation.positionHistory) {
            p.x += temppos.x;
            p.y += temppos.y;
        }
        p.x /= RobotInformation.positionHistory.size();
        p.y /= RobotInformation.positionHistory.size();

        return p;
    }

    /**
     * Reforms the input angle to be between [-180, 180] degrees
     * @param angle: the input angle we want to correct
     * @return the new angle, that is in the [-180, 180] degree range
     */
    private float correctAngle(float angle) {
        while (angle < -180) {
            angle += 360;
        }
        while (angle >= 180) {
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


        // Start by turning 90 degrees
        int SensorDistanceTolerance = 3;
        double start_yaw0 = Math.toDegrees(RobotInformation.getYaw());
        double goal_yaw0 = (start_yaw0 + 90) % 360;
        double current_yaw0 = Math.toDegrees(RobotInformation.getYaw());
        MotorControl.turnRightInPlace();
        while (Math.abs(current_yaw0 - goal_yaw0) >= SensorDistanceTolerance) {
            current_yaw0 = Math.toDegrees(RobotInformation.getYaw());
            Delay.msDelay(100);
        }
        // Then measure left sensor
        int leftSensorDistance = SensorControl.getLeftSensorDistance();
        MotorControl.moveForward();
        Delay.msDelay(1000);

        // We start turning the robot to the right, allowing the left sensor to detect the object.
       /* MotorControl.turnRightInPlace();
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
        }*/
        // If we get here, the avaoidance turn is complete
        forward_distance = leftSensorDistance;
        System.out.println("Avoidance turn complete at distance " + leftSensorDistance);

        // We stop the robot.
        Delay.msDelay(100);
        MotorControl.stopMotors();

        // TODO needs tuning
        MotorControl.setSpeed(MotorControl.mediumSpeed);
        boolean didTurn = false;
        System.out.println("Start loop");
        int attempts_count = 0; // countsturn how many times we have tried to avoid obstacle
        int left_distance_to_large = 0;
        while (true) {
            Delay.msDelay(100);
            int frontSens = SensorControl.getFrontSensorDistance();SensorControl.getLeftSensorDistance();
            int leftSens = Math.min(SensorControl.getLeftSensorDistance(), 60);
            System.out.println("frontSens: " + frontSens + "--- leftSens: " + leftSens);
            if (frontSens > 0 && frontSens <= forward_distance - 5) {
                handleCollision();
            }
            if (leftSens > 50) {
                // break
                MotorControl.moveForward();
                Delay.msDelay(500);
                double start_yaw = Math.toDegrees(RobotInformation.getYaw());
                double goal_yaw = (start_yaw + 90) % 360;
                double current_yaw = Math.toDegrees(RobotInformation.getYaw());
                System.out.println("Start: " + start_yaw + " current: " + current_yaw);
                System.out.println("goal: " + goal_yaw);
                System.out.println(Math.abs(current_yaw - goal_yaw));
                MotorControl.turnLeftInPlace();
                while (Math.abs(current_yaw - goal_yaw) >= 3) {
                    current_yaw = Math.toDegrees(RobotInformation.getYaw());
                    Delay.msDelay(100);
                }
                MotorControl.moveForward();
                Delay.msDelay(1000);

            }
            if (leftSens > (forward_distance + SensorDistanceTolerance) && !didTurn) {
                didTurn = true;
                System.out.println("turnLeftCollision");
                MotorControl.turnLeftInPlace();
            } else if (leftSens < (forward_distance - SensorDistanceTolerance) && !didTurn) {
                didTurn = true;
                System.out.println("turnRightCollision");
                MotorControl.turnRightInPlace();
            } else {
                MotorControl.moveForward();
                System.out.println("moveForwardCollision");
                attempts_count += 1;
                didTurn = false;
                Delay.msDelay(200);
            }

            // If we have tried to avoid enough times
/*            if(attempts_count > 25){
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
            }*/

        }
    }
}


