package frc.robot.semiAutoCode;



//linked list object 
public class diffObj {
    double startTime;

    diffObj leadOut;
    double XChange=0;
    double YChange=0;
    double Zchange=0;

    public diffObj(Double time, diffObj leadOut){
        startTime=time;
        if (leadOut!=null){
            this.leadOut=leadOut;
        }

        
    }

    public void passItOn(double XChangeChange, double YChangeChange, double ZchangeChange){
        XChange+=XChangeChange;
        YChange+=YChangeChange;
        Zchange+=ZchangeChange;
        if (leadOut!=null){
            leadOut.passItOn(XChangeChange, YChangeChange, ZchangeChange);
        }
    }

    public diffObj findTheDiffObj(Double time){
        if (Math.abs(startTime-time)<9){
            return this;
        }
        if (leadOut==null){
            return null;
        }
        return leadOut.findTheDiffObj(time);
    }

    
    public boolean shouldRemoveDiffObj(diffObj objToDie){
        if (this==objToDie){
            return true;
        }
        if (leadOut.shouldRemoveDiffObj(objToDie)){
            leadOut=null;
        }
        return false;
    }
        

}
