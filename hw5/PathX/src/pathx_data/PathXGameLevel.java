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
      
     private static final  int GAME_OFFSET_LOCATION_X = 50;
   private static final  int GAME_OFFSET_LOCATION_Y = 320;
      private static final  int GAME_OFFSET_LOCATION_X1 = 80;
   private static final  int GAME_OFFSET_LOCATION_Y1 = 470;
     private static final  int GAME_OFFSET_LOCATION_X2 = 72;
   private static final  int GAME_OFFSET_LOCATION_Y2 = 530;
     private static final  int GAME_OFFSET_LOCATION_X3 =300;
   private static final  int GAME_OFFSET_LOCATION_Y3 = 415;
     private static final  int GAME_OFFSET_LOCATION_X4 = 295;
   private static final  int GAME_OFFSET_LOCATION_Y4 = 500;
     private static final  int GAME_OFFSET_LOCATION_X5 = 305;
   private static final  int GAME_OFFSET_LOCATION_Y5 = 650;
     private static final  int GAME_OFFSET_LOCATION_X6 = 500;
   private static final  int GAME_OFFSET_LOCATION_Y6 = 570;
     private static final  int GAME_OFFSET_LOCATION_X7 = 580;
   private static final  int GAME_OFFSET_LOCATION_Y7 = 415;
     private static final  int GAME_OFFSET_LOCATION_X8 = 610;
   private static final  int GAME_OFFSET_LOCATION_Y8 = 650;
     private static final  int GAME_OFFSET_LOCATION_X9 = 605;
   private static final  int GAME_OFFSET_LOCATION_Y9 = 870;
     private static final  int GAME_OFFSET_LOCATION_X10 = 605;
   private static final  int GAME_OFFSET_LOCATION_Y10 = 500;
     private static final  int GAME_OFFSET_LOCATION_X11 = 705;
   private static final  int GAME_OFFSET_LOCATION_Y11 = 550;
     private static final  int GAME_OFFSET_LOCATION_X12 = 650;
   private static final  int GAME_OFFSET_LOCATION_Y12 = 770;
     private static final  int GAME_OFFSET_LOCATION_X13 = 700;
   private static final  int GAME_OFFSET_LOCATION_Y13 = 700;
     private static final  int GAME_OFFSET_LOCATION_X14 = 730;
   private static final  int GAME_OFFSET_LOCATION_Y14 = 740;
     private static final  int GAME_OFFSET_LOCATION_X15 = 760;
   private static final  int GAME_OFFSET_LOCATION_Y15 = 650;
     private static final  int GAME_OFFSET_LOCATION_X16 = 870;
   private static final  int GAME_OFFSET_LOCATION_Y16 = 670;
     private static final  int GAME_OFFSET_LOCATION_X17 = 850;
   private static final  int GAME_OFFSET_LOCATION_Y17 = 585;
     private static final  int GAME_OFFSET_LOCATION_X18 = 919;
   private static final  int GAME_OFFSET_LOCATION_Y18 = 785;
     private static final  int GAME_OFFSET_LOCATION_X19 = 930;
   private static final  int GAME_OFFSET_LOCATION_Y19 = 705;
     private static final  int GAME_OFFSET_LOCATION_X20 = 1100;
   private static final  int GAME_OFFSET_LOCATION_Y20 = 765;
      
    
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
      public static int LEVEL_OFFSET_LOCATION_X18 = 919;
    public static int LEVEL_OFFSET_LOCATION_Y18 = 785;
      public static int LEVEL_OFFSET_LOCATION_X19 = 930;
    public static int LEVEL_OFFSET_LOCATION_Y19 = 705;
      public static int LEVEL_OFFSET_LOCATION_X20 = 1100;
    public static int LEVEL_OFFSET_LOCATION_Y20 = 765;
      private int levelArray[];
   public PathXGameLevel()
    {
        completeLevel = false;
        levelState = WHITE_STATE;
        stageUnlock = false;
        
        levelName = "";
        bankBalance = 0;
        levelTotal = 0;
        levelArray = new int[2];
      
        
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
    
    public void restScroll()
    {
         LEVEL_OFFSET_LOCATION_X = 50;
     LEVEL_OFFSET_LOCATION_Y = 320;
        LEVEL_OFFSET_LOCATION_X1 = 80;
     LEVEL_OFFSET_LOCATION_Y1 = 470;
       LEVEL_OFFSET_LOCATION_X2 = 72;
     LEVEL_OFFSET_LOCATION_Y2 = 530;
       LEVEL_OFFSET_LOCATION_X3 =300;
     LEVEL_OFFSET_LOCATION_Y3 = 415;
       LEVEL_OFFSET_LOCATION_X4 = 295;
     LEVEL_OFFSET_LOCATION_Y4 = 500;
       LEVEL_OFFSET_LOCATION_X5 = 305;
     LEVEL_OFFSET_LOCATION_Y5 = 650;
       LEVEL_OFFSET_LOCATION_X6 = 500;
     LEVEL_OFFSET_LOCATION_Y6 = 570;
       LEVEL_OFFSET_LOCATION_X7 = 580;
     LEVEL_OFFSET_LOCATION_Y7 = 415;
       LEVEL_OFFSET_LOCATION_X8 = 610;
     LEVEL_OFFSET_LOCATION_Y8 = 650;
       LEVEL_OFFSET_LOCATION_X9 = 605;
     LEVEL_OFFSET_LOCATION_Y9 = 870;
       LEVEL_OFFSET_LOCATION_X10 = 605;
     LEVEL_OFFSET_LOCATION_Y10 = 500;
       LEVEL_OFFSET_LOCATION_X11 = 705;
     LEVEL_OFFSET_LOCATION_Y11 = 550;
       LEVEL_OFFSET_LOCATION_X12 = 650;
     LEVEL_OFFSET_LOCATION_Y12 = 770;
       LEVEL_OFFSET_LOCATION_X13 = 700;
     LEVEL_OFFSET_LOCATION_Y13 = 700;
       LEVEL_OFFSET_LOCATION_X14 = 730;
     LEVEL_OFFSET_LOCATION_Y14 = 740;
       LEVEL_OFFSET_LOCATION_X15 = 760;
     LEVEL_OFFSET_LOCATION_Y15 = 650;
       LEVEL_OFFSET_LOCATION_X16 = 870;
     LEVEL_OFFSET_LOCATION_Y16 = 670;
       LEVEL_OFFSET_LOCATION_X17 = 850;
     LEVEL_OFFSET_LOCATION_Y17 = 585;
       LEVEL_OFFSET_LOCATION_X18 = 919;
     LEVEL_OFFSET_LOCATION_Y18 = 785;
       LEVEL_OFFSET_LOCATION_X19 = 930;
     LEVEL_OFFSET_LOCATION_Y19 = 705;
       LEVEL_OFFSET_LOCATION_X20 = 1100;
     LEVEL_OFFSET_LOCATION_Y20 = 765;
      
    }
    
    public void setLevelOffset(int stage, int x, int y)
    {
       if(stage ==1)
       {
        LEVEL_OFFSET_LOCATION_X1 = x;
         LEVEL_OFFSET_LOCATION_Y1 = y;  
     }
       else if(stage ==2)
       {
        LEVEL_OFFSET_LOCATION_X2 = x;
         LEVEL_OFFSET_LOCATION_Y2 = y;
     }
       else if(stage ==3)
       {
        LEVEL_OFFSET_LOCATION_X3 = x;
         LEVEL_OFFSET_LOCATION_Y3 = y;
     }
       else if(stage ==4)
       {
        LEVEL_OFFSET_LOCATION_X4 = x;
         LEVEL_OFFSET_LOCATION_Y4 = y;
     }
       else if(stage ==5)
       {
        LEVEL_OFFSET_LOCATION_X5 = x;
         LEVEL_OFFSET_LOCATION_Y5 = y;
     }
       else if(stage ==6)
       {
        LEVEL_OFFSET_LOCATION_X6 = x;
         LEVEL_OFFSET_LOCATION_Y6 = y;
     }
       else if(stage ==7)
       {
        LEVEL_OFFSET_LOCATION_X7 = x;
         LEVEL_OFFSET_LOCATION_Y7 = y;
     }
       else if(stage ==8)
       {
        LEVEL_OFFSET_LOCATION_X8 = x;
         LEVEL_OFFSET_LOCATION_Y8 = y;
     }
       else if(stage ==9)
       {
        LEVEL_OFFSET_LOCATION_X9 = x;
         LEVEL_OFFSET_LOCATION_Y9 = y;
     }
       else if(stage ==20)
       {
        LEVEL_OFFSET_LOCATION_X20 = x;
         LEVEL_OFFSET_LOCATION_Y20 = y;
     }
       else if(stage ==10)
       {
        LEVEL_OFFSET_LOCATION_X10 = x;
         LEVEL_OFFSET_LOCATION_Y10 = y;
     }
       else if(stage ==11)
       {
        LEVEL_OFFSET_LOCATION_X11 = x;
         LEVEL_OFFSET_LOCATION_Y11 = y;
     }
       else if(stage ==12)
       {
        LEVEL_OFFSET_LOCATION_X12 = x;
         LEVEL_OFFSET_LOCATION_Y12 = y;
     }
       else if(stage ==13)
       {
        LEVEL_OFFSET_LOCATION_X13 = x;
         LEVEL_OFFSET_LOCATION_Y13 = y;
     }
       else if(stage ==14)
       {
        LEVEL_OFFSET_LOCATION_X14 = x;
         LEVEL_OFFSET_LOCATION_Y14 = y;
     }
       else if(stage ==15)
       {
        LEVEL_OFFSET_LOCATION_X15 = x;
         LEVEL_OFFSET_LOCATION_Y15 = y;
     }
       else if(stage ==16)
       {
        LEVEL_OFFSET_LOCATION_X16 = x;
         LEVEL_OFFSET_LOCATION_Y16 = y;
     }
       else if(stage ==17)
       {
        LEVEL_OFFSET_LOCATION_X17 = x;
         LEVEL_OFFSET_LOCATION_Y17 = y;
     }
       else if(stage ==18)
       {
        LEVEL_OFFSET_LOCATION_X18 = x;
         LEVEL_OFFSET_LOCATION_Y18 = y;
     }
       else if(stage ==19)
       {
        LEVEL_OFFSET_LOCATION_X19 = x;
         LEVEL_OFFSET_LOCATION_Y19 = y;
     }
      
    }
    
    public int[] getLevelOffsetLocation(int stage)
    {
        if(stage ==1)
       {
           
       levelArray [0]=LEVEL_OFFSET_LOCATION_X1;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y1 ; 
         return levelArray;
     }
       else if(stage ==2)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X2 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y2 ;
         return levelArray;
     }
       else if(stage ==3)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X3 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y3 ;
         return levelArray;
     }
       else if(stage ==4)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X4 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y4 ;
         return levelArray;
     }
       else if(stage ==5)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X5 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y5 ;
         return levelArray;
     }
       else if(stage ==6)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X6 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y6 ;
         return levelArray;
     }
       else if(stage ==7)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X7 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y7 ;
         return levelArray;
     }
       else if(stage ==8)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X8 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y8 ;
         return levelArray;
     }
       else if(stage ==9)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X9 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y9 ;
         return levelArray;
     }
       else if(stage ==20)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X20 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y20 ;
         return levelArray;
     }
       else if(stage ==10)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X10 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y10 ;
         return levelArray;
     }
       else if(stage ==11)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X11 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y11 ;
         return levelArray;
     }
       else if(stage ==12)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X12 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y12 ;
         return levelArray;
     }
       else if(stage ==13)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X13 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y13 ;
         return levelArray;
     }
       else if(stage ==14)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X14 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y14 ;
         return levelArray;
     }
       else if(stage ==15)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X15 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y15 ;
         return levelArray;
     }
       else if(stage ==16)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X16 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y16 ;
         return levelArray;
     }
       else if(stage ==17)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X17 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y17 ;
         return levelArray;
     }
       else if(stage ==18)
       {
       levelArray [0]=LEVEL_OFFSET_LOCATION_X18 ;
         levelArray [1]=LEVEL_OFFSET_LOCATION_Y18 ;
         return levelArray;
     }
       else if(stage ==19)
       {
        levelArray [0]=LEVEL_OFFSET_LOCATION_X19;
         levelArray [1]= LEVEL_OFFSET_LOCATION_Y19;
         return levelArray;
     }
        return null;
        
    }
}
