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
 * Behaviour to request a location with a given type e.g. triaging station
 *
 * @author Maxim
 * @author Thimoty
 * @since 14/01/2023
 */
public class LocationRequestBehaviour extends OneShotBehaviour {
    LocationType locationType;

    LocationRequestBehaviour (LocationType locationType) {
        this.locationType = locationType;
    }

    @Override
    public void action() {
        myAgent.send(Messages.locationRequestMessage(locationType));
    }
}