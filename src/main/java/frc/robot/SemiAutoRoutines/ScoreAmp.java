package frc.robot.SemiAutoRoutines;

import java.util.List;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.commands.RepetitiveOutake;
import frc.robot.commands.ToggleElevator;
import frc.robot.semiAutoCommands.BlinkinGreen;
import frc.robot.semiAutoCommands.BlinkinRed;
import frc.robot.semiAutoCommands.BlinkinYellow;
import frc.robot.semiAutoCommands.stealDriveCommand;
import frc.robot.subsystems.Blinkin;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class ScoreAmp extends SequentialCommandGroup {
    public ScoreAmp(DriveBase drive, Elevator intakeElevator, Intake intake){
        
        super(
            //Init
            new BlinkinYellow(),
            new WaitCommand(Constants.robotStats.SemiAutoRoutineWaitTimes),
            

            //MainLoop
            new BlinkinRed(),
            new ParallelCommandGroup(
                semiAutoManager.getRamseteCommand(
                    TrajectoryGenerator.generateTrajectory(
                        semiAutoManager.getCoords(),
      null,
                       Constants.fieldPosits.ampScore, Constants.TrajectoryGeneratorObjects.trajectoryConfigurer)),
                       new ToggleElevator(intakeElevator)
            ),

            //Ending set
            new BlinkinYellow(),

            new ParallelCommandGroup(
            new RepetitiveOutake(intake),
            new stealDriveCommand(drive)
            ),

            //passOff
            new BlinkinGreen()
        );

    }
}
