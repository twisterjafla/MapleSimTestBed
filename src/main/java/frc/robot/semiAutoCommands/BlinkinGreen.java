package frc.robot.semiAutoCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Blinkin;

public class BlinkinGreen extends InstantCommand{
    public BlinkinGreen(){
        super(
            () -> {Blinkin.setGreen();}
        );
    }
}                  