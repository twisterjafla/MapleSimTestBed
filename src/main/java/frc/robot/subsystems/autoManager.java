package frc.robot.subsystems;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
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
    public static Command currentRoutine=null;
    public static LocalADStar pathBuilder = new LocalADStar();
    public static Node[][] map;
    public static JSONObject jsonMap ;
    protected static double tileSize;
    protected static JSONArray lenArray;

    public static void autoManagerInit() {
        try{
            jsonMap = (JSONObject) new JSONParser().parse(new FileReader(Filesystem.getDeployDirectory()+ "/pathplanner/navgrid.json"));
        }
        catch (IOException e){
        }
        catch (ParseException e){
        }

        //JSONArray lenArray = jsonMap.getArray("field_size");
        lenArray = (JSONArray) jsonMap.get("field_size");
        tileSize=(double)jsonMap.get("nodeSizeMeters"); 
        map = new Node[(int)Math.ceil((Double)lenArray.get(0)/tileSize)][(int)Math.ceil((double)lenArray.get(1)/tileSize)];

    }

    public static void refreshMap(){
        JSONArray grid = (JSONArray)jsonMap.get("grid");
        for (int i=0; i<grid.size(); i++){
            JSONArray row = (JSONArray)grid.get(i);
            for (int j=0; j<row.size(); j++){
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
        if (pose.getX()>(double)lenArray.get(0) || pose.getY()>(double)lenArray.get(1)){
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
        reefPole winningPose=null;
        double winningScore=180;
        for (reefPole posit: FieldPosits.scoringPosits.scoringPoles){
            pathBuilder.setGoalPosition(posit.getScorePosit().getTranslation());
            PathPlannerTrajectory newPath = pathBuilder.getCurrentPath(SystemManager.swerve.constraints, new GoalEndState(0, posit.getScorePosit().getRotation())).generateTrajectory(
                SystemManager.swerve.getRobotVelocity(),
                SystemManager.swerve.getPitch(),
                SystemManager.swerve.config);


            if (scoringPosit.getPointValForItem(SystemManager.reefIndexer.getHighestLevelForRow(posit.getRowAsIndex()))/newPath.getTotalTimeSeconds()>winningScore){
                winningScore=scoringPosit.getPointValForItem(SystemManager.reefIndexer.getHighestLevelForRow(posit.getRowAsIndex()))/newPath.getTotalTimeSeconds();
                winningPose = posit;
            }
        }


        return new scoringPosit(reefLevel.CreateFromLevel(SystemManager.reefIndexer.getHighestLevelForRow(winningPose.getRowAsIndex())),winningPose);
    }
    public static Pose2d getBestIntakePosit(){
        double bestTime=180;
        Pose2d bestPose=null;
        for (Pose2d pose: FieldPosits.coralSpawnPoints.coralSpawnPoints){
            pathBuilder.setGoalPosition(pose.getTranslation());
            PathPlannerTrajectory newPath = pathBuilder.getCurrentPath(SystemManager.swerve.constraints, new GoalEndState(utillFunctions.mpsToMph(Constants.AutonConstants.colisionSpeed), pose.getRotation())).generateTrajectory(
                SystemManager.swerve.getRobotVelocity(),
                SystemManager.swerve.getPitch(),
                SystemManager.swerve.config);

            if(newPath.getTotalTimeSeconds()<bestTime){
                bestPose=pose;
                bestTime=newPath.getTotalTimeSeconds();
            }
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
