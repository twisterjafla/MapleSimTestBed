package frc.robot.semiAutoCommands;

import java.lang.reflect.Method;

import javax.swing.text.Position;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.subsystems.DriveBase;

public class DriveToPoint extends Command{
    DriveBase drive;
    Pose2d goal;
    Pose2d start;

    PIDController turnDrivePID = new PIDController(Constants.semiAuto.turn.driveTurnKp, Constants.semiAuto.turn.driveTurnKi, Constants.semiAuto.turn.driveTurnKd);
    PIDController turnStationaryPID = new PIDController(Constants.semiAuto.turn.finalKp, Constants.semiAuto.turn.finalKi, Constants.semiAuto.turn.finalKd);
    PIDController straightPID = new PIDController(Constants.semiAuto.straight.Kp, Constants.semiAuto.straight.ki, Constants.semiAuto.straight.Kd);
    PIDController activeTurnPID;
    boolean isInRing=false;

    public DriveToPoint(DriveBase drive, Pose2d goal){
        this.drive=drive;
        this.goal=goal;

        addRequirements(drive);
    }


    @Override
    public void initialize(){
        turnStationaryPID.setSetpoint(goal.getRotation().getDegrees());
        turnStationaryPID.setTolerance(Constants.semiAuto.turn.finalTolerence);
    
        turnStationaryPID.enableContinuousInput(-180, 180);
        turnDrivePID.enableContinuousInput(-180, 180); 
        turnDrivePID.setTolerance((Constants.semiAuto.turn.driveTolerence));


        straightPID.setSetpoint(0);
        straightPID.setTolerance(0.1);

        activeTurnPID=turnDrivePID;
        //straightPID.enableContinuousInput(-1, 1);
    }


    @Override
    public void execute(){
        Pose2d current=semiAutoManager.getCoords();
        checkPhase(current);
        double straight =  straightMath(current);
        double turn = turnMath(current);
        // if(straight<0){
        //     turn=-turn;
        // }
       SmartDashboard.putNumber("turnPidGoa", activeTurnPID.getSetpoint());
        drive.drive(straight, turn);
        SmartDashboard.putBoolean("is in ring", isInRing);
        SmartDashboard.putNumber("distanceToCurret", getDistance(current));
    }

    @Override
    public boolean isFinished(){
        SmartDashboard.putBoolean("pid at set", activeTurnPID.atSetpoint());
        return activeTurnPID.atSetpoint()&&isInRing;
    }

    @Override
    public void end(boolean wasIntinterupted){
        drive.drive(0,0);
    }

    public double turnMath(Pose2d current){
        if (activeTurnPID.atSetpoint()){
            activeTurnPID.calculate(current.getRotation().getDegrees());
            return 0;
        } 

        return activeTurnPID.calculate(current.getRotation().getDegrees());
        
    }

    

    public void checkPhase(Pose2d current){
        if (!isInRing&&getDistance(current)<Constants.semiAuto.turn.ringDistance){
            isInRing=true;
            activeTurnPID=turnStationaryPID;
        }
        else if (!isInRing){
            activeTurnPID.setSetpoint(getAngle(current, goal));

            SmartDashboard.putNumber("angle", getAngle(goal, current));
        }
        else if(isInRing&&getDistance(current)>Constants.semiAuto.turn.ringDistance){
            isInRing=false;
            activeTurnPID=turnDrivePID;
            activeTurnPID.setSetpoint(getAngle(current, goal));
            SmartDashboard.putNumber("angle", getAngle(goal, current));
            

            
        }
        
        SmartDashboard.putBoolean("is in ring", isInRing);
        
    }

    public double straightMath(Pose2d current){
        if (isInRing){
            return 0;
        }

        Pose2d predictedForward = new Pose2d(current.getX()+Math.cos(Math.toRadians(getAngle(current, goal)))*0.1, current.getY()+Math.sin(Math.toRadians(getAngle(current, goal)))*0.1, current.getRotation());
        Pose2d predictedBack = new Pose2d(current.getX()-Math.cos(Math.toRadians(getAngle(current, goal)))*0.1, current.getY()-Math.sin(Math.toRadians(getAngle(current, goal)))*0.1, current.getRotation());
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
                return 0;
            }
        }
        else if (goodX==0){
            if (goodY>0){
                return 90;
            }
            else{
                return -90;
            }
        }
        
        double raw = Math.toDegrees(Math.atan(goodY/goodX));
        
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

    public double square(double toSquare){
        return Math.pow(toSquare, 2);
    }
    
}
