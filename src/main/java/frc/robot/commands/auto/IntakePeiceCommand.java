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


    /**
     * initalize a command to intake a peice
     * @param intakePose the pose for the robot to intake from
     */
    public IntakePeiceCommand(Pose2d intakePose){
        this.intakePose=intakePose;
        //addRequirements(SystemManager.swerve);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        generalManager.intake();
        driveCommand=SystemManager.swerve.driveToPose(intakePose, LinearVelocity.ofBaseUnits(Constants.AutonConstants.colisionSpeed, LinearVelocityUnit.combine(Units.Meters, Units.Second)));
        driveCommand.schedule();
        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        mechIsFinished=false;
        driveIsFinished=false;
        coralHasBeenSpawned=false;
        
    }

    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute() {
        //restarts the drive command if it finished early
        SmartDashboard.putBoolean("Drive is finished", driveIsFinished);
        SmartDashboard.putBoolean("coralHasBeenSpawned", coralHasBeenSpawned);
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


        //spawns a coral if the robot is simulated and the time is apropreate
        if (driveIsFinished&&Constants.simConfigs.intakeShouldBeSim&&!coralHasBeenSpawned){
            coralHasBeenSpawned=true;
            new WaitCommand(Constants.AutonConstants.humanPlayerBeingBad).andThen(new CreateCoral(intakePose.plus(Constants.AutonConstants.intakeCoralOffset))).schedule();
    }

    }


    /**
     * function to be called when the mech is in its proper state
     * @param wasInterupted wether or not the intake routine was cancled
     */
    public void mechIsFinishedCall(boolean wasInterupted){
        if (wasInterupted){
            cancel();
            warningManager.throwAlert(warningManager.autoInternalCancled);
        }
        mechIsFinished=true;

    }

    /**
     * @return true once a peice has been aquired
     */
    @Override
    public boolean isFinished(){
        return SystemManager.intake.hasPeice();
    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        SmartDashboard.putBoolean("auto intake is running", false);
        if (driveCommand.isScheduled()){
            driveCommand.cancel();
        }
    }
}   
