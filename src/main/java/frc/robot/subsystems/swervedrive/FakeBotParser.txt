package frc.robot.subsystems.swervedrive;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveDriveConfiguration;
import swervelib.parser.SwerveModuleConfiguration;
import swervelib.parser.SwerveParser;
import swervelib.parser.json.ModuleJson;

public class FakeBotParser extends SwerveParser{
    public FakeBotParser(File directory) throws IOException{
        super(directory);
    }


    @Override
    public SwerveDrive createSwerveDrive(SimpleMotorFeedforward driveFeedforward, double maxSpeed) {
      SwerveModuleConfiguration[] moduleConfigurations = new SwerveModuleConfiguration[moduleJsons.length];

      for(int i = 0; i < moduleConfigurations.length; ++i) {
         ModuleJson module = moduleJsons[i];
         moduleConfigurations[i] = module.createModuleConfiguration(pidfPropertiesJson.angle, pidfPropertiesJson.drive, physicalPropertiesJson.createPhysicalProperties(), swerveDriveJson.modules[i]);
      }

      SwerveDriveConfiguration swerveDriveConfiguration = new SwerveDriveConfiguration(moduleConfigurations, swerveDriveJson.imu.createIMU(), swerveDriveJson.invertedIMU, driveFeedforward, physicalPropertiesJson.createPhysicalProperties());
      return new FakeBot(swerveDriveConfiguration, controllerPropertiesJson.createControllerConfiguration(swerveDriveConfiguration, maxSpeed), maxSpeed);
   }
}
