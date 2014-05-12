package pathx_data;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import pathX_ui.PathXCar;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Stack;
import javax.swing.JPanel;
import pathx.PathX.PathXPropertyType;
import mini_game.MiniGame;
import mini_game.MiniGameDataModel;
import mini_game.SpriteType;
import mini_game.Viewport;
import properties_manager.PropertiesManager;
import static pathx.PathXConstants.*;
import pathX_ui.PathXMiniGame;
import pathX_ui.PathXPanel;
import pathX_ui.PathXCarState;
import pathx_file.Connection;
import pathx_file.Intersection;
import pathx_file.PathXSpecial;
import pathx_file.Road;

/**
 * This class manages the game data for The Sorting Hat.
 *
 * @author Richard McKenna
 */
public class PathXDataModel extends MiniGameDataModel
{
    
    // THIS CLASS HAS A REFERERENCE TO THE MINI GAME SO THAT IT
    // CAN NOTIFY IT TO UPDATE THE DISPLAY WHEN THE DATA MODEL CHANGES
    private MiniGame miniGame;
    
    // THIS STORES THE TILES ON THE GRID DURING THE GAME
    private ArrayList<PathXCar> tilesToSort;
    
    
    // THE LEGAL TILES IN ORDER FROM LOW SORT INDEX TO HIGH
    // private ArrayList<SnakeCell> snake;
    
    // GAME GRID AND TILE DATA
    private int gameTileWidth;
    private int gameTileHeight;
    private int numGameGridColumns;
    private int numGameGridRows;
    private String TTT ;
    private boolean move;
    // THESE ARE THE TILES STACKED AT THE START OF THE GAME
    private ArrayList<PathXCar> stackCars;
     private ArrayList<PathXCar> playerStackCars;
      private ArrayList<PathXCar> banditStackCars;
       private ArrayList<PathXCar> zombieStackCars;
     private ArrayList<PathXCar> policeStackCars;
    
    
    private boolean startGame;
    private int stackCarsX;
    private int stackCarsY;
    private int dragX;
    private int dragY;
    private ArrayList<Integer> zombiePath ;
    private  ArrayList<Integer> policePath ;
    private ArrayList<Integer> banditPath ;
    private boolean addingSpecial;
    float targetX;
    float targetY;
    float policeTargetX ;
    float policeTargetY ;
    float banditTargetX ;
    float banditTargetY ;
    float zombieTargetX ;
    float zombieTargetY;
    
    
    
    private ArrayList<Intersection> snake;
    private ArrayList<PathXGameLevel> levelLocation;
    
    // THESE ARE THE TILES THAT ARE MOVING AROUND, AND SO WE HAVE TO UPDATE
   // private ArrayList<PathXCar> movingCars;
    private ArrayList<PathXCar> movingPoliceCars;
    private ArrayList<PathXCar> movingBanditCars;
    private ArrayList<PathXCar> movingZombieCars;
      private ArrayList<PathXCar> movingPlayerCars;
    //private ArrayList<PathXCar> carsToRender;
    
    // THIS IS THE TILE THE USER IS DRAGGING
    private PathXCar selectedCar;
    private int selectedCarIndex;
    
    // THIS IS THE TEMP TILE
    private PathXCar tempCar;
    
    private MouseController levelHandler;
    // KEEPS TRACK OF HOW MANY BAD SPELLS WERE CAST
    private int badSpellsCounter;
    
    // THESE ARE USED FOR TIMING THE GAME
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    
    // LEVEL
    private String currentLevel;
    
    // THE SORTING ALGORITHM WHICH GENERATES THE PROPER TRANSACTIONS
    // private SortingHatAlgorithm sortingAlgorithm;
    
    // THE PROPER TRANSACTIONS TO USE FOR COMPARISION AGAINST PLAYER MOVES
    private ArrayList<FlyingTransaction> properTransactionOrder;
    private int transactionCounter;
    //  private boolean isUndo;
    
    PathXGameLevel level;
    
    private PathXCarState  mode;
    
    // DATA FOR RENDERING
    private Viewport viewport;
    
    Viewport vPort;
    
    // WE ONLY NEED TO TURN THIS ON ONCE
    boolean levelBeingEdited;
    Image backgroundImage;
    Image startingLocationImage;
    Image destinationImage;
    
    // THE SELECTED INTERSECTION OR ROAD MIGHT BE EDITED OR DELETED
    // AND IS RENDERED DIFFERENTLY
    Intersection selectedIntersection;
    Road selectedRoad;
    
    // WE'LL USE THIS WHEN WE'RE ADDING A NEW ROAD
    Intersection startRoadIntersection;
    
    Intersection startIntersection;
    
    private ArrayList<PathXSpecial> specials;
    // IN CASE WE WANT TO TRACK MOVEMENTS
    int lastMouseX;
    int lastMouseY;
    
    // THESE BOOLEANS HELP US KEEP TRACK OF
    // @todo DO WE NEED THESE?
    boolean isMousePressed;
    boolean isDragging;
    boolean dataUpdatedSinceLastSave;
    boolean playerselected;
    private int total;
    public static int mousePressX;
    public static int mousePressY;
    
