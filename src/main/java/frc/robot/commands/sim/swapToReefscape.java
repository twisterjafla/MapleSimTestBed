package frc.robot.commands.sim;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.reefscape2025.Arena2025Reefscape;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.SystemManager;

public class swapToReefscape extends InstantCommand{
    @Override
    public void execute(){
        SimulatedArena.getInstance().shutDown();

        SimulatedArena.overrideInstance(new Arena2025Reefscape());
        SimulatedArena.getInstance().addDriveTrainSimulation(SystemManager.swerve.getMapleSimDrive().get());
        SimulatedArena.getInstance().resetFieldForAuto();

    } 
}
