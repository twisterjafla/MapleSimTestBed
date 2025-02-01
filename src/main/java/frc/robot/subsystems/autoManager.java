package frc.robot.subsystems;

import java.io.FileReader;
import java.io.IOException;
import java.util.function.BooleanSupplier;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opencv.core.Point;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.FieldPosits;
import frc.robot.SystemManager;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.utillFunctions;
import frc.robot.commands.auto.IntakePeiceCommand;
import frc.robot.commands.auto.ScorePiece;

public class autoManager{

    public static boolean hasControl=false;
    public static BooleanSupplier hasControllSupplier=null;
    public static Command currentRoutine=null;
    public static LocalADStar pathBuilder = new LocalADStar();
    public static Node[][] map;
    public static JSONObject jsonMap ;
    protected static double tileSize;
    protected static double width;
    protected static double length;
    public static void autoManagerInit() {
        try{
            jsonMap = (JSONObject) new JSONParser().parse(new FileReader(Filesystem.getDeployDirectory()+ "/pathplanner/navgridAStar.json"));
        }
        catch (IOException e){
        }
        catch (ParseException e){
        }

        //lenArray = (JSONArray) jsonMap.get("field_size");
        width= (double)jsonMap.get("width");
        length=(double)jsonMap.get("length");
        tileSize=(double)jsonMap.get("nodeSizeMeters"); 

    }

    public static void refreshMap(){
        map = new Node[(int)Math.ceil(length/tileSize)][(int)Math.ceil(width/tileSize)];

        JSONArray grid = (JSONArray)jsonMap.get("grid");
        for (int i=0; i<map.length; i++){
            System.out.println(grid.size());
            JSONArray row = (JSONArray)grid.get(i);
            for (int j=0; j<map[i].length; j++){
                System.out.println(row.size());

                if ((boolean)row.get(j)){
                    map[i][j]=new Node(i, j, map, false, Node.defaultValue);
                }
                else{
                    map[i][j]=new Node(i, j, map, true, Node.defaultValue);
                } 
            }
        }
    }
 
    public static void periodic(){
        if (currentRoutine!=null){
            if (CommandScheduler.getInstance().isScheduled(currentRoutine)){
                currentRoutine=null;
            }
        }
        if (hasControllSupplier!=null){
            if (hasControllSupplier.getAsBoolean()!=hasControl){
                if (hasControllSupplier.getAsBoolean()==false){
                    takeControl();
                }
                else{
                    giveControl();
                }
            }
        }
        if (hasControl){
            if (currentRoutine==null){
                currentRoutine=getAutoAction();
                currentRoutine.schedule();
            }
        }
    }

    public static void swapControl(boolean isGift){
        if (isGift){
            giveControl();
        }
        else{
            takeControl();
        }
    }

    public static void giveControl(){
        hasControl=true;
    }

    public static void takeControl(){
        hasControl=false;
        if (currentRoutine!=null){
            currentRoutine.cancel();
        }
    }

    public static void setControlBooleanSuplier(BooleanSupplier supplier){
        hasControllSupplier=supplier;
    }


    public static void reMakeMap(Pose2d startPoint){
        refreshMap();
        
        Node start = getMapPoint(startPoint);
        start.popFreinds();


    }

    public static Node getMapPoint(Pose2d pose){
        Pair<Integer, Integer> posit = poseToMap(pose);
        if (posit==null){
            return null;
        }
        return map[posit.getFirst()][posit.getSecond()];
    }

 

    public static Pair<Integer, Integer> poseToMap(Pose2d pose){
        if (pose.getX()>width || pose.getY()>length){
            return null;
        }
        return new Pair<Integer, Integer>((int)Math.round(pose.getX()/tileSize), (int)Math.round(pose.getY()/tileSize));
    }

    public static Command getAutoAction(){
        if (SystemManager.intake.hasPeice()){
            return new ScorePiece(getBestScorePosit());
        }
        else{
            return new IntakePeiceCommand(getBestIntakePosit());
        }

    }

    public static void resetAutoAction(){
        getAutoAction().schedule();
    }

