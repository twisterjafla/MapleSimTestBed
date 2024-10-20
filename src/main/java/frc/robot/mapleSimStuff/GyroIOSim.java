package frc.robot.mapleSimStuff;

import org.ironmaple.simulation.drivesims.GyroSimulation;

import edu.wpi.first.math.geometry.Rotation2d;

public class GyroIOSim {
    private final GyroSimulation gyroSimulation;
    public GyroIOSim(GyroSimulation gyroSimulation) {
        this.gyroSimulation = gyroSimulation;
    }
    
    public Rotation2d getGyroRotation() {
        return this.gyroSimulation.getGyroReading();
    }
    
    public double getGyroAngularVelocity() {
        return this.gyroSimulation.getMeasuredAngularVelocityRadPerSec();
    }
}