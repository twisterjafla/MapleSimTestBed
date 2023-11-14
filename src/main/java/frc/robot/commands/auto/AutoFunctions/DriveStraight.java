package frc.robot.commands.auto.AutoFunctions;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;


public class DriveStraight extends CommandBase {
    // Encoder leftEncoder = new Encoder(4,5); 
    // Encoder rightEncoder = new Encoder(5);

    private final PIDController pid = new PIDController(0.012, 0, 0.01);
    private final DriveBase driveBase;

    private double encoderValue;

    public DriveStraight(DriveBase driveSubsystem, double feet) {
    pid.setTolerance(0.1);


    driveBase = driveSubsystem;
    double inches= feet*12; //GOOD
    double wheeldistance=Constants.auto.wheelRadius*3.14*2;//
    double wheelRotations=inches/wheeldistance;//GOOD
    double motorRotations=wheelRotations*Constants.drive.gearRatio;
    //   /double encoderTicks = motorRotations*Constants.auto.TicksPerRotation;
    encoderValue=1*Constants.drive.gearRatio;
      

    //   encoderValue = ((((3*12)//converts feet to inches
    //   /((Constants.auto.wheelRadius*3.14*2))//converts inches to wheel rotations
    //   *Constants.drive.gearRatio)// converts wheel rotations to motor rotations
    //   *Constants.auto.TicksPerRotation)//converts motor rotations to encoder ticks
    //   );
    

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        driveBase.resetEncoder();
        pid.setSetpoint(encoderValue);
        SmartDashboard.putNumber("ticks", encoderValue);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        

        // if (driveBase.getEncoder() < encoderValue ) {
        //     driveBase.drive(0.5, 0);
        // }

        while(driveBase.getEncoder() < encoderValue ) {
            var controllerOutput = pid.calculate(encoderValue-driveBase.getEncoder());

            driveBase.drive(controllerOutput, 0);        }
        end(false);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveBase.drive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

      // Called repeatedly when this Command is scheduled to run

}
