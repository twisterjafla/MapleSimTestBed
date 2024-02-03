package frc.robot.semiAutoCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Blinkin;

public class BlinkinRed extends InstantCommand{
    public BlinkinRed(){
        super(
            () -> {Blinkin.setYellow();}
        );
    }
}                  