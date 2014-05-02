/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx_data;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import mini_game.MiniGame;
import pathX_ui.PathXCarState;
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
    
    /**
     * Constructor for initializing this controller. It will update the
     * model (i.e. the app data), so it needs a reference to it.
     */
    public MouseController(PathXDataModel initModel)
    {
        // KEEP THIS TO USE WHEN THE INTERACTIONS OCCUR
        model = initModel;
    }

    
    
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
                || model.isPlayerSelected()
                || model.isRoadSelected())
        {
            // CHECK TO SEE IF THE USER IS SELECTING A PLAYER
           
            if ( model.findPlayerAtCanvasLocation(canvasX, canvasY))
            {
                // MAKE THIS THE SELECTED INTERSECTION
                model.setPlayerSelected(true);
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
        PathXCarState mode =model.getMode();
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
      //  if (mode == PathXCarState.PLAYER_DRAGGED)
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
