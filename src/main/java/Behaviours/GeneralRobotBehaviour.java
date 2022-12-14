package Behaviours;

import WarehouseRobot.RobotInformation;
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
        if (RobotInformation.currentJob != null) {
            //Fix first time orientation
            RobotInformation.clearHistory();
            Delay.msDelay(3000);
            float x = 0;
            float y = 0;
            ArrayList<ArrayList<Float>> historyCopy =  RobotInformation.positionHistory;
            for (ArrayList<Float> tuple : historyCopy) {
                x += tuple.get(0);
                y += tuple.get(1);
            }
            x /= historyCopy.size();
            y /= historyCopy.size();

            float target_x = RobotInformation.currentJob.getCurrentGoal().x;
            float target_y = RobotInformation.currentJob.getCurrentGoal().y;

            float yaw = (float) Math.toDegrees(RobotInformation.orientation);

            float target_angle = (float) Math.toDegrees(Math.atan2(y - target_y, target_x - x));
            float diff_angle = target_angle - yaw;

            diff_angle = diff_angle % 360;
            while (diff_angle < 0) { //pretty sure this comparison is valid for doubles and floats
                diff_angle += 360.0;
            }

            //initial rotate
            while (true) {
                //TODO set left or right if angles are closer
                Delay.msDelay(100);
                MotorControl.setSpeed(MotorControl.slowSpeed);
                MotorControl.turnLeftInPlace();

                yaw = (float) Math.toDegrees(RobotInformation.orientation);
                diff_angle = target_angle - yaw;
                if (diff_angle < 10) {
                    break;
                }
            }

            //Go to goal
            while (true) {
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
                if (Math.abs(RobotInformation.xPosition - target_x) < 100 &&  Math.abs(RobotInformation.yPosition - target_y) <100) {
                    break;
                }
            }

            //TODO this should handle the next job or next location + messaging
            while(true) {
                Delay.msDelay(100);
                MotorControl.turnLeftInPlace();
            }

        }



    }


}
