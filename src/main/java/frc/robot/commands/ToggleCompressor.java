package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PneumaticsSubsytem;

public class ToggleCompressor extends InstantCommand {
  public ToggleCompressor(PneumaticsSubsytem pneumatics) {
    super(
      ()->{
        pneumatics.toggleCompressor();
      }
    );
  }
}