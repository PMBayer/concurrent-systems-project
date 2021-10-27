package controllerLogic;

public class CarMotor implements CarMotorOutput {
    private int speed;
    private int steeringAngle;

    //Constructor to initialize the Motor with standard values
    //e.g. drive forward with 0 steering at speed of 100
    public CarMotor() {
        this.speed = 100;
        this.steeringAngle = 0;
    }

    //changes the speed of the motor to the value of x
    @Override
    public void setSpeed(int x) throws CarException {
        this.speed = x;
    }

    //sets the steering angle to the value of x
    @Override
    public void steering(int x) throws CarException {
        this.steeringAngle = x;

    }

    public int getSpeed() {return this.speed;}

    public int getSteeringAngle() { return this.steeringAngle;}
}
