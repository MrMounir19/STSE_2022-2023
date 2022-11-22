package Containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

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
    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            properties.setProperty(Profile.LOCAL_HOST, "192.168.0.181");
            properties.setProperty(Profile.LOCAL_PORT, "1099");

            Profile profile = new ProfileImpl(properties);

            AgentContainer agentContainer = runtime.createMainContainer(profile);
            MainContainer.start();

            AgentController Agent1 = agentContainer.createNewAgent("CommandAgent", "Agents.CommandAgent", new Object[]{});
            Agent1.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start() {
        System.out.println("Main container started");
    }
}
