package frc.robot.SemiAutoRoutines;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers.ShortSerializer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.semiAutoManager;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.ShiftableGearbox;

public class testRoutineRunner extends InstantCommand{
    public DriveBase drive;
    public ShiftableGearbox gearbox;
    public testRoutineRunner(DriveBase drive, ShiftableGearbox gearbox){
        this.drive=drive;    
        this.gearbox=gearbox;
    }

    @Override
    public void initialize(){
        Command command = new testRoutine(drive, gearbox);
        if (semiAutoManager.getCurrent()!=null){
            CommandScheduler.getInstance().cancel(semiAutoManager.getCurrent());
        }
        semiAutoManager.setCurrent(command);
        command.schedule();
    
    }
}
