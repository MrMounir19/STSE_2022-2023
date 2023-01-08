package WarehouseServer;

import WarehouseShared.Config;
import WarehouseShared.Position;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import Enums.LocationType;


/**
 * Keeps track of important locations.
 * To be used server-side to route robots towards these locations when necessary.
 *
 * @author Maxim
 * @author Thimoty
 * @since 7/1/2023
 */

public class LocationManager {
    public static ArrayList<Position> triagingStations = new ArrayList<>();
    public static ArrayList<Position> chargingStations = new ArrayList<>();
    public static ArrayList<Position> dropOffStations = new ArrayList<>();

    public static void createLocation(LocationType location){
        try{
            ArrayList<Position> positions = new ArrayList<>();
            for (JsonElement element : Config.getConfig().get(location.toString()).getAsJsonArray()){
                JsonArray jsonPosition = element.getAsJsonArray();
                float x = jsonPosition.get(0).getAsFloat();
                float y = jsonPosition.get(1).getAsFloat();
                positions.add(new Position(x,y));
            }

            switch(location){
                case triagingStations:
                    triagingStations = positions;
                    break;
                case chargingStations:
                    chargingStations = positions;
                    break;
                case dropOffStations:
                    dropOffStations = positions;
                    break;
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

   public static void createLocations(){
        createLocation(LocationType.triagingStations);
        createLocation(LocationType.chargingStations);
        createLocation(LocationType.dropOffStations);
   }

   public static Position getNearestLocation(Position pos, LocationType location) {
        ArrayList<Position> locationArray = null;
       switch(location){
           case triagingStations:
               locationArray = triagingStations;
               break;
           case chargingStations:
               locationArray = chargingStations;
               break;
           case dropOffStations:
               locationArray = dropOffStations;
               break;
       }

       Position nearestLocation = null;
       float nearestDistance = Float.MAX_VALUE;
       for (Position loc : locationArray) {
           float distance = (float) Math.sqrt(Math.pow(pos.x - loc.x, 2) + Math.pow(pos.y - loc.y, 2));
           if (distance < nearestDistance) {
               nearestLocation = loc;
           }
       }

       if (nearestLocation == null) {
           if (chargingStations.size() > 0) {
               nearestLocation = chargingStations.get(0);
           }
       }

       return nearestLocation;
   }

   public static Position getNearestRobotLocation(RobotObject robot, LocationType location){
        return getNearestLocation(robot.position, location);
   }

}
