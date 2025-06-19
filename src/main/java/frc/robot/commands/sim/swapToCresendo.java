package frc.robot.commands.sim;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.crescendo2024.Arena2024Crescendo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.SystemManager;

public class swapToCresendo extends InstantCommand {
    @Override
    public void execute(){
        SimulatedArena.getInstance().shutDown();
        SimulatedArena.overrideInstance(new Arena2024Crescendo());
        SimulatedArena.getInstance().addDriveTrainSimulation(SystemManager.swerve.getMapleSimDrive().get());
        SimulatedArena.getInstance().resetFieldForAuto();
    }
}
