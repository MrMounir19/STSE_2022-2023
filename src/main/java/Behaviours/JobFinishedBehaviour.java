package Behaviours;

import Utils.Messages;
import WarehouseServer.JobStorage;
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
public class JobFinishedBehaviour extends OneShotBehaviour {
    ACLMessage message;

    JobFinishedBehaviour (ACLMessage message) {
        this.message = message;
    }

    @Override
    public void action() {
        RobotObject robot = RobotStorage.getFromACLMessage(message);
        String content = message.getContent();
        int jobId = Messages.toJson(content).get("data").getAsInt();
        Job job = JobStorage.getFromId(jobId);
        Scheduler.finishJob(robot, job);
    }
}
