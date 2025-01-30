package frc.robot.commands.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.FieldPosits;
import frc.robot.SystemManager;

public class AdditionalCommands {
    public static Command SwappingAuto = new QuickSwapCommand(SystemManager.swerve.driveToPose(new Pose2d(3.8,5, new Rotation2d(Math.PI))).andThen(new SetHasNote(true)),
            SystemManager.swerve.driveToPose(FieldPosits.coralSpawnPoints.leftMid).andThen(new SetHasNote(false)), ()->!SystemManager.hasNote);
    
    
}
