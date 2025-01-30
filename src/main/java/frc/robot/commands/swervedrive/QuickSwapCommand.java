package frc.robot.commands.swervedrive;

import java.util.function.BooleanSupplier;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class QuickSwapCommand extends Command{
    boolean current=false;
    BooleanSupplier supplier;
    Command trueCommand;
    Command falseCommand;
    Command selected;
    
    public QuickSwapCommand(Command trueCommand, Command falseCommand, BooleanSupplier supplier){
        this.supplier=supplier;
        this.trueCommand=trueCommand;
        this.falseCommand=falseCommand;
        for (Subsystem subsystem: trueCommand.getRequirements()){
            addRequirements(subsystem);
        }
        for (Subsystem subsystem: falseCommand.getRequirements()){
            if (!this.getRequirements().contains(subsystem)){
                addRequirements(subsystem);
            }
        }

    }
    public QuickSwapCommand(Command trueCommand, Command falseCommand, BooleanSupplier supplier, Subsystem[] requirements){
        this.supplier=supplier;
        this.trueCommand=trueCommand;
        this.falseCommand=falseCommand;
        for (Subsystem subsystem: requirements){
            addRequirements(subsystem);
        }
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
            this.current=supplier.getAsBoolean();
            selected.initialize();
        }
        selected.execute();
    }

    @Override
    public boolean isFinished(){
        return selected.isFinished();
    }

    @Override
    public void end(boolean WasInteruped){
        trueCommand.end(WasInteruped);
        falseCommand.end(WasInteruped);
    }

     @Override
     public void initSendable(SendableBuilder builder) {
       super.initSendable(builder);
       builder.addStringProperty("onTrue", trueCommand::getName, null);
       builder.addStringProperty("onFalse", falseCommand::getName, null);
       builder.addStringProperty(
           "selected",
           () -> {
          if (selected == null) {
               return "null";
             } 
          else {
               return selected.getName();
             }
           },
           null);
        
   }
}
