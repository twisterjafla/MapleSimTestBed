// package frc.robot.subsystems.elevator;
// import com.revrobotics.RelativeEncoder;

// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.motorcontrol.MotorController;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
// import edu.wpi.first.wpilibj.motorcontrol.Talon;
// import frc.robot.Constants;

// public class Elevator implements elevatorInterface {
    
//     public double setpoint = 0;
//     public RelativeEncoder encoder;
//     Talon elevatorMaster = new Talon(Constants.elevatorConstants.LeftMotor);
//     Talon elevatorFollow = new Talon(Constants.elevatorConstants.RightMotor);

//     public void initElevator() {

    
//         elevatorMaster.addFollower(elevatorFollow);
//         elevatorMaster.setInverted(false);
//         elevatorFollow.setInverted(true);

//         encoder = elevatorMaster.getEncoder();

//     }

//     @Override
//     public void setSetpoint(double setpoint) {
//         this.setpoint=setpoint;
//     }

//     @Override
//     public boolean isAtSetpoint() {
//         encoder.getPosition();
//         if (encoder.getPosition() == setpoint) {
//             return true;
//         }
//         else {
//             return false;
//         }
//     }

//     @Override
//     public double getEncoderVal() {
//         return encoder.getPosition();
//     }

//     public double getEncoderValMeters() {
//         return getEncoderVal() * Constants.elevatorConstants.encoderTicksPerMeter;
//     }

//     @Override
//     public void reset() {
//         setpoint = 0;
//     }

//     @Override
//     public void setSetpointInMeters(double setpoint) {
//         setSetpoint(setpoint * Constants.elevatorConstants.encoderTicksPerMeter);
//     }

//     @Override
//     public double getHeight() {
//         return getEncoderValMeters();
//     }
// }
