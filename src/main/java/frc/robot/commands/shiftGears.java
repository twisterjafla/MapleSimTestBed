package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ShiftableGearbox;

public class shiftGears extends InstantCommand {
    
    public shiftGears(boolean isOn, ShiftableGearbox gearBox){
        super(()->{gearBox.shift(isOn);}, gearBox);
    }
}
