package frc.robot.semiAutoCode;



//linked list object 
public class diffObj {
    int startTime;
    diffObj leadIn=null;
    diffObj leadOut;
    boolean isFirst=false;
    
    double XChange=0;
    double YChange=0;

    public diffObj(int time, diffObj leadOut){
        startTime=time;
        if (leadOut!=null){
            this.leadOut=null;
        }
        else{
            leadOut.leadIn=this;
        }
        
        
    }

    public void passItOn(double XChangeChange, double YChangeChange){
        XChange+=XChangeChange;
        YChange+=YChangeChange;
        if (leadOut!=null){
            leadOut.passItOn(XChangeChange, YChangeChange);
        }
    }

    public diffObj findTheDiffObj(int time){
        if (startTime-time<2){
            return this;
        }
        if (leadOut==null){
            return null;
        }
        return leadOut.findTheDiffObj(time);
    }

    public void removeDiffObj(diffObj objToDie){
        if (this==objToDie){
            if (leadIn!=null){
                leadIn.leadOut=leadOut;
            }
            
            if (leadOut!=null){
                leadOut.leadIn=leadIn;
            }
        }
        if (leadOut!=null){
            leadOut.removeDiffObj(objToDie);
        }
    }

    // public void makeNewDiffObj(int time){
    //     if (leadOut==null){
    //         this.leadOut=new diffObj(time, this);
    //     }
    //     else{
    //         leadOut.makeNewDiffObj(time);
    //     }
    // }

}
