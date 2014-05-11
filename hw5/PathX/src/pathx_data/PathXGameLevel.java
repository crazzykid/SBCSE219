/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pathx_data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import static pathx.PathXConstants.*;
import pathx_file.Intersection;
import pathx_file.PathXSpecial;
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
    int playerX;
    int playerY;
       private TreeMap<String, PathXSpecial> specials;      
    private boolean load;
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

    private int levelArray[];
    public PathXGameLevel()
    {
        completeLevel = false;
        levelState = WHITE_STATE;
        stageUnlock = true;
        specials= new TreeMap<String,PathXSpecial>();
        playerX = LEVEL1X;
        playerY =LEVEL1Y;
        levelName = "";
        bankBalance = 0;
        levelTotal = 0;
        levelArray = new int[2];
        
        // INIT THE GRAPH DATA STRUCTURES
        intersections = new ArrayList();
        roads = new ArrayList();         
        load = false;
        
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
    public void setload (){ load = true;}
    public boolean getLoad () { return load; }
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
    public int                      getPlayerX()                    { return playerX;}
    
        public int                      getPlayerY()                    { return playerY;}
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
    
    public void addSpecial(PathXSpecial add)
    {
        specials.put(add.getId(), add);
    }
   
    public void removeSpecial (int i)
    {
        specials.remove(i);
    }
    
    public Iterator getSpecial()
    {
        return specials.values().iterator();
    }
    
    public void removeSpecial(String id)
    {
        specials.remove(id);
    }
}
