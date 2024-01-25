package frc.robot.semiAutoRoutines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class autoScoreAmp extends SequentialCommandGroup {
    
    public autoScoreAmp(){
        super(
            new WaitUntilLegitData(),

            new goToDummy(Constants.mapCoords.amp),

            new scoreAmpDummy()
        );
    }

}
