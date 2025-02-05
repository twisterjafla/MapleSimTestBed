package frc.robot.subsystems.elevator;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControllManager;


//TODO fix syntax errors
public class Elevator implements elevatorInterface {
    
    public double setpoint = 0;
    public RelativeEncoder encoder;


    //TODO use the right kind of motor controller
    // Kraken motor in phoenix 6 library
    TalonFX elevatorMaster = new TalonFX(Constants.elevatorConstants.LeftMotor);
    TalonFXConfiguration elevatorMasterConfig =  new TalonFXConfiguration();

    TalonFX elevatorFollow = new TalonFX(Constants.elevatorConstants.RightMotor);
    TalonFXConfiguration elevatorFollowConfig =  new TalonFXConfiguration();

    wristElevatorControllManager manager;
    public void initElevator() {
        elevatorMasterConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    
        // elevatorMaster.addFollower(elevatorFollow);
        // elevatorMaster.setInverted(false);
        // elevatorFollow.setInverted(true);


        //TODO use cancoder instead of inbuilt
        CANCoder thingy = new CANCoder(Constants.elevatorConstants.LeftMotor);
        encoder = elevatorMaster.getEncoder();

    }

    //TODO make the elevator actualy move. For now just use a pid loop. later we will swap to a trapozoidal using motion magic but thats gonna be a whole thing.
    //TODO REMEBER TO USE THE MANAGER SO WE DONT CRUNCH THE INTAKE PLEASE.


    //TODO reimplement using tolerence
    @Override
    public boolean isAtSetpoint() {
        encoder.getPosition();
        if (encoder.getPosition() == setpoint) {
            return true;
        }
        else {
            return false;
        }
    }



    @Override
    public double getEncoderVal() {
        return encoder.getPosition();
    }

    public double getEncoderValMeters() {
        return getEncoderVal() * Constants.elevatorConstants.encoderTicksPerMeter;
    }

    @Override
    public void reset() {
        setpoint = 0;
    }



    @Override
    public void setSetpoint(double setpoint) {
        setSetpointRaw(setpoint * Constants.elevatorConstants.encoderTicksPerMeter);
    }

    @Override
    public double getHeight() {
        return getEncoderValMeters();
    }

    public void setManager(wristElevatorControllManager manager){
        this.manager=manager;
    }
    public boolean atLegalNonControlState(){
        return Math.abs(getHeight()-Constants.elevatorConstants.maxHeight)<Constants.elevatorConstants.tolerence;
    }

    @Override
    public void setSetpointRaw(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public Translation3d getTranslation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTranslation'");
    }
}
