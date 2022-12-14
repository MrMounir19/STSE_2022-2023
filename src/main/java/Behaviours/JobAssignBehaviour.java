package Behaviours;

import Utils.Messages;
import WarehouseServer.RobotObject;
import WarehouseServer.RobotStorage;
import WarehouseServer.Scheduler;
import WarehouseShared.Job;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 *
 * @author Maxim
 * @author Thimoty
 * @since 10/12/2022
 */
public class JobAssignBehaviour extends OneShotBehaviour {
    ACLMessage message;

    JobAssignBehaviour(ACLMessage message) {
        this.message = message;
    }

    @Override
    public void action() {
        RobotObject robot = RobotStorage.getFromACLMessage(message);
        Job job = Scheduler.requestJob(robot);

        if (job == null) {
            System.out.println("Job is Null");
            return;
        }

        myAgent.send(Messages.assignJobMessage(robot.getRobotId(), job));
    }
}
