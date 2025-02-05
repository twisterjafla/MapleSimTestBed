package frc.robot.subsystems.swervedrive;


import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.SystemManager;

public class realSimulatedDriveTrain extends SwerveDriveSimulation{

    public realSimulatedDriveTrain(DriveTrainSimulationConfig config, Pose2d startingPose){
        super(config, startingPose);
    }

    //public realSimulatedDriveTrain(){}


    public void periodic(){
        this.setSimulationWorldPose(SystemManager.getSwervePose());
    }
}
