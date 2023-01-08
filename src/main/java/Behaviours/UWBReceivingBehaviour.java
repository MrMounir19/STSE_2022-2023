package Behaviours;

import WarehouseRobot.RobotInformation;
import WarehouseServer.RobotStorage;
import WarehouseShared.Position;
import jade.core.behaviours.CyclicBehaviour;
import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class UWBReceivingBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        String broker       = "wss://mqtt.cloud.pozyxlabs.com:443";
        String topic        = "61d730870295a7f3798fdb31";
        String apiKey		= "1d761f94-6fe7-4549-aaa5-73a4ffecc2ee";
        String clientId		= "61d730870295a7f3798fdb31";


        MemoryPersistence persistence = new MemoryPersistence();

        //TODO cleanup messaging
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setUserName(topic);
            mqttConnectOptions.setPassword(apiKey.toCharArray());

//            System.out.println("Connecting to broker: "+broker);
            mqttClient.connect(mqttConnectOptions);
//            System.out.println("Connected");

            mqttClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
//                    System.out.println("Connection lost");
//                    System.out.println(cause);
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    List<JsonObject> robots = msgToObjects(message);
                    for (JsonObject robot: robots) {
                        if (robot.get("success").getAsBoolean()){
                            JsonObject data = robot.getAsJsonObject("data");
                            JsonObject coordinates = data.getAsJsonObject("coordinates");
                            JsonObject orientation = data.getAsJsonObject("orientation");
//                            if (robot.get("tagId").getAsString().equals("26689")) {
//                                System.out.println(orientation.get("yaw").getAsString());
//                                RobotInformation.setRobotPosition( coordinates.get("x").getAsFloat(), coordinates.get("y").getAsFloat(), orientation.get("yaw").getAsFloat());
//                                System.out.println(RobotInformation.getYaw());
//                                Position pos = RobotInformation.position;
//                                float yaw = (float) Math.toDegrees(RobotInformation.yaw);
//                                System.out.println("yaw");
//                                System.out.println(yaw);
//                            }
                            //Master tag
                            if (robot.get("tagId").getAsString().equals("26702")) {
                                RobotInformation.setMasterPosition(coordinates.get("x").getAsFloat(), coordinates.get("y").getAsFloat());
                            }
                            if (myAgent.getAID().getLocalName().contains("ServerAgent")) {
                                RobotStorage.updateRobotPosition(robot.get("tagId").getAsString(), coordinates.get("x").getAsFloat(), coordinates.get("y").getAsFloat(), orientation.get("yaw").getAsFloat());
                            } else {
                                //TODO not hardcoded
                                if (robot.get("tagId").getAsString().equals("26689")) {
                                    RobotInformation.setRobotPosition( coordinates.get("x").getAsFloat(), coordinates.get("y").getAsFloat(), orientation.get("yaw").getAsFloat());
                                }
                            }
                        }
                    }
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Delivery complete");
                }
            });

//            System.out.println("Subscribing to topic: "+topic);
            mqttClient.subscribe(topic);
//            System.out.println("Subscribed to topic: "+topic);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }

    }


    public List<JsonObject> msgToObjects(MqttMessage message){
        JsonParser j = new JsonParser();
        String nmsg = message.toString();
        String[] arrOfStr = nmsg.split("\\{" +  '\"' + "version", -1);
        List<JsonObject> result = new ArrayList();
        for(int i=1; i < arrOfStr.length; i++){
            String temp = "{" + "\"" + "version" + arrOfStr[i];
            temp = temp.replaceAll(",$", "");
            temp = temp.replaceAll("]$", "");
            JsonObject test = j.parse(temp).getAsJsonObject();
            result.add(test);
        }

        return result;
    }

}

//"tagId":"27253","timestamp":1669803954.354,"success":false,"errorCode":"NOT_ALIVE"}]
//        Positioning update: [{"version":"1","tagId":"26659","timestamp":1669803954.356,"success":false,"errorCode":"NOT_ALIVE"},{"version":"1","tagId":"26689","timestamp":1669803954.358,"success":false,"errorCode":"NOT_ALIVE"},{"version":"1","tagId":"26702","timestamp":1669803954.304,"success":true,"data":{"coordinates":{"x":9923,"y":12257,"z":1000},"orientation":{"yaw":5.308000087738037,"roll":-0.0010000000474974513,"pitch":-0.008999999612569809},"tagData":{},"metrics":{"latency":47,"rates":{"success":15.797,"update":15.797}},"zones":[]}},{"version":"1","tagId":"27253","timestamp":1669803954.354,"success":false,"errorCode":"NOT_ALIVE"}]