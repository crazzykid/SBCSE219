/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pathx_data;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mini_game.MiniGame;
import pathX_ui.PathXCarState;
import pathX_ui.PathXMiniGame;
import static pathx.PathXConstants.CLOSE_ROAD;
import static pathx.PathXConstants.CURSORS_PATH;
import static pathx.PathXConstants.DECREASE_SPEED_LIMIT;
import static pathx.PathXConstants.DEFAULT_CURSOR_IMG;
import static pathx.PathXConstants.EMPTY_GAS_TANK;
import static pathx.PathXConstants.FLAT_TIRE;
import static pathx.PathXConstants.FLYING;
import static pathx.PathXConstants.INCREASE_PLAYER_SPEED;
import static pathx.PathXConstants.INCREASE_SPEED_LIMIT;
import static pathx.PathXConstants.INTANGIBILITY;
import static pathx.PathXConstants.INVINCIBILITY;
import static pathx.PathXConstants.MAKE_LIGHT_GREEN;
import static pathx.PathXConstants.MAKE_LIGHT_RED;
import static pathx.PathXConstants.MINDLESS_TERROR;
import static pathx.PathXConstants.MIND_CONTROL;
import static pathx.PathXConstants.STEAL;
import pathx_file.Intersection;
import pathx_file.Road;


/**
 *
 * @author Crazzykid
 */
public class MouseController implements MouseListener, MouseMotionListener 
{
    // WE'LL NEED TO UPDATE THE MODEL BASED ON THE INTERSECTIONS
    