    public static scoringPosit getBestScorePosit(){
        scoringPosit winningPole=null;
        double winningScore=10000;
        for(reefPole pole:FieldPosits.scoringPosits.scoringPoles){
            scoringPosit currentPosit = new scoringPosit(reefLevel.CreateFromLevel(SystemManager.reefIndexer.getHighestLevelForRow(pole.getRowAsIndex())), pole);
            if (currentPosit.getPointValForItem()/getMapPoint(pole.getScorePosit()).score*2+Constants.AutonConstants.bonusScore<winningScore){
                winningPole=currentPosit;
                winningScore=getMapPoint(pole.getScorePosit()).score;
            }
        }
        if (winningPole==null){
            throw new Error("Auto manager was not able to find a path to any reef pole");
        }
        return winningPole;



        // reefPole winningPose=null;
        // double winningScore=180;
        // for (reefPole posit: FieldPosits.scoringPosits.scoringPoles){
        //     pathBuilder.setGoalPosition(posit.getScorePosit().getTranslation());
        //     PathPlannerTrajectory newPath = pathBuilder.getCurrentPath(SystemManager.swerve.constraints, new GoalEndState(0, posit.getScorePosit().getRotation())).generateTrajectory(
        //         SystemManager.swerve.getRobotVelocity(),
        //         SystemManager.swerve.getPitch(),
        //         SystemManager.swerve.config);


        //     if (scoringPosit.getPointValForItem(SystemManager.reefIndexer.getHighestLevelForRow(posit.getRowAsIndex()))/newPath.getTotalTimeSeconds()>winningScore){
        //         winningScore=scoringPosit.getPointValForItem(SystemManager.reefIndexer.getHighestLevelForRow(posit.getRowAsIndex()))/newPath.getTotalTimeSeconds();
        //         winningPose = posit;
        //     }
        // }


        // return new scoringPosit(reefLevel.CreateFromLevel(SystemManager.reefIndexer.getHighestLevelForRow(winningPose.getRowAsIndex())),winningPose);
    }
    public static Pose2d getBestIntakePosit(){
        // double bestTime=180;
        // Pose2d bestPose=null;
        // for (Pose2d pose: FieldPosits.coralSpawnPoints.coralSpawnPoints){
        //     pathBuilder.setGoalPosition(pose.getTranslation());
        //     PathPlannerTrajectory newPath = pathBuilder.getCurrentPath(SystemManager.swerve.constraints, new GoalEndState(utillFunctions.mpsToMph(Constants.AutonConstants.colisionSpeed), pose.getRotation())).generateTrajectory(
        //         SystemManager.swerve.getRobotVelocity(),
        //         SystemManager.swerve.getPitch(),
        //         SystemManager.swerve.config);

        //     if(newPath.getTotalTimeSeconds()<bestTime){
        //         bestPose=pose;
        //         bestTime=newPath.getTotalTimeSeconds();
        //     }
        // }

        // return bestPose;
        reMakeMap(SystemManager.getSwervePose());
        Pose2d bestPose = null;
        double bestScore=100000;

        for (Pose2d point: FieldPosits.coralSpawnPoints.coralSpawnPoints){
            if (getMapPoint(point).score<bestScore){
                bestPose=point;
                bestScore=getMapPoint(point).score;
            }
        }
        if (bestPose==null){
            throw new Error("Auto manager was not able to find a path to any intake station");
        }
        return bestPose;


    }
    
    public static class Node{
        public static double defaultValue=10000;
        double x, y;
        Node[][] host;
        boolean isLegal;
        double score;
        Node[] friends;
        boolean friendsPoped=false;

        public Node(double x, double y, Node[][] host){
            this(x,y,host,true, defaultValue);
        }

        public Node(double x, double y, Node[][] host, boolean isLegal, double score){
            this.x=x;
            this.y=y;
            this.host=host;
            this.isLegal=isLegal;
            this.score = score;
        }

        public void popFreinds(){
            int count=0;
            for (int i=-1; i<2; i++){
                for (int j=-1; i<2; i++){
                    if (i==0 && j==0){
                        continue;
                    }
                    if (i>=0 && i<map.length){
                        if (j>=0&& j<map[j].length){
                            friends[count]=map[i][j];
                            
                        }
                        else{
                            friends[count]=null;
                        }
                    }
                    else{
                        friends[count]=null;
                    }
                }
                count++;
            }
            friendsPoped=true;
            for (Node friend: friends){
                if (!friendsPoped){
                    friend.popFreinds();
                }
            }
        }

        public void update(){
            for (Node friend: friends){
                if (friend!=null&&friend.isLegal){
                    if (friend.score>score+getLength(this, friend)){
                        friend.score=score+getLength(this, friend);
                        friend.update();
                    }
                }   
            }
        }

        public static double getLength(Node nodeA, Node NodeB){
            return Math.sqrt(Math.pow(Math.abs(nodeA.x-NodeB.x), 2)+Math.pow(Math.abs(nodeA.y-NodeB.y), 2));
        }

        public void start(){
            score=0;
            update();
        }

    

    }


}
