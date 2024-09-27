package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveControllerConfiguration;
import swervelib.parser.SwerveDriveConfiguration;

public class FakeBot extends SwerveDrive{

    public FieldObject2d renderer;
    public FakeBot(SwerveDriveConfiguration config, SwerveControllerConfiguration controllerConfig, double maxSpeedMPS) {
        super(config, controllerConfig, maxSpeedMPS);
        SmartDashboard.putData("bot chara map", this.field);
        this.field=null;
    }


    @Override
    public void updateOdometry(){
        super.updateOdometry();
        renderer.setPose(this.getPose());

    }
    
}
