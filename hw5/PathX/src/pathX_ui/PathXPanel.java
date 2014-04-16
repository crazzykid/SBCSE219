package pathX_ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import pathx_data.PathXGameLevel;
import static pathx_data.PathXGameLevel.*;

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
    private PathXRecord theRecord;
    private boolean perfectW;
    private int count;
   // private String perfectTime;
    private String pTime;
         
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
        perfectW = false;
        count = 0;
        //perfectWin = 0;
        numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);
        //perfectTime = data.getCurrentLevel().//.gameTimeToText();
        pTime =data.gameTimeToText();
         
    
        
       
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
        
            // CLEAR THE PANEL
            super.paintComponent(g);
        
            // RENDER THE BACKGROUND, WHICHEVER SCREEN WE'RE ON
            renderBackground(g);

            // ONLY RENDER THIS STUFF IF WE'RE ACTUALLY IN-GAME
            if (!data.notStarted())
            {
                // RENDER THE SNAKE
                if (!data.won())
                    renderSnake(g);
                
                // AND THE TILES
                renderTiles(g);
                
                // AND THE DIALOGS, IF THERE ARE ANY
                renderDialogs(g);
                                
                // RENDERING THE GRID WHERE ALL THE TILES GO CAN BE HELPFUL
                // DURING DEBUGGIN TO BETTER UNDERSTAND HOW THEY RE LAID OUT
                renderGrid(g);
                
                // RENDER THE ALGORITHM NAME
                renderHeader(g);
            }

            // AND THE BUTTONS AND DECOR
            renderGUIControls(g);
            
            if (!data.notStarted())
            {
                // AND THE TIME AND TILES STATS
                renderStats(g);
            }
        
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
        renderSprite(g, bg);
    }

    /**
     * Renders all the GUI decor and buttons.
     * 
     * @param g this panel's rendering context.
     */
    public void renderGUIControls(Graphics g)
    {
         // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> dialogsSprites = game.getGUIDialogs().values();
        for (Sprite s : dialogsSprites)
        {
            renderSprite(g, s);
        }
        
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites)
        {
            if (s.getSpriteType().getSpriteTypeID() != BACKGROUND_TYPE )
                if (s.getSpriteType().getSpriteTypeID() != LEVEL_SELECT_BACKGROUND_TYPE ) 
                    renderSprite(g, s);
            
             if (s.getSpriteType().getSpriteTypeID() == LEVEL_SELECT_BACKGROUND_TYPE)
                renderMap(g, s);
            
        }
        
        // AND NOW RENDER THE BUTTONS
        Collection<Sprite> buttonSprites = game.getGUIButtons().values();
        for (Sprite s : buttonSprites)
        {
            renderSprite(g, s);
        }
        
        
    }
    
    public void renderMap(Graphics g, Sprite s)
    {
           Viewport viewport = data.getViewport();
           ArrayList<PathXGameLevel>  levelLocation = data.getLevelLocation();
            int viewPortX = viewport.getViewportX();
            int viewPortY = viewport.getViewportY();
           int moveX = VIEWPORT_OFFSET_Y + viewPortX;
           int moveY = VIEWPORT_OFFSET_X + viewPortY;

           SpriteType bgST = s.getSpriteType();
            Image img = bgST.getStateImage(s.getState()); 
             g.drawImage(img, 10, 210, 740, 620, viewPortX, viewPortY, moveX, moveY , null); 
  
              // RENDER THE LEVELS
          Collection<Sprite> levelSprites = game.getGUIButtons().values();
          
       
       for (Sprite st : levelSprites)
       {
               if (st.getState().equals(PathXCarState.RED_STATE.toString()) )
        {
            if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE1 )
            {
                    if(levelLocation.get(0).getLevelState().equals(RED_STATE) 
                            && !levelLocation.get(0).getCompletedLevel()  
                            && levelLocation.get(0).getStageUnlock() )
                    {
                        
                        int []level = levelLocation.get(0).getLevelOffsetLocation(1);
                        
                         int x = (level[0] -(2*viewPortX));
                         int y = (level[1] -((7*viewPortY)/6));
                        if( y>180 && x>0)
                        {
                        SpriteType r = st.getSpriteType();
                         Image img1 = r.getStateImage(st.getState());
                         
                         g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null);
                      
                         st.setX(x);
                         st.setY(y);
                        }    
                     
                 }
                   
            }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE2 )
                   {
                       if(levelLocation.get(1).getLevelState().equals(RED_STATE)
                               && !levelLocation.get(1).getCompletedLevel() 
                               && levelLocation.get(1).getStageUnlock() )
                        {
                          int x = ((int)st.getX() -(2*viewPortX));
                         int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>180 && x>0)
                            {
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE3 )
                   {
                       if(levelLocation.get(2).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(2).getCompletedLevel()
                               && levelLocation.get(2).getStageUnlock() )
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE4 )
                   {
                       if(levelLocation.get(3).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(3).getCompletedLevel()
                               && levelLocation.get(3).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE5 )
                   {
                       if(levelLocation.get(4).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(4).getCompletedLevel()
                               && levelLocation.get(4).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE6 )
                   {
                       if(levelLocation.get(5).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(5).getCompletedLevel()
                               && levelLocation.get(4).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE7 )
                   {
                       if(levelLocation.get(6).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(6).getCompletedLevel()
                               && levelLocation.get(6).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE8 )
                   {
                       if(levelLocation.get(7).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(7).getCompletedLevel()
                               && levelLocation.get(7).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE9 )
                   {
                       if(levelLocation.get(8).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(8).getCompletedLevel()
                               && levelLocation.get(8).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE10 )
                   {
                       if(levelLocation.get(9).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(9).getCompletedLevel()
                               && levelLocation.get(9).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE11 )
                   {
                       if(levelLocation.get(10).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(10).getCompletedLevel()
                               && levelLocation.get(10).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE12 )
                   {
                       if(levelLocation.get(11).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(11).getCompletedLevel()
                               && levelLocation.get(11).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE13 )
                   {
                       if(levelLocation.get(12).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(12).getCompletedLevel()
                               && levelLocation.get(12).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                   if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE14 )
                   {
                       if(levelLocation.get(13).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(13).getCompletedLevel()
                               && levelLocation.get(13).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                    if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE15 )
                   {
                       if(levelLocation.get(14).getLevelState().equals(RED_STATE)
                               && !levelLocation.get(14).getCompletedLevel()
                               && levelLocation.get(14).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                      if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE16 )
                   {
                       if(levelLocation.get(15).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(15).getCompletedLevel()
                               && levelLocation.get(15).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                        if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE17 )
                   {
                       if(levelLocation.get(16).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(16).getCompletedLevel()
                               && levelLocation.get(16).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                          if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE18 )
                   {
                       if(levelLocation.get(17).getLevelState().equals(RED_STATE)
                               && !levelLocation.get(17).getCompletedLevel()
                               && levelLocation.get(17).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                            if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE19 )
                   {
                       if(levelLocation.get(18).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(18).getCompletedLevel()
                               && levelLocation.get(18).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                              if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE20 )
                   {
                       if(levelLocation.get(19).getLevelState().equals(RED_STATE) 
                               && !levelLocation.get(19).getCompletedLevel()
                               && levelLocation.get(19).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
        }
                  if (st.getState().equals(PathXCarState.GREEN_STATE.toString()) )
        {
            if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE1 )
            {
                    if(levelLocation.get(0).getLevelState().equals(WHITE_STATE) 
                            && levelLocation.get(0).getCompletedLevel()  
                            && levelLocation.get(0).getStageUnlock() )
                    {
                        int x = ((int)st.getX() -(2*viewPortX));
                        int y = (((int)st.getY()) -((7*viewPortY)/6));
                        if( y>180 && x>0)
                        {
                        SpriteType r = st.getSpriteType();
                         Image img1 = r.getStateImage(st.getState());
                         g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }    
                 }
                   
            }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE2 )
                   {
                       if(levelLocation.get(1).getLevelState().equals(WHITE_STATE)
                               && levelLocation.get(1).getCompletedLevel() 
                               && levelLocation.get(1).getStageUnlock() )
                        {
                          int x = ((int)st.getX() -(2*viewPortX));
                         int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>180 && x>0)
                            {
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE3 )
                   {
                       if(levelLocation.get(2).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(2).getCompletedLevel()
                               && levelLocation.get(2).getStageUnlock() )
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE4 )
                   {
                       if(levelLocation.get(3).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(3).getCompletedLevel()
                               && levelLocation.get(3).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE5 )
                   {
                       if(levelLocation.get(4).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(4).getCompletedLevel()
                               && levelLocation.get(4).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE6 )
                   {
                       if(levelLocation.get(5).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(5).getCompletedLevel()
                               && levelLocation.get(4).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE7 )
                   {
                       if(levelLocation.get(6).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(6).getCompletedLevel()
                               && levelLocation.get(6).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE8 )
                   {
                       if(levelLocation.get(7).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(7).getCompletedLevel()
                               && levelLocation.get(7).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE9 )
                   {
                       if(levelLocation.get(8).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(8).getCompletedLevel()
                               && levelLocation.get(8).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE10 )
                   {
                       if(levelLocation.get(9).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(9).getCompletedLevel()
                               && levelLocation.get(9).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE11 )
                   {
                       if(levelLocation.get(10).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(10).getCompletedLevel()
                               && levelLocation.get(10).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE12 )
                   {
                       if(levelLocation.get(11).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(11).getCompletedLevel()
                               && levelLocation.get(11).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE13 )
                   {
                       if(levelLocation.get(12).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(12).getCompletedLevel()
                               && levelLocation.get(12).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                   if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE14 )
                   {
                       if(levelLocation.get(13).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(13).getCompletedLevel()
                               && levelLocation.get(13).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                    if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE15 )
                   {
                       if(levelLocation.get(14).getLevelState().equals(WHITE_STATE)
                               && levelLocation.get(14).getCompletedLevel()
                               && levelLocation.get(14).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                      if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE16 )
                   {
                       if(levelLocation.get(15).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(15).getCompletedLevel()
                               && levelLocation.get(15).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                        if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE17 )
                   {
                       if(levelLocation.get(16).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(16).getCompletedLevel()
                               && levelLocation.get(16).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                          if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE18 )
                   {
                       if(levelLocation.get(17).getLevelState().equals(WHITE_STATE)
                               && levelLocation.get(17).getCompletedLevel()
                               && levelLocation.get(17).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                            if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE19 )
                   {
                       if(levelLocation.get(18).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(18).getCompletedLevel()
                               && levelLocation.get(18).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                              if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_GREEN_TYPE20 )
                   {
                       if(levelLocation.get(19).getLevelState().equals(WHITE_STATE) 
                               && levelLocation.get(19).getCompletedLevel()
                               && levelLocation.get(19).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
        }
                   if (st.getState().equals(PathXCarState.WHITE_STATE.toString()) )
        {
            if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE1 )
            {
                    if(levelLocation.get(0).getLevelState().equals(WHITE_STATE) 
                            && !levelLocation.get(0).getCompletedLevel()  
                            && !levelLocation.get(0).getStageUnlock() )
                    {
                        int x = ((int)st.getX() -(2*viewPortX));
                        int y = (((int)st.getY()) -((7*viewPortY)/6));
                        if( y>180 && x>0)
                        {
                        SpriteType r = st.getSpriteType();
                         Image img1 = r.getStateImage(st.getState());
                         g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }    
                 }
                   
            }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE2 )
                   {
                       if(levelLocation.get(1).getLevelState().equals(WHITE_STATE)
                               && !levelLocation.get(1).getCompletedLevel() 
                               && !levelLocation.get(1).getStageUnlock() )
                        {
                          int x = ((int)st.getX() -(2*viewPortX));
                         int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>180 && x>0)
                            {
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE3 )
                   {
                       if(levelLocation.get(2).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(2).getCompletedLevel()
                               && !levelLocation.get(2).getStageUnlock() )
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE4 )
                   {
                       if(levelLocation.get(3).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(3).getCompletedLevel()
                               && !levelLocation.get(3).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE5 )
                   {
                       if(levelLocation.get(4).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(4).getCompletedLevel()
                               && !levelLocation.get(4).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE6 )
                   {
                       if(levelLocation.get(5).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(5).getCompletedLevel()
                               && !levelLocation.get(4).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                 if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE7 )
                   {
                       if(levelLocation.get(6).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(6).getCompletedLevel()
                               && !levelLocation.get(6).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE8 )
                   {
                       if(levelLocation.get(7).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(7).getCompletedLevel()
                               && !levelLocation.get(7).getStageUnlock())
                        {
                            int x = ((int)st.getX() -(2*viewPortX));
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE9 )
                   {
                       if(levelLocation.get(8).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(8).getCompletedLevel()
                               && !levelLocation.get(8).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE10 )
                   {
                       if(levelLocation.get(9).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(9).getCompletedLevel()
                               && !levelLocation.get(9).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE11 )
                   {
                       if(levelLocation.get(10).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(10).getCompletedLevel()
                               && !levelLocation.get(10).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE12 )
                   {
                       if(levelLocation.get(11).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(11).getCompletedLevel()
                               && !levelLocation.get(11).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                  if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE13 )
                   {
                       if(levelLocation.get(12).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(12).getCompletedLevel()
                               && !levelLocation.get(12).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                   if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE14 )
                   {
                       if(levelLocation.get(13).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(13).getCompletedLevel()
                               && !levelLocation.get(13).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                    if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE15 )
                   {
                       if(levelLocation.get(14).getLevelState().equals(WHITE_STATE)
                               && !levelLocation.get(14).getCompletedLevel()
                               && !levelLocation.get(14).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                      if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE16 )
                   {
                       if(levelLocation.get(15).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(15).getCompletedLevel()
                               && !levelLocation.get(15).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                        if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE17 )
                   {
                       if(levelLocation.get(16).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(16).getCompletedLevel()
                               && !levelLocation.get(16).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                          if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE18 )
                   {
                       if(levelLocation.get(17).getLevelState().equals(WHITE_STATE)
                               && !levelLocation.get(17).getCompletedLevel()
                               && !levelLocation.get(17).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                            if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE19 )
                   {
                       if(levelLocation.get(18).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(18).getCompletedLevel()
                               && !levelLocation.get(18).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
                              if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_WHITE_TYPE20 )
                   {
                       if(levelLocation.get(19).getLevelState().equals(WHITE_STATE) 
                               && !levelLocation.get(19).getCompletedLevel()
                               && !levelLocation.get(19).getStageUnlock())
                        {
                            int x = ( (((int)st.getX()*5 )/4)- ((2*viewPortX)) );
                             int y = (((int)st.getY()) -((7*viewPortY)/6));
                            if( y>182 && x>0 )
                            {
                               
                             SpriteType r = st.getSpriteType();
                             Image img1 = r.getStateImage(st.getState());
                             g.drawImage(img1, x, y, r.getWidth()-15, r.getHeight()-15, null); 
                        }
                    }
                     
                   }
        }
       }
    }
    
                  
            
            
            
            
           //System.out.println("s getX :" +(int)s.getX() + " \ns getY :" + (int)s.getY() + " \nbgST W :" +bgST.getWidth() + " \nbgst H :" + bgST.getHeight() );
        
          // if (st.getSpriteType().getSpriteTypeID() == GAME_PLAY_LEVEL_RED_TYPE1 )
         //  {
         //    for(int i=0; i<levelLocation.size(); i++) 
         //    {
         //        if(levelLocation.get(i).getLevelState().equals(RED_STATE) && !levelLocation.get(i).getCompletedLevel())
         //        {
        //             if (!s.getState().equals(PathXCarState.INVISIBLE_STATE.toString()))
      //  {
            //SpriteType bgST = s.getSpriteType();
        //    Image img = bgST.getStateImage(s.getState());
        //    g.drawImage(img, (int)s.getX(), (int)s.getY(), bgST.getWidth(), bgST.getHeight(), null); 
            
           //System.out.println("s getX :" +(int)s.getX() + " \ns getY :" + (int)s.getY() + " \nbgST W :" +bgST.getWidth() + " \nbgst H :" + bgST.getHeight() );
      //  }
      //           }
      //       }
               //if(st.getState().equals(WHITE_STATE.toString()))
              
     //      }
       
         //  int x = TIME_X + TIME_OFFSET;
         //  int y = TIME_Y + TIME_TEXT_OFFSET;
         //   g.drawString(time, x, y);
           
      
    
       
    
    public void renderHeader(Graphics g)
    {
        g.setColor(COLOR_ALGORITHM_HEADER);
        
    }
    
    public void renderSnake(Graphics g)
    {
        /*
        ArrayList<SnakeCell> snake = data.getSnake();
        int red = 255;
        Viewport viewport = data.getViewport();
        for (SnakeCell sC : snake)
        {
            int x = data.calculateGridTileX(sC.col);
            int y = data.calculateGridTileY(sC.row);            
            g.setColor(new Color(0, 0, red, 200));
            g.fillRect(x, y, TILE_WIDTH, TILE_HEIGHT);
            red -= COLOR_INC;
            g.setColor(Color.BLACK);
            g.drawRect(x, y, TILE_WIDTH, TILE_HEIGHT);
        }
        */
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
        /*
        // RENDER THE GAME TIME AND THE TILES LEFT FOR IN-GAME
        if (((SortingHatMiniGame)game).isCurrentScreenState(GAME_SCREEN_STATE) 
                && data.inProgress() || data.isPaused())
        {
            // RENDER THE TILES LEFT
            g.setFont(FONT_TEXT_DISPLAY);
            g.setColor(Color.BLACK);

            // RENDER THE TIME
            String time = data.gameTimeToText();
            int x = TIME_X + TIME_OFFSET;
            int y = TIME_Y + TIME_TEXT_OFFSET;
            g.drawString(time, x, y);
            
            
            // RENDER THE MISCAST
            String miscastCount = ""+data.getBadSpellsCounter();
            int xm = TILE_COUNT_X +TILE_COUNT_OFFSET;
            int ym = TILE_COUNT_Y +TILE_TEXT_OFFSET;
            g.drawString(miscastCount, xm, ym);
            
            
            // RENDER THE Algorithm Display
           //  PropertiesManager props = PropertiesManager.getPropertiesManager();     
           // String algorithmPrompt = props.getProperty(PathXPropertyType.TEXT_LABEL_STATS_ALGORITHM);
           //  g.drawString(algorithmPrompt + algorithm,                   STATS_LEVEL_X, STATS_ALGORITHM_Y);
          //  String algorithm = ""+
                    ///SortingHatRecord algorithm1 = ((SortingHatMiniGame)game).getPlayerRecord();
                    SortingHatRecord record = ((SortingHatMiniGame)game).getPlayerRecord();
            String algorithm =  data.getAlgorithmName();
                //game.getDataModel().//algorithm1.getAlgorithm(data.getCurrentLevel());
            int xa = ALGORITHM_TEMP_TILE_X;
            int ya = ALGORITHM_TEMP_TILE_Y;
           
            g.setFont(FONT_SORT_NAME);
            g.setColor(COLOR_ALGORITHM_HEADER);
            g.drawString(algorithm, xa, ya);
            
            
            
        }        
        
        
        
        /IF THE STATS DIALOG IS VISIBLE, ADD THE TEXTUAL STATS
        if (game.getGUIDialogs().get(STATS_DIALOG_TYPE).getState().equals(SortingHatTileState.VISIBLE_STATE.toString()))
        {
            g.setFont(FONT_STATS);
            g.setColor(COLOR_STATS);
            String currentLevel = data.getCurrentLevel();
            int lastSlash = currentLevel.lastIndexOf("/");
            String levelName = currentLevel.substring(lastSlash+1);
            SortingHatRecord record = ((SortingHatMiniGame)game).getPlayerRecord();

            // GET ALL THE STATS
            String algorithm = record.getAlgorithm(currentLevel);
            int games = record.getGamesPlayed(currentLevel);
            int wins = record.getWins(currentLevel);
            int perfectWin=record.getPerfectWins(currentLevel);
            String perfectTime = record.getPerfectFastTime(currentLevel);
            
           
                
           
                 
           
          //  if(data.getBadSpellsCounter()==0 && data.won() )
          //  {
                
                 
        //   if(perfectTime==null){
         //        System.out.println(" sesstion 1 "+perfectTime);
         //            perfectTime = data.gameTimeToText();
                     
                    // pTime =perfectTime;
         //  }
           //     if((data.gameTimeToText().compareTo(perfectTime))<0)
          //      {
            //    perfectTime = data.gameTimeToText();
            //       System.out.println("Thestint time askahglkdbelsbsdlgbdlghsl");
              //  }
                 
                
                //else 
                 //   perfectTime = perfectTime;
               
           // as
           // }

            // GET ALL THE STATS PROMPTS
            PropertiesManager props = PropertiesManager.getPropertiesManager();            
            String algorithmPrompt = props.getProperty(PathXPropertyType.TEXT_LABEL_STATS_ALGORITHM);
            String gamesPrompt = props.getProperty(PathXPropertyType.TEXT_LABEL_STATS_GAMES);
            String winsPrompt = props.getProperty(PathXPropertyType.TEXT_LABEL_STATS_WINS);
            String perfectWins = props.getProperty(PathXPropertyType.TEXT_LABEL_STATS_PERFECT_WINS);
            String fastPerfectWins = props.getProperty(PathXPropertyType.TEXT_LABEL_STATS_FASTEST_PERFECT_WIN);

            // NOW DRAW ALL THE STATS WITH THEIR LABELS
            int dot = levelName.indexOf(".");
            levelName = levelName.substring(0, dot);
            g.drawString(levelName,                                     STATS_LEVEL_X, STATS_LEVEL_Y);
            g.drawString(algorithmPrompt + algorithm,                   STATS_LEVEL_X, STATS_ALGORITHM_Y);
            g.drawString(gamesPrompt + games,                           STATS_LEVEL_X, STATS_GAMES_Y);
            g.drawString(winsPrompt + wins,                             STATS_LEVEL_X, STATS_WINS_Y);
            
            g.drawString(perfectWins + perfectWin,                      STATS_LEVEL_X, STATS_PERFECT_WINS_Y );
             g.drawString(fastPerfectWins + perfectTime,                STATS_LEVEL_X, STATS_FASTEST_PERFECT_WIN_Y );
             
        }
        */
    }
        
    /**
     * Renders all the game tiles, doing so carefully such
     * that they are rendered in the proper order.
     * 
     * @param g the Graphics context of this panel.
     */
    public void renderTiles(Graphics g)
    {
        /*
        // DRAW THE GRID
        ArrayList<SortingHatTile> tilesToSort = data.getTilesToSort();
        for (int i = 0; i < tilesToSort.size(); i++)
        {
            SortingHatTile tile = tilesToSort.get(i);
            if (tile != null)
                renderTile(g, tile);
        }
        
        // THEN DRAW ALL THE MOVING TILES
        Iterator<SortingHatTile> movingTiles = data.getMovingTiles();
        while (movingTiles.hasNext())
        {
            SortingHatTile tile = movingTiles.next();
            renderTile(g, tile);
        }
        
        // AND THE SELECTED TILE, IF THERE IS ONE
        SortingHatTile selectedTile = data.getSelectedTile();
        if (selectedTile != null)
            renderTile(g, selectedTile);
        */
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
        // ONLY RENDER VISIBLE TILES
        if (!tileToRender.getState().equals(PathXCarState.INVISIBLE_STATE.toString()))
        {
            Viewport viewport = data.getViewport();
            int correctedTileX = (int)(tileToRender.getX());
            int correctedTileY = (int)(tileToRender.getY());

            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img,    correctedTileX, 
                                correctedTileY, 
                                bgST.getWidth(), bgST.getHeight(), null); 
        }
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