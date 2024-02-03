package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.limitSwitch;

public class ShootNote extends Command {
  // setActive setActive;
  limitSwitch topSwitch;
  limitSwitch bottomSwitch;
  Intake m_intake;
  double speed;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public ShootNote(Intake importedIntake, double importedSpeed) {
    m_intake = importedIntake;
    speed = importedSpeed;
  }


  @Override
  public void execute() {
    m_intake.intake();
      

  }

    @Override
    public void initialize(){

    }

    @Override
    public void end(boolean wasInterupted){
    }

    @Override
    public boolean isFinished() { 
      return true;

    } 
      
  }