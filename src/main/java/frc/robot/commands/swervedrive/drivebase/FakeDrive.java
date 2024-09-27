package frc.robot.commands.swervedrive.drivebase;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.swervedrive.FakeBot;

public class FakeDrive extends RunCommand{
    public FakeDrive(FakeBot bot, DoubleSupplier dxAdd, DoubleSupplier dyAdd, DoubleSupplier r){
        super(()->{
            bot.drive(dxAdd.getAsDouble(), dyAdd.getAsDouble(), r.getAsDouble());
        }, bot);
        addRequirements(bot);
    }
}
