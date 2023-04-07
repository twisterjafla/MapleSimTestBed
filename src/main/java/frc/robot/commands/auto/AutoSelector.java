package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoSelector extends InstantCommand {

  CommandScheduler scheduler = CommandScheduler.getInstance();

  final SendableChooser<Command> m_chooser = new SendableChooser<Command>();

  public AutoSelector(DriveSubsystem drive,IntakeSubsystem intake,Bucket bucket,Gyro gyro){

    m_chooser.setDefaultOption("Simple Auto", new AutonomousBalanceMobile(drive, intake, bucket, gyro));
    m_chooser.addOption("Complex Auto", new AutonomousGrab(drive, intake, bucket));
    m_chooser.addOption("Auto No Mobile", new AutonomousBalanceNoMobile(drive, intake, bucket, gyro));
    m_chooser.addOption("doNothing", new InstantCommand());

    SmartDashboard.putData("autos: ", m_chooser);
  }

  public Command getSelected(){
    return m_chooser.getSelected();
  }


}
