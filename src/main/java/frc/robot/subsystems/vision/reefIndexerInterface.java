package frc.robot.subsystems.vision;

public interface reefIndexerInterface {
    public boolean[][] getFullReefState();
    public boolean getIsOpen(int row, int level);
    public int getHighestLevelForRow(int row);
    public int getPointValForItem(int row, int level);
    
}
