package WarehouseServer;

public class RobotObject {
    public String robotId;
    public float xPosition;
    public float yPosition;
    public String uwbID;
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
        xPosition = x;
        yPosition = y;
        yaw = orientation;
    }
}
