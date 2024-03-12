package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class OuttakeBack extends WaitCommand {
  private final Intake intake;

  public OuttakeBack(Intake intake) {
    super(Constants.intake.outtakeBackupTime);
    this.intake = intake;


  }

  @Override
  public void initialize(){
    if (intake.beamBreak.isOk()){
      cancel();
    }
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
      super.execute();
      intake.outake();
  }

  @Override
  public void end(boolean interupted){
    if(!interupted){
      intake.stop();
    }
  }
}
