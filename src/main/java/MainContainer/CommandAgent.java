package MainContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lejos.utility.Delay;

public class CommandAgent extends Agent{


    @Override
    public void setup() {
//        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
//        msg1.addReceiver(new AID("MotorAgent", AID.ISLOCALNAME));
//        msg1.setContent("Blue");
//        send(msg1);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage Message = receive();
               if (Message!=null) {
                   System.out.println("Color Sensor Agent:" +Message.getContent());
                   BasicRules();

               }
               else block();
           }
        });
    }
    public  void BasicRules() {
      //  int colorId =Devices.IdColor();


        System.out.println("Sensor agent - Main Container -  init");
        Delay.msDelay(10000);
        System.out.println("Agent start moving");

        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
        msg1.addReceiver(new AID("CollisionAvoidanceAgent", AID.ISLOCALNAME));
        msg1.setContent("Forward");
        send(msg1);

        Delay.msDelay(10000);
        System.out.println("Agent move left");

        ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
        msg2.addReceiver(new AID("CollisionAvoidanceAgent", AID.ISLOCALNAME));
        msg2.setContent("Left");
        send(msg2);

        Delay.msDelay(10000);
        System.out.println("Agent move forward again");
        ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
        msg3.addReceiver(new AID("CollisionAvoidanceAgent", AID.ISLOCALNAME));
        msg3.setContent("Forward");
        send(msg1);

    }
}
