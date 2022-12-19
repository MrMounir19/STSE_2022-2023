package Behaviours;

import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseShared.Job;
import jade.core.behaviours.OneShotBehaviour;

public class FailJobBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        Job job = RobotInformation.currentJob;

        if (job == null) {
            System.out.println("Job is null");
            return;
        }

        myAgent.send(Messages.failedJobMessage(job));
    }
}
