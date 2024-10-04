package frc.robot.subsystems.swervedrive;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveControllerConfiguration;
import swervelib.parser.SwerveDriveConfiguration;

public class FakeBot extends SubsystemBase{

    public FieldObject2d renderer;
    public Field2d field;
    public Pose2d pose;
    double dx, dy;
    public Twist2d backLeft = new Twist2d(-0.5, -0.5, 0);
    public Twist2d frontRight= new Twist2d(0.5, 0.5, 0);
    public FakeBot() {
        pose=new Pose2d(1,1, new Rotation2d(0));
        
        
        
        dx=0;
        dy=0;
        field=new Field2d();
        renderer = field.getObject("René DesCoded");
        SmartDashboard.putData("Field", this.field);

    }


    @Override
    public void periodic(){
        renderer.setPose(pose);
        SmartDashboard.putNumber("robotPositX", pose.getX());
        SmartDashboard.putNumber("robotPositY", pose.getY());
        SmartDashboard.putNumber("robotPositDX", dx);
        SmartDashboard.putNumber("robotPositDY", dy);
        SmartDashboard.putNumber("RobotRotation", pose.getRotation().getDegrees());
        SmartDashboard.putNumber("robotRotation radians", pose.getRotation().getRadians());

    }

    public void drive(double dxAdd, double dyAdd, double r){
        
        dx+=Math.max(Math.min(dxAdd, Constants.sim.maxExel), -Constants.sim.maxExel);
        dy+=Math.max(Math.min(dyAdd, Constants.sim.maxExel), -Constants.sim.maxExel);
        dy+=dyAdd;
        dx+=dxAdd;
        dx=Math.max(Math.min(dx, Constants.sim.maxSpeed),-Constants.sim.maxSpeed);
        dy=Math.max(Math.min(dy, Constants.sim.maxSpeed),-Constants.sim.maxSpeed);
        if (dxAdd==0){
            dx=0;
        }
        if (dyAdd==0){
            dy=0;
        }
        Transform2d transform= new Transform2d(dx/50, dy/50, new Rotation2d(r));
        pose = pose.transformBy(transform);


    }
    public void resetPose(){
        pose=new Pose2d(1, 1, new Rotation2d(0));
        dx=0;
        dy=0;
    }

    private Translation2d getTopCorner(){
        return pose.exp(frontRight).getTranslation();
    }

    private Translation2d getBottemCorner(){
        return pose.exp(backLeft).getTranslation();
    }
    


    public Translation2d applyVelocitySmear(Translation2d corner){
        Translation2d velocityTrans = new Translation2d(dx*0.1, dy*0.1);
        Translation2d finalTrans = new Translation2d(dx*0.25,dy*0.25);
        Translation2d newCorner=corner.plus(velocityTrans);
        if (newCorner.getDistance(pose.getTranslation())>corner.getDistance(pose.getTranslation())){
            return corner.plus(finalTrans);
        }
        return corner;

    }

    public Pair<Translation2d, Translation2d> getHitbox(){
        return Pair.of(getTopCorner(), getBottemCorner());
    }

    public List<Pair<Translation2d, Translation2d>> getTrajHitboxes(){
        List<Pair<Translation2d, Translation2d>> hitboxes = new ArrayList<>();
        Pair<Translation2d, Translation2d> CurrentHitbox = getHitbox();
        hitboxes.add(CurrentHitbox); 
        Translation2d  velocityTrans =new Translation2d(dx, dy);
        for(double i = 0.05; i<0.25; i+=0.05){
            //Pair<Translation2d, Translation2d> trajHitbox = CurrentHitbox;
            //trajHitbox.= trajHitbox.first.plus(velocityTrans.times(i));
            hitboxes.add(Pair.of(CurrentHitbox.getFirst().plus(velocityTrans.times(i)), CurrentHitbox.getSecond().plus(velocityTrans.times(i))));
        }
        return hitboxes;
    }

}
