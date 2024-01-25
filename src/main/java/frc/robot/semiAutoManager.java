package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.drive;
import frc.robot.subsystems.Coords;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;

public class semiAutoManager{
    DriveBase drive;
    Gyro gyro;
    Limelight limelight;
    boolean isGoodToRun;
    CoordSet set;


    public semiAutoManager(DriveBase drive, Gyro gyro, Limelight limelight ){
        this.drive=drive;
        this.gyro=gyro;
        this.limelight=limelight;
        
    }

    public Command getBoardControls(){
        //Code that returns an in integer value that relates to the routine to be run
        return null;
    }
    public void periodic(){
        Command Routine = getBoardControls();
        Coords currentCoords = getCoords();
        if  (isGoodToRun && Routine !=null){
            Routine.schedule();
        }
        
    }

    public Coords getCoords(Coords lastCoords){
        Coords working = limelight.updateCoords(lastCoords);

        working.setEncoders(drive);
        return working; 
        }


}
