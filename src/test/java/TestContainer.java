import WarehouseRobot.RobComponents;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class TestContainer {
    public static void main(String[] args) {

        try {
            Runtime runtime = Runtime.instance();

            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "false");
            properties.setProperty(Profile.MAIN_HOST, "192.168.0.181");
            properties.setProperty(Profile.MAIN_PORT, "1099");
            properties.setProperty(Profile.LOCAL_HOST, "192.168.0.165");
            properties.setProperty(Profile.LOCAL_PORT, "1099");
            properties.setProperty(Profile.PLATFORM_ID, null);
            // TODO: isMain was not an option for properties, but was originally set. If the agent does not work, this may be the cause.

            ProfileImpl profile = new ProfileImpl(properties);

            AgentContainer agentContainer = runtime.createAgentContainer(profile);
            TestContainer.start();

            RobComponents.init();
            AgentController registrationAgent = agentContainer.createNewAgent("RegistrationAgent", "Agents.RegistrationAgent", new Object[]{});
            registrationAgent.start();

        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

    private static void start() {
        System.out.println("Starting container...");
    }
}