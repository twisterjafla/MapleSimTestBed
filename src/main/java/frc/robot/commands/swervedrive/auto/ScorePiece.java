package frc.robot.commands.swervedrive.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.FieldPosits;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;
import frc.robot.Utils.scoringPosit;

public class ScorePiece extends Command{
    scoringPosit posit;
    public ScorePiece(scoringPosit posit){
        this.posit=posit;
    }
}
