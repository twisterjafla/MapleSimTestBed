package frc.robot.subsystems;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    //public static boolean[][] legalityMap;
    public static JSONObject jsonMap ;
    protected static double tileSize;
    protected static double width;
    protected static double length;
    //static int resetCount=0;
    public static int cycleCount=0;
    public static int score=3;
    protected static StructPublisher<Pose2d> bestPosePublisher = NetworkTableInstance.getDefault().getStructTopic("bestPose", Pose2d.struct).publish();
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

        // legalityMap = new boolean[(int)Math.ceil(width/tileSize)][(int)Math.ceil(length/tileSize)];
        map = new Node[(int)Math.ceil(width/tileSize)][(int)Math.ceil(length/(tileSize
        ))];
        JSONArray grid = (JSONArray)jsonMap.get("grid");
        for (int i=0; i<map.length; i++){
            JSONArray row = (JSONArray)grid.get(i);
            for (int j=0; j<map[i].length; j++){
                map[i][j] = map[i][j]=new Node(i/tileSize, j/tileSize, map, !(boolean)row.get(j), Node.defaultValue);
                
                  
            }
        }

        map[0][0].popFreinds();
        
    }


 
    public static void periodic(){
        SmartDashboard.putNumber("Cycle count", cycleCount);
        SmartDashboard.putNumber("Score", score);
        SmartDashboard.putNumber("updateCount", Node.updateCount);

        if (currentRoutine!=null){
            if (!currentRoutine.isScheduled()){
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

        if (currentRoutine==null){
            SmartDashboard.putString("autoRoutine", "null");
            
        }
        else{
            SmartDashboard.putString("autoRoutine", currentRoutine.getName());
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


    public static void resetMap(Pose2d startingPose){
        resetMap();
        getMapPoint(startingPose).start();
    }

    public static void resetMap(){
        // resetCount++;
        // SmartDashboard.putNumber("reset count", resetCount);

        for (int i=0; i<map.length; i++){
            for (int j=0; j<map[i].length; j++){
                map[i][j].reset();
            }          
        }
    }

    public static Node getMapPoint(Pose2d pose){
        Pair<Integer, Integer> posit = poseToMap(pose);
        if (posit==null){
            return null;
        }
        return map[posit.getFirst()][posit.getSecond()];
    }

 

    public static Pair<Integer, Integer> poseToMap(Pose2d pose){
        if (pose.getY()>width || pose.getX()>length){
            return null;
        }
        return new Pair<Integer, Integer>((int)Math.round(pose.getY()/tileSize), (int)Math.round(pose.getX()/tileSize));
    }

    public static Command getAutoAction(){
        if (SystemManager.intake.hasPeice()){
            return new ScorePiece(getBestScorePosit());
        }
        else{
            return new IntakePeiceCommand(getBestIntakePosit());
        }

    }

    @Deprecated
    public static void resetAutoAction(){
        System.out.println("Auto reset");
        getAutoAction().schedule();
    }

    public static scoringPosit getBestScorePosit(){
        resetMap(SystemManager.getSwervePose());
        
        scoringPosit winningPole=null;
        double winningScore=0;
        for(reefPole pole:FieldPosits.scoringPosits.scoringPoles){
            scoringPosit currentPosit = new scoringPosit(reefLevel.CreateFromLevel(SystemManager.reefIndexer.getHighestLevelForRow(pole.getRowAsIndex())), pole);
            if (currentPosit.getPointValForItem()/(getMapPoint(currentPosit.getScorePose()).score*2+Constants.AutonConstants.bonusScore)>winningScore){
                winningPole=currentPosit;
                winningScore=currentPosit.getPointValForItem()/(getMapPoint(pole.getScorePosit()).score*2+Constants.AutonConstants.bonusScore);
            }
        }
        if (winningPole==null){
            throw new Error("Auto manager was not able to find a path to any reef pole");
        }
        bestPosePublisher.set(winningPole.getScorePose());
        // SmartDashboard.putNumber("winningPositScore", getMapPoint(winningPole.getScorePose()).score);
        // SmartDashboard.putBoolean("winningLegality", getMapPoint(winningPole.getScorePose()).isLegal);
        // SmartDashboard.putBoolean("winning posit had freinds", getMapPoint(winningPole.getScorePose()).friendsPoped);
        //SmartDashboard.putNumber("scoring level", winningPole.level.getasInt());
        //SmartDashboard.putNumber("highest open", SystemManager.reefIndexer.getHighestLevelForRow(winningPole.pole.getRowAsIndex()));
        //SmartDashboard.putBoolean("l4 is closed", SystemManager.reefIndexer.getIsClosed(winningPole.pole.getRowAsIndex(), 3));
        return winningPole;

    }
    public static Pose2d getBestIntakePosit(){

        resetMap(SystemManager.getSwervePose());
        Pose2d bestPose = null;
        double bestScore=100000;

        for (Pose2d point: FieldPosits.IntakePoints.coralSpawnPoints){
            if (getMapPoint(point).score<bestScore){
                bestPose=point;
                bestScore=getMapPoint(point).score;
            }
        }
        if (bestPose==null){
            throw new Error("Auto manager was not able to find a path to any intake station");
        }
        bestPosePublisher.set(bestPose);
        return bestPose;


    }
    
    public static class Node{
        public static double defaultValue=10000;
        public static int friendCount=0;
        public static int updateCount=0;
        public static List<Node> que = new LinkedList<Node>();

        double x, y;
        Node[][] host;
        boolean isLegal;
        double score;
        Node[] friends = new Node[8];
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
            friendCount++;
            SmartDashboard.putNumber("friendCount", friendCount);
            
            int count=0;
            for (int i=-1; i<2; i++){
                for (int j=-1; j<2; j++){
                    if (i==0 && j==0){
                        continue;
                    }
                    int a=(int)(x*tileSize)+i;
                    int b = (int)(y*tileSize)+j;
                    if (a>=0 && a<map.length){
                        if (b>=0&& b<map[1].length){
                            friends[count]=map[a][b];  
                        }

                        else{
                            friends[count]=null;
                        }
                    }

                    else{
                        friends[count]=null;
                    }

                    count++;
                }
               
            }
            
            
            friendsPoped=true;
            for (Node friend: friends){
                if (friend!=null){
                    if (!friend.friendsPoped){
                        friend.popFreinds();
                    }
                }
            }
        }

        public void update(){
            // updateCount++;
            // SmartDashboard.putNumber("update count", updateCount);


            // for (Node friend: friends){
            //     if (friend!=null&&friend.isLegal){
                    
            //         if (friend.score<score+getLength(this, friend)){
            //             score=friend.score+getLength(this, friend);
            //         }
                    
            //     }   
            // }

            updateCount++;
            
            
            for (Node friend: friends){
                if (friend!=null&&friend.isLegal){
                    
                    if (friend.score>score+getLength(this, friend)){
                        friend.score=this.score+getLength(this, friend);
                        if (!que.contains(friend)){
                            que.add(friend);
                        }
                    }
                    
                }   
            }
        }

        public static double getLength(Node nodeA, Node NodeB){
            return utillFunctions.pythagorean(nodeA.x, NodeB.x, nodeA.y, NodeB.y);
        }

        public void start(){
            score=0;
            updateCount=0;
            que.clear();
            que.add(this);
            manage();
        }

        public void reset(){
            score=defaultValue;
        }

        public static void manage(){
            
            while(que.size()!=0){
                
                que.remove(0).update();;
            }
        }

    

    }


}
