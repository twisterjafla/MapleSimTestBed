package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
//import frc.robot.subsystems.swervedrive.FakeBot;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class AutoDefenceForFakeBot extends Command {
    //FakeBot fakeBot=SystemManager.fakeBot;
    //SwerveSubsystem target = SystemManager.swerve;
    Pose2d targetGoal;
    public AutoDefenceForFakeBot(Pose2d goal){
        this.targetGoal=goal;
        //addRequirements(fakeBot);
    }

    @Override
    public void execute(){
        // Pose2d currentTargetPose2d = SystemManager.getSwervePose();
        // Transform2d change = currentTargetPose2d.minus(targetGoal);
        
        // change=change.div(1.2);

        // Pose2d selfGoal = targetGoal.transformBy(change);
        // SystemManager.m_field.getObject("Defence Goal").setPose(selfGoal);
        // Transform2d selfToGoal = selfGoal.minus(fakeBot.pose);
        // fakeBot.drive(selfToGoal.getX(), selfToGoal.getY(), 0);

    }
}
