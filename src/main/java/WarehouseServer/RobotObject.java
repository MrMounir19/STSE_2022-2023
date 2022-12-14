package WarehouseServer;

import WarehouseShared.Position;

public class RobotObject {
    public String robotId;
    public String uwbID;
    public Position position;
    public float yaw;
    public RobotObject(String id) {
        robotId = id;
    }

    public String getRobotId() {
        return robotId;
    }

    public String getUwbID() {
        return uwbID;
    }

    public void setRobotPosition(float x, float y, float orientation) {
        position = new Position(x, y);
        yaw = orientation;
    }
}
