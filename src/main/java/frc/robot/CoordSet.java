package frc.robot;

import frc.robot.subsystems.Coords;

public class CoordSet {
    Coords[100] list;
    int currentID=0;


    public CoordSet(){}

    public addNewCoord(Coord tooAdd){
        currentID++;
        if (currentID==100){
            currentID==0;
        }
        list[currentID]=tooAdd;

    }

} 
