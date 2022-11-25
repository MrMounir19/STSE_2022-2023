package Containers;

import Utils.RobotMessage;
import WarehouseRobot.RobComponents;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
public class RobotContainer extends BaseContainer {
    public static void main(String[] args) {
        RobotContainer robotContainer = new RobotContainer();
        robotContainer.setConfigFromPath("./configs/robotcontainer.json");
        robotContainer.start();
    }

    @Override
    protected void createProperties() {
        properties = new ExtendedProperties();
        properties.setProperty(Profile.GUI, "false");
        properties.setProperty(Profile.MAIN_HOST, "127.0.0.1");
        properties.setProperty(Profile.MAIN_PORT, "1099");
        properties.setProperty(Profile.LOCAL_HOST, "127.0.0.1");
        properties.setProperty(Profile.LOCAL_PORT, "1099");
//        properties.setProperty(Profile.PLATFORM_ID, null);
    }

    @Override
    protected void createAgents() {
//        RobComponents.init();
        super.createAgents();
    }

    @Override
    protected void createContainer() {
        agentContainer = runtime.createAgentContainer(profile);
    }
}
