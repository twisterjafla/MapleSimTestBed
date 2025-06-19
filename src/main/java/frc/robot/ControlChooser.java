package frc.robot;


import java.util.function.Consumer;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Utils.BetterTrigger;
import frc.robot.Utils.utillFunctions;

import frc.robot.commands.sim.CreateCoral;
import frc.robot.commands.sim.CreateNote;
import frc.robot.commands.sim.shootSpeaker;
import frc.robot.commands.sim.swapToCresendo;
import frc.robot.commands.sim.swapToReefscape;
import frc.robot.commands.swervedrive.AbsoluteFieldDrive;

import frc.robot.subsystems.generalManager;

public class ControlChooser {

    SendableChooser<EventLoop> chooser=new SendableChooser<>();
    Consumer<ControlChooser>current;
    
    CommandXboxController xbox1;
    CommandXboxController xbox2;
    
    EventLoop controlLoop=CommandScheduler.getInstance().getDefaultButtonLoop();

    
    /**creates a control chooser */
    ControlChooser(){
        
        xbox1=new CommandXboxController(Constants.controllerIDs.commandXboxController1ID);
        xbox2=new CommandXboxController(Constants.controllerIDs.commandXboxController2ID);

        chooser.setDefaultOption("default", CommandScheduler.getInstance().getDefaultButtonLoop());




        chooser.addOption("reefscapeControl", reefscapeControl());
        chooser.addOption("cresendoControl", cresendoControl());


        
        chooser.onChange((EventLoop scheme)->{changeControl(scheme);});
        changeControl(chooser.getSelected());
        
        SmartDashboard.putData("Control chooser", chooser);
       
    }


    /**
     * changes the control shceme to the scheme specified
     * @param scheme the scheme to change too
     */
    public void changeControl(EventLoop scheme){
        CommandScheduler.getInstance().cancelAll();


        CommandScheduler.getInstance().setActiveButtonLoop(scheme);


    }

    /**restarts the control chooser */
    public void restart(){
        changeControl(chooser.getSelected());
    }

    

    //returns an xbox controllers pov buttons in terms of degrees
    public static int getPOVForTest(CommandXboxController controller){
        for (int pov: Constants.OperatorConstants.supportedPOV){
            if (controller.pov(pov).getAsBoolean()){
                return pov;
            }
        } 
        return 0;

    }

    /**
     * configures a default command that can run on a loop.
     * @param defaultCommand the command to make the default
     * @param subsystem the subystem this command is the default for
     * @param loop the loop to attach the default command too
     */
    public static void setDefaultCommand(Command defaultCommand, Subsystem subsystem, EventLoop loop){
        new BetterTrigger(loop, ()->((CommandScheduler.getInstance().requiring(subsystem)==null||CommandScheduler.getInstance().requiring(subsystem)==defaultCommand))).whileTrue(defaultCommand);
    }


    /**@return a new test control loop*/
    private EventLoop reefscapeControl(){
        
        EventLoop loop = new EventLoop();


        setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->-xbox1.getLeftY(), ()->-xbox1.getLeftX(), ()->{
            if(utillFunctions.pythagorean(xbox1.getRightX(), xbox1.getRightY())>=0.2)return Math.atan2(-xbox1.getRightX(), -xbox1.getRightY())/Math.PI; return SystemManager.swerve.getHeading().getRadians()/Math.PI;})
           ,SystemManager.swerve, loop);
       

       
       xbox1.y(loop).onTrue(new InstantCommand(()->generalManager.scoreL4()));
       xbox1.x(loop).onTrue(new InstantCommand(()->generalManager.scoreL3()));
       xbox1.b(loop).onTrue(new InstantCommand(()->generalManager.scoreL2()));
       xbox1.a(loop).onTrue(new InstantCommand(()->generalManager.scoreL1()));

       xbox1.leftTrigger(0.4, loop).onTrue(new swapToReefscape());
       xbox1.rightTrigger(0.4,loop).onTrue(new InstantCommand(()->generalManager.intake()));

       xbox1.rightBumper(loop).onTrue(new InstantCommand(()->generalManager.outtake()));
        xbox1.leftBumper(loop).onTrue(new CreateCoral("leftLeft"));
        return loop;
    }


    private EventLoop cresendoControl(){
        EventLoop loop  = new EventLoop();
        setDefaultCommand(new AbsoluteFieldDrive(SystemManager.swerve, ()->-xbox1.getLeftY(), ()->-xbox1.getLeftX(), ()->{
            if(utillFunctions.pythagorean(xbox1.getRightX(), xbox1.getRightY())>=0.2)return Math.atan2(-xbox1.getRightX(), -xbox1.getRightY())/Math.PI; return SystemManager.swerve.getHeading().getRadians()/Math.PI;})
           ,SystemManager.swerve, loop);        
           
        
        xbox1.leftTrigger(0.4, loop).onTrue(new swapToCresendo());
        xbox1.rightBumper(loop).onTrue(new shootSpeaker());


        xbox1.rightTrigger(0.4,loop).onTrue(new InstantCommand(()->generalManager.intake()));
        xbox1.leftBumper(loop).onTrue(new InstantCommand(()->SystemManager.noteManipulator.addPeice()));

        
        return loop;
    }




}