    int playerMoveX;
    int playerMoveY;
    
    
    /**
     * Constructor for initializing this data model, it will create the data
     * structures for storing tiles, but not the tile grid itself, that is
     * dependent on file loading, and so should be subsequently initialized.
     *
     * @param initMiniGame The Sorting Hat game UI.
     */
    public PathXDataModel(MiniGame initMiniGame)
    {
        
        zombiePath = new ArrayList<Integer>();
        policePath = new ArrayList<Integer>();
        banditPath = new ArrayList<Integer>();
        move = false;
        playerMoveX = 0;
        playerMoveY = 0;
        // KEEP THE GAME FOR LATER
        miniGame = initMiniGame;
        // INIT THESE FOR HOLDING MATCHED AND MOVING TILES
        stackCars = new ArrayList();
        playerStackCars = new ArrayList();
        banditStackCars = new ArrayList();
          policeStackCars = new ArrayList();
            zombieStackCars = new ArrayList();
                //movingCars = new ArrayList();
                movingPoliceCars = new ArrayList();
                movingBanditCars= new ArrayList();
                movingZombieCars= new ArrayList();
                 movingPlayerCars= new ArrayList();
                
        specials = new ArrayList<PathXSpecial>();
        
        startGame= false;
        dragX = 0;
        dragY = 0;
        total =0;
        addingSpecial = false;
        TTT= new String();
        
        level = new PathXGameLevel();
        viewport = new Viewport();
        
        playerselected = false;
        vPort = new Viewport();
        levelBeingEdited = false;
        startRoadIntersection = null;
        mode = PathXCarState.NOTHING_SELECTED;
        
        // NOTHING IS BEING DRAGGED YET
        selectedCar = null;
        selectedCarIndex = -1;
        tempCar = null;
        
        targetX =0;
        targetY =0;
        
        
        levelLocation = new ArrayList<PathXGameLevel>();
        this.initLevelStorage();
        
    }
    
    
    // ACCESSOR METHODS
    public ArrayList<Intersection> getSnake()
    {
        return snake;
    }
//public String getAlgorithmName()
    {
    //   return sortingAlgorithm.name;
}
    public int getBadSpellsCounter()
    {
        return badSpellsCounter;
    }
    
    public int getGameTileWidth()
    {
        return gameTileWidth;
    }
    
    public int getGameTileHeight()
    {
        return gameTileHeight;
    }
    
    public int getNumGameGridColumns()
    {
        return numGameGridColumns;
    }
    
    public int getNumGameGridRows()
    {
        return numGameGridRows;
    }
    
    public PathXCar getSelectedCar()
    {
        return selectedCar;
    }
    
    public String getCurrentLevel()
    {
        return currentLevel;
    }
    public ArrayList<PathXGameLevel> getLevelLocation()
    {
        return levelLocation;
    }
    public int getTotalBalance()
    {
        
        return total;
    }
    public void setTotalBalance(int num)
    {
        
        total = num;
    }
    
    //  public ArrayList<PathXCar> getStackTiles()
    {
    //     return stackCars;
}
    
  //  public Iterator<PathXCar> getMovingTiles()
    {
    //    return movingCars.iterator();
    }
     public Iterator<PathXCar> getMovingPolice()
    {
        return movingPoliceCars.iterator();
    }
      public Iterator<PathXCar> getMovingBandit()
    {
        return movingBanditCars.iterator();
    }
       public Iterator<PathXCar> getMovingZombie()
    {
        return movingZombieCars.iterator();
    }
        public Iterator<PathXCar> getMovingPlayer()
    {
        return movingPlayerCars.iterator();
    }
    
   // public Iterator<PathXCar> getAllTiles()
    {
   //     return carsToRender.iterator();
    }
    public Iterator<PathXCar> getPlayerStack()
    {
        return playerStackCars.iterator();
    }
    public Iterator<PathXCar> getZombieStack()
    {
        return zombieStackCars.iterator();
    }
    public Iterator<PathXCar> getPoliceStack()
    {
        return policeStackCars.iterator();
    }
    public Iterator<PathXCar> getBanditStack()
    {
        return banditStackCars.iterator();
    }
    
    
    // MUTATOR METHODS
    public void setCurrentLevel(String initCurrentLevel)
    {
        currentLevel = initCurrentLevel;
    }
    
    // INIT METHODS - AFTER CONSTRUCTION, THESE METHODS SETUP A GAME FOR USE
    // - initLevel
    // - initTiles
    // - initTile
    /**
     * Called after a level has been selected, it initializes the grid so that
     * it is the proper dimensions.
     */
    public void initLevel (String levelName , ArrayList<Intersection> intersections)
    {
        
        snake = intersections;
// currentLevel = LEVEL1;
        
        // UPDATE THE VIEWPORT IF WE ARE SCROLLING (WHICH WE'RE NOT)
        viewport.updateViewportBoundaries();
        
        vPort.updateViewportBoundaries();
        
        
        
        // INITIALIZE THE PLAYER RECORD IF NECESSARY
        //   PathXRecord playerRecord = ((PathXMiniGame) miniGame).getPlayerRecord();
//        if (!playerRecord.hasLevel(levelName))
        {
        // playerRecord.addLevel(levelName, initSortingAlgorithm.name);
    }
    }
    public PathXGameLevel       getLevel()                  {   return level;                   }
    
    public Viewport         getVport()               {   return vPort;   }
    
    public Viewport getVport2  (){ return viewport;}
    
    
    public ArrayList<Integer> zombiePath()
    {
      ArrayList<Integer> path =  ((PathXMiniGame)miniGame).getFileManager().findPath(((PathXMiniGame)miniGame).getFileManager().getInter(2),
                ((PathXMiniGame)miniGame).getFileManager().getInter(5));
      ArrayList<Integer> pathx =  ((PathXMiniGame)miniGame).getFileManager().findPath(((PathXMiniGame)miniGame).getFileManager().getInter(5),
                ((PathXMiniGame)miniGame).getFileManager().getInter(2));
        
      for(int i = pathx.size()-1; i>=0; i--)
      {
          path.add(pathx.remove(i));
      }
      return path;
    }
    
