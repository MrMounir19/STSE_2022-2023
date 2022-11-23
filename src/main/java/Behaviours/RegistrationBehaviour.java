package Behaviours;

import Utils.RobotMessage;
import com.google.gson.JsonObject;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;


/**
 * This is a simple robot registration behaviour.
 * It receives a ping from a new robot on the network and registers it.
 *
 * @author Maxim
 * @version 1.0
 * @since 23/11/2022
 */
public class RegistrationBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        myAgent.send(RobotMessage.registrationMessage());
    }
}