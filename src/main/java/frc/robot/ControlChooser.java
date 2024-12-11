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
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
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
        chooser.addOption("demoControl", demoControl());


        //chooser.initSendable(new SendableBuilderImpl());
        chooser.onChange((EventLoop scheme)->{changeControl(scheme);});
        //changeControl(CommandScheduler.getInstance().getDefaultButtonLoop());
        changeControl(standardXboxControl());
        

        //warmUpSystem(SystemManager.swerve );
       
    }



    public void changeControl(EventLoop scheme){
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().setActiveButtonLoop(scheme);
        SystemManager.falseForStartup=false;
    }
    public void restart(){
        changeControl(chooser.getSelected());
        changeControl(standardXboxControl());
    }

    public void cancel(){
        changeControl(new EventLoop());
    }


    public static int getPOVForTest(CommandXboxController controller){
        for (int pov: Constants.OperatorConstants.supportedPOV){
            if (controller.pov(pov).getAsBoolean()){
                return pov;
            }
        } 
        return 0;

    }

    public static void setDefaultCommand(Command defaultCommand, Subsystem subsystem, EventLoop loop){
        final Command trueDefaultCommand=new RepeatCommand(defaultCommand);
        new Trigger(loop, ()->((CommandScheduler.getInstance().requiring(subsystem)==null||CommandScheduler.getInstance().requiring(subsystem)==trueDefaultCommand)&&SystemManager.falseForStartup)).whileTrue(trueDefaultCommand);
    }

    private EventLoop getTestControl(){
        EventLoop loop = new EventLoop();
        
       
        // xbox1.b().whileTrue(
        //     Commands.deferredProxy(() -> SystemManager.swerve.driveToPose(
        //                             new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
        //                         ));

        
        xbox1.y(loop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
        //xbox1.b(loop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))));

        // if (!RobotBase.isReal()){
        //     testController.x().onTrue(SystemManager.fakeBot.driveToPose(new Pose2d(2,4, new Rotation2d(0))));
        // }
        setDefaultCommand(new FakeDrive(SystemManager.fakeBot, ()->xbox1.getLeftX(), ()->-xbox1.getLeftY(),()-> Math.PI/180 * getPOVForTest(xbox1)), SystemManager.fakeBot, loop);
        xbox1.x(loop).whileTrue(new AutoDefenceForFakeBot(new Pose2d(2,4, new Rotation2d(0))));
        setDefaultCommand(new QuickSwapCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->xbox1.getLeftX(), ()->-xbox1.getLeftY(),()-> getPOVForTest(xbox1)),
            AdditionalCommands.SwappingAuto, ()->xbox1.getHID().getAButton()), SystemManager.swerve, loop);

        xbox1.b(loop).onTrue(new InstantCommand(()->{changeControl(getTestControl());}));
        return loop;
    }

    private EventLoop standardXboxControl(){
        EventLoop loop = new EventLoop();
        setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->-xbox1.getLeftY(), ()->-xbox1.getLeftX(),()->{
             if(Math.sqrt(Math.pow(xbox1.getRightX(), 2)+Math.pow(xbox1.getRightY(), 2))>=0.2)return Math.atan2(-xbox1.getRightX(), -xbox1.getRightY()); return SystemManager.swerve.getHeading().getRadians()/Math.PI;})
            ,SystemManager.swerve, loop);
        //setDefaultCommand(SystemManager.swerve.driveCommand(()->0, ()->0, ()->xbox1.getLeftX(), ()->xbox1.getLeftY()), SystemManager.swerve, loop);
        xbox1.b(loop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))));
        return loop;
    }

    private EventLoop demoControl(){
        EventLoop loop = new EventLoop();
        setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->xbox1.getLeftX(), ()->-xbox1.getLeftY(),()-> getPOVForTest(xbox1)),SystemManager.swerve, loop);
        ///xbox1.b(loop).onTrue(SystemManager.swerve.driveToPose(new Pose2d(15,1.2, new Rotation2d(Math.PI))));
        return loop;
    }




}

