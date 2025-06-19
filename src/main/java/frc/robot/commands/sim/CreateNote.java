package frc.robot.commands.sim;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.crescendo2024.CrescendoNoteOnField;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoralOnField;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class CreateNote extends InstantCommand {
    @Override
    public void execute(){
        SimulatedArena.getInstance().addGamePiece(new CrescendoNoteOnField(new Translation2d(8.4, 4)));
    }
}
