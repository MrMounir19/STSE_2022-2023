package Containers;

import WarehouseRobot.RobComponents;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

/**
 * This is a simple container class. It creates a container for a single agent, to be deployed on a single machine.
 * The final version of the project will have multiple agents per machine, and multiple machines.
 * This is primarily for testing and early development purposes.
 *
 * @author Senne
 * @author Maxim
 * @author Anthony
 * @author Thimoty
 * @version 1.0
 * @see jade.core.Runtime
 * @see jade.util.ExtendedProperties
 * @see jade.core.Profile
 * @see jade.core.ProfileImpl
 * @see jade.wrapper.AgentContainer
 * @see jade.wrapper.AgentController
 * @since 02/11/2022
 */
public class SimpleContainer {
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
            SimpleContainer.start();

            RobComponents.init();
            AgentController motorAgent = agentContainer.createNewAgent("RobotAgent", "Agents.RobotAgent", new Object[]{});
            motorAgent.start();

        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

    private static void start() {
        System.out.println("Starting container...");
    }
}
