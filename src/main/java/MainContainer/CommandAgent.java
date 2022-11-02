package MainContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

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
                   IdColor();

               }
               else block();
           }
        });
    }
    public  void IdColor() {
      //  int colorId =Devices.IdColor();


        System.out.println("Sensor agent - Main Container -  reads the color sensor");
        int colorId = 2;

        if (colorId==2) {
            ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
            msg1.addReceiver(new AID("MotorAgent", AID.ISLOCALNAME));
            msg1.setContent("Blue");
            send(msg1);
        }
        else   if (colorId==3) {
            ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
            msg1.addReceiver(new AID("MotorAgent", AID.ISLOCALNAME));
            msg1.setContent("Green");
            send(msg1);
        }
        else   if (colorId==4) {
            ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
            msg1.addReceiver(new AID("MotorAgent", AID.ISLOCALNAME));
            msg1.setContent("Yellow");
            send(msg1);
        }
        else   if (colorId==5) {
            ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
            msg1.addReceiver(new AID("MotorAgent", AID.ISLOCALNAME));
            msg1.setContent("Red");
            send(msg1);
        }
        else IdColor();
    }
}
