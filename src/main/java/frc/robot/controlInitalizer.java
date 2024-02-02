package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.semiAutoCommands.CancelCurrentRoutine;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Midi;

//object to deal with all ofthe dirty work of multiple control schemes
public class controlInitalizer {

    public static ToggleCompressor toggleCompressor=null;

  
    public static RunIntake runIntake=null;
    public static RunIntake runIntakeBackward=null;
    public static ToggleBucket toggleBucket=null;
    public static IntakeToggle toggleIntake=null;
    public static DriveBase driveSubsystem=null;
    public static CancelCurrentRoutine cancel;
    public static Boolean hasBeenInitalizedFromRobot = false;
    public static Boolean hasBeenInitalizedFromSemiAutoManager = false;

    public static void controlInitalizerFromRobot(
        ToggleCompressor ToggleCompressor,
        RunIntake RunIntake,
        RunIntake RunIntakeBackward,
        ToggleBucket ToggleBucket,
        IntakeToggle ToggleIntake,
        DriveBase DriveSubsystem){
        
        hasBeenInitalizedFromRobot=true;
        toggleCompressor=ToggleCompressor;
        runIntake=RunIntake;
        runIntakeBackward=RunIntakeBackward;
        toggleBucket=ToggleBucket;
        toggleIntake=ToggleIntake;
        driveSubsystem=DriveSubsystem;


    }

    public static void controlInitalizerFromSemiAutoManager(CancelCurrentRoutine Cancel){
        hasBeenInitalizedFromSemiAutoManager=true;
        cancel = Cancel;
    }


    public static void checkInit(){
        if (! hasBeenInitalizedFromSemiAutoManager || ! hasBeenInitalizedFromRobot){
            throw new Error("control initalizer was not properly initalized!");
        }
    }

    public static final void configureTwoControllersBasic( CommandXboxController movementController, CommandXboxController manipulatorController){
        checkInit();

        driveSubsystem.setDefaultCommand(
        new ArcadeDrive(
              driveSubsystem,
              () -> ((-movementController.getLeftTriggerAxis() + movementController.getRightTriggerAxis())),
              () -> (-movementController.getLeftX() )
        ));


      manipulatorController.leftBumper() //intake
      .whileTrue(runIntake);

      manipulatorController.rightBumper()//outake
      .whileTrue(runIntakeBackward);

      manipulatorController.x()
      .onTrue(toggleBucket);

      manipulatorController.a()
      .onTrue(toggleIntake);

      manipulatorController.y()
      .onTrue(toggleCompressor);
    }

    public static final void configureOneControllersBasic(CommandXboxController controller){
        checkInit();

        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> ((-controller.getLeftTriggerAxis() + controller.getRightTriggerAxis())),
                  () -> (-controller.getLeftX() )
            ));
    
    
    
    
            controller.leftBumper() //intake
          .whileTrue(runIntake);
    
          controller.rightBumper()//outake
          .whileTrue(runIntakeBackward);
    
          controller.x()
          .onTrue(toggleBucket);
    
          controller.a()
          .onTrue(toggleIntake);
    
          controller.y()
          .onTrue(toggleCompressor);
        
    }

    public static final void initalizeJaceControllWithSecondController(CommandXboxController movementController, CommandXboxController manipulatorController){
        checkInit();

        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> ( movementController.getLeftY()),
                  () -> (-movementController.getLeftX())
            ));
    
    
    
    
            movementController.leftBumper() //intake
          .whileTrue(runIntake);
    
          movementController.rightBumper()//outake
          .whileTrue(runIntakeBackward);
    
          movementController.x()
          .onTrue(toggleBucket);
    
          movementController.a()
          .onTrue(toggleIntake);
    
          movementController.y()
          .onTrue(toggleCompressor);
        
    }
    public static final void initalizeMIDIAloneControl(Midi midi){
        checkInit();

        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> (midi.getButtonFromDict("slider1").getValAsOneToNegOne()),
                  () -> (midi.getButtonFromDict("sliderAB").getValAsOneToNegOne())
                  ));

       midi.getButtonFromDict("button1").buttonTrigger.whileTrue(runIntake);
    }

    public static final void jaceControllWithMidi(CommandXboxController movementController, Midi midi){
        checkInit();

        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> ( movementController.getLeftY()),
                  () -> (-movementController.getLeftX())
            ));

        midi.getButtonFromDict("button9").buttonTrigger.onFalse(cancel);
    }


}
