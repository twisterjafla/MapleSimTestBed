package frc.robot.subsystems.lidar;

import java.util.List;
import java.util.function.BooleanSupplier;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;

public interface lidarInterface {
    public List<Pair<Translation2d, Translation2d>> fetchObsticles();
    public boolean isConnected();
    public void setKillSwitchSupplier(BooleanSupplier killSwithcSupplier);
    public boolean isAlive();
}
