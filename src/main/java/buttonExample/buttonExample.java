package buttonExample;




import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

//import jason.asSyntax.Literal;

public class buttonExample {


    static final EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S1); //drop sensor
    static  boolean call_mode =false;										//drop sensor


    static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S4);     //sort Agent Color Sensor
    static  boolean call_mode2 =false;



    static EV3MediumRegulatedMotor motor1 = new EV3MediumRegulatedMotor(MotorPort.B); // Conveyor 2  Motor



    static EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.D); // Conveyor 2  Motor


    static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2); // DropButton

    static EV3MediumRegulatedMotor dropmotor = new EV3MediumRegulatedMotor(MotorPort.A); // DropMotor
    static EV3LargeRegulatedMotor shredermotor = new EV3LargeRegulatedMotor(MotorPort.C); // ShredMotor
    static EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S3);


    public static boolean check_button() {


        boolean touch= touchSensor.isPressed();


        return touch;
        //if (distanceValue<100) {

    }



    public static void main(String[] args) {



                // motor.stop();
                // motor.getPosition();
        while(true) {
            boolean value = check_button();
            if (value == true) {


                motor1.setSpeed(500);
                Delay.msDelay(1);
                motor1.backward();
                Delay.msDelay(1);

            } else {

                motor1.stop();
                Delay.msDelay(1);

            }
/*


                // motor.stop();
                // motor.getPosition();




            }



        }

*/

        }}}
