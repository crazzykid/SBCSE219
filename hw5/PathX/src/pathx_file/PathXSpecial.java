/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx_file;

import java.util.GregorianCalendar;
import pathX_ui.PathXCarState;
import static pathx.PathXConstants.MILLIS_IN_AN_HOUR;
import static pathx.PathXConstants.MILLIS_IN_A_MINUTE;
import static pathx.PathXConstants.MILLIS_IN_A_SECOND;
import static pathx.PathXConstants.*;
/**
 *
 * @author Crazzykid
 */
public class PathXSpecial {
    
    private int x;
    private int y;
    private PathXCarState name;
     private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private String imageLocation;
    private String id;
    public PathXSpecial(int x, int y, PathXCarState type)
    {
        
        this.x = x;
        this.y = y;
        id  = ""+x+y;
        name = type;
        if(type.MAKE_LIGHT_GREEN ==type)
        {
         imageLocation =MAKE_LIGHT_GREEN;   
        }
        else
            if(type.FLAT_TIRE ==type)
        {
         imageLocation =FLAT_TIRE;   
        }
         else
            if(type.CLOSE_ROAD ==type)
        {
         imageLocation =CLOSE_ROAD;   
        }
          else
            if(type.EMPTY_GAS_TANK ==type)
        {
         imageLocation =EMPTY_GAS_TANK;   
        }
          else
            if(type.MAKE_LIGHT_RED ==type)
        {
         imageLocation =MAKE_LIGHT_RED;   
        }
           else
            if(type.MINDLESS_TERROR ==type)
        {
         imageLocation =MINDLESS_TERROR;   
        }
           else
            if(type.MIND_CONTROL ==type)
        {
         imageLocation =MIND_CONTROL;   
        }
          else
            if(type.STEAL ==type)
        {
         imageLocation =STEAL;   
        }
          else
            if(type.INVINCIBILITY ==type)
        {
         imageLocation =INVINCIBILITY;   
        }
        
        
                
        
    }
    
    public String getId()   { return id;}
    public String getImageLocation()
    {
        return imageLocation;
    }
       // MUTATOR METHODS
    public void setX(int x)
    {   this.x = x;         }
    public void setY(int y)
    {   this.y = y;         }
    
    public int getX()   { return x;}
      public int getY()   { return y;}
    public PathXCarState getType() { return name;}
    
     public String timeToText(long timeInMillis)
    {
        // FIRST CALCULATE THE NUMBER OF HOURS,
        // SECONDS, AND MINUTES
        long hours = timeInMillis / MILLIS_IN_AN_HOUR;
        timeInMillis -= hours * MILLIS_IN_AN_HOUR;
        long minutes = timeInMillis / MILLIS_IN_A_MINUTE;
        timeInMillis -= minutes * MILLIS_IN_A_MINUTE;
        long seconds = timeInMillis / MILLIS_IN_A_SECOND;

        // THEN ADD THE TIME OF GAME SUMMARIZED IN PARENTHESES
        String minutesText = "" + minutes;
        if (minutes < 10)
        {
            minutesText = "0" + minutesText;
        }
        String secondsText = "" + seconds;
        if (seconds < 10)
        {
            secondsText = "0" + secondsText;
        }
        // TTT= hours + ":" + minutesText + ":" + secondsText;
        return hours + ":" + minutesText + ":" + secondsText;
    }

      public String gameTimeToText()
    {
        // CALCULATE GAME TIME USING HOURS : MINUTES : SECONDS
        if ((startTime == null) || (endTime == null))
        {
            return "";
        }
        long timeInMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        return timeToText(timeInMillis);
    }
    
      public long getTimeLong() 
      {
          return endTime.getTimeInMillis() - startTime.getTimeInMillis();
      }
      
      
      public void startTimer()
      {
          startTime = new GregorianCalendar();
               
      }
      
        public void endTimer()
      {
          endTime = new GregorianCalendar();
               
      }
      
}
