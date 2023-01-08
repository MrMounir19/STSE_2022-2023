package Behaviours;

import WarehouseServer.JobStorage;
import WarehouseServer.RobotObject;
import WarehouseShared.Job;
import jade.core.behaviours.CyclicBehaviour;

import java.util.ArrayList;
import java.util.Map;

public class GeneralServerBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        checkIdleRobots();
    }

    private void checkIdleRobots(){
        for(Map.Entry<RobotObject, ArrayList<Job>> set : JobStorage.robotJobs.entrySet()){
            if(!set.getValue().isEmpty()){
                continue;
            }
            handleIdleRobot(set.getKey().getRobotId());
        }
    }

    private void handleIdleRobot(String robotId) {
        myAgent.addBehaviour(new JobAssignBehaviour(robotId));
    }
}
