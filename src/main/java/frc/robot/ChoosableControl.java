package frc.robot;

import java.util.function.BiConsumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.XboxController;

public class ChoosableControl {
    BiConsumer init;
    BiConsumer execute;
    BiConsumer end;
    XboxController controller1;
    XboxController controller2;
    String name;
    ChoosableControl(
        BiConsumer<XboxController,XboxController> init,
        BiConsumer<XboxController,XboxController> execute,
        BiConsumer<XboxController,XboxController> end,
        String name){
        this.init=init;
        this.execute=execute;
        this.end=end;
        this.name=name;
    }
    void init(XboxController controller1, XboxController controller2){
        init.accept(controller1, controller2);
    }
    void execute(XboxController controller1, XboxController controller2){
        execute.accept(controller1, controller2);
    }
    void end(XboxController controller1, XboxController controller2){
        end.accept(controller1, controller2);
    }   
}
