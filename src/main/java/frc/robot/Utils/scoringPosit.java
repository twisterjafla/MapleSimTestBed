package frc.robot.Utils;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;

public class scoringPosit {

    public reefLevel level;
    public reefPole pole;
    public scoringPosit(reefLevel level, reefPole pole){
        this.pole=pole;
        this.level=level;

    }

    @Deprecated
    public Pose2d getNeededPose(){
        return null;
    }


}
