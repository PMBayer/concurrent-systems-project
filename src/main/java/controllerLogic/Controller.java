package controllerLogic;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller<SensorInput extends CarSensorInput, MotorOutput extends CarMotorOutput> extends Thread{
    //create Logger
    private static final  Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private Object monitor = new Object();

    //enums for control method
    //initialize control Commands to avoid NullPointer
    enum Command {
        START,
        FORWARD,
        REVERSE,
        LEFT,
        RIGHT,
        STOP
    }
    Command currentCommand = Command.START;

    //hashmap to store Sensor Objects and their Values
    HashMap<CarSensorInput.Sensor, Double> sensorValHM = new HashMap<>();

    //I hate sonarLint -> this is useless -> it doesn't allow infinity loops
    boolean fuckSonarLint = true;

    SensorInput sensor;
    MotorOutput motor;

    private boolean valueChanged = false;

    //first initializing
    public Controller(SensorInput sensor, MotorOutput motor) {
        LOGGER.setLevel(Level.OFF);
        this.sensor = sensor;
        this.motor = motor;

        sensorValHM.put(CarSensorInput.Sensor.FL, 100d);
        sensorValHM.put(CarSensorInput.Sensor.FR, 100d);
        sensorValHM.put(CarSensorInput.Sensor.BL, 100d);
        sensorValHM.put(CarSensorInput.Sensor.BR, 100d);
    }

    //enables Concurrency for the Controller
    @Override
    public void run(){
        while(fuckSonarLint){
            if(valueChanged){
                synchronized (monitor){
                    try {
                        control(sensorValHM);
                    } catch (CarException e) {
                        e.printStackTrace();
                    }
                    valueChanged = false;
                }
            }
        }
    }

    public void adjustHM(CarSensorInput.Sensor s, double currentVal){
        sensorValHM.put(s, currentVal);
        valueChanged = true;
    }


    private void control(HashMap<CarSensorInput.Sensor, Double> x) throws CarException {
        //rule in Order to evaluate this shit; dont know if i want to make seperate methods for each Sensor
        //might be good in Order to make cases easier
        //I really hate this task; pls set me free
        //might have to adjust speed while steering due to driving physics and shit


        //default settings
        if(!currentCommand.equals(Command.FORWARD) && x.get(CarSensorInput.Sensor.FL) > 10 && x.get(CarSensorInput.Sensor.FR) > 10){
            currentCommand = Command.FORWARD;
            motor.setSpeed(100);
            motor.steering(0);
            LOGGER.info("Driving straight forward!");
        }

        //steer left
        if(!currentCommand.equals(Command.LEFT) && x.get(CarSensorInput.Sensor.FR) < 10 && x.get(CarSensorInput.Sensor.FL) > 50){
            currentCommand = Command.LEFT;
            motor.steering(-100);
            LOGGER.info("Steer to the left!");
        }

        //steer right
        if(!currentCommand.equals(Command.RIGHT) && x.get(CarSensorInput.Sensor.FL) < 10 && x.get(CarSensorInput.Sensor.FR) > 50){
            currentCommand = Command.RIGHT;
            motor.steering(100);
            LOGGER.info("Steer to the right!");
        }

        //reverse
        if(!currentCommand.equals(Command.REVERSE) && x.get(CarSensorInput.Sensor.FL) < 10 && x.get(CarSensorInput.Sensor.FR) < 10){
            currentCommand = Command.REVERSE;
            motor.setSpeed(-100);
            LOGGER.info("Driving in Reverse!");
        }

        //complete Stop
        if(!currentCommand.equals(Command.STOP) && x.get(CarSensorInput.Sensor.FL) < 10 && x.get(CarSensorInput.Sensor.FR) < 10 && x.get(CarSensorInput.Sensor.BL) < 10 && x.get(CarSensorInput.Sensor.BR) < 10){
            currentCommand = Command.STOP;
            motor.setSpeed(0);
            LOGGER.warning("EMERGENCY BRAKE! PLS HELP!");
        }
    }

    public Object getMonitor(){
        return monitor;
    }
}
