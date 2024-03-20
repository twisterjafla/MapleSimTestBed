package frc.robot.semiAutoCommands;

import java.lang.reflect.Method;

import javax.swing.text.Position;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.ShiftableGearbox;

public class DriveToPoint extends Command{
    DriveBase drive;
    Pose2d goal;
    Pose2d start;

    PIDController turnDrivePID = new PIDController(Constants.semiAuto.turn.driveTurnKp, Constants.semiAuto.turn.driveTurnKi, Constants.semiAuto.turn.driveTurnKd);
    PIDController turnStationaryPID = new PIDController(Constants.semiAuto.turn.finalKp, Constants.semiAuto.turn.finalKi, Constants.semiAuto.turn.finalKd);
    PIDController turnPID;

    PIDController innerStraightPID = new PIDController(Constants.semiAuto.straight.innerKp, Constants.semiAuto.straight.innerKi, Constants.semiAuto.straight.innerKd);
    PIDController outerStraightPID = new PIDController(Constants.semiAuto.straight.outerKp, Constants.semiAuto.straight.outerKi, Constants.semiAuto.straight.outerKd);
    PIDController straightPID;

    boolean isInInnerRing=false;
    boolean isInOuterRing=false;

    final double PI = Math.PI;
    ShiftableGearbox gearbox;

    

    public DriveToPoint(DriveBase drive, Pose2d goal, ShiftableGearbox gearBox){
        this.drive=drive;
        this.goal=goal;
        this.gearbox=gearBox;

        addRequirements(drive);
    }


    @Override
    public void initialize(){
        turnStationaryPID.setSetpoint(goal.getRotation().getRadians());
        turnStationaryPID.setTolerance(Constants.semiAuto.turn.finalTolerence);
    
        turnStationaryPID.enableContinuousInput(-PI, PI);
        turnDrivePID.enableContinuousInput(-PI/2, PI/2); 
        turnDrivePID.setTolerance((Constants.semiAuto.turn.driveTolerence));
        //gearbox.shift(false);

        outerStraightPID.setSetpoint(0);
        innerStraightPID.setSetpoint(0);
        //straightPID.setTolerance(0.1);

        turnPID=turnDrivePID;
        straightPID=outerStraightPID;
        SmartDashboard.putBoolean("hasFinished", false);

               //straightPID.enableContinuousInput(-1, 1);
    }


    @Override
    public void execute(){
        Pose2d current=semiAutoManager.getCoords();
        checkPhase(current);
        SmartDashboard.putNumber("turnPidGoal", turnPID.getSetpoint());
        drive.drive(straightMath(current), turnMath(current));
        SmartDashboard.putBoolean("is in ring", isInInnerRing);
        SmartDashboard.putNumber("distanceToCurret", getDistance(current));
    }

    @Override
    public boolean isFinished(){
        SmartDashboard.putBoolean("pid at set", turnPID.atSetpoint());
        return turnPID.atSetpoint()&&isInInnerRing;
        //return true;
    }

    @Override
    public void end(boolean wasIntinterupted){
        SmartDashboard.putBoolean("hasFinished", true);
        drive.drive(0,0);
    }

    public double turnMath(Pose2d current){
        if (turnPID.atSetpoint()){
            turnPID.calculate(current.getRotation().getRadians());
            return 0;
        } 
        return turnPID.calculate(current.getRotation().getRadians());
        
    }

    

    public void checkPhase(Pose2d current){
        if (!isInInnerRing&&getDistance(current)<Constants.semiAuto.goalRingDistance){
            isInInnerRing=true;
            turnPID=turnStationaryPID;
        }
        else if (!isInInnerRing){
            turnPID.setSetpoint(getAngle(current, goal));

        }
        else if(isInInnerRing&&getDistance(current)>Constants.semiAuto.goalRingDistance){
            isInInnerRing=false;
            turnPID=turnDrivePID;
            turnPID.setSetpoint(getAngle(current, goal));
             
        }
        
        if(!isInOuterRing&&getDistance(current)<Constants.semiAuto.outerRingDistance){
            isInOuterRing=true;
            straightPID=innerStraightPID;
        }
        else if(isInOuterRing&&getDistance(current)>Constants.semiAuto.outerRingDistance){
            isInOuterRing=false;
            straightPID=outerStraightPID;
        }

        SmartDashboard.putBoolean("is in inner ring", isInInnerRing);
        SmartDashboard.putBoolean("is in outer ring", isInOuterRing);

        
    }

    public double straightMath(Pose2d current){
        if (isInInnerRing){
            return 0;
        }   
        Pose2d predictedForward = current.exp(new Twist2d(0.1, 0, 0));
    
        Pose2d predictedBack = current.exp( new Twist2d(-0.1, 0, 0));
        // if (current.getRotation().getDegrees()>90||current.getRotation().getDegrees()<-90){
        //     predictedBack=predictedFieldForward;
        //     predictedForward=predictedFieldBack;
        // }
        // else{
        //     predictedBack=predictedFieldBack;
        //     predictedForward=predictedFieldForward;
        // }


        // if (current.getRotation().getDegrees()>90 || current.getRotation().getDegrees()<-90){
        //     predictedForward = new Pose2d(current.getX()-Math.cos(getAngle(current, goal))*0.1, current.getY()-Math.sin(getAngle(current, goal))*0.1, current.getRotation());
        //     predictedBack = new Pose2d(current.getX()+Math.cos(getAngle(current, goal))*0.1, current.getY()+Math.sin(getAngle(current, goal))*0.1, current.getRotation());
        // }



        if (getDistance(current)>getDistance(predictedForward)){
            SmartDashboard.putBoolean("forwardDrive", true);
            return straightPID.calculate(-getDistance(current));
        }
        else if(getDistance(current)>getDistance(predictedBack)){
            SmartDashboard.putBoolean("forwardDrive", false);
            return straightPID.calculate(getDistance(current));
        }
        else{
            return 0;
        }
        
    }




    public double getDistance(Pose2d pointA, Pose2d pointB){
        return Math.sqrt(square(pointA.getX()-pointB.getX())+square(pointA.getY()-pointB.getY()));
    }

    public double getDistance(Pose2d pointA){
        return getDistance(pointA, goal);
    }

    public double getAngle(Pose2d rotationA, Pose2d  rotationB){
        double goodY = rotationA.getY()-rotationB.getY();
        double goodX = rotationA.getX()-rotationB.getX();
        if (goodY==0){
            if (goodX>0){
                return 0;
            }
            else{
                return PI;
            }
        }
        else if (goodX==0){
            if (goodY>0){
                return PI/2;
            }
            else{
                return -PI/2;
            }
        }
        
        double raw = Math.atan(goodY/goodX);
        
        // if (goodX<0&&goodY<0){
        //     return -180+raw;
        // }
        // else if (goodY<0){
        //     return 180-raw;
        // }    
        // else{
        //     return raw;
        // }                    
        return raw;
    }

    // public double getAngle360(Pose2d pointA, Pose2d pointB){
    //     double raw = getAngle(pointA, pointB);
        
        

    //     if (pointA.getX()<pointB.getX()&&pointA.getY()<pointB.getY()){
    //         return -180+raw;
    //     }
    //     else if (pointA.getX()<pointB.getX()){
    //         return 180-raw;
    //     }    
    //     else{
    //         return raw;
    //     }    
    // }


    public double square(double toSquare){
        return Math.pow(toSquare, 2);
    }
    
}
