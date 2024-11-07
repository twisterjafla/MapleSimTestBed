package frc.robot;


import java.util.Currency;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swervedrive.AdditionalCommands;
import frc.robot.commands.swervedrive.QuickSwapCommand;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.commands.swervedrive.drivebase.AutoDefenceForFakeBot;
import frc.robot.commands.swervedrive.drivebase.FakeDrive;

public class ControlChooser {
    Map<String, Consumer<ControlChooser>> schemes= new HashMap<>();
    SendableChooser<Consumer<ControlChooser>> chooser =new SendableChooser<Consumer<ControlChooser>>();
    Consumer<ControlChooser>current =null;
    CommandXboxController xbox1;
    CommandXboxController xbox2;
    EventLoop controlLoop=CommandScheduler.getInstance().getDefaultButtonLoop();
    

    

    ControlChooser(){
        xbox1=new CommandXboxController(Constants.controllerIDs.commandXboxController1ID);
        xbox2=new CommandXboxController(Constants.controllerIDs.commandXboxController2ID);




        schemes.put("testControl", (
            ControlChooser host)->{
                
                //testController.a().onTrue((Commands.runOnce(SystemManager.swerve::zeroGyro)));
                //testController.x().onTrue(Commands.runOnce(SystemManager.swerve::addFakeVisionReading));
                //testController.b().whileTrue(
                //    Commands.deferredProxy(() -> SystemManager.swerve.driveToPose(
                //                            new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                //                        ));

                //SystemManager.swerve.setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->testController.getLeftX(), ()->-testController.getLeftY(),()-> getPOVForTest(testController)));
                //host.xbox1.y(host.controlLoop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
                //host.xbox1.b(host.controlLoop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))));
                //testController.y().whileTrue(drivebase.aimAtSpeaker(2));
                // driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
                // if (!RobotBase.isReal()){
                //     testController.x().onTrue(SystemManager.fakeBot.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
                // }
                //SystemManager.fakeBot.setDefaultCommand(new FakeDrive(SystemManager.fakeBot, ()->host.xbox1.getLeftX(), ()->-host.xbox1.getLeftY(),()-> Math.PI/180 * getPOVForTest(host.xbox1)));
                //host.xbox1.x(host.controlLoop).whileTrue(new AutoDefenceForFakeBot(new Pose2d(2,4, new Rotation2d(0))));
                //SystemManager.swerve.setDefaultCommand(new QuickSwapCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->host.xbox1.getLeftX(), ()->-host.xbox1.getLeftY(),()-> getPOVForTest(host.xbox1)),
                //    AdditionalCommands.SwappingAuto, ()->host.xbox1.a(host.controlLoop).getAsBoolean(), new Subsystem[]{SystemManager.swerve}));
            }
        );



        //schemes.put("testSchemeDOESNOTWORK",(ControlChooser host)->{});
            
            
            
        
        boolean first=true;
        for (Map.Entry<String, Consumer<ControlChooser>> scheme: schemes.entrySet()){
            if (first){
                chooser.setDefaultOption(scheme.getKey(), scheme.getValue());
                first=false;
            }
            else{
                chooser.addOption(scheme.getKey(), scheme.getValue());
            }
        }
        chooser.getSelected().accept(this);
        

        
    }

    public void update(){
        if (current!=chooser.getSelected()){
            CommandScheduler.getInstance().cancelAll();
            controlLoop= new EventLoop();
            CommandScheduler.getInstance().setActiveButtonLoop(controlLoop);
            
            current=chooser.getSelected();
            current.accept(this);
        }
    }


    public static int getPOVForTest(CommandXboxController controller){
        for (int pov: Constants.OperatorConstants.supportedPOV){
            if (controller.pov(pov).getAsBoolean()){
                return pov;
            }
        } 
        return 0;

    }

}

