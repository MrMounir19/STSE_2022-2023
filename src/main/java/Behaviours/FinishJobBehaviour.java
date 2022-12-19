package Behaviours;

import Utils.Messages;
import WarehouseRobot.RobotInformation;
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
 * @since 19/12/2022
 */
public class FinishJobBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        Job job = RobotInformation.currentJob;

        if (job == null) {
            System.out.println("Job is null");
            return;
        }

        myAgent.send(Messages.finishedJobMessage(job));
    }
}
