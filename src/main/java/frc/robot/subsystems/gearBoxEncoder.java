package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class gearBoxEncoder extends SubsystemBase{
    CANSparkMax spark;
    RelativeEncoder encoder;
    double count=0;
    boolean isHigh=false;
    double lowRatio;
    double highRatio;
    double currentRatio;
    double sharedRatio;


    gearBoxEncoder(CANSparkMax spark, double lowRatio, double highRatio){
        this.spark=spark;
        encoder=spark.getEncoder();
        count=encoder.getPosition();
        this.lowRatio=lowRatio;
        this.highRatio=highRatio;
    }

    gearBoxEncoder(CANSparkMax spark, double lowRatio, double highRatio, double sharedRatio){
        this.spark=spark;
        encoder=spark.getEncoder();
        count=encoder.getPosition();
        this.lowRatio=lowRatio;
        this.highRatio=highRatio;
        encoder.setPositionConversionFactor(sharedRatio);
    }




    @Override
    public void periodic(){
        count+=encoder.getPosition()*currentRatio;
        encoder.setPosition(0);
    }

    public void shift(boolean isGoingHigh){
        isHigh=isGoingHigh;
        if (isHigh){
            this.currentRatio=highRatio;
        }
        else{
            this.currentRatio=lowRatio;
        }

    }

    public double getPosition(){
        return count;
    }

    public boolean getIsHigh(){
        return isHigh;
    }

    public void resetEncoderPosition(){
        encoder.setPosition(0);
        count=0;
    }
    


}
