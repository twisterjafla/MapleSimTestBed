package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ShiftableGearbox;

public class shiftGears extends InstantCommand {
    
    public shiftGears(boolean isHigh, ShiftableGearbox gearBox){
        super(()->{gearBox.shift(isHigh);}, gearBox);
    }
}
