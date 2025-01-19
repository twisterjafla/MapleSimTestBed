package frc.robot.commands.swervedrive.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakePeiceCommand extends Command{
    Pose2d intakePose;
    public IntakePeiceCommand(Pose2d intakePose){
        this.intakePose=intakePose;
    }
}
