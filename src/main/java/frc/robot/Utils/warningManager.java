package frc.robot.Utils;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

public class warningManager {
    public static Alert badGeneralRoutine = new Alert("Warning, a state was sheduled in generalManager that did not use the correct callback. generalManager caught this issue and resolved it but strange bugs may still occur", AlertType.kError);
    public static Alert autoInternalCancled = new Alert("an Autos state request was cancled early without the autos permision", AlertType.kError);
    public static Alert generalRoutineCalledManualy= new Alert("Warning, a state was sheduled manualy as apposed to going through general manager. this state has been cancled", AlertType.kError);
    public static Alert noWristElevatorManagerSet = new Alert("Warning, the wrist or elevator was missing its manager when it tried to run periodic", AlertType.kError);
    public static void throwAlert(Alert alert){
        alert.set(true);
    }
    public static void freeAlert(Alert alert){
        alert.set(false);
    }
    


}
