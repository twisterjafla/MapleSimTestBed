package frc.robot;


import java.util.Currency;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ControlChooser {
    Map<String, Consumer<ControlChooser>> schemes= new HashMap<>();
    SendableChooser<Consumer<ControlChooser>> chooser =new SendableChooser<Consumer<ControlChooser>>();
    Consumer<ControlChooser>current =null;
    CommandXboxController xbox1;
    CommandXboxController xbox2;

    ControlChooser(){
        

        for (Map.Entry<String, Consumer<ControlChooser>> scheme: schemes.entrySet()){
            chooser.addOption(scheme.getKey(), scheme.getValue());
        }
    }

    public void update(){
        if (current!=chooser.getSelected()){
            refreshControllers();
            current=chooser.getSelected();
            current.accept(this);
        }
    }
    public void refreshControllers(){
        xbox1=new CommandXboxController(Constants.controllerID.commandXboxController1ID);
        xbox2=new CommandXboxController(Constants.controllerID.commandXboxController2ID);
    }

}

