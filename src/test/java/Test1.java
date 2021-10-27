import controllerLogic.CarException;
import controllerLogic.CarMotorOutput;
import controllerLogic.CarSensorInput;
import controllerLogic.Controller;

import java.util.logging.Logger;

class Test1 implements CarSensorInput, CarMotorOutput {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private int speed = 999;
    private int steeringAngle = 999;

    Test1(){

    }

    public static void main(String[] args) {
        Test1 testObj = new Test1();
        testObj.testSteerRight();
        testObj.testSteerLeft();
        testObj.testReverse();
        testObj.testStop();

        System.exit(0);
    }
/********************* Test Methods ******************************************************/

    public void testSteerRight() {
        Controller<Test1, Test1> testC = new Controller<>(this, this);
        testC.adjustHM(CarSensorInput.Sensor.FL, 5);
        testC.adjustHM(CarSensorInput.Sensor.FR, 70);
        testC.start();

        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(steeringAngle == 100){
            LOGGER.info("[Test 1 - Steer Right!] Test Passed! :)");
        }else{
            LOGGER.info("[Test 1 - Steer Right!] Test Failed! :(");
        }
    }

    public void testSteerLeft(){
        Controller<Test1, Test1> testC = new Controller<>(this, this);
        testC.adjustHM(CarSensorInput.Sensor.FL, 70);
        testC.adjustHM(CarSensorInput.Sensor.FR, 5);
        testC.start();

        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(steeringAngle == -100){
            LOGGER.info("[Test 2 - Steer Left!] Test Passed! :)");
        }else{
            LOGGER.info("[Test 2 - Steer Left!] Test Failed! :(");
        }
    }

    public void testReverse(){
        Controller<Test1, Test1> testC = new Controller<>(this, this);
        testC.adjustHM(CarSensorInput.Sensor.FL, 5);
        testC.adjustHM(CarSensorInput.Sensor.FR, 5);
        testC.start();

        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(speed == -100){
            LOGGER.info("[Test 3 - Reverse!] Test Passed! :)");
        }else{
            LOGGER.info("[Test 3 - Reverse!] Test Failed! :(");
        }
    }

    public void testStop(){
        Controller<Test1, Test1> testC = new Controller<>(this, this);
        testC.adjustHM(CarSensorInput.Sensor.FL, 5);
        testC.adjustHM(CarSensorInput.Sensor.FR, 5);
        testC.adjustHM(CarSensorInput.Sensor.BR, 5);
        testC.adjustHM(CarSensorInput.Sensor.BL, 5);
        testC.start();

        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(speed == 0){
            LOGGER.info("[Test 4 - Stop!] Test Passed! :)");
        }else{
            LOGGER.info("[Test 3 - Stop!] Test Failed! :(");
        }
    }

/********************* Implemented by Interface ******************************************************/

    @Override
    public void setSpeed(int x) throws CarException {
        this.speed = x;
    }

    @Override
    public void steering(int x) throws CarException {
        this.steeringAngle = x;
    }

    @Override
    public double getDistance(Sensor s) throws CarException, InterruptedException {
        return 0;
    }

    @Override
    public void startThreads() {
        // TODO document why this method is empty
    }

    @Override
    public void init(Controller controller) {
        // TODO document why this method is empty
    }
}

