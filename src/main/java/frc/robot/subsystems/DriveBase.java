package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveBase extends SubsystemBase {
 // new CANSparkMax(Constats.drive.lt, MotorType.kBrushless);

// left side
 public final CANSparkMax sparkMaxlt = new CANSparkMax(Constants.drive.lt, MotorType.kBrushless);
 public final CANSparkMax sparkMaxlr = new CANSparkMax(Constants.drive.lr, MotorType.kBrushless);
 public final CANSparkMax sparkMaxlf = new CANSparkMax(Constants.drive.lf, MotorType.kBrushless);

//right side
public final CANSparkMax sparkMaxrt = new CANSparkMax(Constants.drive.rt, MotorType.kBrushless);
public final CANSparkMax sparkMaxrr = new CANSparkMax(Constants.drive.rr, MotorType.kBrushless);
public final CANSparkMax sparkMaxrf = new CANSparkMax(Constants.drive.rf, MotorType.kBrushless);
public final RelativeEncoder encoderR;
public final RelativeEncoder encoderL;


  final MotorControllerGroup leftMotors = new MotorControllerGroup(
    sparkMaxlt,
    sparkMaxlr,
    sparkMaxlf
      );

  final MotorControllerGroup rightMotors = new MotorControllerGroup(
    sparkMaxrt,
    sparkMaxrr,
    sparkMaxrf
      );

  final DifferentialDrive m_RobotDrive;

  public DriveBase() {

    //left voltage ramping
    encoderR=sparkMaxrt.getEncoder();
    encoderL= sparkMaxlt.getEncoder();
    sparkMaxlt.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxlr.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxlf.setOpenLoopRampRate(Constants.drive.rampspeed);

    //right voltage ramping
    sparkMaxrt.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxrr.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxrf.setOpenLoopRampRate(Constants.drive.rampspeed);


    //leftMotors.setInverted(true);
    //m_RobotDrive = new DifferentialDrive(rightMotors, leftMotors)
    m_RobotDrive = new DifferentialDrive(leftMotors, rightMotors);

    addChild("Drive", m_RobotDrive);
    
  }
  //returns average of encoder values. 
  public double getEncoder(){
    SmartDashboard.putNumber("encoder", (encoderL.getPosition()+encoderR.getPosition())/2);
    return (encoderL.getPosition()+encoderR.getPosition())/2;
  }
  public void resetEncoder(){
  }


  public void drive(final double ySpeed, final double rotateValue) {
    getEncoder();
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }
}
