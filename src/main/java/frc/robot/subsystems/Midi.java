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
    NetworkTable table = NetworkTableInstance.getDefault().getTable("datatable");


    
    Dictionary<String, Double> values = new Hashtable<>();
    Dictionary<String, MidiButton> buttons = new Hashtable<>();

    // Retrieve the entries from the table
    NetworkTableEntry entry1 = table.getEntry("key1");
    NetworkTableEntry entry2 = table.getEntry("key2");

    // Get the values of the entries as an Object
    Object value1 = entry1.getValue();
    Object value2 = entry2.getValue();

    // Cast the values to their respective types
    //dict.put();

    public void InitButtons(){
        for (String name: Constants.Midi.buttonNames) {
            buttons.put(name, new MidiButton(name, this));
            values.put(name, 0.0);
        }
    }

    public void initalizeMidiControll(){
        
        buttons.get("button1").buttonTrigger.onFalse(null);
    }
    
   


    public double getValFromDict(String key){
        return values.get(key);
    }

}