    public ArrayList<Integer> banditPath()
    {
      ///  return   ((PathXMiniGame)miniGame).getFileManager().findPath(((PathXMiniGame)miniGame).getFileManager().getInter(2),
       //         ((PathXMiniGame)miniGame).getFileManager().getInter(10));
        
        
        ArrayList<Integer> path =  ((PathXMiniGame)miniGame).getFileManager().findPath(((PathXMiniGame)miniGame).getFileManager().getInter(2),
                ((PathXMiniGame)miniGame).getFileManager().getInter(5));
      ArrayList<Integer> pathx =  ((PathXMiniGame)miniGame).getFileManager().findPath(((PathXMiniGame)miniGame).getFileManager().getInter(5),
                ((PathXMiniGame)miniGame).getFileManager().getInter(2));
        
      for(int i = pathx.size()-1; i>=0; i--)
      {
          path.add(pathx.remove(i));
      }
      return path;
    }
    
    
    public ArrayList<Integer> policePath()
    {
        return   ((PathXMiniGame)miniGame).getFileManager().findPath(((PathXMiniGame)miniGame).getFileManager().getInter(1),
                ((PathXMiniGame)miniGame).getFileManager().getInter(0));
        
    }
    
    
//    public void setLevel(PathXGameLevel level, int num)
//    {
//
//        levelLocation.add(num, level); }
    public void setMove( boolean mode)     {   move = mode;}
    
    public boolean getMove() { return move;}
    public Image            getBackgroundImage()        {   return backgroundImage;         }
    public Image            getStartingLocationImage()  {   return startingLocationImage;   }
    public Image            getDesinationImage()        {   return destinationImage;        }
    public Intersection     getSelectedIntersection()   {   return selectedIntersection;    }
    public Road             getSelectedRoad()           {   return selectedRoad;            }
    public Intersection     getStartRoadIntersection()  {   return startRoadIntersection;   }
    // public int              getLastMouseX()             {   return lastMouseX;              }
    //  public int              getLastMouseY()             {   return lastMouseY;              }
    public Intersection     getStartingLocation()       {   return level.startingLocation;  }
    public Intersection     getDestination()            {   return level.destination;       }
    public boolean          isNothingSelected()      { return mode == PathXCarState.NOTHING_SELECTED; }
    public boolean isIntersectionSelected() { return mode == PathXCarState.INTERSECTION_SELECTED; }
    public boolean isIntersectionDragged()  { return mode == PathXCarState.PLAYER_DRAGGED; }
    public boolean isRoadSelected()         { return mode == PathXCarState.ROAD_SELECTED; }
    public PathXCarState getMode()                { return mode;}
    
    public boolean          isStartingLocation(Intersection testInt)
    {   return testInt == level.startingLocation;           }
    public boolean isDestination(Intersection testInt)
    {   return testInt == level.destination;                }
    public boolean isSelectedIntersection(Intersection testIntersection)
    {   return testIntersection == selectedIntersection;    }
    
    
    public boolean isPlayerSelected(){ return playerselected;}
    
    public void setPlayerSelected(boolean mode) { playerselected = mode;}
    
    public boolean isSelectedRoad(Road testRoad)
    {   return testRoad == selectedRoad;                    }
    
    // ITERATOR METHODS FOR GOING THROUGH THE GRAPH
    
    public Iterator intersectionsIterator()
    {
        ArrayList<Intersection> intersections = snake;//level.getIntersections();
        return intersections.iterator();
    }
    public Iterator roadsIterator()
    {
        ArrayList<Road> roads = level.roads;
        return roads.iterator();
    }
    
    
    // MUTATOR METHODS
    public void switchState(PathXCarState initMode)
    {
        levelHandler = ((PathXMiniGame)miniGame).getMouseController();
        
        // SET THE NEW EDIT MODE
        mode = initMode;
        // UPDATE THE CURSOR
        levelHandler.updateCursor(initMode);
        
        // selectedIntersection = null;
        //   selectedRoad = null;
        // RENDER
        miniGame.getCanvas().repaint();
        
    }
    
    
    public void setLastMousePosition(int initX, int initY)
    {
        lastMouseX = initX;
        lastMouseY = initY;
        
    }
    public void setSelectedIntersection(Intersection i)
    {
        selectedIntersection = i;
       // if(!this.isPlayerMoving())
        {
             ((PathXMiniGame)miniGame).getFileManager().findShortestPath(startIntersection, i);
                this.moveAnimation();
        }
        
        ((PathXMiniGame)miniGame).getFileManager().clearPath();
        startIntersection = i;
        selectedRoad = null;
        
        
    }
    
    public void setSelectedRoad(Road r)
    {
        selectedRoad = r;
        selectedIntersection = null;
        
    }
    
    // AND THEN ALL THE SERVICE METHODS FOR UPDATING THE LEVEL
    // AND APP STATE
    
    /**
     * For selecting the first intersection when making a road. It will
     * find the road at the (canvasX, canvasY) location.
     */
    
    
    /**
     * For selecting the second intersection when making a road. It will
     * find the road at the (canvasX, canvasY) location.
     */
    
    
    /**
     * Sets up the model to edit a brand new level.
     */
    
    
    /**
     * Updates the background image.
     */
    
