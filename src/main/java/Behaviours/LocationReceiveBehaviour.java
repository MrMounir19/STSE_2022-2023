package Behaviours;

import Utils.Messages;
import WarehouseRobot.RobotInformation;
import WarehouseShared.Position;
import com.google.gson.JsonArray;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
/**
 * Handles the change of the current destination when a new destination is received from a location request
 * @author Maxim
 * @author Thimoty
 * @since 14/01/2023
 */

public class LocationReceiveBehaviour extends OneShotBehaviour {
    protected ACLMessage message;

    LocationReceiveBehaviour(ACLMessage message) {
        this.message = message;
    }

    @Override
    public void action() {
        String content = message.getContent();
        JsonArray destinationJson = Messages.toJson(content).get("data").getAsJsonObject().get("position").getAsJsonArray();
        Position newDestination = new Position(destinationJson.get(0).getAsFloat(), destinationJson.get(1).getAsFloat());
        RobotInformation.setCurrentDestination(newDestination);
    }
}
