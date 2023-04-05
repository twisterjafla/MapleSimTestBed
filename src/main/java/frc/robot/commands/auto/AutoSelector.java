package frc.robot.commands.auto;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

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

    PathPlannerTrajectory ComplexAuto = PathPlanner.loadPath("AutonomousBalanceNoMobile", new PathConstraints(4, 3));
    PathPlannerTrajectory AutoNoMobile = PathPlanner.loadPath("AutonomousBalanceNoMobile", new PathConstraints(4, 3));
    PathPlannerTrajectory SimpleAuto = PathPlanner.loadPath("AutonomousBalanceMobile", new PathConstraints(4, 3));

    m_chooser.setDefaultOption("Simple Auto", SimpleAuto),
    m_chooser.addOption("Complex Auto", ComplexAuto),
    m_chooser.addOption("Auto No Mobile", AutoNoMobile);
   

    SmartDashboard.putData("autos: ", m_chooser);
  }

  public Command getSelected(){
    return m_chooser.getSelected();
  }
<<<<<<< HEAD

 

// This trajectory can then be passed to a path follower such as a PPSwerveControllerCommand
// Or the path can be sampled at a given point in time for custom path following

// Sample the state of the path at 1.2 seconds
PathPlannerState exampleState = (PathPlannerState) AutonomousBalanceMobile.sample(1.2);

// Print the velocity at the sampled time


=======
>>>>>>> 8da32c88092e31219142cdf36e9f0cb38307258b
}
