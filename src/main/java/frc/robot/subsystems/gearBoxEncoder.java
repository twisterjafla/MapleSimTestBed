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

    /** creates a gear box encoders with two encoder values that get swaped between
     * @param spark the spark max that the encoder is pulled from
     * @param lowRatio the ratio that gets used when the box is in its low state
     * @param highRatio the ratio that gets used when the box is in its high state
     */
    gearBoxEncoder(CANSparkMax spark, double lowRatio, double highRatio){
        this.spark=spark;
        encoder=spark.getEncoder();
        count=encoder.getPosition();
        this.lowRatio=lowRatio;
        this.highRatio=highRatio;
    }


    /** creates a gear box encoders with two encoder values that get swaped between, also inclues a shared ratio that is used in both the high and low state
     * @param spark the spark max that the encoder is pulled from
     * @param lowRatio the ratio that gets used when the box is in its low state
     * @param highRatio the ratio that gets used when the box is in its high state
     * @param sharedRatio a ratio that is aplied in both the low and high state, should be used for wheel circumference
     */
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
        count+=encoder.getPosition()/currentRatio;
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
