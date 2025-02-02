package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.warningManager;
import frc.robot.commands.sim.CreateCoral;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;

public class IntakePeiceCommand extends Command{
    Pose2d intakePose;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;



    public IntakePeiceCommand(Pose2d intakePose){
        this.intakePose=intakePose;
        //addRequirements(SystemManager.swerve);
    }

    @Override
    public void initialize(){
        if (!RobotBase.isReal()){
            new CreateCoral(intakePose).schedule();
        }
        generalManager.intake();
        driveCommand=SystemManager.driveToPose(intakePose);
        driveCommand.schedule();
        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        mechIsFinished=false;
        SmartDashboard.putBoolean("auto intake is running", true);
    }


    @Override
    public void execute() {
        // if (mechIsFinished==mechCommand.isScheduled()){
        //     autoManager.resetAutoAction();
            
        // }

    }


    public void mechIsFinishedCall(boolean wasInterupted){
        if (wasInterupted){
            //autoManager.resetAutoAction();
            warningManager.throwAlert(warningManager.autoInternalCancled);
        }
        mechIsFinished=true;
    }


    public boolean isFinished(){
        return SystemManager.hasPeice();
    }

    @Override
    public void end(boolean wasInterupted){
        SmartDashboard.putBoolean("auto intake is running", false);
        if (driveCommand.isScheduled()){
            driveCommand.cancel();
        }
    }
}   
