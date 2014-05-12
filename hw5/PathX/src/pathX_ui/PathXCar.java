package pathX_ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import static pathx.PathXConstants.*;
import pathx_data.PathXDataModel;
import pathx_data.PathXGameLevel;
import pathx_file.Connection;
import pathx_file.Intersection;
import pathx_file.PathXSpecial;

/**
 * This class represents a single tile in the game world.
 *
 * @author Richard McKenna
 */
public class PathXCar extends Sprite
{
    // EACH TILE HAS AN ID, WHICH WE'LL USE FOR SORTING
    private int tileId;
    
    private String carType;
    // WHEN WE PUT A TILE IN THE GRID WE TELL IT WHAT COLUMN AND ROW
    // IT IS LOCATED TO MAKE THE UNDO OPERATION EASY LATER ON
    private int gridColumn;
    private int gridRow;
     private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private boolean stop;
    
    // THIS IS true WHEN THIS TILE IS MOVING, WHICH HELPS US FIGURE
    // OUT WHEN IT HAS REACHED A DESTINATION NODE
    private boolean movingToTarget;
    
    // THE TARGET COORDINATES IN WHICH IT IS CURRENTLY HEADING
    private float targetX;
    private float targetY;
    
    // WIN ANIMATIONS CAN BE GENERATED SIMPLY BY PUTTING TILES ON A PATH
    private ArrayList<Integer> movePath;
    
    private ArrayList<Integer>playerPath;
    
    // THIS INDEX KEEPS TRACK OF WHICH NODE ON THE WIN ANIMATION PATH
    // THIS TILE IS CURRENTLY TARGETING
    private int movePathIndex;
    private PathXDataModel data;
    private boolean loop;
    private String imageFileName;
    /**
     * This constructor initializes this tile for use, including all the
     * sprite-related data from its ancestor class, Sprite.
     */
    public PathXCar(    SpriteType initSpriteType,
            float initX, 	float initY,
            float initVx, 	float initVy,
            String initState
    )
    {
        // SEND ALL THE Sprite DATA TO A Sprite CONSTRUCTOR
        super(initSpriteType, initX, initY, initVx, initVy, initState);
        
        // tileId = initTileId;
        carType ="";
        imageFileName ="";
        loop = false;
        playerPath = new  ArrayList<Integer>();
       
    }
    
    // ACCESSOR METHODS
    // -getTileId
    // -getTileType
    // -getGridColumn
    // -getGridRow
    // -getTargetX
    // -getTargetY
    // -isMovingToTarget
    
    /**
     * Accessor method for getting the tile id for this tile.
     *
     * @return The id for this tile, which should match the rendered id.
     */
    public int getTileId()
    {
        return tileId;
    }
    public void setStop(boolean mode)
    {
        stop = mode;
    }
    public boolean getStop()
    {
        return stop;
    }
    public void initCarData(PathXDataModel model)
    {
        data = model;
    }
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
    
      
      
      public void startTimer()
      {
          startTime = new GregorianCalendar();
               
      }
      public void stopTimer()
      {
          startTime = null;
      }
      
    public void setImageFileName(String ImageFileName)
    {   this.imageFileName = ImageFileName;
    
    }
    /**
     * Accessor method for getting the tile grid column that this tile
     * is either currently in, or was most recently in.
     *
     * @return The grid column this tile is or most recently was located in.
     */
    public int getGridColumn()
    {
        return gridColumn;
    }
    
    /**
     * Accessor method for getting the tile grid row that this tile
     * is either currently in, or was most recently in.
     *
     * @return The grid row this tile is or most recently was located in.
     */
    public int getGridRow()
    {
        return gridRow;
    }
    
    /**
     * Accessor method for getting the x-axis target coordinate for this tile.
     *
     * @return The x-axis target coordinate for this tile.
     */
    public float getTargetX()
    {
        return targetX;
    }
    
