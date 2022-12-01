package WarehouseServer;

public class RobotObject {
    public String robotId;
    public float xPosition;
    public float yPosition;
    public String uwbID;

    public RobotObject(String id) {
        robotId = id;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotPosition(float x, float y) {
        xPosition = x;
        yPosition = y;
    }
}
