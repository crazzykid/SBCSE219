/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx_data;
import static pathx.PathXConstants.*;
/**
 *
 * @author Crazzykid
 */
public class PathXGameLevel 
{
      private boolean completeLevel;
      private String levelState;
      private String levelName;
      private int bankBalance;
      private int levelTotal;
      private boolean stageUnlock;
      
   public PathXGameLevel()
    {
        completeLevel = false;
        levelState = WHITE_STATE;
        stageUnlock = false;
        
        levelName = "";
        bankBalance = 0;
        levelTotal = 0;
      
        
    }
   public boolean getStageUnlock()
    {
        return stageUnlock;
    }
    public boolean getCompletedLevel()
    {
        return completeLevel;
    }
    
    public String getLevelState ()
    {
        return levelState;
    }
    public String getLevelName()
    {
        return levelName;
        
    }
    public int getBankBalance ()
    {
        return bankBalance;
    }
    
    public int getLevelTotal()
    {
    return levelTotal;
    }
    
    
    public void setCompletedLevel(boolean state)
    {
         completeLevel = state;
    }
    public void setStageUnlock(boolean state)
    {
         stageUnlock = state;
    }
    
    public void setLevelState (String level)
    {
         levelState = level;
    }
    public void setLevelName( String name)
    {
      levelName = name;
        
    }
    public void setBankBalance ( int money)
    {
        bankBalance = money;
    }
    
    public void setLevelTotal(int money)
    {
         levelTotal = money;
    }
}
