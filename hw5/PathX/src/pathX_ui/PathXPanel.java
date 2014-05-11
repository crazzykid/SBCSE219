package pathX_ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import properties_manager.PropertiesManager;
import pathx_data.PathXDataModel;
import static pathx.PathXConstants.*;
import pathx.PathX.PathXPropertyType;
import pathx_data.PathXRecord;
import pathX_ui.PathXCarState;
import static pathX_ui.PathXCarState.MOUSE_OVER_STATE;
import static pathX_ui.PathXCarState.PLAYER_SELECTED;
import pathx_data.PathXGameLevel;
import static pathx_data.PathXGameLevel.*;
import pathx_file.Intersection;
import pathx_file.PathXSpecial;
import pathx_file.Road;

/**
 * This class performs all of the rendering for The Sorting Hat game application.
 *
 * @author Richard McKenna
 */
public class PathXPanel extends JPanel
{
    // THIS IS ACTUALLY OUR Sorting Hat APP, WE NEED THIS
    // BECAUSE IT HAS THE GUI STUFF THAT WE NEED TO RENDER
    private MiniGame game;
    
    // AND HERE IS ALL THE GAME DATA THAT WE NEED TO RENDER
    private PathXDataModel data;
    
    // WE'LL USE THIS TO FORMAT SOME TEXT FOR DISPLAY PURPOSES
    private NumberFormat numberFormatter;
    
    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING UNSELECTED TILES
    private BufferedImage blankTileImage;
    
    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING SELECTED TILES
    private BufferedImage blankTileSelectedImage;
    
    // THIS IS FOR WHEN THE USE MOUSES OVER A TILE
    private BufferedImage blankTileMouseOverImage;
    
    PathXGameLevel playerLevel;
    // private String perfectTime;
    private String pTime;
    
    Ellipse2D.Double recyclableCircle;
    public static Ellipse2D.Double playerCircle;
    Line2D.Double recyclableLine;
    HashMap<Integer, BasicStroke> recyclableStrokes;
    int triangleXPoints[] = {-ONE_WAY_TRIANGLE_WIDTH/2,  -ONE_WAY_TRIANGLE_WIDTH/2,  ONE_WAY_TRIANGLE_WIDTH/2};
    int triangleYPoints[] = {ONE_WAY_TRIANGLE_WIDTH/2, -ONE_WAY_TRIANGLE_WIDTH/2, 0};
    GeneralPath recyclableTriangle;
    Graphics2D first ;
    
    /**
     * This constructor stores the game and data references,
     * which we'll need for rendering.
     *
     * @param initGame The Sorting Hat game that is using
     * this panel for rendering.
     *
     * @param initData The Sorting Hat game data.
     */
    public PathXPanel(MiniGame initGame, PathXDataModel initData)
    {
        game = initGame;
        data = initData;
        
        playerLevel = data.getLevel();
        //perfectWin = 0;
        numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);
        //perfectTime = data.getCurrentLevel().//.gameTimeToText();
        pTime =data.gameTimeToText();
        
        
        // MAKE THE RENDER OBJECTS TO BE RECYCLED
        recyclableCircle = new Ellipse2D.Double(0, 0, INTERSECTION_RADIUS * 2, INTERSECTION_RADIUS * 2);
        recyclableLine = new Line2D.Double(0,0,0,0);
        recyclableStrokes = new HashMap();
        playerCircle = new Ellipse2D.Double(0, 0, INTERSECTION_RADIUS * 2, INTERSECTION_RADIUS * 2);
       
        for (int i = 1; i <= 10; i++)
        {
            recyclableStrokes.put(i, new BasicStroke(i*2));
        }
        