       PathXDataModel model;
       private PathXMiniGame game;
         private  HashMap<PathXCarState, Cursor> cursors;
         private JFrame theWindow;
    /**
     * Constructor for initializing this controller. It will update the
     * model (i.e. the app data), so it needs a reference to it.
     */
    public MouseController(PathXDataModel initModel, PathXMiniGame game, JFrame initWindow)
    {
        // KEEP THIS TO USE WHEN THE INTERACTIONS OCCUR
        model = initModel;
        theWindow = initWindow;
        this.game = game;
        
        this.initCursors(model);
        
        
    }
    
    
    @Override
    public void mousePressed(MouseEvent me)
    {
        if(model.getStartGame())
        {
        // MAKE SURE THE CANVAS HAS FOCUS SO THAT IT
        // WILL PROCESS THE PROPER KEY PRESSES
        ((JPanel)me.getSource()).requestFocusInWindow();
        
        // THESE ARE CANVAS COORDINATES
        int canvasX = me.getX();
        int canvasY = me.getY();
        System.out.println("canvasX : " + canvasX);
               System.out.println("canvasX : " + canvasY);
             if (model.isAddingSpecial())
        {
            // TRY ADDING AN INTERSECTION
            model.addSpecialAtCanvasLocation(canvasX, canvasY, model.getMode());
             model.setAddingSpecial(false);
        }
        System.out.println("model mode : " + model.getMode());
        // IF WE ARE IN ONE OF THESE MODES WE MAY WANT TO SELECT
        // ANOTHER INTERSECTION ROAD
        //if (model.isNothingSelected()
        //     || model.isPlayerSelected()
        //     || model.isRoadSelected())
        {
        // CHECK TO SEE IF THE USER IS SELECTING A PLAYER
        
        if ( model.findPlayerAtCanvasLocation(canvasX, canvasY))
        {
            // MAKE THIS THE SELECTED INTERSECTION
            model.setPlayerSelected(true);
            model.switchState(PathXCarState.PLAYER_DRAGGED);
            return;
        }
        Intersection i = model.findIntersectionAtCanvasLocation(canvasX, canvasY);
        if (i != null)
        {
            // MAKE THIS THE SELECTED INTERSECTION
            model.setSelectedIntersection(i);
            
            // model.switchEditMode(PXLE_EditMode.INTERSECTION_DRAGGED);
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
        PathXCarState mode =model.getMode();
        if (mode == PathXCarState.PLAYER_DRAGGED)
        {
            // RELEASE IT, BUT KEEP IT AS THE SELECTED ITEM
          //  model.draggPlayer(me.getX() , me.getY());
            //model.switchState(PathXCarState.INTERSECTION_SELECTED);
        }
         model.switchState(PathXCarState.NOTHING_SELECTED);
    }
    
    /**
     * This method will be used to respond to right-button mouse clicks
     * on intersections so that we can toggle them open or closed.
     */
    @Override
    public void mouseClicked(MouseEvent me)
    {
        if(model.getStartGame())
        {
        System.out.println("testing mouseclick function");
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
        System.out.println("testing mouseevent me");
        // WE ONLY CARE IF WE ARE IN INTERSECTION DRAGGED MODE
        PathXCarState mode = model.getMode();
            // if (mode == PathXCarState.PLAYER_DRAGGED)
        
        {
        // DRAG IT
      //  model.moveSelectedPlayer(me.getX(), me.getY());
    }
         
    }
    
    // WE WON'T BE USING THESE METHODS, BUT NEED TO OVERRIDE
    // THEM TO KEEP THE COMPILER HAPPY
    @Override
    public void mouseEntered(MouseEvent me)     {}
    public void mouseExited(MouseEvent me)      {}   
    
    
    
     // INITIALIZES ALL THE CURSORS TO BE SWITCHED ON DEMAND
    private void initCursors(PathXDataModel model)
    {
       Cursor defaultCursor = loadCursor(DEFAULT_CURSOR_IMG);
        Cursor greenLightCursor = loadCursor(MAKE_LIGHT_GREEN);
        Cursor redLightCursor = loadCursor(MAKE_LIGHT_RED);
        Cursor decreaseSpeedLimitCursor = loadCursor(DECREASE_SPEED_LIMIT);
        Cursor increaseSpeedLimitCursor = loadCursor(INCREASE_SPEED_LIMIT);
        Cursor increasePlayerSpeed = loadCursor(INCREASE_PLAYER_SPEED);
        Cursor flatTire = loadCursor (FLAT_TIRE);
        Cursor emptyGasTank = loadCursor(EMPTY_GAS_TANK);
        Cursor closeRoad = loadCursor (CLOSE_ROAD);
        Cursor steal = loadCursor (STEAL);
        Cursor mindControl = loadCursor(MIND_CONTROL);
        Cursor intangibility = loadCursor(INTANGIBILITY);
        Cursor mindlessTerror = loadCursor(MINDLESS_TERROR);
        Cursor flying = loadCursor(FLYING);
        Cursor invincibility = loadCursor(INVINCIBILITY);

        cursors = new HashMap();
        cursors.put(PathXCarState.NOTHING_SELECTED, defaultCursor);
        cursors.put(PathXCarState.MAKE_LIGHT_GREEN, greenLightCursor);
        cursors.put(PathXCarState.MAKE_LIGHT_RED, redLightCursor);
        cursors.put(PathXCarState.DECREASE_SPEED_LIMIT, decreaseSpeedLimitCursor);
        cursors.put(PathXCarState.INCREASE_SPEED_LIMIT, increaseSpeedLimitCursor);
        cursors.put(PathXCarState.INCREASE_PLAYER_SPEED, increasePlayerSpeed);
        cursors.put(PathXCarState.FLAT_TIRE, flatTire);
        cursors.put(PathXCarState.EMPTY_GAS_TANK, emptyGasTank);
        cursors.put(PathXCarState.CLOSE_ROAD, closeRoad);
        cursors.put(PathXCarState.STEAL, steal);
        cursors.put(PathXCarState.MIND_CONTROL, mindControl);
        cursors.put(PathXCarState.INTANGIBILITY, intangibility);
        cursors.put(PathXCarState.MINDLESS_TERROR, mindlessTerror);
        cursors.put(PathXCarState.FLYING, flying);
        cursors.put(PathXCarState.INVINCIBILITY, invincibility);
     
        
        // START WITH THE CORRECT CURSOR
        updateCursor(model.getMode());
    }
     public void updateCursor(PathXCarState editMode)
    {
        Cursor cursorToUse = cursors.get(editMode);
        theWindow.setCursor(cursorToUse);
     
    }
    
    // HELPER METHOD FOR LOADING ONE OF THE CUSTOM CURSORS
    private Cursor loadCursor(String cursorImageFile)
    {
        
        Image img = game.loadImage(CURSORS_PATH + cursorImageFile);
        Point hotSpot = new Point(0,0);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor cursor = toolkit.createCustomCursor(img, hotSpot, cursorImageFile);
        return cursor;
    }
    
    
    
    
    
    
    
}
