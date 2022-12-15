package Behaviours;

import WarehouseRobot.RobotInformation;
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
            float target_angle = (float) Math.abs(Math.toDegrees(Math.atan2(p.y - targetpos.y, targetpos.x - p.x)));
            System.out.println("target");
            System.out.println(target_angle);

            float diff_angle = Math.abs(target_angle - yaw);

            diff_angle = diff_angle % 360;
            while (diff_angle < 0) { //pretty sure this comparison is valid for doubles and floats
                diff_angle += 360.0;
            }
            System.out.println("diff angle");
            System.out.println(diff_angle);
            System.out.println("angle calculation done");

            //initial rotate
            do {
                //TODO set left or right if angles are closer
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.mediumSpeed);
                MotorControl.turnLeftInPlace();

                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = Math.abs(target_angle - yaw);
                System.out.println(diff_angle);
            } while (!(diff_angle < 1));
            System.out.println("finished rotate loop");

            // ------------------ Go to goal ------------------
            while (!RobotInformation.currentJob.GoalFinished()) {
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.mediumSpeed);
                MotorControl.moveForward();

                yaw = (float) Math.toDegrees(RobotInformation.yaw);
                diff_angle = target_angle - yaw;
                //Forward with slight rotation if we deviate
//                if (diff_angle > 10 && diff_angle <= 180) {
//                    MotorControl.moveForwardPrecise(MotorControl.mediumSpeed, 1 + diff_angle / 170, 1);
//                } else if (diff_angle < 350 && diff_angle > 180) {
//                    MotorControl.moveForwardPrecise(MotorControl.mediumSpeed, 1, 1 + diff_angle / 170);
//                }
                //TODO smoothen this since the location can jitter
                System.out.println("X dist:" + Math.abs(RobotInformation.position.x - targetpos.x) + "  y dist:" + Math.abs(RobotInformation.position.y - targetpos.y));
                System.out.println("x pos: " + RobotInformation.position.x + "  ypos:" + RobotInformation.position.y);
                System.out.println("target x pos: " + targetpos.x + "  targetypos:" + targetpos.y);

                if (Math.abs(RobotInformation.position.x - targetpos.x) < 200 && Math.abs(RobotInformation.position.y - targetpos.y) < 200) {
                    System.out.println("X dist:" + Math.abs(RobotInformation.position.x - targetpos.x) + "  y dist:" + Math.abs(RobotInformation.position.x - targetpos.y));
                    // Tell system to move on to the next goal
                    System.out.println("reach goal");
                    MotorControl.stopMotors();
                    Delay.msDelay(1000);
                    RobotInformation.currentJob.advanceGoal();
                    break;
                }

            /*//TODO this should handle the next job or next location + messaging
            while(true) {
                Delay.msDelay(100);
                MotorControl.turnLeftInPlace();
            }*/


            }
        }

    }


}
