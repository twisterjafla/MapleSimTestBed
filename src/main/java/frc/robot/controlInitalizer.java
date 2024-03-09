package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.commands.ElevatorParallelCommands.ElevatorMove;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.ShiftableGearbox;
import frc.robot.subsystems.WristIntake;

//object to deal with all ofthe dirty work of multiple control schemes
public class controlInitalizer {
    final DriveBase m_driveSubsystem;
    final ShiftableGearbox gearBox;
    final WristIntake wrist;
    final Intake intake;
    final Elevator elevator;

    public controlInitalizer(
        DriveBase m_driveSubsystem, ShiftableGearbox gearBox, WristIntake wrist, Intake intake, Elevator elevator){
        this.gearBox=gearBox;
        this.m_driveSubsystem=m_driveSubsystem;
        this.wrist = wrist;
        this.intake = intake;
        this.elevator = elevator;


    }


    public final void configureTwoControllersBasic( CommandXboxController movementController, CommandXboxController manipulatorController){
        m_driveSubsystem.setDefaultCommand(
        new ArcadeDrive(
              m_driveSubsystem,
              () -> ((-movementController.getLeftTriggerAxis() + movementController.getRightTriggerAxis())),
              () -> (-movementController.getLeftX() )
        ));


    }

    public final void configureOneControllersBasic(CommandXboxController controller){
        m_driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  m_driveSubsystem,
                  () -> ((-controller.getLeftTriggerAxis() + controller.getRightTriggerAxis())),
                  () -> (-controller.getLeftX() )
            ));

        
    }

    public final void initalizeJaceControllWithSecondController(CommandXboxController movementController, CommandXboxController manipulatorController, WristIntake wrist, Intake intake, Elevator elevator){
        m_driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  m_driveSubsystem,
                  () -> ( movementController.getLeftY()),
                  () -> (-movementController.getRightX())
            ));

        movementController.x().onTrue(new shiftGears(false, gearBox)).onFalse(new shiftGears(true, gearBox));

        // movementController.rightTrigger().onTrue(new WristMove(wrist, Constants.wrist.positions.up));
        // movementController.leftTrigger().onTrue(new WristMove(wrist, Constants.wrist.positions.intake));
        movementController.a().onTrue(new RepetitiveIntake(intake));
        movementController.b().onTrue(new RepetitiveOutake(intake));
        movementController.rightBumper().whileTrue(new ElevatorParallel(elevator, Constants.elevator.elevatorUpSpeed));
        movementController.leftBumper().whileTrue(new ElevatorParallel(elevator, Constants.elevator.elevatorDownSpeed));
        movementController.povUp().whileTrue(new stayAtTop(elevator));
        
    }
}
