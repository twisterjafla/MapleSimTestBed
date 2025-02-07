package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.LinearVelocityUnit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.utillFunctions;
import frc.robot.Utils.warningManager;
import frc.robot.commands.sim.CreateCoral;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;

public class IntakePeiceCommand extends Command{
    Pose2d intakePose;
    boolean mechIsFinished=false;
    boolean driveIsFinished=false;
    boolean coralHasBeenSpawned=false;
    Command driveCommand;
    Command mechCommand;



    public IntakePeiceCommand(Pose2d intakePose){
        this.intakePose=intakePose;
        //addRequirements(SystemManager.swerve);
    }

    @Override
    public void initialize(){
        // if (!RobotBase.isReal()){
        //     new CreateCoral(intakePose).schedule();
        // }
        generalManager.intake();
        driveCommand=SystemManager.swerve.driveToPose(intakePose, LinearVelocity.ofBaseUnits(Constants.AutonConstants.colisionSpeed, LinearVelocityUnit.combine(Units.Meters, Units.Second)));
        driveCommand.schedule();
        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        mechIsFinished=false;
        driveIsFinished=false;
        coralHasBeenSpawned=false;
        
    }


    @Override
    public void execute() {
        // if (mechIsFinished==mechCommand.isScheduled()){
        //     autoManager.resetAutoAction();
            
        // }
        if (!driveCommand.isScheduled()){
            if (utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), intakePose.getX(), SystemManager.getSwervePose().getY(), intakePose.getY())
                >=Constants.AutonConstants.autoDriveIntakeTolerence){
                if (
                    utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), intakePose.getX(),
                    SystemManager.getSwervePose().getY(), intakePose.getY())
                    <=Constants.AutonConstants.distanceWithinPathplannerDontWork){

                    driveCommand= new smallAutoDrive(intakePose);
                }
                driveCommand.schedule();
            }
            else{
                driveIsFinished=true;
            }
        }
        
        if (mechIsFinished&&driveIsFinished&&!RobotBase.isReal()&&!coralHasBeenSpawned){
            coralHasBeenSpawned=true;
            new WaitCommand(Constants.AutonConstants.humanPlayerBeingBad).andThen(new CreateCoral(intakePose.plus(Constants.AutonConstants.intakeCoralOffset))).schedule();
    }

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
