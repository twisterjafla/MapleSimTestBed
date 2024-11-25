package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swervedrive.AdditionalCommands;
import frc.robot.commands.swervedrive.QuickSwapCommand;
import frc.robot.commands.swervedrive.SetHasNote;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.commands.swervedrive.drivebase.AutoDefenceForFakeBot;
// import frc.robot.commands.swervedrive.drivebase.FakeDrive;
// import frc.robot.commands.swervedrive.drivebase.FakePoseReset;

public class ControlManager {
    public static int getPOVForTest(CommandXboxController controller){
        for (int pov: Constants.OperatorConstants.supportedPOV){
            if (controller.pov(pov).getAsBoolean()){
                return pov;
            }
        } 
        return 0;

    }


    public static void testControl(){
        CommandXboxController testController = new CommandXboxController(Constants.OperatorConstants.mainControllerPort);
        //testController.a().onTrue((Commands.runOnce(SystemManager.swerve::zeroGyro)));
        //testController.x().onTrue(Commands.runOnce(SystemManager.swerve::addFakeVisionReading));
        //testController.b().whileTrue(
        //    Commands.deferredProxy(() -> SystemManager.swerve.driveToPose(
        //                            new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
        //                        ));

        //SystemManager.swerve.setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->testController.getLeftX(), ()->-testController.getLeftY(),()-> getPOVForTest(testController)));
        testController.y().onTrue(SystemManager.swerve.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
        testController.b().onTrue(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))));
        //testController.y().whileTrue(drivebase.aimAtSpeaker(2));
        // driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
        // if (!RobotBase.isReal()){
        //     testController.x().onTrue(SystemManager.fakeBot.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
        // }
        //SystemManager.fakeBot.setDefaultCommand(new FakeDrive(SystemManager.fakeBot, ()->testController.getLeftX(), ()->-testController.getLeftY(),()-> Math.PI/180 * getPOVForTest(testController)));
        //testController.x().whileTrue(new AutoDefenceForFakeBot(new Pose2d(2,4, new Rotation2d(0))));
        SystemManager.swerve.setDefaultCommand(new QuickSwapCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->testController.getLeftX(), ()->-testController.getLeftY(),()-> getPOVForTest(testController)),
        AdditionalCommands.SwappingAuto, ()->testController.a().getAsBoolean(), new Subsystem[]{SystemManager.swerve}));
        
    }
}
