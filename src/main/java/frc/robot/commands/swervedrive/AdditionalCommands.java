package frc.robot.commands.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.SystemManager;

public class AdditionalCommands {
    public static Command SwappingAuto = new QuickSwapCommand(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))).andThen(new SetHasNote(true)),
            SystemManager.swerve.driveToPose(new Pose2d(2,4, new Rotation2d(0))).andThen(new SetHasNote(false)), ()->!SystemManager.hasNote);
    
    
}
