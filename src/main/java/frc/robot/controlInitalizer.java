package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.DriveBase;

//object to deal with all ofthe dirty work of multiple control schemes
public class controlInitalizer {

    final ToggleCompressor toggleCompressor;

  
    final RunIntake runIntake;
    final RunIntake runIntakeBackward;
    final ToggleBucket toggleBucket;
    final IntakeToggle toggleIntake;
    final DriveBase m_driveSubsystem;

    public controlInitalizer(
        ToggleCompressor toggleCompressor,
        RunIntake runIntake,
        RunIntake runIntakeBackward,
        ToggleBucket toggleBucket,
        IntakeToggle toggleIntake,
        DriveBase m_driveSubsystem){

        this.toggleCompressor=toggleCompressor;
        this.runIntake=runIntake;
        this.runIntakeBackward=runIntakeBackward;
        this.toggleBucket=toggleBucket;
        this.toggleIntake=toggleIntake;
        this.m_driveSubsystem=m_driveSubsystem;


    }


    public final void configureTwoControllersBasic( CommandXboxController movementController, CommandXboxController manipulatorController){
        m_driveSubsystem.setDefaultCommand(
        new ArcadeDrive(
              m_driveSubsystem,
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

    public final void configureOneControllersBasic(CommandXboxController controller){
        m_driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  m_driveSubsystem,
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

    public final void initalizeJaceControllWithSecondController(CommandXboxController movementController, CommandXboxController manipulatorController){
        m_driveSubsystem.setDefaultCommand(
            new ArcadeDrive(
                  m_driveSubsystem,
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


}
