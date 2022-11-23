package Containers;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main container class. It is responsible for creating the main container for our system.
 * It essentially serves as the "server" for our system.
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
public class MainContainer {
    protected static ArrayList<AgentController> agents = new ArrayList<>();
    protected static AgentContainer agentContainer = null;

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            properties.setProperty(Profile.LOCAL_HOST, "192.168.0.181");
            properties.setProperty(Profile.LOCAL_PORT, "1099");

            Profile profile = new ProfileImpl(properties);

            agentContainer = runtime.createMainContainer(profile);
            MainContainer.start();

            createAgents();

            for (AgentController agent : agents) {
                agent.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAgents() {
        try {
//            AgentController Agent1 = agentContainer.createNewAgent("CommandAgent", "Agents.CommandAgent", new Object[]{});
//            AgentController Agent2 = agentContainer.createNewAgent("UWBAgent", "Agents.UWBAgent", new Object[]{});
            agents.add(agentContainer.createNewAgent("TestAgent", "Agents.TestAgent", new Object[]{}));
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        System.out.println("Main container started");
    }
}
