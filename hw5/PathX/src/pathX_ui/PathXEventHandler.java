package pathX_ui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import static pathx.PathXConstants.GAME_SCREEN_STATE;
import static pathx.PathXConstants.MENU_SCREEN_STATE;
import static pathx.PathXConstants.VIEWPORT_INC;
import static pathx.PathXConstants.SCROLL_DOWN;
import static pathx.PathXConstants.SCROLL_LEFT;
import static pathx.PathXConstants.SCROLL_RIGHT;
import static pathx.PathXConstants.SCROLL_UP;
import pathx.PathX;
import javax.swing.JOptionPane;
import mini_game.Sprite;
import mini_game.Viewport;
import static pathx.PathXConstants.GAME_HELP_SCREEN_STATE;
import static pathx.PathXConstants.GAME_LEVEL_STATE;
import static pathx.PathXConstants.GAME_PLAY_LEVEL_RED_TYPE1;
import static pathx.PathXConstants.GAME_SETTINGS_STATE;
import static pathx.PathXConstants.HELP_QUIT_TYPE;
import pathx_data.PathXFileManager;
import pathx_data.PathXDataModel;
import properties_manager.PropertiesManager;
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
    /**
     * Constructor, it just keeps the game for when the events happen.
     */
    public PathXEventHandler(PathXMiniGame initGame)
    {
        game = initGame;
        viewPortX =0;
        viewPortY = 0;
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
        viewport.resetScroll();
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
            
           // 
                  
        }
        
        // RESET THE GAME AND ITS DATA
        //game.reset();        
        
    }
    
    public void respondToStartRequest()
    {
        
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
       public void respondToStageRequest(String stage)
    {
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
      //  if (game.getDataModel().inProgress())
       // {
           //if( game.isCurrentScreenState(GAME_SCREEN))
             
                    game.switchToGameScreen();
                    
      //  }
        // RESET THE GAME AND ITS DATA
       // game.reset();        
    }
       
     public void respondToScrollRequest(String scrollType)
     {
          PathXDataModel data = (PathXDataModel)game.getDataModel();
          Viewport viewport = data.getViewport();
          
          
         if(scrollType.equals(SCROLL_DOWN) )
         {
             if(viewPortY <=600)
             {
                 viewPortY+=50; 
                 viewport.scroll(0, 50);
             }
           
         }
         else if(scrollType.equals(SCROLL_UP))
         {
             if(viewPortY >0)
             {
                 viewPortY+= -50; 
                 viewport.scroll(0, -50);
             }
         }
           else if(scrollType.equals(SCROLL_RIGHT))
           {
               if(viewPortX <850)
             { 
                 viewPortX +=50;
                 viewport.scroll(50, 0);
                
             }
           }
           else if(scrollType.equals(SCROLL_LEFT))
           {
               if(viewPortX>0)
               {
                   viewPortX += -50;
                    viewport.scroll(-50, 0);
                  
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
           if(viewPortX>0)
               {
                   viewPortX += -50;
                    viewport.scroll(-50, 0);
               }
        }
        
        else if (keyCode == KeyEvent.VK_RIGHT)
        {            
            if(viewPortX <850)
             { 
                 viewPortX +=50;
                 viewport.scroll(50, 0);
                
             }
        }
        
        else  if (keyCode == KeyEvent.VK_UP)
        {            
           if(viewPortY >0)
             {
                 viewPortY+= -50; 
                 viewport.scroll(0, -50);
             }
        }
      else     if (keyCode == KeyEvent.VK_DOWN)
        {            
            if(viewPortY <=600)
             {
                 viewPortY+=50; 
                 viewport.scroll(0, 50);
             }
           
        }
         
         
        
           
          
        
    }
}