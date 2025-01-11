package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;

public class SimReef {
    Pose3d[][] targets = {
        {new Pose3d(4.75, 3.25, 0.45, new Rotation3d()), new Pose3d(4.75, 3.25, 0.8, new Rotation3d()), new Pose3d(4.75, 3.25, 1.2, new Rotation3d()), new Pose3d(4.75, 3.25, 1.825498, new Rotation3d())},
        {new Pose3d(5, 3.4, 0.45, new Rotation3d()), new Pose3d(5, 3.4, 0.8, new Rotation3d()), new Pose3d(5, 3.4, 1.2, new Rotation3d()), new Pose3d(4, 3.4, 1.825498, new Rotation3d())},
        {new Pose3d(5.3, 3.8, 0.45, new Rotation3d()), new Pose3d(5.3, 3.8, 0.8, new Rotation3d()), new Pose3d(5.3, 3.8, 1.2, new Rotation3d()), new Pose3d(5.3, 3.8, 1.825498, new Rotation3d())},
        {new Pose3d(5.3, 4.1, 0.45, new Rotation3d()), new Pose3d(5.3, 4.1, 0.8, new Rotation3d()), new Pose3d(5.3, 4.1, 1.2, new Rotation3d()), new Pose3d(5.3, 4.1, 1.825498, new Rotation3d())},
        {new Pose3d(5, 4.6, 0.45, new Rotation3d()), new Pose3d(5, 4.6, 0.8, new Rotation3d()), new Pose3d(5, 4.6, 1.2, new Rotation3d()), new Pose3d(4, 4.6, 1.825498, new Rotation3d())},
        {new Pose3d(4.75, 4.8, 0.45, new Rotation3d()), new Pose3d(4.75, 4.8, 0.8, new Rotation3d()), new Pose3d(4.75, 4.8, 1.2, new Rotation3d()), new Pose3d(4.75, 4.8, 1.825498, new Rotation3d())},
        {new Pose3d(4.2, 4.8, 0.45, new Rotation3d()), new Pose3d(4.2, 4.8, 0.8, new Rotation3d()), new Pose3d(4.2, 4.8, 1.2, new Rotation3d()), new Pose3d(4.2, 4.8, 1.825498, new Rotation3d())},
        {new Pose3d(4, 4.6, 0.45, new Rotation3d()), new Pose3d(4, 4.6, 0.8, new Rotation3d()), new Pose3d(4, 4.6, 1.2, new Rotation3d()), new Pose3d(4, 4.6, 1.825498, new Rotation3d())},
        {new Pose3d(3.6, 4.1, 0.45, new Rotation3d()), new Pose3d(3.6, 4.1, 0.8, new Rotation3d()), new Pose3d(3.6, 4.1, 1.2, new Rotation3d()), new Pose3d(3.6, 4.1, 1.825498, new Rotation3d())},
        {new Pose3d(3.6, 3.8, 0.45, new Rotation3d()), new Pose3d(3.6, 3.8, 0.8, new Rotation3d()), new Pose3d(3.6, 3.8, 1.2, new Rotation3d()), new Pose3d(3.6, 3.8, 1.825498, new Rotation3d())},
        {new Pose3d(4, 3.4, 0.45, new Rotation3d()), new Pose3d(4, 3.4, 0.8, new Rotation3d()), new Pose3d(4, 3.4, 1.2, new Rotation3d()), new Pose3d(4, 3.4, 1.825498, new Rotation3d())},
        {new Pose3d(4.2, 3.25, 0.45, new Rotation3d()), new Pose3d(4.2, 3.25, 0.8, new Rotation3d()), new Pose3d(4.2, 3.25, 1.2, new Rotation3d()), new Pose3d(4.2, 3.25, 1.825498, new Rotation3d())}

    };
    public SimReef(){

    }

    public Pose3d[][] getAll(){
        return targets;
    }

    public Pose3d[] getAllUnpacked(){
        Pose3d[] newlist = new Pose3d[48];
        int posit=0;
        for (Pose3d[] arr: targets){
            for (Pose3d item: arr){
                newlist[posit]=item;
                posit++;
            }
        }
        return newlist;
    }
}
