package ExampleAgentsSimple;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class SimpleContainer {
    public static void main(String[] args) {

        try {


            Runtime runtime = Runtime.instance();
            String target ="192.168.0.174";
            String source ="192.168.0.180";
            ProfileImpl p = new ProfileImpl();

            p.setParameter(Profile.MAIN_HOST,target);
            p.setParameter(Profile.LOCAL_HOST,source);
            p.setParameter(Profile.LOCAL_PORT,"1099");
            p.setParameter(Profile.MAIN_PORT,"1099");
            p.setParameter(Profile.MAIN, "false");

            AgentContainer agentContainer=runtime.createAgentContainer(p);
            ExampleAgentsSimple.SimpleContainer.start();

            Object reference = new Object();
            Object agent_args[] = new Object[1];
            agent_args[0] = reference;

            AgentController basicAgent = agentContainer.createNewAgent("BasicAgent",
                    "ExampleAgentsSimple.BasicAgent", agent_args);
            basicAgent.start();







        } catch (ControllerException e) {
            e.printStackTrace();
        }


    }

    private static void start() {
        InitComps.configuration();
    }
}
