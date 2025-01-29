package frc.robot.Utils;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
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
        return pole.getScorePosit();
    }

    public static int getPointValForItem(int level) {
        if (DriverStation.isAutonomous()){
            switch (level){
                case 4: return 7;
                case 3: return 6;
                case 2: return 4;
                case 1: return 3;
            }
        }
        else{
            switch(level){
                case 4: return 5;
                case 3: return 4;
                case 2: return 3;
                case 1: return 2;
            }
        }
        return 0;
    }


}
