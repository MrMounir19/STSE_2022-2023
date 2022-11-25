package Containers;

import Utils.RobotMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
public class MainContainer extends BaseContainer {
    public static void main(String[] args) {
        MainContainer mainContainer = new MainContainer();
        mainContainer.setConfigFromPath("./configs/maincontainer.json");
        mainContainer.start();
    }
}
