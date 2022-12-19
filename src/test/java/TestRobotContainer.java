import Containers.BaseContainer;
import Containers.RobotContainer;
import WarehouseRobot.RobComponents;

// For parallel testing of containers on localhost.
public class TestRobotContainer extends RobotContainer {
    public static void main(String[] args) {
        TestRobotContainer robotContainer = new TestRobotContainer();
        robotContainer.setConfigFromPath("./configs/testrobotcontainer.json");
        robotContainer.start();
    }
}
