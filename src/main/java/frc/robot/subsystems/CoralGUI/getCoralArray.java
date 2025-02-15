package frc.robot.subsystems.CoralGUI;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class getCoralArray {
    
    private String[][] coralArray = new String[4][12];  // Array to hold the Coral data for 4 floors and 12 columns

    public getCoralArray() {
        // Initialize the network table instance
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        
        // Retrieve the network tables for each floor
        NetworkTable coralF1 = inst.getTable("GridData").getSubTable("CoralF1");
        NetworkTable coralF2 = inst.getTable("GridData").getSubTable("CoralF2");
        NetworkTable coralF3 = inst.getTable("GridData").getSubTable("CoralF3");
        NetworkTable coralF4 = inst.getTable("GridData").getSubTable("CoralF4");

        // Update the coralArray with data from the network tables
        for (int i = 0; i < 12; i++) {
            coralArray[3][i] = coralF4.getEntry(Integer.toString(i)).getString("0"); // Floor 4 (top)
            coralArray[2][i] = coralF3.getEntry(Integer.toString(i)).getString("0"); // Floor 3
            coralArray[1][i] = coralF2.getEntry(Integer.toString(i)).getString("0"); // Floor 2
            coralArray[0][i] = coralF1.getEntry(Integer.toString(i)).getString("0"); // Floor 1 (bottom)
        }
    }

    // Function to check if a specific floor and column contains a 1
    public boolean checkCoralValue(int floor, int column) {
        if (floor < 1 || floor > 4 || column < 1 || column > 12) {
            throw new IllegalArgumentException("Floor must be between 1 and 4, Column must be between 1 and 12.");
        }
        // Convert floor to array index (floor 1 = index 0, floor 4 = index 3)
        int floorIndex = 4 - floor;
        // Check if the value is 1
        return coralArray[floorIndex][column - 1].equals("1");
    }
}
