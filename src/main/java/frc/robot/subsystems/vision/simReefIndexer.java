package frc.robot.subsystems.vision;

import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeReefSimulation;

import edu.wpi.first.wpilibj.DriverStation;

public class simReefIndexer implements reefIndexerInterface{


    @Override
    public boolean[][] getFullReefState() {
        int[][]base = ReefscapeReefSimulation.getInstance().get().getBranches(DriverStation.getAlliance().get());
        boolean[][]returnable = new boolean[12][4];
        int i = 0;
        
        for(int[] branch : base){
            int j = 0;
            for(int item: branch){
                returnable[i][j] = item>0;
                j++;
            }
            i++;
        }
        return returnable;
    }

    @Override
    public boolean getIsClosed(int row, int level) {
        return ReefscapeReefSimulation.getInstance().get().getBranches(DriverStation.getAlliance().get())[row][level]>0;
    }

    @Override
    public int getHighestLevelForRow(int row) {
        if (!getIsClosed(row, 4)){
            return 4;
        }
        if (!getIsClosed(row, 3)){
            return 3;
        }
        if (!getIsClosed(row, 2)){
            return 2;
        }
        return 1;
    }

 


    @Override
    public boolean hasAlgea(int row, int level){
        return false;
    }

    @Override 
    public boolean[][] getAlgeaPosits(){
        return new boolean[2][6];
    }
    
}
