package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class simElevator  extends SubsystemBase implements elevatorInterface{

    public double setpoint=0;
    public double position=0;
    public Mechanism2d mech = new Mechanism2d(0,0);//Constants.elevatorConstants.fromRobotCenter.getX(), Constants.elevatorConstants.fromRobotCenter.getZ());//0.86, 1.75);
    public MechanismRoot2d root = mech.getRoot("elevatorRoot", Constants.elevatorConstants.fromRobotCenter.getX(), Constants.elevatorConstants.fromRobotCenter.getY());
    public MechanismLigament2d elevator = new MechanismLigament2d("elevator main", Constants.elevatorConstants.compressedLen, Constants.elevatorConstants.angle.getDegrees());
    public MechanismLigament2d wrist;

    public simElevator(){
        root.append(elevator);
        wrist = elevator.append(new MechanismLigament2d("wrist ligament", Constants.intakeConstants.intakeLength, 0, 10, new Color8Bit(0,0,365)));
        SmartDashboard.putData("elevator", mech);
    }

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
        return new Translation3d(getHeight()*Math.cos(Constants.elevatorConstants.angle.getRadians()), 0, getHeight()*Math.sin(Constants.elevatorConstants.angle.getRadians()));
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

        elevator.setLength(getHeight()+Constants.elevatorConstants.fromRobotCenter.getZ());
        wrist.setAngle(SystemManager.wrist.getcurrentLocation().getDegrees());
        SmartDashboard.putNumber("wristVal", SystemManager.wrist.getcurrentLocation().getDegrees());
    }



    
    
}
