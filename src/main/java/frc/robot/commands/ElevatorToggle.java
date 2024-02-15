package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.limitSwitch;

public class ElevatorToggle extends Command {
  // setActive setActive;
  Elevator elevator;
  Boolean hasHitTop = false;
  limitSwitch activeSwitch;
  double speed;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public ElevatorToggle(final Elevator elevator) {
    this.elevator = elevator;
		}


    @Override
    public void execute() {
      elevator.moveElevator(speed);
      

    }

    @Override
    public void initialize(){
      if (elevator.isUp){
        activeSwitch=elevator.bottomSwitch;
        speed=Constants.elevator.elevatorDown;
      }
      else{
        activeSwitch=elevator.topSwitch;
        speed=Constants.elevator.elevatorUp;
      }

    }

    @Override
    public void end(boolean wasInterupted){
      elevator.isUp=!elevator.isUp;
    }

    @Override
    public boolean isFinished() { 
      return activeSwitch.getVal(); 

    } 
      
  }