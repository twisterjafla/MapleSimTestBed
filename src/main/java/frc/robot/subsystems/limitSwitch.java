package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class limitSwitch extends SubsystemBase{

    private DigitalInput switch;

    public limitSwitch(int index){
        switch = new DigitalInput(index);
    }

    public Boolean getVal(){
        return switch;
    }

}
