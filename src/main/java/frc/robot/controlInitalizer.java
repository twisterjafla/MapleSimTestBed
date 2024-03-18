package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.SemiAutoRoutines.*;
import frc.robot.commands.*;
import frc.robot.semiAutoCommands.CancelCurrentRoutine;

import frc.robot.Constants.intake.intakeNote;
import frc.robot.commands.*;
import frc.robot.commands.DriveCommands.*;
import frc.robot.commands.ElevatorCommands.*;
import frc.robot.commands.IntakeCommands.*;
import frc.robot.commands.IntakeCommands.RepetitiveOutake.OuttakeMain;
import frc.robot.commands.WristComands.*;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Midi;
import frc.robot.subsystems.ShiftableGearbox;
import frc.robot.subsystems.WristIntake;


//object to deal with all ofthe dirty work of multiple control schemes
public class controlInitalizer {
    static DriveBase driveSubsystem;
    static ShiftableGearbox gearBox;
    static WristIntake wrist;
    static Intake intake;
    static Elevator elevator;
    static CancelCurrentRoutine cancel;
    static boolean hasBeenInitalizedFromRobot=false;
    static boolean hasBeenInitalizedFromSemiAutoManager=false;
    public static Command testRoutine;


    public static void controlInitalizerFromRobot(
        DriveBase DriveSubsystem, ShiftableGearbox GearBox, WristIntake Wrist, Intake Intake, Elevator Elevator){
        hasBeenInitalizedFromRobot=true;
        gearBox=GearBox;
        driveSubsystem=DriveSubsystem;
        wrist = Wrist;
        intake = Intake;
        elevator = Elevator;
        testRoutine = new testRoutineRunner(driveSubsystem, GearBox);



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
              () -> (movementController.getLeftX() )
        ));

      

    }

    public static final void configureOneControllersBasic(CommandXboxController controller){
        checkInit();

        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> ((-controller.getLeftTriggerAxis() + controller.getRightTriggerAxis())),
                  () -> (controller.getLeftX() )
            ));

        
    }

    public static final void initalizeJaceControllWithSecondController(CommandXboxController movementController, CommandXboxController manipulatorController){
        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> ( movementController.getLeftY()),
                  () -> (-movementController.getRightX())
            ));
        movementController.rightTrigger().onTrue(new shiftGears(true, gearBox)).onFalse(new shiftGears(false, gearBox));

        movementController.x().onTrue(new shiftGears(false, gearBox)).onFalse(new shiftGears(true, gearBox));

        movementController.leftTrigger().onTrue(new WristMoveAuto(wrist, Constants.wrist.positions.intake));
        // movementController.a().whileTrue(new IntakeNote(intake));
        // movementController.b().whileTrue(new ShootNote(intake));
        movementController.a().onTrue(new IntakeMain(intake));
        movementController.b().onTrue(new OuttakeMain(intake));
        movementController.rightBumper().whileTrue(new ElevatorToggle(elevator, Constants.elevator.elevatorUpSpeed));
        movementController.leftBumper().whileTrue(new ElevatorToggle(elevator, Constants.elevator.elevatorDownSpeed));
        movementController.y().onTrue(new wristReset(wrist));
        movementController.povUp().whileTrue(new stayAtTopMain(elevator));
        
    }


    public static final void initalizeMIDIAloneControl(Midi midi){
        checkInit();

        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> (midi.getButtonFromDict("slider1").getValAsOneToNegOne()),
                  () -> (midi.getButtonFromDict("sliderAB").getValAsOneToNegOne())
                  ));

       //midi.getButtonFromDict("button1").buttonTrigger.whileTrue(runIntake);
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

    public static final void autoDriveTest(CommandXboxController controller){
        driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  driveSubsystem,
                  () -> ( controller.getLeftY()),
                  () -> (-controller.getRightX())
            )); 

        controller.y().onTrue(cancel);
        controller.x().onFalse(testRoutine);
        //controller.rightTrigger().onTrue(new shiftGears(true, gearBox)).onFalse(new shiftGears(false, gearBox));
    }



}
