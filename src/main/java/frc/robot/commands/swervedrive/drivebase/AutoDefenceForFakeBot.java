package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.subsystems.swervedrive.FakeBot;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class AutoDefenceForFakeBot extends Command {
    FakeBot fakeBot=SystemManager.fakeBot;
    //SwerveSubsystem target = SystemManager.swerve;
    Pose2d targetGoal;
    public AutoDefenceForFakeBot(Pose2d goal){
        this.targetGoal=goal;
        addRequirements(fakeBot);
    }

    @Override
    public void execute(){
        Pose2d currentTargetPose2d = SystemManager.getSwervePose();
        Twist2d change = currentTargetPose2d.log(targetGoal);
        if (change.dx<1){
            return;
        }
        change.dx=1;
        Pose2d selfGoal = targetGoal.exp(change);
        Transform2d selfToGoal = fakeBot.pose.minus(selfGoal);
        fakeBot.drive(selfGoal.getX(), selfGoal.getY(), 0);

    }
}
