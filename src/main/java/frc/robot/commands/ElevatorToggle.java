package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.limitSwitch;

public class ElevatorToggle extends CommandBase {
  // setActive setActive;
  Elevator elevator;
  Boolean hasHitTop = false;
  limitSwitch topSwitch = new limitSwitch(Constants.elevator.topLimitSwitch);
  limitSwitch bottomSwitch = new limitSwitch(Constants.elevator.bottomLimitSwitch);
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
        activeSwitch=bottomSwitch;
        speed=Constants.elevator.elevatorDown;
      }
      else{
        activeSwitch=topSwitch;
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