        // MAKING THE TRIANGLE FOR ONE WAY STREETS IS A LITTLE MORE INVOLVED
        recyclableTriangle =  new GeneralPath(   GeneralPath.WIND_EVEN_ODD,
                triangleXPoints.length);
        recyclableTriangle.moveTo(triangleXPoints[0], triangleYPoints[0]);
        for (int index = 1; index < triangleXPoints.length; index++)
        {
            recyclableTriangle.lineTo(triangleXPoints[index], triangleYPoints[index]);
        };
        recyclableTriangle.closePath();
        
    }
    
    
    
    
    // MUTATOR METHODS
    // -setBlankTileImage
    // -setBlankTileSelectedImage
    
    /**
     * This mutator method sets the base image to use for rendering tiles.
     *
     * @param initBlankTileImage The image to use as the base for rendering tiles.
     */
    public void setBlankTileImage(BufferedImage initBlankTileImage)
    {
        blankTileImage = initBlankTileImage;
    }
    
    /**
     * This mutator method sets the base image to use for rendering selected tiles.
     *
     * @param initBlankTileSelectedImage The image to use as the base for rendering
     * selected tiles.
     */
    public void setBlankTileSelectedImage(BufferedImage initBlankTileSelectedImage)
    {
        blankTileSelectedImage = initBlankTileSelectedImage;
    }
    
    public void setBlankTileMouseOverImage(BufferedImage initBlankTileMouseOverImage)
    {
        blankTileMouseOverImage = initBlankTileMouseOverImage;
    }
    
    /**
     * This is where rendering starts. This method is called each frame, and the
     * entire game application is rendered here with the help of a number of
     * helper methods.
     *
     * @param g The Graphics context for this panel.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        try
        {
            // MAKE SURE WE HAVE EXCLUSIVE ACCESS TO THE GAME DATA
            game.beginUsingData();
            
            Graphics2D g2 = (Graphics2D) g;
            
            first = (Graphics2D) g;
            // CLEAR THE PANEL
            super.paintComponent(g);
            
            // RENDER THE BACKGROUND, WHICHEVER SCREEN WE'RE ON
            
            
            // ONLY RENDER THIS STUFF IF WE'RE ACTUALLY IN-GAME
            if (!data.notStarted())
            {
                // AND THE TILES
                
                
                // AND THE DIALOGS, IF THERE ARE ANY
                renderDialogs(g);
                
                // RENDERING THE GRID WHERE ALL THE TILES GO CAN BE HELPFUL
                // DURING DEBUGGIN TO BETTER UNDERSTAND HOW THEY RE LAID OUT
                renderGrid(g);
                
                // RENDER THE HEADER
                //  renderHeader(g);
                  
                   
                if(((PathXMiniGame)game).isCurrentScreenState(GAME_SCREEN_STATE))
                {
                    int i =0;
                    renderLevelBackground(g2);
                    renderRoads(g2);
                    renderIntersections(g2);
                    //renderPlayer(g2);
                  // renderPlayerTiles(g); 
                   renderTiles(g);
                   
                 //  if(i ==0)
              //  data.carsMovingAround();
                //data.switchCarOrder();
                   
                   renderSpecials(g);
                    renderStats(g);
                }
            }
            
             if(!((PathXMiniGame)game).isCurrentScreenState(GAME_LEVEL_STATE))
             renderBackground(g);
         
            
            
          if(((PathXMiniGame)game).isCurrentScreenState(GAME_LEVEL_STATE))
            renderMap(g);
            // AND THE BUTTONS AND DECOR
           
            renderGUIControls(g);
            
            
            // AND FINALLY, TEXT FOR DEBUGGING
            renderDebuggingText(g);
        }
        finally
        {
            // RELEASE THE LOCK
            game.endUsingData();
        }
    }
    
    
    // RENDERING HELPER METHODS
    // - renderBackground
    // - renderGUIControls
    // - renderSnake
    // - renderTiles
    // - renderDialogs
    // - renderGrid
    // - renderDebuggingText
    
    /**`
     * Renders the background image, which is different depending on the screen.
     *
     * @param g the Graphics context of this panel.
     */
    public void renderBackground(Graphics g)
    {
        // THERE IS ONLY ONE CURRENTLY SET
        Sprite bg = game.getGUIDecor().get(BACKGROUND_TYPE);
         if (bg.getSpriteType().getSpriteTypeID() != LEVEL_SELECT_BACKGROUND_TYPE )

        renderSprite(g, bg);
    }
    private void renderLevelBackground(Graphics2D g2)
    {
        Viewport viewport = data.getVport();
       
        Image backgroundImage = data.getBackgroundImage();
        
        int viewPortX = viewport.getViewportX();
        int viewPortY = viewport.getViewportY();
        int moveX = VIEWPORT_OFFSET_Y + viewPortX;
        int moveY = VIEWPORT_OFFSET_X + viewPortY;
        
        viewport.setViewportSize(740, 840);
      //  System.out.println("ViewPort Width :"+ viewport.getViewportWidth() + "\nViewPort Height :"+ viewport.getViewportHeight());
       // g2.drawImage(backgroundImage, 165, 210, 740, 620, viewPortX, viewPortY, moveX, moveY , null);
         g2.drawImage(backgroundImage, 165, 0, viewport.getViewportWidth() , viewport.getViewportHeight(), viewport.getViewportX(), viewport.getViewportY(), viewport.getViewportX() + viewport.getViewportWidth(), viewport.getViewportY() + viewport.getViewportHeight(), null);
         
         
    }
    // HELPER METHOD FOR RENDERING THE LEVEL ROADS
    private void renderRoads(Graphics2D g2)
    {
        // GO THROUGH THE ROADS AND RENDER ALL OF THEM
        Viewport viewport = data.getVport();
        
        Iterator<Road> it = data.roadsIterator();
        g2.setStroke(recyclableStrokes.get(INT_STROKE));
        while (it.hasNext())
        {
            Road road = it.next();
            if (!data.isSelectedRoad(road))
                renderRoad(g2, road, INT_OUTLINE_COLOR);
        }
        // AND RENDER THE SELECTED ONE, IF THERE IS ONE
        Road selectedRoad = data.getSelectedRoad();
        if (selectedRoad != null)
        {
            renderRoad(g2, selectedRoad, HIGHLIGHTED_COLOR);
        }
    }
    // HELPER METHOD FOR RENDERING A SINGLE ROAD
    private void renderRoad(Graphics2D g2, Road road, Color c)
    {
        Viewport viewport = data.getVport();
        g2.setColor(c);
        int strokeId = road.getSpeedLimit()/10;
        
        // CLAMP THE SPEED LIMIT STROKE
        if (strokeId < 1) strokeId = 1;
        if (strokeId > 10) strokeId = 10;
        g2.setStroke(recyclableStrokes.get(strokeId));
        
        // LOAD ALL THE DATA INTO THE RECYCLABLE LINE
        recyclableLine.x1 = road.getNode1().x-viewport.getViewportX()+ VIEWPORT_OFFSET_X-160;
        recyclableLine.y1 = road.getNode1().y-viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220;
        recyclableLine.x2 = road.getNode2().x-viewport.getViewportX()+ VIEWPORT_OFFSET_X-160;
        recyclableLine.y2 = road.getNode2().y-viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220;
        
        // AND DRAW IT
         if( road.getNode1().y-viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220 >100)
        g2.draw(recyclableLine);
        
        // AND IF IT'S A ONE WAY ROAD DRAW THE MARKER
        if (road.isOneWay())
        {
            this.renderOneWaySignalsOnRecyclableLine(g2);
        }
    }
    int count = 0;
       
    public static double playerX = 0;
    public static double playerY = 0;
    
    private void renderPlayer(Graphics2D g2)
    {
         Viewport viewport = data.getVport();
       
                  g2.setColor(Color.BLUE);
       // if (count == 0) {
            playerCircle.x = playerLevel.getStartingLocation().x - viewport.getViewportX() + playerLevel.getPlayerX()  - INTERSECTION_RADIUS + playerX;
            playerCircle.y = playerLevel.getStartingLocation().y - viewport.getViewportY() + playerLevel.getPlayerY() - INTERSECTION_RADIUS  + playerY;
            //count++;
      //  }
               
                    
                    if(data.isPlayerSelected() )
                {
                     g2.setColor(Color.PINK);
                } else
                {
                    g2.setColor(Color.BLUE);
                }
                Stroke s = recyclableStrokes.get(INT_STROKE);
                g2.setStroke(s);
                // if( intersection.y -viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220 >100)
               // g2.draw(recyclableCircle);
                
                 g2.fill(playerCircle);
                    g2.draw(playerCircle);
                    
                    
    }
 
    
    public void renderPlayerMove()
    {
           
//        first.setColor(Color.BLUE);
//           
//            playerCircle.x = viewport.getViewportX()+ VIEWPORT_OFFSET_X + playerX;
//              playerCircle.y = ((PathXDataModel)data).getMousePressY() + playerY;
//                
//             
//                first.fill(playerCircle);
//                    first.draw(playerCircle);
//        
    }
    // HELPER METHOD FOR RENDERING AN INTERSECTION
    private void renderIntersections(Graphics2D g2)
    {
        Viewport viewport = data.getVport();
        
        Iterator<Intersection> it = data.intersectionsIterator();
        while (it.hasNext())
        {
            Intersection intersection = it.next();
            
            // ONLY RENDER IT THIS WAY IF IT'S NOT THE START OR DESTINATION
            // AND IT IS IN THE VIEWPORT
            if ((!data.isStartingLocation(intersection))
                    && (!data.isDestination(intersection))
                    ) // && viewport.isCircleBoundingBoxInsideViewport(intersection.x, intersection.y, INTERSECTION_RADIUS))
            {
                // FIRST FILL
                if (intersection.isOpen())
                {
                    g2.setColor(OPEN_INT_COLOR);
                } else
                {
                    g2.setColor(CLOSED_INT_COLOR);
                }
                recyclableCircle.x = intersection.x - viewport.getViewportX()+ VIEWPORT_OFFSET_X-160 - INTERSECTION_RADIUS;
                recyclableCircle.y = intersection.y - viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220 - INTERSECTION_RADIUS;
                g2.fill(recyclableCircle);
                
                // AND NOW THE OUTLINE
                
                 
                
            
            
                if (data.isSelectedIntersection(intersection))
                {
                    g2.setColor(HIGHLIGHTED_COLOR);
                } else
                {
                    g2.setColor(INT_OUTLINE_COLOR);
                }
                Stroke s = recyclableStrokes.get(INT_STROKE);
                g2.setStroke(s);
                 if( intersection.y -viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220 >100)
                g2.draw(recyclableCircle);
            }
        }
        
        // AND NOW RENDER THE START AND DESTINATION LOCATIONS
        Image startImage = data.getStartingLocationImage();
        Intersection startInt = data.getLevel().getStartingLocation();
        renderIntersectionImage(g2, startImage, startInt);
        Image destImage = data.getDesinationImage();
        Intersection destInt = data.getDestination();
        renderIntersectionImage(g2, destImage, destInt);
    }
