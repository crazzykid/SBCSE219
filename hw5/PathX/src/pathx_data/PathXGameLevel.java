/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pathx_data;
import java.util.ArrayList;
import static pathx.PathXConstants.*;
import pathx_file.Intersection;
import pathx_file.Road;
/**
 *
 * @author Crazzykid
 */
public class PathXGameLevel
{
    private boolean completeLevel;
    private String levelState;
   // private String levelName;
    private int bankBalance;
    private int levelTotal;
    private boolean stageUnlock;
    // EVERY LEVEL HAS A NAME
    String levelName;

    // THE LEVEL BACKGROUND
    String startingLocationImageFileName;

    // COMPLETE LIST OF INTERSECTIONS SORTED LEFT TO RIGHT
    ArrayList<Intersection> intersections;

    // COMPLETE LIST OF ROADS SORTED BY STARTING INTERSECTION LOCATION LEFT TO RIGHT
    ArrayList<Road> roads;

    // THE STARTING LOCATION AND DESTINATION
    Intersection startingLocation;
    String backgroundImageFileName;
    Intersection destination;
    String destinationImageFileName;

    // THE AMOUNT OF MONEY TO BE EARNED BY THE LEVEL
    int money;

    // THE NUMBER OF POLICE, BANDITS, AND ZOMBIES
    int numPolice;
    int numBandits;
    int numZombies;
    
    
   
    
    public static int LEVEL_OFFSET_LOCATION_X = 50;
    public static int LEVEL_OFFSET_LOCATION_Y = 320;
    public static int LEVEL_OFFSET_LOCATION_X1 = 80;
    public static int LEVEL_OFFSET_LOCATION_Y1 = 470;
    public static int LEVEL_OFFSET_LOCATION_X2 = 300;
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
        
        // INIT THE GRAPH DATA STRUCTURES
        intersections = new ArrayList();
        roads = new ArrayList();         
        
        
    }
     public void init (  String initLevelName,
                        String initBackgroundImageFileName,
                        String initStartingLocationImageFileName,
                        int startingLocationX, 
                        int startingLocationY,
                        String initDestinationImageFileName,
                        int destinationX, 
                        int destinationY)
    {
        // THESE THINGS ARE KNOWN
        levelName = initLevelName;
        backgroundImageFileName = initBackgroundImageFileName;
        startingLocationImageFileName = initStartingLocationImageFileName;
        destinationImageFileName = initDestinationImageFileName;
        
        // AND THE STARTING LOCATION AND DESTINATION
        startingLocation = new Intersection(startingLocationX, startingLocationY);
        intersections.add(startingLocation);
        destination = new Intersection(destinationX, destinationY);
        intersections.add(destination);
        
        // THESE THINGS WILL BE PROVIDED DURING LEVEL EDITING
        money = 0;
        numPolice = 0;
        numBandits = 0;
        numZombies = 0;
    }
    
    // ACCESSOR METHODS
    
    public String                   getLevelName()                      {   return levelName;                       }
    public String                   getStartingLocationImageFileName()  {   return startingLocationImageFileName;   }
    public String                   getBackgroundImageFileName()        {   return backgroundImageFileName;         }
    public String                   getDestinationImageFileName()       {   return destinationImageFileName;        }
    public ArrayList<Intersection>  getIntersections()                  {   return intersections;                   }
    public ArrayList<Road>          getRoads()                          {   return roads;                           }
    public Intersection             getStartingLocation()               {   return startingLocation;                }
    public Intersection             getDestination()                    {   return destination;                     }
    public int                      getMoney()                          {   return money;                           }
    public int                      getNumPolice()                      {   return numPolice;                       }
    public int                      getNumBandits()                     {   return numBandits;                      }
    public int                      getNumZombies()                     {   return numZombies;                      }
    
    // MUTATOR METHODS
    public void setLevelName(String levelName)    
    {   this.levelName = levelName;                                             }
    public void setNumBandits(int numBandits)
    {   this.numBandits = numBandits;                                           }
    public void setBackgroundImageFileName(String backgroundImageFileName)    
    {   this.backgroundImageFileName = backgroundImageFileName;                 }
    public void setStartingLocationImageFileName(String startingLocationImageFileName)    
    {   this.startingLocationImageFileName = startingLocationImageFileName;     }
    public void setDestinationImageFileName(String destinationImageFileName)    
    {   this.destinationImageFileName = destinationImageFileName;               }
    public void setMoney(int money)    
    {   this.money = money;                                                     }
    public void setNumPolice(int numPolice)    
    {   this.numPolice = numPolice;                                             }
    public void setNumZombies(int numZombies)
    {   this.numZombies = numZombies;                                           }
    public void setStartingLocation(Intersection startingLocation)
    {   this.startingLocation = startingLocation;                               }
    public void setDestination(Intersection destination)
    {   this.destination = destination;                                         }
    
    /**
     * Clears the level graph and resets all level data.
     */
    public void reset()
    {
        levelName = "";
        startingLocationImageFileName = "";
        intersections.clear();
        roads.clear();
        startingLocation = null;
        backgroundImageFileName = "";
        destination = null;
        destinationImageFileName = "";
        money = 0;
        numPolice = 0;
        numBandits = 0;
        numZombies = 0;
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
