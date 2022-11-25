import Containers.RobotContainer;

// For parallel testing of containers on localhost.
public class TestRobotContainer extends RobotContainer {
    public static void main(String[] args) {
        TestRobotContainer robotContainer = new TestRobotContainer();
        robotContainer.setConfigFromPath("./configs/robotcontainer.json");
        robotContainer.start();
    }
}
