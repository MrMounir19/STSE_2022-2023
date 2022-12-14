package Behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 *
 * @author Maxim
 * @author Thimoty
 * @since 10/12/2022
 */
public class JobFinishedBehaviour extends OneShotBehaviour {
    ACLMessage message;

    JobFinishedBehaviour (ACLMessage message) {
        this.message = message;
    }

    @Override
    public void action() {

    }
}
