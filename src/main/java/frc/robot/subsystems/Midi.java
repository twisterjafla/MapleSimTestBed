package frc.robot.subsystems;

import java.util.Dictionary;
import java.util.Hashtable;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Midi {
    // Get the default table
    NetworkTable table = NetworkTableInstance.getDefault().getTable("datatable");

    Dictionary<String, Integer> dict = new Hashtable<>();

    // Retrieve the entries from the table
    NetworkTableEntry entry1 = table.getEntry("key1");
    NetworkTableEntry entry2 = table.getEntry("key2");

    // Get the values of the entries as an Object
    Object value1 = entry1.getValue();
    Object value2 = entry2.getValue();

    // Cast the values to their respective types
    //dict.put();

}
