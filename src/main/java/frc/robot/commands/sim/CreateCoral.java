package frc.robot.commands.sim;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoral;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.FieldPosits;

public class CreateCoral extends InstantCommand{
    public Pose3d dropSpot;

    /**
     * 
     * @param posit one of the following; leftLeft, leftMid, leftRight, rightLeft, rightMid, rightRight
     * @throws Error if posit is not one of the above options
     */
    public CreateCoral(String posit){
        switch (posit){
            case "leftLeft":
                dropSpot=FieldPosits.coralSpawnPoints.leftLeft;
                break;
            case "leftMid":
                dropSpot=FieldPosits.coralSpawnPoints.leftMid;
                break;
            case "leftRight":
                dropSpot=FieldPosits.coralSpawnPoints.leftRight;
                break;
            case "rightLeft":
                dropSpot=FieldPosits.coralSpawnPoints.rightLeft;
                break;
            case "rightMid":
                dropSpot=FieldPosits.coralSpawnPoints.rightMid;
                break;
            case "rightRight":
                dropSpot=FieldPosits.coralSpawnPoints.rightRight;
                break;
            default:
                throw new Error("Tried to instantiate a coral creator with an invalid string " + posit);
        }
            

    }

    public CreateCoral(Pose3d posit){
        dropSpot=posit;
    }


    @Override
    public void execute(){
        SimulatedArena.getInstance().addGamePiece(new ReefscapeCoral(dropSpot.toPose2d()));
    }
}
