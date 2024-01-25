package frc.robot;

import frc.robot.subsystems.Coords;

public class CoordSet {

    int currentID=0;
    Coords Coordlist[] = new Coords[4];


    public CoordSet(){}

    public void addNewCoord(Coords tooAdd){
        currentID++;
        if (currentID==100){
            currentID=0;
        }
        Coordlist[currentID]=tooAdd;
    }
    public Coords GetCurrent(){
        return Coordlist[currentID];
    }



} 
