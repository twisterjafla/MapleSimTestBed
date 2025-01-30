package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.states.intaking;
import frc.robot.commands.states.scoreL1Config;
import frc.robot.commands.states.scoreL2Config;
import frc.robot.commands.states.scoreL3Config;
import frc.robot.commands.states.scoreL4Config;
import frc.robot.commands.states.starting;

public class FieldPosits {
    public static class StaringGamePeices{
        public static final Pose2d leftStack= new Pose2d(1.2, 5.8, new Rotation2d());
        public static final Pose2d midStack = new Pose2d(1.2, 4, new Rotation2d());
        public static final Pose2d rightStack = new Pose2d(1.2, 2.2, new Rotation2d());
    }

    /**
     * THESE ARE NOT GRAB POINTS FOR THE ROBOT. THESE POINTS TELL MAPLE SIM WHERE TO SPAWN CORAL AND ARE NOT ACCESABLE BY ROBOT
     */
    public static class coralSpawnPoints{
        public static final Pose2d leftLeft= new Pose2d(1.3, 7.7, new Rotation2d());
        public static final Pose2d leftMid= new Pose2d(0.8, 7.3, new Rotation2d());
        public static final Pose2d leftRight= new Pose2d(0.4, 7.1, new Rotation2d());
        public static final Pose2d rightLeft= new Pose2d(0.4, 1, new Rotation2d());
        public static final Pose2d rightMid= new Pose2d(0.8, 0.7, new Rotation2d());
        public static final Pose2d rightRight= new Pose2d(1.3, 0.3, new Rotation2d());
        public static final Pose2d[] coralSpawnPoints = {leftLeft, leftMid, leftRight, rightLeft, rightMid, rightRight};

    }

    public static class scoringPosits{
        public static final Pose2d A = new Pose2d();
        public static final Pose2d B = new Pose2d();
        public static final Pose2d C = new Pose2d();
        public static final Pose2d D = new Pose2d();
        public static final Pose2d E = new Pose2d();
        public static final Pose2d F = new Pose2d();
        public static final Pose2d G = new Pose2d();
        public static final Pose2d H = new Pose2d();
        public static final Pose2d I = new Pose2d();
        public static final Pose2d J = new Pose2d();
        public static final Pose2d K = new Pose2d();
        public static final Pose2d L = new Pose2d();
        public static final Pose2d[] scoringPosits = {A, B, C, D, E, F, G, H, I, J, K, L};
        public static final reefPole[] scoringPoles = {
            reefPole.A,
            reefPole.B,
            reefPole.C,
            reefPole.D,
            reefPole.E,
            reefPole.F,
            reefPole.G,
            reefPole.H,
            reefPole.I,
            reefPole.J,
            reefPole.K,
            reefPole.L

        };
    }

    // enum poles {
    //     1,
    //     2,
    //     3,
    //     4,
    //     5,
    //     6,
    //     7,
    //     8,
    //     9,
    //     10,
    //     11
    
    // }

    public static enum reefPole{
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L;

        public Pose2d getScorePosit() {
            switch (this) {
                case A:
                    return scoringPosits.A;
                case B:
                    return scoringPosits.B;
                case C:
                    return scoringPosits.C;
                case D:
                    return scoringPosits.D;
                case E:
                    return scoringPosits.E;
                case F:
                    return scoringPosits.F;
                case G:
                    return scoringPosits.G;
                case H:
                    return scoringPosits.H;
                case I:
                    return scoringPosits.I;
                case J:
                    return scoringPosits.J;
                case K:
                    return scoringPosits.K;
                case L:
                    return scoringPosits.L;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }

        public int getRowAsIndex() {
            switch (this) {
                case A:
                    return 0;
                case B:
                    return 1;
                case C:
                    return 2;
                case D:
                    return 3;
                case E:
                    return 4;
                case F:
                    return 5;
                case G:
                    return 6;
                case H:
                    return 7;
                case I:
                    return 8;
                case J:
                    return 9;
                case K:
                    return 10;
                case L:
                    return 11;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }
      }
      
    public static enum reefLevel{
        L1,
        L2,
        L3,
        L4;
        
        public static reefLevel CreateFromLevel(int level){
            switch (level){
                case 0: return reefLevel.L1;
                case 1: return reefLevel.L2;
                case 2: return reefLevel.L3;
                case 3: return reefLevel.L4;
                default:
                    throw new Error("you tried to make a pole of an invalid level DUMBASS");
            }
        }

        public int getasInt(){
            switch (this){
                case L1:
                    return 1;
                case L2:
                    return 2;
                case L3:
                    return 3;
                case L4:
                    return 4;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }
    }


    
}
