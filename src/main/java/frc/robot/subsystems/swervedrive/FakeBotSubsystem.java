package frc.robot.subsystems.swervedrive;

import java.io.File;

import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class FakeBotSubsystem extends SwerveSubsystem{
    public final FakeBot swerveDrive;
    public FakeBotSubsystem(File directory){
        super(directory);
        // SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
        try
        {
            swerveDrive = (FakeBot) new FakeBotParser(directory).createSwerveDrive(Constants.MAX_SPEED);
            // Alternative method if you don't want to supply the conversion factor via JSON files.
            // swerveDrive = new SwerveParser(directory).createSwerveDrive(maximumSpeed, angleConversionFactor, driveConversionFactor);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        
        // swerveDrive.setHeadingCorrection(false); // Heading correction should only be used while controlling the robot via angle.
        // swerveDrive.setCosineCompensator(false);//!SwerveDriveTelemetry.isSimulation); // Disables cosine compensation for simulations since it causes discrepancies not seen in real life.
        // setupPathPlanner();
        // constraints = new PathConstraints(
        // swerveDrive.getMaximumVelocity(), 4.0,
        // swerveDrive.getMaximumAngularVelocity(), Units.degreesToRadians(720));
        
    }
}