    /**
     * Accessor method for getting the y-axis target coordinate for this tile.
     *
     * @return The y-axis target coordinate for this teil.
     */
    public float getTargetY()
    {
        return targetY;
    }
    
    /**
     * Accessor method for getting whether this tile is currently moving toward
     * target coordinates or not.
     *
     * @return true if this tile is currently moving toward target coordinates,
     * false otherwise.
     */
    public boolean isMovingToTarget()
    {
        return movingToTarget;
    }
    
    // MUTATOR METHODS
    // -setGridCell
    // -setTarget
    public void setCarType(String type)     { carType = type;}
    
    public String getCarType()     { return carType; }
    /**
     * Mutator method for setting both the grid column and row that
     * this tile is being placed in.
     *
     * @param initGridColumn The column this tile is being placed in
     * in The Sorting Hat game grid.
     *
     * @param initGridRow The row this tile is being placed in
     * in The Sorting Hat game grid.
     */
    public void setGridCell(int initGridColumn, int initGridRow)
    {
        gridColumn = initGridColumn;
        gridRow = initGridRow;
    }
    
    /**
     * Mutator method for setting bot the x-axis and y-axis target
     * coordinates for this tile.
     *
     * @param initTargetX The x-axis target coordinate to move this
     * tile towards.
     *
     * @param initTargetY The y-axis target coordinate to move this
     * tile towards.
     */
    public void setTarget(float initTargetX, float initTargetY)
    {
        targetX = initTargetX + LEVEL1X;
        targetY = initTargetY + LEVEL1Y;
    }
    
    // PATHFINDING METHODS
    // -calculateDistanceToTarget
    // -initWinPath
    // -startMovingToTarget
    // -updateWinPath
    
    /**
     * This method calculates the distance from this tile's current location
     * to the target coordinates on a direct line.
     *
     * @return The total distance on a direct line from where the tile is
     * currently, to where its target is.
     */
    public float calculateDistanceToTarget()
    {
        // GET THE X-AXIS DISTANCE TO GO
        float diffX = targetX - x;
        
        // AND THE Y-AXIS DISTANCE TO GO
        float diffY = targetY - y;
        
        // AND EMPLOY THE PYTHAGOREAN THEOREM TO CALCULATE THE DISTANCE
        float distance = (float)Math.sqrt((diffX * diffX) + (diffY * diffY));
        
        // AND RETURN THE DISTANCE
        
        return distance;
    }
    
      public float calculateDistanceToSpecial(int specX, int specY)
    {
        // GET THE X-AXIS DISTANCE TO GO
        float diffX = specX - x;
        
        // AND THE Y-AXIS DISTANCE TO GO
        float diffY = specY - y;
        
        // AND EMPLOY THE PYTHAGOREAN THEOREM TO CALCULATE THE DISTANCE
        float distance = (float)Math.sqrt((diffX * diffX) + (diffY * diffY));
        
        // AND RETURN THE DISTANCE
        
        return distance;
    }
    
    /**
     * This method builds a path for this tile for the
     * win animations by slightly randomizing the locations
     * of the nodes in the winPathNodes argument.
     */
    public void initRoadPath(ArrayList<Integer> intersectionNodes)
    {
        
        // CONSTRUCT THE PATH
        movePath = new ArrayList(intersectionNodes.size());
        for (int i = 0; i < intersectionNodes.size(); i+=2)
        {
            // AND FILL IT WITH FUZZY PATH NODES
            int toleranceX = 0;//(int)(MOVE_PATH_TOLERANCE ) - (MOVE_PATH_TOLERANCE/2);
            int toleranceY = 0;//'//(int)(MOVE_PATH_TOLERANCE ) - (MOVE_PATH_TOLERANCE/2);
            int x = intersectionNodes.get(i) + toleranceX;
            int y = intersectionNodes.get(i+1) + toleranceY;
            movePath.add(x);
            movePath.add(y);
        }
        // movePath = intersectionNodes;
         
    }
    
