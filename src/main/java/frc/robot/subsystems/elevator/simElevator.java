package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class simElevator  extends SubsystemBase implements elevatorInterface{

    public double setpoint=0;
    public double position=0;

    @Override
    public void setSetpointRaw(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public boolean isAtSetpoint() {
        return Math.abs(setpoint-position)<Constants.elevatorConstants.tolerence;
    }

    @Override
    public Translation3d getTranslation() {
        return new Translation3d(getHeight()*Math.cos(Constants.elevatorConstants.angle.getRadians()), 0, getHeight()*Math.sin(Constants.elevatorConstants.angle.getRadians())).plus(Constants.elevatorConstants.fromRobotCenter);
    }

    @Override
    public double getEncoderVal() {
        return position;
    }

    @Override
    public void reset() {
        setpoint=0;
    }

    @Override
    public void setSetpoint(double setpoint) {
       setSetpointRaw(setpoint*Constants.elevatorConstants.encoderToMeters);
    }

    @Override
    public double getHeight() {
        return getEncoderVal()/Constants.elevatorConstants.encoderToMeters;
    }

    @Override
    public void periodic(){
        if (Math.abs(setpoint-position)<Constants.elevatorConstants.speedForSim){
            position=setpoint;
        }
        else if (setpoint>position){
            position+=Constants.elevatorConstants.speedForSim;
        }
        else{
            position-=Constants.elevatorConstants.speedForSim;
        }
    }

    
    
}
