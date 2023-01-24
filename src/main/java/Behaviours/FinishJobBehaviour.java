package Behaviours;

import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseShared.Job;
import jade.core.behaviours.OneShotBehaviour;

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

        System.out.println("Finishing job: " + job);

        myAgent.send(Messages.finishedJobMessage(job));

        RobotInformation.takeJobFromQueue(); //TODO: Should we do this here?
    }
}
