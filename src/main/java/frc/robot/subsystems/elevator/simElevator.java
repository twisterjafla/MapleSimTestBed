package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.hardware.core.CoreTalonFX;
import com.ctre.phoenix6.hardware.traits.HasTalonControls;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.wristElevatorControlManager;

public class simElevator  extends elevatorIO{

    private final DCMotor m_elevatorGearbox = DCMotor.getKrakenX60(2);

    // Standard classes for controlling our elevator
    private final ProfiledPIDController m_controller =
        new ProfiledPIDController(
            Constants.elevatorConstants.slot0Configs.kP,
            Constants.elevatorConstants.slot0Configs.kI,
            Constants.elevatorConstants.slot0Configs.kD,
            new TrapezoidProfile.Constraints(2.45, 2.45));


    ElevatorFeedforward m_feedforward =
        new ElevatorFeedforward(
            Constants.elevatorConstants.slot0Configs.kS,
            Constants.elevatorConstants.slot0Configs.kG,
            Constants.elevatorConstants.slot0Configs.kV,
            Constants.elevatorConstants.slot0Configs.kA);

    
    private final TalonFX motor = new TalonFX(50);

    // Simulation classes help us simulate what's going on, including gravity.
    private final ElevatorSim m_elevatorSim =
        new ElevatorSim(
            m_elevatorGearbox,
            Constants.elevatorConstants.gearRatio,
            Constants.elevatorConstants.mass,
            Constants.elevatorConstants.drumRadius,
            0,
            Constants.elevatorConstants.l4EncoderVal,
            true,
            0,
            0.01,
            0.0);
    
    private final TalonFXSimState m_motorSim = new TalonFXSimState(motor);




     // create a Motion Magic request, voltage output
     final MotionMagicVoltage motionVoltage = new MotionMagicVoltage(0);
     

    

    /** Advance the simulation. */
    public void simulationPeriodic() {

    }

    /**
     * Run control loop to reach and maintain goal.
     *
     * @param goal the position to maintain
     */
    public void reachGoal(double goal) {
        m_controller.setGoal(goal);

        // With the setpoint value we run PID control like normal
        double pidOutput = m_controller.calculate(m_encoder.getDistance());
        double feedforwardOutput = m_feedforward.calculate(m_controller.getSetpoint().velocity);
        m_motor.setVoltage(pidOutput + feedforwardOutput);
    }

    /** Stop the control loop and motor output. */
    public void stop() {
        m_controller.setGoal(0.0);
        m_motor.set(0.0);
    }

    /** Update telemetry, including the mechanism visualization. */
    public void updateTelemetry() {
        // Update elevator visualization with position
        m_elevatorMech2d.setLength(m_encoder.getDistance());
    }

    public double position=0;
    protected double goal=0;
    

    @Override
    public double getEncoderVal() {
        return position;
    }

    @Override
    public void periodic(){


        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.elevator||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){
            goal=setpoint;
        }
        // quit if the wrist is having issues
        else if(wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.fixWrist){
            return;
        }
        else{
            goal=Constants.elevatorConstants.maxHeight;
        }


       
        // set target position to 100 rotations

        // elevatorPid.setSetpoint(goal);
        // motor.set(elevatorPid.calculate(getHeight())+Constants.elevatorConstants.g);

        m_motorSim.setControl(motionVoltage.withPosition(goal));

                // In this method, we update our simulation of what our elevator is doing
        // First, we set our "inputs" (voltages)
        m_elevatorSim.setInput(motionVoltage.with() * RobotController.getBatteryVoltage());

        // Next, we update it. The standard loop time is 20ms.
        m_elevatorSim.update(0.020);

        // Finally, we set our simulated encoder's readings and simulated battery voltage
        m_encoderSim.setDistance(m_elevatorSim.getPositionMeters());
        // SimBattery estimates loaded battery voltages
        RoboRioSim.setVInVoltage(
            BatterySim.calculateDefaultBatteryLoadedVoltage(m_elevatorSim.getCurrentDrawAmps()));
        
        updateRender();
        
    }





    
    
}
