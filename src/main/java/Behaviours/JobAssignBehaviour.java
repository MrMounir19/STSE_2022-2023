package Behaviours;

import Utils.Messages;
import WarehouseServer.RobotObject;
import WarehouseServer.RobotStorage;
import WarehouseServer.Scheduler;
import WarehouseShared.Job;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 *
 * @author Maxim
 * @author Thimoty
 * @since 10/12/2022
 */
public class JobAssignBehaviour extends OneShotBehaviour {
    String robotId;

    JobAssignBehaviour(String robotId) {
        this.robotId = robotId;
    }

    @Override
    public void action() {
        System.out.println("Attempting to assign job to " + robotId + ".");
        RobotObject robot = RobotStorage.getFromID(robotId);

        if (robot == null) {
            System.out.println("Tried assigning job to " + robotId + ". But robot was not found in the system.");
            return;
        }

        Job job = Scheduler.requestJob(robot);

        if (job == null) {
            System.out.println("Job is null. Could not assign job.");
            return;
        }

        myAgent.send(Messages.assignJobMessage(robot.getRobotId(), job));
    }
}
