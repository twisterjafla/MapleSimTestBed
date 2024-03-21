
package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Blinkin{


   
  private static Spark m_blinkin;
  private static boolean hasBeenInitalized = false;

  /**
   * Creates a new Blinkin LED controller.
   * 
   */
  public static void BlinkinInit() {
    m_blinkin = new Spark(Constants.blinkinPort);
    hasBeenInitalized=true;
  }

  /*
   * Set the color and blink pattern of the LED strip.
   * 
   * Consult the Rev Robotics Blinkin manual Table 5 for a mapping of values to patterns.
   * 
   * @param val The LED blink color and patern value [-1,1]
   * 
   */ 

  public static void setRed() {
    checkIfInit();
    m_blinkin.set(0.61);
  }

  public static void setGreen() {
    checkIfInit();
    m_blinkin.set(0.71);
  }
  public static void setYellow() {
    checkIfInit();
    m_blinkin.set(0.69);
  }

  private static void checkIfInit(){
    if (!hasBeenInitalized){
      BlinkinInit();
    }
  }

  
  

}