    public double calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2)
    {
        double diffXSquared = Math.pow(x1 - x2, 2);
        double diffYSquared = Math.pow(y1 - y2, 2);
        return Math.sqrt(diffXSquared + diffYSquared);
    }
    
    public Intersection findIntersectionAtCanvasLocation(int canvasX, int canvasY)
    {
        // CHECK TO SEE IF THE USER IS SELECTING AN INTERSECTION
        for (Intersection i : level.intersections)
        {
            double distance = calculateDistanceBetweenPoints(i.x  + LEVEL1X, i.y  + LEVEL1Y, canvasX +vPort.getViewportX() , canvasY + vPort.getViewportY() );
            if (distance < INTERSECTION_RADIUS)
            {
                // MAKE THIS THE SELECTED INTERSECTION
                return i;
            }
        }
        return null;
    }
    
    public boolean findPlayerAtCanvasLocation(int canvasX, int canvasY)
    {
        // CHECK TO SEE IF THE USER IS SELECTING AN INTERSECTION
        
        
        double distance = calculateDistanceBetweenPoints(level.playerX, level.playerY, canvasX + viewport.getViewportX(), canvasY + viewport.getViewportY());
        if (distance < INTERSECTION_RADIUS)
        {
            // MAKE THIS THE SELECTED INTERSECTION
            return true;
        }
        
        return false;
    }
    public void unselectEverything()
    {
        selectedIntersection = null;
        selectedRoad = null;
        startRoadIntersection = null;
        miniGame.getCanvas().repaint();
    }
    
    /**
     * Searches to see if there is a road at (canvasX, canvasY), and if
     * there is, it selects and returns it.
     */
    public Road selectRoadAtCanvasLocation(int canvasX, int canvasY)
    {
        Iterator<Road> it = level.roads.iterator();
        Line2D.Double tempLine = new Line2D.Double();
        while (it.hasNext())
        {
            Road r = it.next();
            tempLine.x1 = r.getNode1().getX();
            tempLine.y1 = r.getNode1().getY();
            tempLine.x2 = r.getNode2().getX();
            tempLine.y2 = r.getNode2().getY();
            double distance = tempLine.ptSegDist(canvasX+vPort.getViewportX(), canvasY+vPort.getViewportY());
            
            // IS IT CLOSE ENOUGH?
            if (distance <= INT_STROKE)
            {
                // SELECT IT
                this.selectedRoad = r;
                mode = PathXCarState.ROAD_SELECTED;
                return selectedRoad;
            }
        }
        return null;
    }
    public void draggPlayer(int canvasX, int canvasY)
    {
        
        for (int i = 0; i < playerStackCars.size(); i++)
        {
            // GET EACH TILE
            PathXCar tile = playerStackCars.get(i);
            
            if(tile.getCarType()==PLAYER)
            {
                Intersection in = findIntersectionAtCanvasLocation(canvasX, canvasY);
                Intersection inter = findIntersectionAtCanvasLocation((int)dragX, (int)dragY);
                
                if(inter!=null)
                {
                    ArrayList<Connection> neighb = ((PathXMiniGame)miniGame).getFileManager().getAllNeighbors(inter.getId());
                    for(Connection c : neighb)
                    {
                        
                        if(in.getId()==c.getIntersection1ID() || in.getId()== c.getIntersection2ID())
                        {
                            tile.setX(in.getX() + LEVEL1X +10);
                            tile.setY(in.getY() + LEVEL1Y +10);
                            
                            ((PathXMiniGame)miniGame).getCanvas().repaint();
                            dragX =0;
                            dragY =0;
                            return;
                        }
                    }
                    
                    
                }
                tile.setX(dragX);
                tile.setY(dragY);
                dragX =0;
                dragY =0;
                
            }
            
        }
        
    }
    public void moveSelectedPlayer(int canvasX, int canvasY)
    {
        for (int i = 0; i < playerStackCars.size(); i++)
        {
            // GET EACH TILE
            PathXCar tile = playerStackCars.get(i);
            
            if(tile.getCarType()==PLAYER &&  ((PathXMiniGame)miniGame).isCurrentScreenState(GAME_SCREEN_STATE))
            {
                ArrayList<Connection> startLocation = ((PathXMiniGame)miniGame).getFileManager().getAllNeighbors(level.getStartingLocation().getId());
                
                int startX = level.getStartingLocation().getX();
                
                int startY = level.getStartingLocation().getY();
                float totalX = ((startX + ((PathXMiniGame)miniGame).getFileManager().getIntersectionMap().get(startLocation.get(0).Intersection2ID).getX())/2) + LEVEL1X;
                
                float totalY = ((startY + ((PathXMiniGame)miniGame).getFileManager().getIntersectionMap().get(startLocation.get(0).Intersection2ID).getY())/2) + LEVEL1Y;
                
                if(dragX ==0 )
                {
                    
                    dragX = (int)tile.getX();
                    dragY = (int)tile.getY();
                    
                    if(dragX == totalX && dragY == totalY)
                    {
                        dragX = level.getStartingLocation().getX()+LEVEL1X;
                        dragY = level.getStartingLocation().getY() + LEVEL1Y;
                        
                    }
                }
                tile.setX(canvasX);
                tile.setY(canvasY);
                mode = mode.PLAYER_DRAGGED;
                ((PathXMiniGame)miniGame).getCanvas().repaint();
            }
        }
    }
    
    /**
     * Updates the background image.
     */
    public void updateBackgroundImage(String newBgImage)
    {
        
        // UPDATE THE LEVEL TO FIT THE BACKGROUDN IMAGE SIZE
        level.backgroundImageFileName = newBgImage;
        backgroundImage = miniGame.loadImage(LEVELS_PATH + level.backgroundImageFileName);
        int levelWidth = backgroundImage.getWidth(null);
        int levelHeight = backgroundImage.getHeight(null);
        vPort.setLevelDimensions(levelWidth, levelHeight);
        
        vPort.setGameWorldSize(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        vPort.setNorthPanelHeight(100);
        vPort.setViewportSize(740, 620);
        
        miniGame.getCanvas().repaint();
    }
    
    /**
     * Updates the image used for the starting location and forces rendering.
     */
    public void updateStartingLocationImage(String newStartImage)
    {
        level.startingLocationImageFileName = newStartImage;
        startingLocationImage = miniGame.loadImage(LEVELS_PATH + level.startingLocationImageFileName);
        miniGame.getCanvas().repaint();
        startIntersection = level.getStartingLocation();
    }
    
    /**
     * Updates the image used for the destination and forces rendering.
     */
    public void updateDestinationImage(String newDestImage)
    {
        level.destinationImageFileName = newDestImage;
        destinationImage = miniGame.loadImage(LEVELS_PATH + level.destinationImageFileName);
        miniGame.getCanvas().repaint();
    }
    public void addSpecialAtCanvasLocation(int canvasX, int canvasY, PathXCarState type)
    {
        // FIRST MAKE SURE THE ENTIRE INTERSECTION IS INSIDE THE LEVEL
        if ((canvasX - INTERSECTION_RADIUS) < 0) return;
        if ((canvasY - INTERSECTION_RADIUS) < 0) return;
        if ((canvasX + INTERSECTION_RADIUS) > vPort.levelWidth) return;
        if ((canvasY + INTERSECTION_RADIUS) > vPort.levelHeight) return;
        
        // AND ONLY ADD THE INTERSECTION IF IT DOESN'T OVERLAP WITH
        // AN EXISTING INTERSECTION
        //   for(Intersection i : level.intersections)
        {
        //    double distance = calculateDistanceBetweenPoints(i.x-vPort.x, i.y-viewport.y, canvasX, canvasY);
        //    if (distance < INTERSECTION_RADIUS)
        //        return;
    }
        
        // LET'S ADD A NEW INTERSECTION
        int intX = canvasX + vPort.getViewportX();
        int intY = canvasY + vPort.getViewportY();
        PathXSpecial newSpec = new PathXSpecial(intX, intY, type);
        newSpec.startTimer();
        level.addSpecial(newSpec);
        miniGame.getCanvas().repaint();
        
        // System.out.println("timer" + newSpec.getTimeLong());
        
    }
    
    private void updateTime()
    {
        Iterator  spec = level.getSpecial();
        while(spec.hasNext())
        {
            PathXSpecial s = (PathXSpecial)spec.next();
            s.endTimer();
            
            if((s.getTimeLong()/MILLIS_IN_A_SECOND) >10)
                level.removeSpecial(s.getId());
            
        }
        
        // KEEP THE GAME TIMER GOING IF THE GAME STILL IS
        endTime = new GregorianCalendar();
    }
    
    public boolean isAddingSpecial()
    {
        return addingSpecial;
    }
    
    public void setAddingSpecial( boolean mode) { addingSpecial = mode;}
    public void moveViewport2(int incX, int incY)
    {
        // MOVE THE VIEWPORT
        viewport.scroll(incX, incY);
        
        
        // AND NOW FORCE A REDRAW
        miniGame.getCanvas().repaint();
    }
    
    
    public void moveViewport(int incX, int incY)
    {
        // MOVE THE VIEWPORT
        vPort.scroll(incX, incY);
        
        // AND NOW FORCE A REDRAW
        miniGame.getCanvas().repaint();
    }
    public void initLevelStorage()
    {
        
        PathXGameLevel GameLevel;
        GameLevel = new PathXGameLevel();
        
        GameLevel.setMoney(TOTALMONEYLEVEL1);
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL1);
        GameLevel.setName(LEVEL1);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL2);
        GameLevel.setMoney(TOTALMONEYLEVEL2);
        GameLevel.setName(LEVEL2);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL3);
        GameLevel.setMoney(TOTALMONEYLEVEL3);
        GameLevel.setName(LEVEL3);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL4);
        GameLevel.setMoney(TOTALMONEYLEVEL4);
        GameLevel.setName(LEVEL4);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL5);
        GameLevel.setMoney(TOTALMONEYLEVEL5);
        GameLevel.setName(LEVEL5);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL6);
        GameLevel.setMoney(TOTALMONEYLEVEL6);
        GameLevel.setName(LEVEL6);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL7);
        GameLevel.setMoney(TOTALMONEYLEVEL7);
        GameLevel.setName(LEVEL7);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL8);
        GameLevel.setMoney(TOTALMONEYLEVEL8);
        GameLevel.setName(LEVEL8);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL9);
        GameLevel.setMoney(TOTALMONEYLEVEL9);
        GameLevel.setName(LEVEL9);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL10);
        GameLevel.setMoney(TOTALMONEYLEVEL10);
        GameLevel.setName(LEVEL10);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL11);
        GameLevel.setMoney(TOTALMONEYLEVEL11);
        GameLevel.setName(LEVEL11);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL12);
        GameLevel.setMoney(TOTALMONEYLEVEL12);
        GameLevel.setName(LEVEL12);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL13);
        GameLevel.setMoney(TOTALMONEYLEVEL13);
        GameLevel.setName(LEVEL13);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL14);
        GameLevel.setMoney(TOTALMONEYLEVEL14);
        GameLevel.setName(LEVEL14);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL15);
        GameLevel.setMoney(TOTALMONEYLEVEL15);
        GameLevel.setName(LEVEL15);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL16);
        GameLevel.setMoney(TOTALMONEYLEVEL16);
        GameLevel.setName(LEVEL16);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL17);
        GameLevel.setMoney(TOTALMONEYLEVEL17);
        GameLevel.setName(LEVEL17);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL18);
        GameLevel.setMoney(TOTALMONEYLEVEL18);
        GameLevel.setName(LEVEL18);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL19);
        GameLevel.setMoney(TOTALMONEYLEVEL19);
        GameLevel.setName(LEVEL19);
        levelLocation.add(GameLevel);
        GameLevel = new PathXGameLevel();
        GameLevel.setBankBalance(0);
        GameLevel.setLevelName(NAMELEVEL20);
        GameLevel.setMoney(TOTALMONEYLEVEL20);
        GameLevel.setName(LEVEL20);
        levelLocation.add(GameLevel);
        
    }
    
    
    /**
     * This method loads the tiles, creating an individual sprite for each. Note
     * that tiles may be of various types, which is important during the tile
     * matching tests.
     */
    public void initTiles()
    {
        
        stackCars = ((PathXMiniGame)miniGame).getFileManager().tileToRender();
      //  carsToRender = stackCars;
        
        
        
        
        ArrayList<Integer> zombiePath = this.zombiePath();
        ArrayList<Integer> policePath = this.policePath();
        ArrayList<Integer> banditPath = this.banditPath();
        
        for(PathXCar c: stackCars)
        {
            c.initCarData(this);
           if(c.getCarType()==PLAYER)
               playerStackCars.add(c);
          else if(c.getCarType()==POLICE)
               policeStackCars.add(c);
          else if(c.getCarType()==BANDIT)
               banditStackCars.add(c);
          else if(c.getCarType()==ZOMBIE)
               zombieStackCars.add(c);
        }
        
        
    }
    
    // HELPER METHOD FOR INITIALIZING OUR WIZARD AND WITCHES TRADING CARD TILES
    private BufferedImage buildTileImage(BufferedImage backgroundImage, BufferedImage spriteImage)
    {
        // BASICALLY THIS RENDERS TWO IMAGES INTO A NEW ONE, COMBINING THEM, AND THEN
        // RETURNING THE RESULTING IMAGE
        BufferedImage bi = new BufferedImage(TILE_WIDTH, TILE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(spriteImage, TILE_IMAGE_OFFSET_X, TILE_IMAGE_OFFSET_Y, null);
        return bi;
    }
    
    /**
     * Used to calculate the x-axis pixel location in the game grid for a tile
     * placed at column with stack position z.
     *
     * @param column The column in the grid the tile is located.
     *
     * @return The x-axis pixel location of the tile
     */
    public int calculateGridTileX(int column)
    {
        return viewport.getViewportMarginLeft() + (column * TILE_WIDTH) - viewport.getViewportX();
    }
    
    /**
     * Used to calculate the y-axis pixel location in the game grid for a tile
     * placed at row.
     *
     * @param row The row in the grid the tile is located.
     *
     * @return The y-axis pixel location of the tile
     */
    public int calculateGridTileY(int row)
    {
        return viewport.getViewportMarginTop() + (row * TILE_HEIGHT) - viewport.getViewportY();
    }
    
    /**
     * Used to calculate the grid column for the x-axis pixel location.
     *
     * @param x The x-axis pixel location for the request.
     *
     * @return The column that corresponds to the x-axis location x.
     */
    public int calculateGridCellColumn(int x)
    {
        // ADJUST FOR THE MARGIN
        x -= viewport.getViewportMarginLeft();
        
        // ADJUST FOR THE VIEWPORT
        x = x + viewport.getViewportX();
        
        if (x < 0)
        {
            return -1;
        }
        
        // AND NOW GET THE COLUMN
        return x / TILE_WIDTH;
    }
    
    /**
     * Used to calculate the grid row for the y-axis pixel location.
     *
     * @param y The y-axis pixel location for the request.
     *
     * @return The row that corresponds to the y-axis location y.
     */
    public int calculateGridCellRow(int y)
    {
        // ADJUST FOR THE MARGIN
        y -= viewport.getViewportMarginTop();
        
        // ADJUST FOR THE VIEWPORT
        y = y + viewport.getViewportY();
        
        if (y < 0)
        {
            return -1;
        }
        
        // AND NOW GET THE ROW
        return y / TILE_HEIGHT;
    }
    
    // TIME TEXT METHODS
    // - timeToText
    // - gameTimeToText
    /**
     * This method creates and returns a textual description of the timeInMillis
     * argument as a time duration in the format of (H:MM:SS).
     *
     * @param timeInMillis The time to be represented textually.
     *
     * @return A textual representation of timeInMillis.
     */
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
    /**
     * This method builds and returns a textual representation of the game time.
     * Note that the game may still be in progress.
     *
     * @return The duration of the current game represented textually.
     */
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
    
    // GAME DATA SERVICE METHODS
    // -enableTiles
    // -moveAllTilesToStack
    // -moveTiles
    // -playWinAnimation
    // -processMove
    // -selectTile
    // -undoLastMove
    /**
     * This method can be used to make all of the tiles either visible (true) or
     * invisible (false). This should be used when switching between the menu
     * and game screens.
     *
     * @param enable Specifies whether the tiles should be made visible or not.
     */
    public void enableTiles(boolean enable)
    {
        // PUT ALL THE TILES IN ONE PLACE WHERE WE CAN PROCESS THEM TOGETHER
        moveAllTilesToStack();
        
        // GO THROUGH ALL OF THEM
        for (PathXCar car : playerStackCars)
        {
            // AND SET THEM PROPERLY
            if (enable)
            {
                car.setState(PathXCarState.VISIBLE_STATE.toString());
            } else
            {
                car.setState(PathXCarState.INVISIBLE_STATE.toString());
            }
        }
        for (PathXCar car : policeStackCars)
        {
            // AND SET THEM PROPERLY
            if (enable)
            {
                car.setState(PathXCarState.VISIBLE_STATE.toString());
            } else
            {
                car.setState(PathXCarState.INVISIBLE_STATE.toString());
            }
        }
        for (PathXCar car : banditStackCars)
        {
            // AND SET THEM PROPERLY
            if (enable)
            {
                car.setState(PathXCarState.VISIBLE_STATE.toString());
            } else
            {
                car.setState(PathXCarState.INVISIBLE_STATE.toString());
            }
        }
        for (PathXCar car : zombieStackCars)
        {
            // AND SET THEM PROPERLY
            if (enable)
            {
                car.setState(PathXCarState.VISIBLE_STATE.toString());
            } else
            {
                car.setState(PathXCarState.INVISIBLE_STATE.toString());
            }
        }
    }
    
    /**
     * This method moves all the tiles not currently in the stack to the stack.
     */
    public void moveAllTilesToStack()
    {
        moveCars(movingPlayerCars, playerStackCars);
        moveCars(movingBanditCars, banditStackCars);
        moveCars(movingZombieCars, zombieStackCars);
        moveCars(movingPoliceCars, policeStackCars);
        // moveTiles(tilesToSort, stackCars);
    }
    
    /**
     * This method removes all the tiles in from argument and moves them to
     * argument.
     *
     * @param from The source data structure of tiles.
     *
     * @param to The destination data structure of tiles.
     */
    private void moveCars(ArrayList<PathXCar> from, ArrayList<PathXCar> to)
    {
        // GO THROUGH ALL THE TILES, TOP TO BOTTOM
        for (int i = from.size() - 1; i >= 0; i--)
        {
            PathXCar car = from.remove(i);
            
            // ONLY ADD IT IF IT'S NOT THERE ALREADY
            if (!to.contains(car))
            {
                to.add(car);
            }
        }
    }
    
    /**
     * This method sets up and starts the animation shown after a game is won.
     */
    public void moveAnimation()
    {
        
        ArrayList<Integer> movePath = new ArrayList<Integer>();
        ArrayList<Intersection> intersection = ((PathXMiniGame)miniGame).getFileManager().getPlayerPath();
        
        int j =0;
        for(Intersection intsec : intersection )
        {
            if(j==1)
            {
                targetX =intsec.getX();
                targetY = intsec.getY();
            }
            if(j>1)
            {
                
                movePath.add(intsec.getX());
                movePath.add(intsec.getY());
            }
            j++;
        }
        
        moveAllTilesToStack();
        
        for (int i = 0; i < playerStackCars.size(); i++)
        {
            // GET EACH TILE
            PathXCar tile = playerStackCars.get(i);
            
            if(tile.getCarType()==PLAYER)
                // MAKE SURE IT'S MOVED EACH FRAME
            {
           
                    tile.setTarget(targetX, targetY);
                    movingPlayerCars.add(tile);
                    tile.initRoadPath(movePath);
                
                
            }
        }
        
        
    }
    
    public boolean isPlayerMoving()
    {
        for (int i = 0; i < playerStackCars.size(); i++)
        {
            // GET EACH TILE
            PathXCar tile = playerStackCars.get(i);
            
            if(tile.getCarType()==PLAYER)
             return tile.isMovingToTarget();
        }
        return false;
    }
    public void switchCarOrder()
    {
        
//        Stack<Integer> zombieStack = new Stack<Integer>();
//        for(int i =0; i< zombiePath.size(); i++)
//        {
//            zombieStack.push(zombiePath.get(i));
//        }
//        zombiePath = new ArrayList<Integer>();
//        while(!zombieStack.isEmpty())
//        {
//            zombiePath.add(zombieStack.pop());
//        }
//        
//        
//        Stack<Integer> policeStack = new Stack<Integer>();
//        for(int i =0; i< policePath.size(); i++)
//        {
//            policeStack.push(policePath.get(i));
//        }
//        policePath = new ArrayList<Integer>();
//        while(!policeStack.isEmpty())
//        {
//            policePath.add(policeStack.pop());
//        }
        
        Stack<Integer> banditStack = new Stack<Integer>();
        for(int i =0; i< banditPath.size(); i++)
        {
            banditStack.push(banditPath.get(i));
        }
        banditPath = new ArrayList<Integer>();
        while(!banditStack.isEmpty())
        {
            banditPath.add(banditStack.pop());
        }
        carsMovingAround();
    }
    public void carsMovingAround()
    {
        
        ArrayList<Integer> zombiePath = this.zombiePath();
        ArrayList<Integer> policePath = this.policePath();
        ArrayList<Integer> banditPath = this.banditPath();
        
        policeTargetX = policePath.get(2);
        policeTargetY = policePath.get(3);
        banditTargetX = banditPath.get(2);
        banditTargetY = banditPath.get(2);
        zombieTargetX = zombiePath.get(2);
        zombieTargetY = zombiePath.get(3);
        
        moveAllTilesToStack();
        
        for (int i = 0; i < zombieStackCars.size(); i++)
        {
            // GET EACH TILE
            PathXCar tile = zombieStackCars.get(i);
            if(tile.getCarType()==ZOMBIE)
                // MAKE SURE IT'S MOVED EACH FRAME
            {
                tile.setTarget(zombieTargetX, zombieTargetY);
                movingZombieCars.add(tile);
                tile.initRoadPath(zombiePath);
            }
            
        }
        for (int i = 0; i < policeStackCars.size(); i++)
        {
            PathXCar tile = policeStackCars.get(i);
            if(tile.getCarType()==POLICE)
                // MAKE SURE IT'S MOVED EACH FRAME
            {
                tile.setTarget(policeTargetX, policeTargetY);
                movingPoliceCars.add(tile);
                tile.initRoadPath(policePath);
            }
        }
         for (int i = 0; i < banditStackCars.size(); i++)
        {
            PathXCar tile = banditStackCars.get(i);
            if(tile.getCarType()==BANDIT)
                // MAKE SURE IT'S MOVED EACH FRAME
            {
                tile.setTarget(banditTargetX, banditTargetY);
                movingBanditCars.add(tile);
                tile.initRoadPath(banditPath);
            }
           
        }
        
    }
    /**
     * Gets the next swap operation using the list generated by the proper
     * algorithm.
     */
    public FlyingTransaction getNextSwapTransaction()
    {
        return properTransactionOrder.get(transactionCounter);
    }
    
    /**
     * Swaps the tiles at the two indices.
     */
    
    
    /**
     * This method updates all the necessary state information to process the
     * swap transaction.
     */
    
    
    // THIS HELPER METHOD FINDS THE TILE IN THE DATA STRUCTURE WITH
    // THE GRID LOCATION OF col, row, AND RETURNS IT'S INDEX
    
    
    // OVERRIDDEN METHODS
    // - checkMousePressOnSprites
    // - endGameAsWin
    // - reset
    // - updateAll
    // - updateDebugText
    
    /**
     * This method provides a custom game response for handling mouse clicks on
     * the game screen. We'll use this to close game dialogs as well as to
     * listen for mouse clicks on grid cells.
     *
     * @param game The Sorting Hat game.
     *
     * @param x The x-axis pixel location of the mouse click.
     *
     * @param y The y-axis pixel location of the mouse click.
     */
    //public int getMousePressX() { return mousePressX;}
    // public int getMousePressY() { return mousePressY;}
    
    @Override
    public void checkMousePressOnSprites(MiniGame game, int x, int y)
    {
        // FIGURE OUT THE CELL IN THE GRID
        int col = calculateGridCellColumn(x);
        int row = calculateGridCellRow(y);
        
        mousePressX = x;
        mousePressY = y;
        
        
        // IF WE ARE CURRENTLY DRAGGING AN INTERSECTION
        
        // mode = PathXCarState.PLAYER_SELECTED;
        
        
        System.out.println("testing mouse press on for X:" + x+ "\t and for col "+col);
        
        System.out.println("testing mouse press on for Y:" + y+ "\t and for row "+row);
        
        // DISABLE THE STATS DIALOG IF IT IS OPEN
        //  if (game.getGUIDialogs().get(STATS_DIALOG_TYPE).getState().equals(PathXCarState.VISIBLE_STATE.toString()))
        
        //       game.getGUIDialogs().get(STATS_DIALOG_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        //       return;
    }
    /**
     * Responds to when the user presses the mouse button on the canvas,
     * it may respond in a few different ways depending on what the
     * current edit mode is.
     */
    
    
    
    
    
    /**
     * Called when the game is won, it will record the ending game time, update
     * the player record, display the win dialog, and play the win animation.
     */
    
    public void endGameAsWin()
    {
        
         super.endGameAsWin();
       
        miniGame.getAudio().stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString());
        miniGame.getAudio().stop(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString());
        miniGame.getAudio().play(PathXPropertyType.AUDIO_CUE_WIN.toString(), false);
        
    }
    
    /**
     * Updates the player record, adding a game without a win.
     */
    public void endGameAsLoss()
    {
        
        super.endGameAsLoss();
        
    }
    public void setStartGame(boolean value)
    {
        startGame = value;
    }
    public boolean getStartGame()
    {
        
        return startGame;
    }
    
    /**
     * Called when a game is started, the game grid is reset.
     *
     * @param game
     */
    @Override
    public void reset(MiniGame game)
    {
        // PUT ALL THE TILES IN ONE PLACE AND MAKE THEM VISIBLE
        moveAllTilesToStack();
        
        // RESET THE BAD SPELLS COUNTER
        
        
        // RANDOMLY ORDER THEM
        moveAllTilesToStack();
        
        
        
        // SEND THE TILES OFF TO THE GRID TO BE SORTED
        
        
        // START THE CLOCK
        startTime = new GregorianCalendar();
        
        // AND START ALL UPDATES
        beginGame();
        
        
    }
    
    public void resetPathXCar()
    {
        stackCars = new  ArrayList<PathXCar> ();
        zombiePath = new ArrayList<Integer>();
        policePath = new ArrayList<Integer>();
        banditPath = new ArrayList<Integer>();
        movingPlayerCars = new ArrayList<>();
        
        movingPoliceCars = new ArrayList<>();
        movingBanditCars = new ArrayList<>();
        movingZombieCars= new ArrayList<>();
        movingPlayerCars= new ArrayList<>();
        
         playerStackCars = new ArrayList<PathXCar> ();
       banditStackCars = new ArrayList<PathXCar> ();
       zombieStackCars = new ArrayList<PathXCar> ();;
      policeStackCars = new ArrayList<PathXCar> ();;
        
    }
    
    
    /**
     * Called each frame, this method updates all the game objects.
     *
     * @param game The Sorting Hat game to be updated.
     */
    @Override
    public void updateAll(MiniGame game)
    {
        
        try
        {
            // MAKE SURE THIS THREAD HAS EXCLUSIVE ACCESS TO THE DATA
            game.beginUsingData();
            
            // WE ONLY NEED TO UPDATE AND MOVE THE MOVING TILES
            for (int i = 0; i < movingPlayerCars.size(); i++)
            {
                // GET THE NEXT TILE
                PathXCar tile = movingPlayerCars.get(i);
                
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
                
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget() )
                {
                    movingPlayerCars.remove(tile);
                }
             
                
            }
            for (int i = 0; i < movingPoliceCars.size(); i++)
            {
                // GET THE NEXT TILE
                PathXCar tile = movingPoliceCars.get(i);
                
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
                
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget() )
                {
                    movingPoliceCars.remove(tile);
                }
                
                //  movingPoliceCars.remove(tile);
                //    switchCarOrder();
             
            }
            for (int i = 0; i < movingZombieCars.size(); i++)
            {
                // GET THE NEXT TILE
                PathXCar tile = movingZombieCars.get(i);
                
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
                
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget() )
                {
                    movingZombieCars.remove(tile);
                }
                
            }
            for (int i = 0; i < movingBanditCars.size(); i++)
            {
                // GET THE NEXT TILE
                PathXCar tile = movingBanditCars.get(i);
                
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
                
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget() )
                {
                    movingBanditCars.remove(tile);
                }
                
                
            }
            
            // IF THE GAME IS STILL ON, THE TIMER SHOULD CONTINUE
            if (inProgress())
            {
                updateTime();
            }
        } finally
        {
            // MAKE SURE WE RELEASE THE LOCK WHETHER THERE IS
            // AN EXCEPTION THROWN OR NOT
            game.endUsingData();
        }
    }
    
    /**
     * This method is for updating any debug text to present to the screen. In a
     * graphical application like this it's sometimes useful to display data in
     * the GUI.
     *
     * @param game The Sorting Hat game about which to display info.
     */
    /**
     * Responds to when the user presses the mouse button on the canvas,
     * it may respond in a few different ways depending on what the
     * current edit mode is.
     */
    
    @Override
    public void updateDebugText(MiniGame game)
    {
    }
    
    
}