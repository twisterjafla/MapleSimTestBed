package frc.robot.subsystems.blinkin;

public interface blinkinInterface {

    enum color{
        white(0.93, "white"),
        blue(0.85,"blue"),
        orange(0.65, "orange"),
        green(0.77, "green");

        String name;
        double id;
        color(double id, String name){
            this.id=id;
            this.name=name;
        }

        color(){
            id=0.93;
            name="default";
        }
    }

    public void setColor(color color);
    public color getColor();
}
