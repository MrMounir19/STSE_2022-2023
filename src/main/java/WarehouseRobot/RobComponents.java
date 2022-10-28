package WarehouseRobot;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;


public class RobComponents {
    static public EV3LargeRegulatedMotor motorL = null;
    static public EV3LargeRegulatedMotor motorR = null;
    static public EV3UltrasonicSensor ussF = null;
    static public EV3UltrasonicSensor ussL = null;

    public static void init() {
        motorL = new EV3LargeRegulatedMotor(MotorPort.B);
        motorR = new EV3LargeRegulatedMotor(MotorPort.C);
        ussF = new EV3UltrasonicSensor(SensorPort.S1);
        ussL = new EV3UltrasonicSensor(SensorPort.S2);
    }
}
