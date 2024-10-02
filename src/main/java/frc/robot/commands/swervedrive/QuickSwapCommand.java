package frc.robot.commands.swervedrive;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class QuickSwapCommand extends Command{
    boolean current=false;
    BooleanSupplier supplier;
    Command trueCommand;
    Command falseCommand;
    Command selected;
    
    QuickSwapCommand(Command trueCommand, Command falseCommand, BooleanSupplier supplier){
        this.supplier=supplier;
        this.trueCommand=trueCommand;
        this.falseCommand=falseCommand;
    }

    @Override
    public void initialize(){
        this.current=supplier.getAsBoolean();
        if (current){
            this.selected=trueCommand;
        }
        else{
            this.selected=falseCommand;
        }
        this.selected.initialize();
    }

    @Override
    public void execute(){
        if (supplier.getAsBoolean()!=current){
            if (supplier.getAsBoolean()){
                selected=trueCommand;
            }
            else{
                selected=falseCommand;
            }
            selected.initialize();
        }
        selected.execute();
    }
}
