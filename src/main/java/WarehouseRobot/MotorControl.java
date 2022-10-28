package WarehouseRobot;

import lejos.utility.Delay;

public class MotorControl {
    static int speed = 600;
    static final public int slowSpeed = 100;
    static final public int mediumSpeed = 600;
    static final public int fastSpeed = 1500;
    static int leftWheelModifier = 1;
    static int rightWheelModifier = 1;
    static int controlDelayMs = 1;
    static int invertControls = 1; // 1 or -1

    /**
     * @param speed speed to apply to both motors.
     */
    private static void applyToBoth(int speed) {
        RobComponents.motorL.setSpeed(invertControls * speed * leftWheelModifier);
        RobComponents.motorR.setSpeed(invertControls * speed * rightWheelModifier);
        Delay.msDelay(controlDelayMs);
    }

    /**
     * Sets both motors to the same speed.
     * @param speed speed to apply to both motors.
     */
    public static void setSpeed(int speed) {
        MotorControl.speed = speed;
    }

    /**
     * Turns left in place.
     */
    public static void turnLeftInPlace() {
        applyToBoth(speed);
        RobComponents.motorL.backward();
        RobComponents.motorR.forward();
        Delay.msDelay(controlDelayMs);
    }

    /**
     * Turns right in place.
     */
    public static void turnRightInPlace() {
        applyToBoth(speed);
        RobComponents.motorL.forward();
        RobComponents.motorR.backward();
        Delay.msDelay(controlDelayMs);
    }

    /**
     * Move forward.
     */
    public static void moveForward() {
        applyToBoth(speed);
        RobComponents.motorL.forward();
        RobComponents.motorR.forward();
        Delay.msDelay(controlDelayMs);
    }

    /**
     * Move backward.
     */
    public static void moveBackward() {
        applyToBoth(speed);
        RobComponents.motorL.backward();
        RobComponents.motorR.backward();
        Delay.msDelay(controlDelayMs);
    }

    /**
     * Sets both motors to 0 speed, stopping the robot.
     */
    public static void stopMotors() {
        applyToBoth(0);
    }
}