    public void updatePlayerMove(ArrayList<Intersection> init)
    {
        Iterator<Intersection> path = init.iterator();
        
        while (path.hasNext())
        {
            Intersection temp = path.next();
            
            playerPath.add(temp.getX());
            playerPath.add(temp.getY());
            
        }
        //  movePath = playerPath;
        
    }
    /**
     * Allows the tile to start moving by initializing its properly
     * scaled velocity vector pointed towards it target coordinates.
     *
     * @param maxVelocity The maximum velocity of this tile, which
     * we'll then compute the x and y axis components for taking into
     * account the trajectory angle.
     */
    public void startMovingToTarget(float maxVelocity)
    {
        // LET ITS POSITIONG GET UPDATED
        movingToTarget = true;
        
        // CALCULATE THE ANGLE OF THE TRAJECTORY TO THE TARGET
        float diffX = targetX - x;
        float diffY = targetY - y;
        float tanResult = diffY/diffX;
        float angleInRadians = (float)Math.atan(tanResult);
        
        // COMPUTE THE X VELOCITY COMPONENT
        vX = (float)(maxVelocity * Math.cos(angleInRadians));
        
        // CLAMP THE VELOCTY IN CASE OF NEGATIVE ANGLES
        if ((diffX < 0) && (vX > 0)) vX *= -1;
        if ((diffX > 0) && (vX < 0)) vX *= -1;
        
        // COMPUTE THE Y VELOCITY COMPONENT
        vY = (float)(maxVelocity * Math.sin(angleInRadians));
        
        // CLAMP THE VELOCITY IN CASE OF NEGATIVE ANGLES
        if ((diffY < 0) && (vY > 0)) vY *= -1;
        if ((diffY > 0) && (vY < 0)) vY *= -1;
    }
    
