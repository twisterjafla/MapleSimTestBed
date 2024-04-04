package frc.robot.subsystems;

import java.util.Dictionary;
import java.util.Hashtable;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class Midi {
    // Get the default table
    NetworkTable table = NetworkTableInstance.getDefault().getTable("MidiTable");


    
    Dictionary<String, Double> values = new Hashtable<>();
    Dictionary<String, MidiButton> buttons = new Hashtable<>();



    // Cast the values to their respective types
    //dict.put();

    public void InitButtons(){
        for (String name: Constants.Midi.buttonNames) {
            buttons.put(name, new MidiButton(name, this));
            values.put(name, 0.0);
        }
    }

    public void readInputs(){
        for (String name: Constants.Midi.buttonNames){
            values.put(name, table.getEntry(name).getDouble(0));
        }
    }
    
   


    public double getValFromDict(String key){
        return values.get(key);
    }

    public MidiButton getButtonFromDict(String key){
        return buttons.get(key);
    }

}
