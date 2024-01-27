package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class shiftGears extends InstantCommand {
    
    public shiftGears(boolean isOn, ShiftableGearbox gearBox){
        super(()->{gearBox.shift(isOn);}, gearBox);
    }
}
