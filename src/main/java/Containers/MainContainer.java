package Containers;

/**
 * This is the main container class. It is responsible for creating the main container for our system.
 * It essentially serves as the "server" for our system.
 *
 * @author Maxim
 * @author Thimoty
 * @author Senne
 * @author Anthony
 * @version 1.0
 * @see BaseContainer
 * @since 02/11/2022
 */
public class MainContainer extends BaseContainer {
    public static void main(String[] args) throws Exception {
        MainContainer mainContainer = new MainContainer();
        mainContainer.setConfigFromPath("./configs/maincontainer.json");
        mainContainer.start();
    }
}
