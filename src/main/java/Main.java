import controllerLogic.CarMotor;
import controllerLogic.CarSensor;
import controllerLogic.Controller;

public class Main {

    public static void main(String[] args) {
        CarSensor carS = new CarSensor();
        Controller<CarSensor, CarMotor> controller = new Controller<>(carS, new CarMotor());
        carS.init(controller);
        carS.startThreads();
        controller.start();
    }
}
