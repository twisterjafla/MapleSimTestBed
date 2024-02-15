package frc.robot.SemiAutoRoutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveBase;

public class testRoutineRunner extends InstantCommand{
    public DriveBase drive;

    public testRoutineRunner(DriveBase drive){
        this.drive=drive;    
    }

    @Override
    public void initialize(){
        new testRoutine(drive).schedule();;
    }
}
