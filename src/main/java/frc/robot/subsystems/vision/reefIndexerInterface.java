package frc.robot.subsystems.vision;

public interface reefIndexerInterface {
    public boolean[][] getFullReefState();
    public boolean getIsClosed(int row, int level);
    public int getHighestLevelForRow(int row);
    public int getPointValForItem(int level);
    public boolean hasAlgea(int row, int level);
    public boolean[][] getAlgeaPosits();
    
}
