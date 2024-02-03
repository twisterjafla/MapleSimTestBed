package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import java.util.function.BooleanSupplier;

public class limitSwitch extends SubsystemBase{

    private DigitalInput limit;
    BooleanSupplier valSupplier = () -> this.getVal();

    public limitSwitch(int index){
        limit = new DigitalInput(index);
    }

    public Boolean getVal(){
        return limit.get();
    }
    public boolean isOk(){
        //needs to be added later. should use the fact that the input pins will always have one active
        return true;
    }

    public void runWhenHit(Command runner){
        new SequentialCommandGroup(new WaitUntilCommand(valSupplier), runner);
    }

    public boolean getMultipleVals(limitSwitch... limitSwitches) {
        int counterTrues = 0;
        int overallCounter = 0;
        for (limitSwitch limitSwitch : limitSwitches) {
            overallCounter++;
            if (limitSwitch.getVal()){
                counterTrues++;
            }
        }

        if (counterTrues == overallCounter){
            return true;
        } 
        else {
            return false;
        }
    }

}
