package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.limitSwitch;

public class IntakeNote extends Command {
  // setActive setActive;
  limitSwitch topSwitch;
  limitSwitch bottomSwitch;
  Intake m_intake;
  double speed;

  public IntakeNote(Intake importedIntake) {
    m_intake = importedIntake;
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