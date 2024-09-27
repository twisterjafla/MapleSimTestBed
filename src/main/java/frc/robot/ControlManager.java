package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;

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

        SystemManager.swerve.setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->testController.getLeftX(), ()->-testController.getLeftY(),()-> Math.PI/180*getPOVForTest(testController)));
    
        //testController.y().whileTrue(drivebase.aimAtSpeaker(2));
        // driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());

        
    }
}
