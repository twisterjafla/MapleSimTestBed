package frc.robot.Utils;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;

public class scoringPosit {

    public reefLevel level;
    public reefPole pole;
    public scoringPosit(reefLevel level, reefPole pole){
        this.pole=pole;
        this.level=level;

    }

    
    public Pose2d getScorePose(){
        return pole.getScorePosit().plus(new Transform2d(level.getTranslation(), new Rotation2d()));
    }

    public scoringPosit clone(){
        return new scoringPosit(level, pole);
    }

    public int getPointValForItem() {
        if (DriverStation.isAutonomous()){
            switch (level){
                case L4: return 7;
                case L3: return 6;
                case L2: return 4;
                case L1: return 3;
            }
        }
        else{
            switch(level){
                case L4: return 5;
                case L3: return 4;
                case L2: return 3;
                case L1: return 2;
            }
        }
        return 0;
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
