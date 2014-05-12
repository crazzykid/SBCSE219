package pathX_ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import static pathx.PathXConstants.GAME_SCREEN_STATE;
import static pathx.PathXConstants.MENU_SCREEN_STATE;
import static pathx.PathXConstants.VIEWPORT_INC;
import static pathx.PathXConstants.SCROLL_DOWN;
import static pathx.PathXConstants.SCROLL_LEFT;
import static pathx.PathXConstants.SCROLL_RIGHT;
import static pathx.PathXConstants.SCROLL_UP;
import pathx.PathX;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mini_game.Sprite;
import mini_game.Viewport;
import static pathx.PathXConstants.GAME_DIALOG_STATE;
import static pathx.PathXConstants.GAME_HELP_SCREEN_STATE;
import static pathx.PathXConstants.GAME_LEVEL_STATE;
import static pathx.PathXConstants.GAME_PLAY_LEVEL_RED_TYPE1;
import static pathx.PathXConstants.GAME_SETTINGS_STATE;
import static pathx.PathXConstants.HELP_QUIT_TYPE;
import static pathx.PathXConstants.HELP_QUIT_TYPE2;
import static pathx.PathXConstants.PATH_DATA;
import pathx_file.PathXFileManager;
import pathx_data.PathXDataModel;
import pathx_file.Intersection;
import pathx_file.Road;
import properties_manager.PropertiesManager;
import pathX_ui.PathXPanel.*;
import static pathx.PathXConstants.GAME_SETTINGS_CHECK_BUTTON_TYPE;
import static pathx.PathXConstants.GAME_SETTINGS_MUSIC_BUTTON_TYPE;
import static pathx.PathXConstants.LEAVE_TOWN_TYPE;
import static pathx.PathXConstants.LEVEL1;
import static pathx.PathXConstants.SCROLL_SPEED;
import static pathx.PathXConstants.TRY_AGAIN_TYPE;
import pathx_data.PathXGameLevel;
import pathx_file.Connection;
//import sorting_hat.data.SortingHatDataModel;
//import sorting_hat.file.SortingHatFileManager;

/**
 *
 * @author Richard McKenna & __Lamar Myles_________
 */
public class PathXEventHandler 
{
    // THE SORTING HAT GAME, IT PROVIDES ACCESS TO EVERYTHING
    private PathXMiniGame game;
    private int viewPortX;
    private int viewPortY;
    private int  vPortX;
         PathXDataModel model;
         public static boolean checkButton;
          public static boolean checkMusicButton;
    /**
     * Constructor, it just keeps the game for when the events happen.
     */
    public PathXEventHandler(PathXMiniGame initGame)
    {
        game = initGame;
        viewPortX =0;
        viewPortY = 0;
         vPortX = 0;
         checkButton = false;
         checkMusicButton = false;
         model = (PathXDataModel)game.getDataModel();
    }

    /**
     * Called when the user clicks the close window button.
     */
    public void respondToExitRequest()
    {
        //System.out.println("testing respondtoexitrequest");
        
        
        int closeGame  = JOptionPane.YES_NO_OPTION;
        int Result = JOptionPane.showConfirmDialog(null, "Are You Sure You Want TO Close Path X", "PathX Mini Game ",closeGame);
        
        if(Result==0)
            System.exit(0);
        
    }
    
    /**
     * Called when the user clicks the New button.
     */
    public void respondToHomeRequest()
    {
        
        PathXDataModel data = (PathXDataModel)game.getDataModel();
        Viewport viewport = data.getViewport();
        
        if( game.isCurrentScreenState(GAME_SCREEN_STATE))
            game.switchToLevelSelect();
        else
            game.switchToSplashScreen();
        viewPortX =0;
        viewPortY = 0;
        
        vPortX = 0;
        viewport.resetScroll();
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
            
            //
            
        }
         data.getVport().reset();
            data.getVport2().reset();
        
