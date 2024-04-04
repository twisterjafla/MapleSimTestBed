package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public class MidiButton {

    public String key;
    public Midi parentBoard;
    public Trigger buttonTrigger;
    public Trigger diffTrigger;
    public double TrueVal=127;
    public boolean diffIsGreater=true;
    public double Offset=0;
    

    public MidiButton(String key, Midi parentBoard){
        this.key=key;
        this.parentBoard=parentBoard;
        buttonTrigger = new Trigger(this::getIsTrue);
        diffTrigger = new Trigger(this::isDiff);
    }
    public void setTrueValue(double toSet){
        TrueVal=toSet;
    }

    public void setDiffIsGreater(boolean diffIsGreater){
        this.diffIsGreater=diffIsGreater;
    }

    public double getValue(){
        return parentBoard.getValFromDict(key);
    }

    public double getValAsOneToNegOne(){
        return ((getValue()/64)-1);
    }

    public void setOffset(double Offset){
        this.Offset=Offset;
    }

    public boolean getIsTrue(){
        return getValue()==TrueVal;
    }

    public boolean isDiff(){
        if (diffIsGreater){
            return getValue()>TrueVal;
        }
        else{
            return getValue()<TrueVal;
        }
    }

    

    
}
