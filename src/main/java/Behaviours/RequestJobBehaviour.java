package Behaviours;

import Utils.Messages;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 *
 * @author Maxim
 * @author Thimoty
 * @since 19/12/2022
 */
public class RequestJobBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        myAgent.send(Messages.requestJobMessage());
    }
}