    public boolean getLoop()
    {
        
        return loop;
    }
    /**
     * After a win, while the tiles are animating, this method is called
     * each frame to make sure that when the tile reaches the next node
     * in the path, it moves on to the following path node.
     *
     * @param game The Sorting Hat game we are updating.
     */
    public boolean checkSpecialCollison()
    {
        if(this.carType ==BANDIT || this.carType == POLICE || this.carType == ZOMBIE || this.carType==PLAYER )
        {
           PathXGameLevel level = data.getLevel();
           Iterator it = level.getSpecial();
           while(it.hasNext())
           {
               PathXSpecial s = (PathXSpecial) it.next();
               int x = s.getX();
               int y = s.getY();
                if(calculateDistanceToSpecial(x,y) < MAX_TILE_VELOCITY)
                    return false;     
           }
             
        }
        return true;  
    }
     public int checkCollison()
    {
////        if(this.carType==PLAYER )
////        {
////           PathXCar cars; 
////           Iterator it = data.getAllTiles();
////           while(it.hasNext())
////           {
////               cars  = (PathXCar) it.next();
////               if(cars.getCarType() !=PLAYER)
////               {
////               int x = (int)cars.getX();
////               int y = (int)cars.getY();
////                if(calculateDistanceToSpecial(x,y) <= 0)
////                  {
////                      if(cars.getCarType()== BANDIT)
////                      return 1;
////                      else
////                          if(cars.getCarType()== POLICE)
////                                return 2;
////                      else
////                          if(cars.getCarType()== ZOMBIE)
////                                return 3;
//                  } 
//                  }
//               }
//           }
        return 0;
    }
    
    
    public void updateMovePath(MiniGame game)
    {
        if(checkCollison()>0)
        {
            if(checkCollison()==1)
            {
                int money = data.getLevel().getMoney();
                money = (money - ((money/100)*10));
                data.getLevel().setMoney(money);  
            }
            
            else 
                if (checkCollison()==2)
                {
                  int money = data.getLevel().getMoney();
                  money = 0;
                  data.getLevel().setMoney(money);  
                
                  int bank = data.getTotalBalance();
                  bank = bank - ((bank/100)*10);
                  data.setTotalBalance(bank);

                }
        }
        if(checkCollison()==3)
        {
            
              startMovingToTarget(1/10);
            
            
        }
        if(checkSpecialCollison() && checkCollison() !=3)
        {

             startMovingToTarget(1);
          
        // IS THE TILE ALMOST AT THE PATH NODE IT'S TARGETING?
        if (calculateDistanceToTarget() < MAX_TILE_VELOCITY)
        {
            if(this.carType !=PLAYER)
            {
            // PUT IT RIGHT ON THE NODE
            x = targetX ;
            y = targetY ;
            
            }
            else
                {
            // PUT IT RIGHT ON THE NODE
            x = targetX;
            y = targetY;
            
            }
                
            System.out.println(this.carType+"  Target X : " + targetX);
            System.out.println(this.carType+"  Target Y : " + targetY);
            
            if(movePath.size() > 1) 
            { 
                // AND TARGET THE NEXT NODE IN THE PATH
                if(this.carType ==PLAYER)
                {
                targetX = movePath.get(0) + LEVEL1X;
                targetY = movePath.get(1) + LEVEL1Y;
                
                }
                else
                {
                    targetX =LEVEL1X +  movePath.get(movePathIndex);
                    targetY = LEVEL1Y + movePath.get(movePathIndex +1);
                }
                if(this.carType==PLAYER)
                {
                movePath.remove(1); 
                 movePath.remove(0);
                }
            } 
            
             
            // START THE TILE MOVING AGAIN AND RANDOMIZE IT'S SPEED
           
            
            // AND ON TO THE NEXT PATH FOR THE NEXT TIME WE PICK A TARGET
             if(this.carType!=PLAYER)
             {
               movePathIndex += 2;
            
             movePathIndex %= (movePath.size() );
             }
        }
        // JUST A NORMAL PATHING UPDATE
        else
        {
            // THIS WILL SIMPLY UPDATE THIS TILE'S POSITION USING ITS CURRENT VELOCITY
            super.update(game);
        }
        
        }
        else
        {
            
                vX = 0;
                vY = 0;
            // THIS WILL SIMPLY UPDATE THIS TILE'S POSITION USING ITS CURRENT VELOCITY
            super.update(game);
        }
        
    }
    
    // METHODS OVERRIDDEN FROM Sprite
    // -update
    
    /**
     * Called each frame, this method ensures that this tile is updated
     * according to the path it is on.
     *
     * @param game The Sorting Hat game this tile is part of.
     */
    @Override
    public void update(MiniGame game)
    {
        // IF WE ARE IN A POST-WIN STATE WE ARE PLAYING THE WIN
        // ANIMATION, SO MAKE SURE THIS TILE FOLLOWS THE PATH
       
        if (movePath!=null)
        {
            
            updateMovePath(game);
          
        }
        
        
        else
            // IF NOT, IF THIS TILE IS ALMOST AT ITS TARGET DESTINATION,
            // JUST GO TO THE TARGET AND THEN STOP MOVING
           
        
            if (calculateDistanceToTarget() < MAX_TILE_VELOCITY && this.carType !=BANDIT && this.carType != POLICE && this.carType != ZOMBIE )
            {
                vX = 0;
                vY = 0;
                x = targetX;
                y = targetY;
                movingToTarget = false;
                
                
            }
            else if (calculateDistanceToTarget() < MAX_TILE_VELOCITY && this.carType ==BANDIT || this.carType == POLICE || this.carType == ZOMBIE )
                loop = true;
            // OTHERWISE, JUST DO A NORMAL UPDATE, WHICH WILL CHANGE ITS POSITION
            // USING ITS CURRENT VELOCITY.
            else
            {
                super.update(game);
                loop = false;
            }
    }
}