// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.limitSwitch;

// public class RaiseIntake extends Command {
//   Intake intake;
//   limitSwitch activeSwitch;

//   public RaiseIntake(Intake intake) {
//     this.intake = intake;
//     addRequirements(intake);
//   }

//   @Override
//   public void execute() {
//     intake.raiseIntake();
//   }

//   @Override
//   public void initialize() {
//     if (intake.isUp) {
//       activeSwitch = intake.bottomSwitch;
//     }
//     else {
//       activeSwitch = intake.topSwitch;
//     }
//   }

//   @Override
//   public void end(boolean wasInterupted) {
//     intake.isUp=!intake.isUp;
//   }

//   @Override
//   public boolean isFinished() { 
//     return activeSwitch.getVal(); 
//   } 
      
// }