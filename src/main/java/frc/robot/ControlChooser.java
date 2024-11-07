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
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swervedrive.AdditionalCommands;
import frc.robot.commands.swervedrive.QuickSwapCommand;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.commands.swervedrive.drivebase.AutoDefenceForFakeBot;
import frc.robot.commands.swervedrive.drivebase.FakeDrive;

public class ControlChooser {
    //Map<String, Consumer<ControlChooser>> schemes= new HashMap<>();
    SendableChooser<EventLoop> chooser =new SendableChooser<EventLoop>();
    Consumer<ControlChooser>current =null;
    CommandXboxController xbox1;
    CommandXboxController xbox2;
    EventLoop controlLoop=CommandScheduler.getInstance().getDefaultButtonLoop();
    

    

    ControlChooser(){
        xbox1=new CommandXboxController(Constants.controllerIDs.commandXboxController1ID);
        xbox2=new CommandXboxController(Constants.controllerIDs.commandXboxController2ID);

        chooser.setDefaultOption("default", CommandScheduler.getInstance().getDefaultButtonLoop());

        if (!RobotBase.isReal()){
            chooser.addOption("testControl", getTestControl());
        }


        chooser.addOption("StandardXboxControl", standardXboxControl());
    }

    public void update(){
        if (CommandScheduler.getInstance().getActiveButtonLoop()!=chooser.getSelected()){
            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().setActiveButtonLoop(chooser.getSelected());
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

    private EventLoop getTestControl(){
        EventLoop loop = new EventLoop();
        
       
        xbox1.b().whileTrue(
            Commands.deferredProxy(() -> SystemManager.swerve.driveToPose(
                                    new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                                ));

        
        xbox1.y(loop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
        xbox1.b(loop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))));

        // if (!RobotBase.isReal()){
        //     testController.x().onTrue(SystemManager.fakeBot.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
        // }
        SystemManager.fakeBot.setDefaultCommand(new FakeDrive(SystemManager.fakeBot, ()->xbox1.getLeftX(), ()->-xbox1.getLeftY(),()-> Math.PI/180 * getPOVForTest(xbox1)));
        xbox1.x(loop).whileTrue(new AutoDefenceForFakeBot(new Pose2d(2,4, new Rotation2d(0))));
        SystemManager.swerve.setDefaultCommand(new QuickSwapCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->xbox1.getLeftX(), ()->-xbox1.getLeftY(),()-> getPOVForTest(xbox1)),
            AdditionalCommands.SwappingAuto, ()->xbox1.a(loop).getAsBoolean(), new Subsystem[]{SystemManager.swerve}));
        return loop;
    }

    private EventLoop standardXboxControl(){
        EventLoop loop = new EventLoop();
        SystemManager.swerve.setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->xbox1.getLeftX(), ()->-xbox1.getLeftY(),()-> getPOVForTest(xbox1)));
        return loop;
    }


}

