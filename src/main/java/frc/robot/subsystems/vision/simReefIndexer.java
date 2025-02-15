package frc.robot.subsystems.vision;

import java.util.LinkedList;
import java.util.List;

import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeReefSimulation;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.FieldPosits;

public class simReefIndexer extends SubsystemBase implements reefIndexerInterface{
     int heartBeat=0;
     final boolean[][] algaeSorce={{true,false},{false,true},{true,false},{false,true},{true,false},{false,true}};;
     boolean[][] algae = algaeSorce;


        StructArrayPublisher<Pose3d> algeaPublisher = NetworkTableInstance.getDefault()
    .getStructArrayTopic("reefAlgae", Pose3d.struct).publish();

    public simReefIndexer(){
    }

    @Override
    public void periodic(){
        Pose3d[] algaeRender = new Pose3d[12];
        for (int i=0; i<6;i++){
            for (int j=0;j<2;j++){
                //System.out.println(algae[i][j]);
                if (algae[i][j]){
                    algaeRender[i*2+j] = FieldPosits.algaeRenderPosits[i][j];
                }
                else{
                    algaeRender[i*2+j] = new Pose3d();//FieldPosits.algaeRenderPosits[i][j];
                }
                
            }
        }
        algeaPublisher.set(algaeRender);
    }

    @Override
    public boolean[][] getFullReefState() {
        int[][]base = ReefscapeReefSimulation.getInstance().get().getBranches(DriverStation.Alliance.Blue);
        boolean[][]returnable = new boolean[12][4];
        int i = 0;
        
        for(int[] branch : base){
            int j = 0;
            for(int item: branch){
                returnable[i][j] = item!=0;
                j++;
            }
            i++;
        }
        i=0;
        for (int[] pole: base){
            SmartDashboard.putNumberArray(String.valueOf(i), new double[]{pole[0],pole[1],pole[2],pole[3]});
            i++;
        }
        heartBeat++;
        SmartDashboard.putNumber("reef heartbeat", heartBeat);
        
        //reefBranch.set(publishArr);
        return returnable;
    }

    @Override
    public boolean getIsClosed(int row, int level) {
    
        return getFullReefState()[row][level];
    }

    @Override
    public int getHighestLevelForRow(int row) {
        if (!getIsClosed(row, 3)){
            return 4;
        }
        if (!getIsClosed(row, 2)){
            return 3;
        }
        if (!getIsClosed(row, 1)){
            return 2;
        }
        return 1;
    }

    @Override
    public void resetSIMONLY(){
        ReefscapeReefSimulation.getInstance().get().clearReef();
    }

 


    @Override
    public boolean hasAlgea(int row, int level){
        return false;
    }

    @Override 
    public boolean[][] getAlgeaPosits(){
        return new boolean[2][6];
    }


    @Override 
    public boolean isOpenSmart(int row, int level){
        return !getIsClosed(row, level)&&!blockedByAlgae(row, level);
        
    }

    @Override
    public boolean blockedByAlgae(int row, int level){
        if (level==0||level==3){
            return false;
        }
        else if (level==1){
            return !hasAlgea((int)Math.floor(row/2), 0);
        }
        else{
            return !hasAlgea((int)Math.floor(row/2), 1)&&!hasAlgea((int)Math.floor(row/2), 0);
        }
    }

    @Override
    public void freeAlgea(int row, int level){
        algae[row][level]=false;
    }

    
}