        // RESET THE GAME AND ITS DATA
        //game.reset();
        
    }
    
 
    
    public void respondToBackRequest()
    {
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        //  if (game.getDataModel().inProgress())
        // {
        //if(
        
        //  if(game.
        game.getDataModel().endGameAsLoss();
        
        
        game.switchToSplashScreen();
        
     
        
        //  }
        // RESET THE GAME AND ITS DATA
        // game.reset();
    }
    public void respondToLeaveTownRequest()
    {
        
        PathXDataModel data = (PathXDataModel)game.getDataModel();
          data.reset(game);

            game.getGUIDialogs().get(GAME_DIALOG_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
             game.getGUIButtons().get(TRY_AGAIN_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
              game.getGUIButtons().get(TRY_AGAIN_TYPE).setEnabled(false);
              game.getGUIButtons().get(LEAVE_TOWN_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
               game.getGUIButtons().get(LEAVE_TOWN_TYPE).setEnabled(false);
            // GO TO THE GAME
            data.getVport().reset();
            data.getVport2().reset();
            game.switchToLevelSelect();
    
    
    }
     public void respondToSettingsCheckMusic()
     {
         
         if( !checkMusicButton)
       {
         game.getGUIButtons().get(GAME_SETTINGS_MUSIC_BUTTON_TYPE).setState(PathXCarState.SELECTED_STATE.toString());
         game.getGUIButtons().get(GAME_SETTINGS_MUSIC_BUTTON_TYPE).setEnabled(true);
               checkMusicButton = true;
       }
         else
         {
               game.getGUIButtons().get(GAME_SETTINGS_MUSIC_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
              game.getGUIButtons().get(GAME_SETTINGS_MUSIC_BUTTON_TYPE).setEnabled(true);
               checkMusicButton = false;
         }
         
     }
    
     public void respondToSettingsCheck()
     {
         
         if( !checkButton)
       {
         game.getGUIButtons().get(GAME_SETTINGS_CHECK_BUTTON_TYPE).setState(PathXCarState.SELECTED_STATE.toString());
         game.getGUIButtons().get(GAME_SETTINGS_CHECK_BUTTON_TYPE).setEnabled(true);
               checkButton = true;
       }
         else
         {
               game.getGUIButtons().get(GAME_SETTINGS_CHECK_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
              game.getGUIButtons().get(GAME_SETTINGS_CHECK_BUTTON_TYPE).setEnabled(true);
               checkButton = false;
         }
         
     }
            
        
        
         public void respondToSelectLevelRequest(String levelFile, int pos)
    {
             
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
      //  if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        
        PathXDataModel data = (PathXDataModel)game.getDataModel();
      PathXGameLevel level = (PathXGameLevel)data.getLevel();
      
      if( level.getStageUnlock())
        {
              System.out.println("levelfile name asfksakahfso aspesa " + levelFile);
            // GET THE GAME'S DATA MODEL, WHICH IS ALREADY LOCKED FOR US
            
            // UPDATE THE DATA
            PathXFileManager fileManager = game.getFileManager();
            fileManager.loadLevel(levelFile, pos);
            data.reset(game);

            data.setCurrentLevel(levelFile);
            // GO TO THE GAME
            data.getVport().reset();
            data.getVport2().reset();
         viewPortX =0;
        viewPortY = 0;
            game.switchToGameScreen();
        }        
    }
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        //  if (game.getDataModel().inProgress())
        // {
        //if( game.isCurrentScreenState(GAME_SCREEN))
        
    //    game.switchToGameScreen();
    //  game.getGUIDialogs().get(GAME_DIALOG_STATE).setState(PathXCarState.VISIBLE_STATE.toString());
    //   game.getGUIButtons().get(HELP_QUIT_TYPE2).setState(PathXCarState.VISIBLE_STATE.toString());
   //     game.getGUIButtons().get(HELP_QUIT_TYPE2).setEnabled(true);
   //     
        //  }
        // RESET THE GAME AND ITS DATA
        // game.reset();
   // }
    
    public void respondToScrollRequest(String scrollType)
    {
        PathXDataModel data = (PathXDataModel)game.getDataModel();
        Viewport viewport = data.getVport2();
        
        
        if(scrollType.equals(SCROLL_DOWN) ) 
        {
            if(viewPortY <=380)
            {
                viewPortY+=SCROLL_SPEED;
                
               // viewport.scroll(0, 50);
              if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                data.moveViewport2(0, SCROLL_SPEED);
               
               if( game.isCurrentScreenState(GAME_SCREEN_STATE))
                       if( viewPortY <= 35)
                        data.moveViewport(0, SCROLL_SPEED);
                      else
                         viewPortY+=-SCROLL_SPEED;
                
                //viewport.move(0, 30);
            }
            
        }
        else if(scrollType.equals(SCROLL_UP))
        {
            if(viewPortY >0)
            {
                viewPortY+= -SCROLL_SPEED;
               // viewport.scroll(0, -50);
                
               // viewport.move(0, -30);
                  if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                 data.moveViewport2(0, -SCROLL_SPEED);
                  
                  if( game.isCurrentScreenState(GAME_SCREEN_STATE))
                       data.moveViewport(0, -SCROLL_SPEED);
            }
        }
     
        else if(scrollType.equals(SCROLL_RIGHT) )
        {
            if(vPortX <462)
            {
             
               vPortX += SCROLL_SPEED;
                //viewport.scroll(50, 0);
                  if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                 data.moveViewport2(SCROLL_SPEED, 0);
                  
                      if( game.isCurrentScreenState(GAME_SCREEN_STATE) )
                          if(vPortX <= 380)
                        data.moveViewport(SCROLL_SPEED, 0);
                    else
                     vPortX+= -SCROLL_SPEED;
            
            }
        }
        else if(scrollType.equals(SCROLL_LEFT))
        {
            if(vPortX>0)
            {
                vPortX += -SCROLL_SPEED;
               // viewport.scroll(-50, 0);
                    if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                 data.moveViewport2(-SCROLL_SPEED, 0);
                    
                         if( game.isCurrentScreenState(GAME_SCREEN_STATE))
                                       data.moveViewport(-SCROLL_SPEED, 0);
               // viewport.move(-30, 0);
                
            }
        }
        
        
    }
    
    public void respondToUndoRequest()
    {
        
        PathXDataModel data = (PathXDataModel)game.getDataModel();
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        
        // ONLY DO THIS IF THE GAME IS NO OVER
        if (game.getDataModel().inProgress())
        {
            // FIND A MOVE IF THERE IS ONE
            // SortTransaction theSwap = data.updateUndo();
            //  if(theSwap!=null)
            {
            //      data.swapTiles(theSwap.getFromIndex(), theSwap.getToIndex());
            //      game.getAudio().play(TheSortingHat.SortingHatPropertyType.AUDIO_CUE_CHEAT.toString(), false);
        }
        }
        
        
    }
    public void respondToPause()
    {
         PathXDataModel data = (PathXDataModel)game.getDataModel();
        if(!data.isPaused())
            data.pause();
        else
            data.unpause();
        
    }
    public void respondToTryAgain()
    {
          
        PathXDataModel data = (PathXDataModel)game.getDataModel();
        
          
         
        game.getGUIDialogs().get(GAME_DIALOG_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
            game.getGUIButtons().get(TRY_AGAIN_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
              game.getGUIButtons().get(TRY_AGAIN_TYPE).setEnabled(false);
              game.getGUIButtons().get(LEAVE_TOWN_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
               game.getGUIButtons().get(LEAVE_TOWN_TYPE).setEnabled(false);
        System.out.println("respond to start game : " );
          
          data.resetPathXCar();
           ((PathXMiniGame)game).getFileManager().restPathXCar();
           
            ((PathXMiniGame)game).getFileManager().reloadPathXCar();
              data.setStartGame(false);
           data.reset(game);
         
    }
    
    public void respondToStartGame()
    {
         
        PathXDataModel data = (PathXDataModel)game.getDataModel();
        
          data.carsMovingAround();
          
          data.setStartGame(true);
          
        
    }
    
    public void respondToQuitDialogRequest()
    {
        
         game.getGUIDialogs().get(GAME_DIALOG_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
         game.getGUIButtons().get(HELP_QUIT_TYPE2).setState(PathXCarState.INVISIBLE_STATE.toString());
    }
    public void respondToQuitRequest()
    {
        
        if( game.isCurrentScreenState(GAME_SCREEN_STATE))
            game.switchToGameScreen();
        
        
        if( game.isCurrentScreenState(MENU_SCREEN_STATE))
            game.switchToSplashScreen();
        
        if( game.isCurrentScreenState(GAME_SETTINGS_STATE))
            game.switchToSettingsScreen();
        
        if( game.isCurrentScreenState(GAME_LEVEL_STATE))
            game.switchToLevelSelect();
        
       
        
        
    }
    
    /**
     * Called when the user clicks a button to select a level.
     */
    public void respondToSelectMenuRequest(String menuType)
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //  System.out.println("Testing MenuType********************* " + menuType);
        //WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            //  GET THE GAME'S DATA MODEL, WHICH IS ALREADY LOCKED FOR US
            PathXDataModel data = (PathXDataModel)game.getDataModel();
            
            // UPDATE THE DATA
            //PathXFileManager fileManager = game.getFileManager();
            //fileManager.loadLevel(MenuType);
            //data.reset(game);
            
            // GO TO THE GAME
            if(menuType.equals("./data/./pathx/IMAGE_PLAY.png"))
                game.switchToLevelSelect();
            
            if(menuType.equals("./data/./pathx/IMAGE_SETTINGS.png"))
                game.switchToSettingsScreen();
            
            if(menuType.equals("./data/./pathx/IMAGE_HELP.png"))
            {
                game.getGUIDialogs().get(GAME_HELP_SCREEN_STATE).setState(PathXCarState.VISIBLE_STATE.toString());
                
                game.getGUIButtons().get(HELP_QUIT_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
                game.getGUIButtons().get(HELP_QUIT_TYPE).setEnabled(true);
                // ACTIVATE THE MENU BUTTONS
                ArrayList<String> levels = props.getPropertyOptionsList(PathX.PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
                for (String level : levels)
                {
                    game.getGUIButtons().get(level).setEnabled(false);
                }
            }
            if(menuType.equals("./data/./pathx/IMAGE_RESET.png"))
            {
                     JOptionPane.showMessageDialog(null, "Reset Button Currently Unavailable") ;
            }
        }
    }
    
    /**
     * Called when the user clicks the Stats button.
     */
    public void respondToDisplayStatsRequest()
    {
        // DISPLAY THE STATS
        // game.displayStats();
    }
    
    public static int position = 0;
    
    /**
     * Called when the user presses a key on the keyboard.
     */
    public void respondToKeyPress(int keyCode)
    {
        PathXDataModel data = (PathXDataModel)game.getDataModel();
        Viewport viewport = data.getViewport();
        // CHEAT BY ONE MOVE. NOTE THAT IF WE HOLD THE C
        // KEY DOWN IT WILL CONTINUALLY CHEAT
        if (keyCode == KeyEvent.VK_LEFT)
        {
            if(vPortX>0)
            {
                vPortX += -SCROLL_SPEED;
               // viewport.scroll(-50, 0);
                    if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                 data.moveViewport2(-SCROLL_SPEED, 0);
                    
                         if( game.isCurrentScreenState(GAME_SCREEN_STATE))
                                       data.moveViewport(-SCROLL_SPEED, 0);
               // viewport.move(-30, 0);
            }
        }
        
        else if (keyCode == KeyEvent.VK_RIGHT)
        {
            if(vPortX <462)
            {
               vPortX += SCROLL_SPEED;
                //viewport.scroll(50, 0);
                  if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                 data.moveViewport2(SCROLL_SPEED, 0);
                  
                      if( game.isCurrentScreenState(GAME_SCREEN_STATE) )
                          if(vPortX <= 380)
                        data.moveViewport(SCROLL_SPEED, 0);
                    else
                     vPortX+= -SCROLL_SPEED;
            }
        }
        
        else  if (keyCode == KeyEvent.VK_UP)
        {
            if(viewPortY >0)
            {
                viewPortY+= -SCROLL_SPEED;
               // viewport.scroll(0, -50);
                
               // viewport.move(0, -30);
                  if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                 data.moveViewport2(0, -SCROLL_SPEED);
                  
                  if( game.isCurrentScreenState(GAME_SCREEN_STATE))
                       data.moveViewport(0, -SCROLL_SPEED);
            }
        }
        else if (keyCode == KeyEvent.VK_DOWN)
        {
            if(viewPortY <=380)
            {
                viewPortY+=SCROLL_SPEED;
                 // viewport.scroll(0, 50);
              if( game.isCurrentScreenState(GAME_LEVEL_STATE))
                data.moveViewport2(0, SCROLL_SPEED);
               
               if( game.isCurrentScreenState(GAME_SCREEN_STATE) )
                   if(viewPortY <= 35)
                data.moveViewport(0, SCROLL_SPEED);
               else
                    viewPortY+=-SCROLL_SPEED;
               
               
               
            }
            
        }
        else if (data.getStartGame())
    {
       if(keyCode == KeyEvent.VK_A)
        {
             data.endGameAsWin();
        }
        
         // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_G)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.MAKE_LIGHT_GREEN);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_R)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.MAKE_LIGHT_RED);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_T)
        {
            // CHANGE THE APP MODE
            data.setAddingSpecial(true);
            data.switchState(PathXCarState.FLAT_TIRE);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_E)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.EMPTY_GAS_TANK);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_H)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.CLOSE_ROAD);
        }
          // CLOSE INTERSECTION
        else if (keyCode == KeyEvent.VK_C)
        {
            // CHANGE THE APP MODE
           
        }
          // OPEN INTERSECTION
        else if (keyCode == KeyEvent.VK_O)
        {
            // CHANGE THE APP MODE
          
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_Q)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.STEAL);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_M)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.MIND_CONTROL);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_B)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.INTANGIBILITY);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_L)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.MINDLESS_TERROR);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_Y)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.FLYING);
        }
          // MAKE GREEN LIGHT
        else if (keyCode == KeyEvent.VK_V)
        {
            // CHANGE THE APP MODE
             data.setAddingSpecial(true);
            data.switchState(PathXCarState.INVINCIBILITY);
        }
        
        else if (keyCode == KeyEvent.VK_ESCAPE)
        {
            // CHANGE THE APP MODE
            data.unselectEverything();
            data.switchState(PathXCarState.NOTHING_SELECTED);
        }
        
        // INCREASE THE SPEED LIMIT ON THE SELECTED ROAD
        else if (keyCode == KeyEvent.VK_X)
        {
      //      data.increaseSelectedRoadSpeedLimit();
        }
        // DECREASE THE SPEED LIMIT ON THE SELECTED ROAD
        else if (keyCode == KeyEvent.VK_Z)
        {
     //       model.decreaseSelectedRoadSpeedLimit();
        }
        
        else if (keyCode == KeyEvent.VK_P)
        {
     //       model.decreaseSelectedRoadSpeedLimit();
        }
        
        else     if (keyCode == KeyEvent.VK_M)
        {
           data.setMove(true);
           
            PathXFileManager fileManager = game.getFileManager();
            ArrayList<Integer> pathParser = fileManager.findPath(fileManager.getInter(0), fileManager.getInter(12));
            System.out.println("Printing the arraylist of integers :  " + pathParser.toString());
            
            TreeMap<String, Road> roadMapCopy = fileManager.getRoadMap();
            TreeMap<String, Intersection> intersectionMapCopy = fileManager.getIntersectionMap();
            data.setMove(false);
            //ArrayList<Connection> str = fileManager.getAllNeighbors(fileManager.getInter(3).getId());
          //  System.out.println("SIZE OF ARRAY: " + str.size());
           // for(int i=0; i < str.size(); i++)
            {
          //      System.out.println(str.get(i).toString());
            }
            
            int moveX = 0;
            int moveY = 0;
            
            fileManager.getTreeRoad();
            //for(int i = 0; i < pathParser.size(); i++) {
                //System.out.println("ID 2: " + connection.Intersection2ID);
//                Road aRoad = roadMapCopy.get(pathParser.get(position).road);
         //       Intersection aIntersection = intersectionMapCopy.get(pathParser.get(position).Intersection2ID);
//                moveX =  Integer.parseInt(pathParser.get(i).Intersection2ID.substring(0, 3));
//                moveY =  Integer.parseInt(pathParser.get(i).Intersection2ID.substring(3, 5));
        //        System.out.println("X: " + aIntersection.x + "\n" + "Y: " + aIntersection.y);
                //    }
            position++;
      //      PathXPanel.playerCircle.x = aIntersection.x;
      //      PathXPanel.playerCircle.y = aIntersection.y;
        }
        
    }
     
    /**
     * Constructor for initializing this controller. It will update the
     * model (i.e. the app data), so it needs a reference to it.
     */
    

    /**
     * Responds to when the user presses the mouse button on the canvas,
     * it may respond in a few different ways depending on what the 
     * current edit mode is.
     */
    }
}