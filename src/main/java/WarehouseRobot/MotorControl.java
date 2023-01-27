package WarehouseRobot;

import lejos.utility.Delay;

/**
 * This class is used to control the motors of the robot.
 *
 * @author Maxim
 * @author Anthony
 * @author Senne
 * @version 1.0
 * @see RobComponents
 * @since 26/10/2022
 */
public class MotorControl {
    static final public int slowSpeed = 50;
    static final public int mediumSpeed = 100;
    static final public int fastSpeed = 200;
    static int speed = 100;
    static int leftWheelModifier = 1;
    static int rightWheelModifier = 1;
    static int controlDelayMs = 1;
    static int invertControls = 1; // 1 or -1

    /**
     * Applies the given speed to the motors.
     *
     * @param speed speed to apply to both motors.
     */
    private static void applyToBoth(int speed) {
        RobComponents.motorL.setSpeed((int) invertControls * speed * leftWheelModifier);
        RobComponents.motorR.setSpeed((int) invertControls * speed * rightWheelModifier);
        Delay.msDelay(controlDelayMs);
    }

    /**
     * Sets the current motor speed to the given speed.
     *
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
        RobComponents.motorL.forward();
        RobComponents.motorR.forward();
    }

    public static void moveForwardPrecise(int speed, float leftSpeedMultiplier, float rightSpeedMultiplier) {
        System.out.println("new Left speed: " + (invertControls * speed * leftSpeedMultiplier));
        System.out.println("new Right speed: " + (invertControls * speed * rightSpeedMultiplier));

        RobComponents.motorL.setSpeed((int) (invertControls * speed * leftSpeedMultiplier));
        RobComponents.motorR.setSpeed((int) (invertControls * speed * rightSpeedMultiplier));
        RobComponents.motorL.forward();
        RobComponents.motorR.forward();
        Delay.msDelay(controlDelayMs);
    }
}
