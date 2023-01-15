package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightCommand extends CommandBase {
    private final DriveSubsystem m_driveSubsystem;
    private final double m_time;
    Timer timer;
    
    /**
     * Creates a new ArcadeDrive command.
     *
     * @param left       The control input for the left side of the drive
     * @param right      The control input for the right sight of the drive
     * @param driveSubsystem The driveSubsystem subsystem to drive
     */
    public DriveStraightCommand(DriveSubsystem driveSubsystem, double time) {
        m_driveSubsystem = driveSubsystem;
        m_time = time;
        addRequirements(m_driveSubsystem);
        timer = new Timer();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_driveSubsystem.drive(Constants.AUTONOMOUS_FORWARD_SPEED, 0);
    }
  
    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
      return timer.get() > m_time; // Runs until interrupted
    }
  
    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
      m_driveSubsystem.drive(0, 0);
    }
}
