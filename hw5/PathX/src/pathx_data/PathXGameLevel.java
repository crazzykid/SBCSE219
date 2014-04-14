/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx_data;

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
      public static int LEVEL_OFFSET_LOCATION_X = 50;
    public static int LEVEL_OFFSET_LOCATION_Y = 320;
       public static int LEVEL_OFFSET_LOCATION_X1 = 80;
    public static int LEVEL_OFFSET_LOCATION_Y1 = 470;
      public static int LEVEL_OFFSET_LOCATION_X2 = 72;
    public static int LEVEL_OFFSET_LOCATION_Y2 = 530;
      public static int LEVEL_OFFSET_LOCATION_X3 =300;
    public static int LEVEL_OFFSET_LOCATION_Y3 = 415;
      public static int LEVEL_OFFSET_LOCATION_X4 = 295;
    public static int LEVEL_OFFSET_LOCATION_Y4 = 500;
      public static int LEVEL_OFFSET_LOCATION_X5 = 305;
    public static int LEVEL_OFFSET_LOCATION_Y5 = 650;
      public static int LEVEL_OFFSET_LOCATION_X6 = 500;
    public static int LEVEL_OFFSET_LOCATION_Y6 = 570;
      public static int LEVEL_OFFSET_LOCATION_X7 = 580;
    public static int LEVEL_OFFSET_LOCATION_Y7 = 415;
      public static int LEVEL_OFFSET_LOCATION_X8 = 610;
    public static int LEVEL_OFFSET_LOCATION_Y8 = 650;
      public static int LEVEL_OFFSET_LOCATION_X9 = 605;
    public static int LEVEL_OFFSET_LOCATION_Y9 = 870;
      public static int LEVEL_OFFSET_LOCATION_X10 = 605;
    public static int LEVEL_OFFSET_LOCATION_Y10 = 500;
      public static int LEVEL_OFFSET_LOCATION_X11 = 705;
    public static int LEVEL_OFFSET_LOCATION_Y11 = 550;
      public static int LEVEL_OFFSET_LOCATION_X12 = 650;
    public static int LEVEL_OFFSET_LOCATION_Y12 = 770;
      public static int LEVEL_OFFSET_LOCATION_X13 = 700;
    public static int LEVEL_OFFSET_LOCATION_Y13 = 700;
      public static int LEVEL_OFFSET_LOCATION_X14 = 730;
    public static int LEVEL_OFFSET_LOCATION_Y14 = 740;
      public static int LEVEL_OFFSET_LOCATION_X15 = 760;
    public static int LEVEL_OFFSET_LOCATION_Y15 = 650;
      public static int LEVEL_OFFSET_LOCATION_X16 = 870;
    public static int LEVEL_OFFSET_LOCATION_Y16 = 670;
      public static int LEVEL_OFFSET_LOCATION_X17 = 850;
    public static int LEVEL_OFFSET_LOCATION_Y17 = 585;
      public static int LEVEL_OFFSET_LOCATION_X18 = 900;
    public static int LEVEL_OFFSET_LOCATION_Y18 = 830;
      public static int LEVEL_OFFSET_LOCATION_X19 = 20;
    public static int LEVEL_OFFSET_LOCATION_Y19 = 120;
      public static int LEVEL_OFFSET_LOCATION_X20 = 20;
    public static int LEVEL_OFFSET_LOCATION_Y20 = 120;
      
   public PathXGameLevel()
    {
        completeLevel = false;
        levelState = "RED_STATE";
        levelName = "";
        bankBalance = 0;
        levelTotal = 0;
      
        
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
