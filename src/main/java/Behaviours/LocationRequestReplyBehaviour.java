package Behaviours;

import Enums.LocationType;
import Utils.Messages;
import WarehouseServer.LocationManager;
import WarehouseServer.RobotObject;
import WarehouseServer.RobotStorage;
import WarehouseShared.Position;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Behaviour to handle the request message of a location
 *
 * @author Maxim
 * @author Thimoty
 * @since 14/01/2023
 */
public class LocationRequestReplyBehaviour extends OneShotBehaviour {
    ACLMessage message;

    LocationRequestReplyBehaviour (ACLMessage message) {
        this.message = message;
    }

    @Override
    public void action() {
        RobotObject robot = RobotStorage.getFromACLMessage(message);
        String content = message.getContent();
        String locationTypeStr = Messages.toJson(content).get("data").getAsJsonObject().get("locationType").getAsString();
        LocationType locationType = LocationType.valueOf(locationTypeStr);

        Position destination;
        switch (locationType) {
            case chargingStation:
                destination = LocationManager.getNearestRobotLocation(robot, LocationType.chargingStation);
                break;
            case triagingStation:
                destination = LocationManager.getNearestRobotLocation(robot, LocationType.triagingStation);
                break;
            case dropOffStation:
                destination = LocationManager.getNearestRobotLocation(robot, LocationType.dropOffStation);
                break;
            default:
                destination = null;
                break;
        }

        if (destination == null) {
            System.out.println("Could not find a valid " + locationTypeStr + " position.");
            return;
        }

        myAgent.send(Messages.locationRequestReplyMessage(robot.getRobotId(), destination));
    }
}