// HELPER METHOD FOR RENDERING AN IMAGE AT AN INTERSECTION, WHICH IS
    // NEEDED BY THE STARTING LOCATION AND THE DESTINATION
    private void renderIntersectionImage(Graphics2D g2, Image img, Intersection i)
    {
        Viewport viewport = data.getVport();
        // CALCULATE WHERE TO RENDER IT
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        int x1 = i.x-(w/2);
        int y1 = i.y-(h/2);
        int x2 = x1 + img.getWidth(null);
        int y2 = y1 + img.getHeight(null);
        
        // ONLY RENDER IF INSIDE THE VIEWPORT
        if (viewport.isRectInsideViewport(x1, y1, x2, y2));
        {
            g2.drawImage(img, x1 - viewport.getViewportX()+ VIEWPORT_OFFSET_X-160, y1 - viewport.getViewportY()+ VIEWPORT_OFFSET_Y-220, null);
        }
    }
    
    // HELPER METHOD FOR RENDERING SOME SCREEN STATS, WHICH CAN
    // HELP WITH DEBUGGING
    private void renderStats(Graphics2D g2)
    {
        Viewport viewport = data.getVport();
        g2.setColor(STATS_TEXT_COLOR);
        g2.setFont(STATS_TEXT_FONT);
        //g2.drawString(MOUSE_SCREEN_POSITION_TITLE + data.getLastMouseX() + ", " + data.getLastMouseY(),
        //         STATS_X, MOUSE_SCREEN_POSITION_Y);
        int levelMouseX = data.getLastMouseX() + viewport.getViewportX();
        int levelMouseY = data.getLastMouseY() + viewport.getViewportY();
        g2.drawString(MOUSE_LEVEL_POSITION_TITLE + levelMouseX + ", " + levelMouseY,
                STATS_X, MOUSE_LEVEL_POSITION_Y);
        g2.drawString(VIEWPORT_POSITION_TITLE + viewport.getViewportX() + ", " + viewport.getViewportY(),
                STATS_X, VIEWPORT_POSITION_Y);
    }
    
    // YOU'LL LIKELY AT THE VERY LEAST WANT THIS ONE. IT RENDERS A NICE
    // LITTLE POINTING TRIANGLE ON ONE-WAY ROADS
    private void renderOneWaySignalsOnRecyclableLine(Graphics2D g2)
    {
        // CALCULATE THE ROAD LINE SLOPE
        double diffX = recyclableLine.x2 - recyclableLine.x1;
        double diffY = recyclableLine.y2 - recyclableLine.y1;
        double slope = diffY/diffX;
        
        // AND THEN FIND THE LINE MIDPOINT
        double midX = (recyclableLine.x1 + recyclableLine.x2)/2.0;
        double midY = (recyclableLine.y1 + recyclableLine.y2)/2.0;
        
        // GET THE RENDERING TRANSFORM, WE'LL RETORE IT BACK
        // AT THE END
        AffineTransform oldAt = g2.getTransform();
        
        // CALCULATE THE ROTATION ANGLE
        double theta = Math.atan(slope);
        if (recyclableLine.x2 < recyclableLine.x1)
            theta = (theta + Math.PI);
        
        // MAKE A NEW TRANSFORM FOR THIS TRIANGLE AND SET IT
        // UP WITH WHERE WE WANT TO PLACE IT AND HOW MUCH WE
        // WANT TO ROTATE IT
        AffineTransform at = new AffineTransform();
        at.setToIdentity();
        at.translate(midX, midY);
        at.rotate(theta);
        g2.setTransform(at);
        
        // AND RENDER AS A SOLID TRIANGLE
        g2.fill(recyclableTriangle);
        
        // RESTORE THE OLD TRANSFORM SO EVERYTHING DOESN'T END UP ROTATED 0
        g2.setTransform(oldAt);
    }
    /**
     * Renders all the GUI decor and buttons.
     *
     * @param g this panel's rendering context.
     */
    public void renderGUIControls(Graphics g)
    {
        // GET EACH DECOR IMAGE ONE AT A TIME
        
        
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites)
        {
            if (s.getSpriteType().getSpriteTypeID() != BACKGROUND_TYPE )
                if (s.getSpriteType().getSpriteTypeID() != LEVEL_SELECT_BACKGROUND_TYPE )
                    renderSprite(g, s);
            
          //  if (s.getSpriteType().getSpriteTypeID() == LEVEL_SELECT_BACKGROUND_TYPE)
                
        }
        
        // AND NOW RENDER THE BUTTONS
        Collection<Sprite> buttonSprites = game.getGUIButtons().values();
        for (Sprite s : buttonSprites)
        {
            renderSprite(g, s);
        }
        
        Collection<Sprite> dialogsSprites = game.getGUIDialogs().values();
        for (Sprite s : dialogsSprites)
        {
            renderSprite(g, s);
        }
        Collection<Sprite> buttonSprites2 = game.getGUIButtons().values();
        for (Sprite s : buttonSprites2)
        {
            if (s.getSpriteType().getSpriteTypeID() == HELP_QUIT_TYPE2
                    ||s.getSpriteType().getSpriteTypeID() == HELP_QUIT_TYPE)
                renderSprite(g, s);
            
            
            if (s.getState().equals(PathXCarState.VISIBLE_STATE.toString())
                    || s.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()))
            {
                if (s.getSpriteType().getSpriteTypeID() == HELP_QUIT_TYPE2 )
                    renderStats(g);
            }
        }
        
    }
    
    public void  renderSpecials(Graphics g)
    {
         Viewport viewport = data.getVport();
        
       Iterator it = data.getLevel().getSpecial();
       
       PathXSpecial spec;
       while(it.hasNext())
       {
        
           spec = (PathXSpecial)it.next();
            Image img = game.loadImage(CURSORS_PATH + spec.getImageLocation());
           
             g.drawImage(img, spec.getX()-viewport.getViewportX(),spec.getY()-viewport.getViewportY(), img.getWidth(null), img.getHeight(null), null);
       }
        
    }
    public void renderMap(Graphics g)
    {
        
        
         Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites)
        {
            //if (s.getSpriteType().getSpriteTypeID() != BACKGROUND_TYPE )
            //    if (s.getSpriteType().getSpriteTypeID() != LEVEL_SELECT_BACKGROUND_TYPE )
            //        renderSprite(g, s);
            
            if (s.getSpriteType().getSpriteTypeID() == LEVEL_SELECT_BACKGROUND_TYPE)
            {  
       
        Viewport viewport = data.getVport2();
        
        //ArrayList<PathXGameLevel>  levelLocation = data.getLevelLocation();
        
        PathXGameLevel levelLocation = data.getLevel();
        int viewPortX = viewport.getViewportX();
        int viewPortY = viewport.getViewportY();
        int moveX = VIEWPORT_OFFSET_Y + viewPortX;
        int moveY = VIEWPORT_OFFSET_X + viewPortY;
        SpriteType bgST = s.getSpriteType();
        Image img = bgST.getStateImage(s.getState());
        
   
        viewport.setGameWorldSize(img.getWidth(null), img.getHeight(null));
        viewport.setNorthPanelHeight(100);
        viewport.setViewportSize(740, 620);
       
        
        viewport.setLevelDimensions(img.getWidth(null), img.getHeight(null));
        g.drawImage(img, 10, 120, viewport.getViewportWidth(), viewport.getViewportHeight(), viewport.getViewportX(), viewport.getViewportY(), (viewport.getViewportX() + viewport.getViewportWidth()), (viewport.getViewportY() + viewport.getViewportHeight()), null);

     

              //  System.out.println(" Move X "+ (viewport.getViewportX() + viewport.getViewportWidth()) + " Move Y " + (viewport.getViewportY() + viewport.getViewportHeight()) );
        
     //   System.out.println(viewport.getViewportWidth()+"       "+ viewport.getViewportHeight());
      //  System.out.println("X & Y :  "+viewport.getViewportY() +"     "+viewport.getViewportX() );
       // g.drawImage(img, 10, 210, 740, 620, viewPortX, viewPortY, moveX, moveY , null);
        
        // RENDER THE LEVELS
        Collection<Sprite> levelSprites = game.getGUIButtons().values();
        
        
        for (Sprite st : levelSprites)
        {
             
            if (st.getState().equals(PathXCarState.RED_STATE.toString()) 
                    || st.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()))
            {
               
             
                    
                if (st.getSpriteType().getSpriteTypeID().equals(GAME_PLAY_LEVEL_RED_TYPE1) )
                {
                   
                    
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock() )
                    {
                     
                        
                        int x = (LEVEL_OFFSET_LOCATION_X1 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y1 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                        
                    }
                    if( st.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString())  )
                        g.drawString(NAMELEVEL1, 300, 300);
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE2 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock() )
                    {
                       int x = (LEVEL_OFFSET_LOCATION_X2 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y2 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE3 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock() )
                    {
                       int x = (LEVEL_OFFSET_LOCATION_X3 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y3 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE4 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X4 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y4 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE5 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X5 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y5 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE6 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X6 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y6 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE7 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X7 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y7 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE8 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X8 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y8 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE9 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X9 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y9 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE10 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X10 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y10 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE11 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X11 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y11 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE12 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X12 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y12 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE13 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                       int x = (LEVEL_OFFSET_LOCATION_X13 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y13 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE14 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X14 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y14 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE15 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X15 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y15 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE16 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X16 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y16 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE17 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X17 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y17 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE18 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                       int x = (LEVEL_OFFSET_LOCATION_X18 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y18 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE19 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X19 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y19 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE20 )
                {
                    if(levelLocation.getLevelState().equals(RED_STATE)
                            && !levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X20 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y20 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
            }
            if (st.getState().equals(PathXCarState.GREEN_STATE.toString()) 
                    || st.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()))
            {
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE1 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock() )
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X1 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y1 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                          //  g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                   if( st.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()) )
                           
                        g.drawString(NAMELEVEL1, 300, 300);
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE2 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock() )
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X2 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y2 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE3 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock() )
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X3 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y3 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE4 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X4 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y4 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE5 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X5 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y5 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE6 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X6 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y6 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE7 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X7 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y7 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE8 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                       int x = (LEVEL_OFFSET_LOCATION_X8 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y8 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE9 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X9 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y9 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE10 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X10 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y10 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE11 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X11 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y11 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE12 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X12 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y12 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE13 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X13 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y13-((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE14 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X14 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y14 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE15 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X15 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y15 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE16 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X16 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y16 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE17 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X17 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y17 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE18 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X18 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y18 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE19 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X19 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y19 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE20 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && levelLocation.getCompletedLevel()
                            && levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X20 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y20 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
            }
            if (st.getState().equals(PathXCarState.WHITE_STATE.toString()) 
                    || st.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()))
            {
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE1 )
                     
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock() )
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X1 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y1 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                       //     g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                      //      
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                     if( st.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()) )
                        g.drawString(NAMELEVEL1, 300, 300);
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE2 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock() )
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X2 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y2 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE3 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock() )
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X3 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y3 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE4 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X4 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y4 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE5 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X5 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y5 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE6 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X6 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y6 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE7 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X7 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y7 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE8 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X8 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y8 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE9 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X9 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y9 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE10 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X10 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y10 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE11 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X11 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y11 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE12 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X12 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y12 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE13 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X13 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y13 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE14 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X14 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y14 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE15 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X15 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y15 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE16 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X16 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y16 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE17 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X17 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y17 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE18 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X18 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y18 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE19 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X19 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y19 -((5*viewPortY)/6));
                        if( y>100 && x>=8)
                        {
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
                if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE20 )
                {
                    if(levelLocation.getLevelState().equals(WHITE_STATE)
                            && !levelLocation.getCompletedLevel()
                            && !levelLocation.getStageUnlock())
                    {
                        int x = (LEVEL_OFFSET_LOCATION_X20 -viewPortX);
                        int y = (LEVEL_OFFSET_LOCATION_Y20 -((5*viewPortY)/6));
                        if( y<590 &&  x<=722)
                        {
                            
                           // System.out.println(x);
                            SpriteType r = st.getSpriteType();
                            Image img1 = r.getStateImage(st.getState());
                            
                            g.drawImage(img1, x , y , r.getWidth()-15, r.getHeight()-15, null);
                            
                            st.setX(x);
                            st.setY(y);
                        }
                    }
                    
                }
            }
        }
    }

        }
    }
    
    public void renderHeader(Graphics g)
    {
       // g.setColor(COLOR_ALGORITHM_HEADER);
        
    }
    
    public void renderSnake(Graphics g)
    {
        
        ArrayList<Intersection> snake = data.getSnake();
        int red = 255;
        Viewport viewport = data.getViewport();
        for (Intersection sC : snake)
        {
            int x = sC.getY();//data.calculateGridTileX(sC.getY());
            int y = sC.getX();//data.calculateGridTileY(sC.getX());
            g.setColor(new Color(0, 0, red, 200));
            g.fillRect(x, y, TILE_WIDTH, TILE_HEIGHT);
            red -= COLOR_INC;
            g.setColor(Color.BLACK);
            g.drawRect(x, y, TILE_WIDTH, TILE_HEIGHT);
            
            System.out.println(x);
        }
        
    }
    
    
    
    /**
     * This method renders the on-screen stats that change as
     * the game progresses. This means things like the game time
     * and the number of tiles remaining.
     *
     * @param g the Graphics context for this panel
     */
    public void renderStats(Graphics g)
    {
        Viewport viewport = data.getVport();
       
        String totalBalance = BALANCE+ data.getTotalBalance();
                String Bank = BANK+data.getLevel().getMoney();
            int xa = BALANCE_X;
            int ya = BALANCE_Y;
           
            g.setFont(FONT_NAME);
            g.setColor(COLOR_HEADER);
            g.drawString(totalBalance, xa, ya);
             g.drawString(Bank, xa, ya+35);
        
      
    }
    
    /**
     * Renders all the game tiles, doing so carefully such
     * that they are rendered in the proper order.
     *
     * @param g the Graphics context of this panel.
     */
   
    public void renderTiles(Graphics g)
    {
        Iterator<PathXCar> tilesTorender = data.getAllTiles();
         while (tilesTorender.hasNext())
        {
            PathXCar tile = tilesTorender.next();
            renderTile(g, tile);
        }
        
        // THEN DRAW ALL THE MOVING TILES
        Iterator<PathXCar> movingTiles = data.getMovingTiles();
        while (movingTiles.hasNext())
        {
            PathXCar tile = movingTiles.next();
            renderTile(g, tile);
        }
        
        // AND THE SELECTED TILE, IF THERE IS ONE
       // PathXCar selectedTile = data.getSelectedTile();
      //  if (selectedTile != null)
          //  renderTile(g, selectedTile);
    }
    /**
     * Helper method for rendering the tiles that are currently moving.
     *
     * @param g Rendering context for this panel.
     *
     * @param tileToRender Tile to render to this panel.
     */
   public void renderTile(Graphics g, PathXCar tileToRender)
    {
        
         if (!tileToRender.getState().equals(PathXCarState.INVISIBLE_STATE.toString()) 
                 && !tileToRender.getSpriteType().equals(GAME_BUTTON_PLAYER_TYPE))
         
        // ONLY RENDER VISIBLE TILES
       // if (!tileToRender.getCarType().equals(PLAYER))
        {
            Viewport viewport = data.getVport();
            int correctedTileX = (int)(tileToRender.getX()  - viewport.getViewportX());
            int correctedTileY = (int)(tileToRender.getY() - viewport.getViewportY());
          
            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img,    correctedTileX,
                    correctedTileY,
                    bgST.getWidth(), bgST.getHeight(), null);
        }
          if (!tileToRender.getState().equals(PathXCarState.INVISIBLE_STATE.toString()) 
                 && tileToRender.getSpriteType().equals(GAME_BUTTON_PLAYER_TYPE))
         
        // ONLY RENDER VISIBLE TILES
       // if (!tileToRender.getCarType().equals(PLAYER))
        {
            Viewport viewport = data.getVport();
            int correctedTileX = (int)(tileToRender.getX()  - viewport.getViewportX());
            int correctedTileY = (int)(tileToRender.getY() - viewport.getViewportY());
          
            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img,    correctedTileX,
                    correctedTileY,
                    bgST.getWidth(), bgST.getHeight(), null);
        }
         
         
         
         
    }
    
    
    public void renderPlayerTiles( Graphics g)
    {
      Viewport viewport = data.getVport();
       
       PathXCar renderPlayer = ((PathXMiniGame)game).getFileManager().playerToRender();
       
            int correctedTileX = (int)(renderPlayer.getX());
            int correctedTileY = (int)(renderPlayer.getY() );
        // THEN DRAW ALL THE MOVING TILES
      
        
        SpriteType bgST = renderPlayer.getSpriteType();
            Image img = bgST.getStateImage(renderPlayer.getState());
            g.drawImage(img,  correctedTileX,
                    correctedTileY,
                    bgST.getWidth(), bgST.getHeight(), null);
      
        
    }
    /**
     * Renders the game dialog boxes.
     *
     * @param g This panel's graphics context.
     */
    public void renderDialogs(Graphics g)
    {
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> dialogSprites = game.getGUIDialogs().values();
        for (Sprite s : dialogSprites)
        {
            // RENDER THE DIALOG, NOTE IT WILL ONLY DO IT IF IT'S VISIBLE
            renderSprite(g, s);
        }
    }
    
    /**
     * Renders the s Sprite into the Graphics context g. Note
     * that each Sprite knows its own x,y coordinate location.
     *
     * @param g the Graphics context of this panel
     *
     * @param s the Sprite to be rendered
     */
    public void renderSprite(Graphics g, Sprite s)
    {

        
        // ONLY RENDER THE VISIBLE ONES
        if (!s.getState().equals(PathXCarState.INVISIBLE_STATE.toString()) &&
                !s.getState().equals(PathXCarState.RED_STATE.toString()) &&
                !s.getState().equals(PathXCarState.WHITE_STATE.toString()) &&
                !s.getState().equals(PathXCarState.GREEN_STATE.toString()))
        {
            SpriteType bgST = s.getSpriteType();
            Image img = bgST.getStateImage(s.getState());
            
            g.drawImage(img, (int)s.getX(), (int)s.getY(), bgST.getWidth(), bgST.getHeight(), null);
            
            //System.out.println("s getX :" +(int)s.getX() + " \ns getY :" + (int)s.getY() + " \nbgST W :" +bgST.getWidth() + " \nbgst H :" + bgST.getHeight() );
        }
    }
    
    /**
     * This method renders grid lines in the game tile grid to help
     * during debugging.
     *
     * @param g Graphics context for this panel.
     */
    public void renderGrid(Graphics g)
    {
        // ONLY RENDER THE GRID IF WE'RE DEBUGGING
        if (data.isDebugTextRenderingActive())
        {
            for (int i = 0; i < data.getNumGameGridColumns(); i++)
            {
                for (int j = 0; j < data.getNumGameGridRows(); j++)
                {
                    int x = data.calculateGridTileX(i);
                    int y = data.calculateGridTileY(j);
                    g.drawRect(x, y, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }
    }
    
    /**
     * Renders the debugging text to the panel. Note
     * that the rendering will only actually be done
     * if data has activated debug text rendering.
     *
     * @param g the Graphics context for this panel
     */
    public void renderDebuggingText(Graphics g)
    {
        // IF IT'S ACTIVATED
        if (data.isDebugTextRenderingActive())
        {
            // ENABLE PROPER RENDER SETTINGS
            g.setFont(FONT_DEBUG_TEXT);
            g.setColor(COLOR_DEBUG_TEXT);
            
            // GO THROUGH ALL THE DEBUG TEXT
            Iterator<String> it = data.getDebugText().iterator();
            int x = data.getDebugTextX();
            int y = data.getDebugTextY();
            while (it.hasNext())
            {
                // RENDER THE TEXT
                String text = it.next();
                g.drawString(text, x, y);
                y += 20;
            }
        }
    }
}