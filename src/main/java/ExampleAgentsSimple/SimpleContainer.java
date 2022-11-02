package ExampleAgentsSimple;

import WarehouseRobot.RobComponents;
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
            String source ="192.168.0.165";
            ProfileImpl p = new ProfileImpl(target,1099,null,false);

            p.setParameter(Profile.LOCAL_HOST,source);
            p.setParameter(Profile.LOCAL_PORT,"1099");

            AgentContainer agentContainer=runtime.createAgentContainer(p);
            ExampleAgentsSimple.SimpleContainer.start();

            // Properties properties = new ExtendedProperties();
            // properties.setProperty(Profile.GUI, "true");
            //      properties.
            //Profile profile = new ProfileImpl(properties);
            RobComponents.init();
            AgentController motorAgent = agentContainer.createNewAgent("CollisionAvoidanceAgent",
                    "Agents.CollisionAvoidanceAgent",new Object[]{});
            motorAgent.start();







        } catch (ControllerException e) {
            e.printStackTrace();
        }


    }

    private static void start() {
        InitComps.configuration();
    }
}
