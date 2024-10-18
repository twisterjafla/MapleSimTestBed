package frc.robot.mapleSimStuff;

public class GyroIOSim implements GyroIO {
    private final GyroSimulation gyroSimulation;
    public GyroIOSim(GyroSimulation gyroSimulation) {
        this.gyroSimulation = gyroSimulation;
    }
    
    @Override // specified by GroIOSim interface
    public Rotation2d getGyroRotation() {
        return this.gyroSimulation.getGyroReading();
    }
    
    @Override // specified by GroIOSim interface
    public double getGyroAngularVelocity() {
        return this.gyroSimulation.getMeasuredAngularVelocityRadPerSec();
    }
}