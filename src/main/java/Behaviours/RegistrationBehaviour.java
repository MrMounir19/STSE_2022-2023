package Behaviours;

import Utils.Messages;
import jade.core.behaviours.OneShotBehaviour;


/**
 * This is a simple robot registration behaviour.
 * It is called once when the robot joins the network, and facilitates the registration of the robot with the server.
 *
 * @author Maxim
 * @version 1.0
 * @since 23/11/2022
 */
public class RegistrationBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        myAgent.send(Messages.registrationMessage());
    }
}