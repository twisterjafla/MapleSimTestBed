package frc.robot.subsystems.vision;

import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeReefSimulation;

public class simReefIndexer implements reefIndexerInterface{

    public boolean[][] reefState = new boolean[12][4]

    @Override
    public boolean[][] getFullReefState() {
        ReefscapeReefSimulation.getInstance();
    }

    @Override
    public boolean getIsOpen(int row, int level) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIsOpen'");
    }

    @Override
    public int getHighestLevelForRow(int row) {
        if (getIsOpen(row, 4)){
            return 4;
        }
        if (getIsOpen(row, 3)){
            return 3;
        }
        if (getIsOpen(row, 2)){
            return 2;
        }
        return 1;
    }

    @Override
    public int getPointValForItem(int row, int level) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPointValForItem'");
    }
    
}
