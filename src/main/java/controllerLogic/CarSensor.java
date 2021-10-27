package controllerLogic;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarSensor implements CarSensorInput {
    //create Logger
    private static final Logger LOGGER = Logger.getLogger(CarSensor.class.getName());

    Controller controller;

    public CarSensor() {
        LOGGER.setLevel(Level.OFF);
    }

    @Override
    public double getDistance(CarSensorInput.Sensor s) throws CarException, InterruptedException {
        return 0;
    }

    @Override
    public void startThreads() {
        SensorThread stFL = new SensorThread(CarSensorInput.Sensor.FL);
        SensorThread stFR = new SensorThread(CarSensorInput.Sensor.FR);
        SensorThread stBL = new SensorThread(CarSensorInput.Sensor.BL);
        SensorThread stBR = new SensorThread(CarSensorInput.Sensor.BR);

        stFL.start(); stFR.start(); stBL.start(); stBR.start();
    }

    @Override
    public void init(Controller controller) {
        this.controller = controller;
    }

    class SensorThread extends Thread{
        Random rd = new Random();
        double formerVal = 999;
        double currentVal = 0;
        CarSensorInput.Sensor s;

        SensorThread(CarSensorInput.Sensor s){
            this.s = s;
        }

        //method to get distance as measured by sensor
        //currently returning random Values for testing purposes
        public double getDistance(CarSensorInput.Sensor s) throws CarException {
            currentVal = 0 + (100 - 0) * rd.nextDouble();
            return currentVal;
        }

        //what do the threads... whoop whoop
        @Override
        public void run(){
            while(true){
                //retrieve distance through Sensor Method
                try{
                    currentVal = getDistance(s);
                    if(currentVal != formerVal){
                        synchronized (controller.getMonitor()){
                            controller.adjustHM(s, currentVal);
                            LOGGER.info(currentVal + "   " + s.toString());
                            Thread.sleep(10);

                            //set currentVal to former Val
                            formerVal = currentVal;
                        }
                    }
                } catch (CarException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
