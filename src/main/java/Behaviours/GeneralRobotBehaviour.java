package Behaviours;

import WarehouseRobot.RobotInformation;
import WarehouseRobot.SensorControl;
import WarehouseShared.Position;
import jade.core.behaviours.CyclicBehaviour;
import WarehouseRobot.MotorControl;
import lejos.utility.Delay;

import java.awt.*;
import java.security.spec.ECField;
import java.util.ArrayList;


public class GeneralRobotBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        block(100);
        if (RobotInformation.currentJob == null && RobotInformation.jobs.size() > 0) {
            System.out.println("Taking first job");
            RobotInformation.takeJobFromQueue();
        }
        Position p = RobotInformation.position;
        if (!RobotInformation.isInitialized) {
            System.out.println("Init");
            //Fix first time orientation
            RobotInformation.clearHistory();
            Delay.msDelay(3000);
            p = new Position(0, 0);
            ArrayList<Position> historyCopy = RobotInformation.positionHistory;
            for (Position temppos : historyCopy) {
                p.x += temppos.x;
                p.y += temppos.y;
            }
            p.x /= historyCopy.size();
            p.y /= historyCopy.size();
            // -------------------------------------
            RobotInformation.isInitialized = true;
        }
        if (RobotInformation.currentJob != null) {
            System.out.println("Has job");
            Delay.msDelay(150);
            System.out.println("job loop");
            Position targetpos = RobotInformation.currentJob.getCurrentGoal();
            System.out.println("goal x:" + targetpos.x +"  goal y: " + targetpos.y);
            p = RobotInformation.position;
            float yaw = (float) Math.toDegrees(RobotInformation.yaw);
            System.out.println("yaw");
            System.out.println(yaw);
            System.out.println();
            float target_angle = (float)Math.toDegrees(Math.atan2(targetpos.y - p.y, targetpos.x - p.x));
            System.out.println("target");
            System.out.println(target_angle);

            float diff_angle = target_angle - yaw;


            if (diff_angle < -180) {
                diff_angle += 360;
            } else if (diff_angle >= 180) {
                diff_angle -=360;
            }
            System.out.println("diff angle");
            System.out.println(diff_angle);
            System.out.println("angle calculation done");

            //initial rotate
            do {
                //TODO set left or right if angles are closer
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.fastSpeed);
                MotorControl.turnLeftInPlace();

                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = target_angle - yaw;
                if (diff_angle < -180) {
                    diff_angle += 360;
                } else if (diff_angle >= 180) {
                    diff_angle -=360;
                }
                System.out.println(diff_angle);
            } while (!(diff_angle < 1));
            System.out.println("finished rotate loop");

            // ------------------ Go to goal ------------------
            while (!RobotInformation.currentJob.GoalFinished()) {
                Delay.msDelay(100);

                //COllision check
                int forward_distance = SensorControl.getFrontSensorDistance();
                if (forward_distance > 0 && forward_distance <= 6) {
                    System.out.println("Collision detected at range: " + forward_distance);
                    handleCollision();
//                    myAgent.addBehaviour(new ObstacleAvoidanceBehaviour());
                    //Idk deze behaviour stop de rest ni
                    break;
                }


                MotorControl.moveForward();
                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = target_angle - yaw;
                if (diff_angle < -180) {
                    diff_angle += 360;
                } else if (diff_angle >= 180) {
                    diff_angle -=360;
                }
                //Forward with slight rotation if we deviate
                System.out.println(diff_angle);
                if (diff_angle > 2 && diff_angle <= 180) {
                    System.out.println("corretign");
                    MotorControl.moveForwardPrecise(MotorControl.fastSpeed, 1, 1 + diff_angle / 170);
                } else if (diff_angle < -2 && diff_angle >= -180) {
                    System.out.println("corretign");
                    MotorControl.moveForwardPrecise(MotorControl.fastSpeed, 1 + diff_angle / 170, 1);
                } else {
                    MotorControl.moveForward();
                }
                //TODO smoothen this since the location can jitter
                System.out.println("X dist:" + Math.abs(RobotInformation.position.x - targetpos.x) + "  y dist:" + Math.abs(RobotInformation.position.y - targetpos.y));
                System.out.println("x pos: " + RobotInformation.position.x + "  ypos:" + RobotInformation.position.y);
                System.out.println("target x pos: " + targetpos.x + "  targetypos:" + targetpos.y);

                if (Math.abs(RobotInformation.position.x - targetpos.x) < 200 && Math.abs(RobotInformation.position.y - targetpos.y) < 200) {
                    MotorControl.setSpeed(MotorControl.mediumSpeed);
                    if (Math.abs(RobotInformation.position.x - targetpos.x) < 100 && Math.abs(RobotInformation.position.y - targetpos.y) < 100) {

                        System.out.println("X dist:" + Math.abs(RobotInformation.position.x - targetpos.x) + "  y dist:" + Math.abs(RobotInformation.position.x - targetpos.y));
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

            /*//TODO this should handle the next job or next location + messaging
            while(true) {
                Delay.msDelay(100);
                MotorControl.turnLeftInPlace();
            }*/


            }
        }

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
        MotorControl.turnLeftInPlace();
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
            if (turn_delay > 100000) {
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
        System.out.println("Start loop");
        while (true) {
            // TODO: This never exits the loop? @Anthony @Senne
            Delay.msDelay(100);
            int forward = SensorControl.getFrontSensorDistance();
            System.out.println("get front sensor");
            if (forward > 0 && forward <= forward_distance - 5) {
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

            //Check if back on path
            //calculate distance of current position to line
            System.out.println("need stop?");
            int x1 = (int) RobotInformation.currentJob.previousGoal.x;
            int y1 = (int) RobotInformation.currentJob.previousGoal.y;

            System.out.println("need stop2?");
            int x2 = (int) RobotInformation.currentJob.currentGoal.x;
            int y2 = (int) RobotInformation.currentJob.currentGoal.y;

            System.out.println("need stop3?");
            int x0 = (int) RobotInformation.position.x;
            int y0 = (int) RobotInformation.position.y;

            double linedistance = Math.abs((x2-x1)*(y1-y0)-(x1-x0)*(y2-y1))/Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
            if (linedistance < 5) {
                break;
            }
        }
    }
}
