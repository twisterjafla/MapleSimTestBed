package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Intake;

public class AutoSelector {

  SendableChooser<Command> m_chooser = new SendableChooser<Command>();

  public AutoSelector(DriveBase drive,Intake intake,Bucket bucket,Gyro gyro){

    m_chooser.setDefaultOption("Auto Balance Mobile", new AutonomousBalanceMobile(drive, intake, bucket, gyro));
    m_chooser.addOption("Auto Grab", new AutonomousGrab(drive, intake, bucket));
    m_chooser.addOption("Auto No Mobile", new AutonomousBalanceNoMobile(drive, intake, bucket, gyro));
    m_chooser.addOption("doNothing", new InstantCommand());
    m_chooser.addOption("Dump Do Nothing", new AutonomousDumpDoNothing(drive, intake, bucket));

    SmartDashboard.putData("autos: ", m_chooser);
  }

  public Command getSelected(){
    return m_chooser.getSelected();
  }


}
