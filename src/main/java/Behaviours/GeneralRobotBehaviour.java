package Behaviours;

import WarehouseRobot.RobotInformation;
import WarehouseShared.Position;
import jade.core.behaviours.CyclicBehaviour;
import WarehouseRobot.MotorControl;
import lejos.utility.Delay;

import java.awt.*;
import java.util.ArrayList;


public class GeneralRobotBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        if (RobotInformation.currentJob == null && RobotInformation.jobs.size() > 0) {
            RobotInformation.takeJobFromQueue();
        }
        Position p = RobotInformation.position;
        if(!RobotInformation.isInitialized){
            //Fix first time orientation
            RobotInformation.clearHistory();
            Delay.msDelay(3000);
            p = new Position(0,0);
            ArrayList<Position> historyCopy =  RobotInformation.positionHistory;
            for (Position temppos : historyCopy) {
                p.x += temppos.x;
                p.y += temppos.y;
            }
            p.x /= historyCopy.size();
            p.y /= historyCopy.size();
            // -------------------------------------
            RobotInformation.isInitialized = true;
        }
        assert RobotInformation.currentJob != null;
        while (!RobotInformation.currentJob.GoalFinished()) {
            Position targetpos = RobotInformation.currentJob.getCurrentGoal();
            float yaw = (float) Math.toDegrees(RobotInformation.orientation);

            float target_angle = (float) Math.toDegrees(Math.atan2(p.y - targetpos.y, targetpos.x - p.x));
            float diff_angle = target_angle - yaw;

            diff_angle = diff_angle % 360;
            while (diff_angle < 0) { //pretty sure this comparison is valid for doubles and floats
                diff_angle += 360.0;
            }

            //initial rotate
            do {
                //TODO set left or right if angles are closer
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.slowSpeed);
                MotorControl.turnLeftInPlace();

                yaw = (float) Math.toDegrees(RobotInformation.orientation);
                diff_angle = target_angle - yaw;
            } while (!(diff_angle < 10));

            // ------------------ Go to goal ------------------
            Delay.msDelay(100);
            MotorControl.setSpeed(MotorControl.mediumSpeed);
            MotorControl.moveForward();

            yaw = (float) Math.toDegrees(RobotInformation.orientation);
            diff_angle = target_angle - yaw;
            //Forward with slight rotation if we deviate
            if (diff_angle > 10 && diff_angle <= 180) {
                MotorControl.moveForwardPrecise(MotorControl.mediumSpeed, 1+ diff_angle/170, 1);
            } else if (diff_angle < 350 && diff_angle > 180) {
                MotorControl.moveForwardPrecise(MotorControl.mediumSpeed, 1, 1+ diff_angle/170);
            }
            //TODO smoothen this since the location can jitter
            if (Math.abs(RobotInformation.position.x - targetpos.x) < 100 &&  Math.abs(RobotInformation.position.x - targetpos.y) <100) {
                // Tell system to move on to the next goal
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
