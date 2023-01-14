package Behaviours;

import WarehouseServer.JobStorage;
import WarehouseServer.RobotObject;
import WarehouseServer.RobotStorage;
import jade.core.behaviours.CyclicBehaviour;

public class GeneralServerBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        checkIdleRobots();
        block(1000);
    }

    private void checkIdleRobots() {
        for(RobotObject robotObject : RobotStorage.getRobots()){
            if(!JobStorage.checkRobotIdle(robotObject)){
                continue;
            }
            handleIdleRobot(robotObject.getRobotId());
        }
    }

    private void handleIdleRobot(String robotId) {
        myAgent.addBehaviour(new JobAssignBehaviour(robotId));
    }
}
