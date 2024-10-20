package frc.robot.mapleSimStuff;

import edu.wpi.first.math.geometry.Rotation2d;

public interface GyroIO {
    
    class GyroIOInputs {
        public boolean connected = false;
        public Rotation2d yawPosition = new Rotation2d();
        public Rotation2d[] odometryYawPositions = new Rotation2d[]{};
        public double yawVelocityRadPerSec = 0.0;
    }

    void updateInputs(GyroIOInputs inputs);
}