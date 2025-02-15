package frc.robot.subsystems.AlgaeRemover;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class simAlgaeRemover extends SubsystemBase implements algaeRemoverInterface {
    boolean isRunning=false;
    Timer runningTimer;
    double RemoveTime=0.5;
    
    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void startRunning() {
        runningTimer.reset();
        runningTimer.start();
        
    }

    @Override
    public void stopRunning() {
        isRunning=false;
    }

    @Override
    public void periodic(){
        if (isRunning&&runningTimer.get()>RemoveTime){
            runningTimer.stop();
            isRunning=false;
        }
    }
    
}
