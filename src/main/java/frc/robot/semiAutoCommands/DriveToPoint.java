package frc.robot.semiAutoCommands;

import java.lang.reflect.Method;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.subsystems.DriveBase;

public class DriveToPoint extends Command{
    DriveBase drive;
    Pose2d goal;

    PIDController turnPid = new PIDController(Constants.semiAuto.turn.Kp, 0, Constants.semiAuto.turn.Kd);
    PIDController straightPID = new PIDController(Constants.semiAuto.straight.Kp, 0, Constants.semiAuto.straight.Kd);

    boolean isInRing=false;

    public DriveToPoint(DriveBase drive, Pose2d goal){
        this.drive=drive;
        this.goal=goal;

        addRequirements(drive);
    }

    @Override
    public void initialize(){
        turnPid.setSetpoint(goal.getRotation().getDegrees());
        turnPid.setTolerance(Constants.semiAuto.turn.tolerence);
        turnPid.enableContinuousInput(-1, 1);
  
        straightPID.enableContinuousInput(-1, 1);
    }


    @Override
    public void execute(){
        Pose2d current=semiAutoManager.getCoords();
        checkPhase(getDistance(current, goal), current);
        drive.drive(straightMath(current), turnMath(current));
    
    }

    @Override
    public boolean isFinished(){
        return turnPid.atSetpoint()&&isInRing;
    }

    @Override
    public void end(boolean wasIntinterupted){
        drive.drive(0,0);
    }

    public double turnMath(Pose2d current){
        return turnPid.calculate(current.getRotation().getDegrees());
    }

    

    public void checkPhase(double distance, Pose2d current){
        if (!isInRing&&distance<Constants.semiAuto.turn.ringDistance){
            isInRing=true;
            turnPid.setSetpoint(goal.getRotation().getDegrees());
        }
        else if (!isInRing){
            if (goal.getX()-current.getX()==0){
                turnPid.setSetpoint(90);
            }
            turnPid.setSetpoint(getAngle(current, goal));
        }
        
    }

    public double straightMath(Pose2d current){
        if (isInRing){
            return 0;
        }
        double currentDistance=getDistance(current, goal);
        Pose2d predicted = new Pose2d(current.getX()+Math.sin(getAngle(current, goal)), current.getY()+Math.cos(getAngle(current, goal)), current.getRotation());
        double predictedDistance = getDistance(predicted, goal);
        if (predictedDistance>currentDistance){
            return 0;
        }
        straightPID.setSetpoint(getDistance(current, goal));
        return straightPID.calculate(0);
    }




    public double getDistance(Pose2d pointA, Pose2d pointB){
        return Math.sqrt(square(pointA.getX()-pointB.getX())+square(pointA.getY()-pointB.getY()));
    }

    public double getAngle(Pose2d rotationA, Pose2d  rotationB){
        double goodY = rotationA.getY()-rotationB.getY();
        double goodX = rotationA.getX()-rotationB.getX();
        if (goodX==0){
            if (goodY>0){
                return 0;
            }
            else{
                return 180;
            }
        }
        else if (goodY==0){
            if (goodX>0){
                return 90;
            }
            else{
                return -90;
            }
        }
        
        double raw = Math.atan(goodY/goodX);
        
        if (goodX<0&&goodY<0){
            return -180+raw;
        }
        else if (goodX<0){
            return 180+raw;
        }    
        else{
            return raw;
        }
    }

    public double square(double toSquare){
        return Math.pow(toSquare, 2);
    }
    
}
