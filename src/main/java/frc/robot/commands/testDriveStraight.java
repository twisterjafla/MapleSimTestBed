package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveBase;

public class testDriveStraight extends WaitCommand {
    DriveBase drive;

    public testDriveStraight(DriveBase drive){
        super(1);
        this.drive=drive;
        
    }

    @Override
    public void execute(){
        drive.drive(0.5, 0);
    }
}
