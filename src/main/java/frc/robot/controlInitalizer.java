package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.ShiftableGearbox;

//object to deal with all ofthe dirty work of multiple control schemes
public class controlInitalizer {

  
    final DriveBase m_driveSubsystem;

    final ShiftableGearbox gearBox;

    public controlInitalizer(
        DriveBase m_driveSubsystem, ShiftableGearbox gearBox){
        this.gearBox=gearBox;
        this.m_driveSubsystem=m_driveSubsystem;


    }


    public final void configureTwoControllersBasic( CommandXboxController movementController, CommandXboxController manipulatorController){
        m_driveSubsystem.setDefaultCommand(
        new ArcadeDrive(
              m_driveSubsystem,
              () -> ((-movementController.getLeftTriggerAxis() + movementController.getRightTriggerAxis())),
              () -> (-movementController.getLeftX() )
        ));
        movementController.rightTrigger().onTrue(new shiftGears(true, gearBox)).onFalse(new shiftGears(false, gearBox));


    }

    public final void configureOneControllersBasic(CommandXboxController controller){
        m_driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  m_driveSubsystem,
                  () -> ((-controller.getLeftTriggerAxis() + controller.getRightTriggerAxis())),
                  () -> (-controller.getLeftX() )
            ));

        
    }

    public final void initalizeJaceControllWithSecondController(CommandXboxController movementController, CommandXboxController manipulatorController){
        m_driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  m_driveSubsystem,
                  () -> ( movementController.getLeftY()),
                  () -> (-movementController.getLeftX())
            ));
        
    }


}
