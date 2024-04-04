package frc.robot.semiAutoCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Blinkin;

public class BlinkinYellow extends InstantCommand{
    public BlinkinYellow(){
        super(
            () -> {Blinkin.setYellow();}
        );
    }
}                  
