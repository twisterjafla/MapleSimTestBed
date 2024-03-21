package frc.robot.semiAutoCommands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class stealDriveCommand extends Command{
    public DriveBase drive;
 
    public stealDriveCommand(DriveBase drive){
        this.drive=drive;
        addRequirements(drive);
      }

    public void execute(){
        drive.drive(0,0);
        
    }
}
