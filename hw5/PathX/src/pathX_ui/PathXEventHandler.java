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
import pathx_file.Connection;
//import sorting_hat.data.SortingHatDataModel;
//import sorting_hat.file.SortingHatFileManager;

/**
 *
 * @author Richard McKenna & __Lamar Myles_________
 */
public class PathXEventHandler implements MouseListener, MouseMotionListener
{
    // THE SORTING HAT GAME, IT PROVIDES ACCESS TO EVERYTHING
    private PathXMiniGame game;
    private int viewPortX;
    private int viewPortY;
         PathXDataModel model;
    /**
     * Constructor, it just keeps the game for when the events happen.
     */
    public PathXEventHandler(PathXMiniGame initGame)
    {
        game = initGame;
        viewPortX =0;
        viewPortY = 0;
        
        
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
   // public void respondToStageRequest(String stage)
   // {
         public void respondToSelectLevelRequest(String levelFile)
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
      //  if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            // GET THE GAME'S DATA MODEL, WHICH IS ALREADY LOCKED FOR US
            PathXDataModel data = (PathXDataModel)game.getDataModel();
        
            // UPDATE THE DATA
            PathXFileManager fileManager = game.getFileManager();
            fileManager.loadLevel(PATH_DATA + levelFile +".xml");
            data.reset(game);

            // GO TO THE GAME
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
        else     if (keyCode == KeyEvent.VK_M)
        {
            PathXFileManager fileManager = game.getFileManager();
            ArrayList<Connection> pathParser = fileManager.findShortestPath(fileManager.getInter(0), fileManager.getInter(1));
            TreeMap<String, Road> roadMapCopy = fileManager.getRoadMap();
            TreeMap<String, Intersection> intersectionMapCopy = fileManager.getIntersectionMap();
            
            int moveX = 0;
            int moveY = 0;
            //for(int i = 0; i < pathParser.size(); i++) {
                //System.out.println("ID 2: " + connection.Intersection2ID);
                Road aRoad = roadMapCopy.get(pathParser.get(position).road);
                Intersection aIntersection = intersectionMapCopy.get(pathParser.get(position).Intersection2ID);
//                moveX =  Integer.parseInt(pathParser.get(i).Intersection2ID.substring(0, 3));
//                moveY =  Integer.parseInt(pathParser.get(i).Intersection2ID.substring(3, 5));
                System.out.println("X: " + aIntersection.x + "\n" + "Y: " + aIntersection.y);
                //    }
            position++;
            PathXPanel.playerCircle.x = aIntersection.x;
            PathXPanel.playerCircle.y = aIntersection.y;
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
    @Override
    public void mousePressed(MouseEvent me)
    {
        // MAKE SURE THE CANVAS HAS FOCUS SO THAT IT
        // WILL PROCESS THE PROPER KEY PRESSES
        ((JPanel)me.getSource()).requestFocusInWindow();
        
        // THESE ARE CANVAS COORDINATES
        int canvasX = me.getX();
        int canvasY = me.getY();
        
      
        // IF WE ARE IN ONE OF THESE MODES WE MAY WANT TO SELECT
        // ANOTHER INTERSECTION ROAD
       if (model.isNothingSelected()
                || model.isIntersectionSelected()
                || model.isRoadSelected())
        {
            // CHECK TO SEE IF THE USER IS SELECTING A PLAYER
            Intersection i = model.findIntersectionAtCanvasLocation(canvasX, canvasY);
            if (i != null)
            {
                // MAKE THIS THE SELECTED INTERSECTION
                model.setSelectedIntersection(i);
                model.switchState(PathXCarState.PLAYER_DRAGGED);
                return;
            }                      
            
            // IF NO INTERSECTION WAS SELECTED THEN CHECK TO SEE IF 
            // THE USER IS SELECTING A ROAD
            Road r = model.selectRoadAtCanvasLocation(canvasX, canvasY);
            if (r != null)
            {
                // MAKE THIS THE SELECTED ROAD
                model.setSelectedRoad(r);
                model.switchState(PathXCarState.ROAD_SELECTED);
                return;
            }

            // OTHERWISE DESELECT EVERYTHING
            model.unselectEverything();            
        }
        // PERHAPS THE USER IS WANTING TO ADD THE FIRST INTERSECTION OF A ROAD
 
    }
  
    /**
     * This method is called when the user releases the mouse button
     * over the canvas. This will end intersection dragging if that is
     * currently happening.
     */
    @Override
    public void mouseReleased(MouseEvent me)
    {
        // IF WE ARE CURRENTLY DRAGGING AN INTERSECTION
        PathXCarState mode = model.getMode();
        if (mode == PathXCarState.PLAYER_DRAGGED)
        {
            // RELEASE IT, BUT KEEP IT AS THE SELECTED ITEM
            model.switchState(PathXCarState.INTERSECTION_SELECTED);
        }
    }

    /**
     * This method will be used to respond to right-button mouse clicks
     * on intersections so that we can toggle them open or closed.
     */
    @Override
    public void mouseClicked(MouseEvent me)
    {
        // RIGHT MOUSE BUTTON IS TO TOGGLE OPEN/CLOSE INTERSECTION
        if (me.getButton() == MouseEvent.BUTTON3)
        {
            // SEE IF WE CLICKED ON AN INTERSECTION
            Intersection i = model.findIntersectionAtCanvasLocation(me.getX(), me.getY());
            if (i != null)
            {
                // TOGGLE THE INTERSECTION
                i.toggleOpen();
                model.switchState(PathXCarState.NOTHING_SELECTED);
            }            
        }
    }

    /**
     * This method is called every time the user moves the mouse. We
     * use this to keep track of where the mouse is at all times on
     * the canvas, which helps us render the road being added after
     * the user has selected the first intersection.
     */
    @Override
    public void mouseMoved(MouseEvent me)
    {
        // UPDATE THE POSITION
        model.setLastMousePosition(me.getX(), me.getY());
    }
    
    /**
     * This function is called when we drag the mouse across the canvas with
     * the mouse button pressed. We use this to drag items intersections
     * across the canvas.
     */
    @Override
    public void mouseDragged(MouseEvent me)
    {
        // WE ONLY CARE IF WE ARE IN INTERSECTION DRAGGED MODE
        PathXCarState mode = model.getMode();
        if (mode == PathXCarState.PLAYER_DRAGGED)
        {
            // DRAG IT
            model.moveSelectedPlayer(me.getX(), me.getY());
        }    
    }
    
    // WE WON'T BE USING THESE METHODS, BUT NEED TO OVERRIDE
    // THEM TO KEEP THE COMPILER HAPPY
    @Override
    public void mouseEntered(MouseEvent me)     {}    
    public void mouseExited(MouseEvent me)      {}   
}