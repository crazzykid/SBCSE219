package pathX_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pathx_data.PathXDataModel;
import mini_game.MiniGame;
import mini_game.MiniGameState;
import static pathx.PathXConstants.*;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import properties_manager.PropertiesManager;
import pathx.PathXConstants;
import pathx.PathX.PathXPropertyType;
import pathx_data.PathXFileManager;
import pathx_data.PathXRecord;
import static pathx_data.PathXGameLevel.*;
import pathx_data.PathXGameLevel;
//import sorting_hat.data.PathXDataModel;

/**
 * This is the actual mini game, as extended from the mini game framework. It
 * manages all the UI elements.
 * 
 * @author Richard McKenna
 */
public class PathXMiniGame extends MiniGame
{
    // THE PLAYER RECORD FOR EACH LEVEL, WHICH LIVES BEYOND ONE SESSION
    private PathXRecord record;

    // HANDLES GAME UI EVENTS
    private PathXEventHandler eventHandler;
    
    // HANDLES ERROR CONDITIONS
    private PathXErrorHandler errorHandler;
    
    // MANAGES LOADING OF LEVELS AND THE PLAYER RECORDS FILES
    private PathXFileManager fileManager;
    
    // THE SCREEN CURRENTLY BEING PLAYED
    private String currentScreenState;
    private Insets marginlessInsets;
    
    private JLabel splashScreenImageLabel;
    private JPanel specialSelectionPanel;
    private ArrayList<JButton> specialButtons;
    private JPanel westToolbar;
    private JButton gameButton;
    private JButton statsButton;
    private JButton helpButton;
    private JButton exitButton;
     private JScrollPane statsScrollPane;
     private ArrayList<PathXGameLevel> levelLocation;
    
    
    // ACCESSOR METHODS
        // - getPlayerRecord
        // - getErrorHandler
        // - getFileManager
        // - isCurrentScreenState
    
    /**
     * Accessor method for getting the player record object, which
     * summarizes the player's record on all levels.
     * 
     * @return The player's complete record.
     */
    public PathXRecord getPlayerRecord() 
    { 
        return record; 
    }

     public ArrayList getGameLevelLocation()
     {
         return levelLocation;
     }
    /**
     * Accessor method for getting the application's error handler.
     * 
     * @return The error handler.
     */
    
    public PathXErrorHandler getErrorHandler()
    {
        return errorHandler;
    }

    /**
     * Accessor method for getting the app's file manager.
     * 
     * @return The file manager.
     */
    public PathXFileManager getFileManager()
    {
        return fileManager;
    }

    /**
     * Used for testing to see if the current screen state matches
     * the testScreenState argument. If it mates, true is returned,
     * else false.
     * 
     * @param testScreenState Screen state to test against the 
     * current state.
     * 
     * @return true if the current state is testScreenState, false otherwise.
     */
    public boolean isCurrentScreenState(String testScreenState)
    {
        return testScreenState.equals(currentScreenState);
    }
    
    // VIEWPORT UPDATE METHODS
        // - initViewport
        // - scroll
        // - moveViewport

     // SERVICE METHODS
        // - displayStats
        // - savePlayerRecord
        // - switchToGameScreen
        // - switchToSplashScreen
        // - updateBoundaries
   
    /**
     * This method displays makes the stats dialog display visible,
     * which includes the text inside.
    
    public void displayStats()
    {
        // MAKE SURE ONLY THE PROPER DIALOG IS VISIBLE
        guiDialogs.get(WIN_DIALOG_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiDialogs.get(STATS_DIALOG_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
    }
     */
    /**
     * This method forces the file manager to save the current player record.
     */
    public void savePlayerRecord()
    {
        fileManager.saveRecord(record);
    }
    
//    private JButton initToolbarButton(JPanel toolbar, PathXPropertyType prop)
//    {
//        // GET THE NAME OF THE IMAGE, WE DO THIS BECAUSE THE
//        // IMAGES WILL BE NAMED DIFFERENT THINGS FOR DIFFERENT LANGUAGES
//        PropertiesManager props = PropertiesManager.getPropertiesManager();
//        String imageName = props.getProperty(prop);
//        
//        // LOAD THE IMAGE
//        Image image = loadImage(imageName);
//        ImageIcon imageIcon = new ImageIcon(image);
//        
//        // MAKE THE BUTTON
//        JButton button = new JButton(imageIcon);
//        button.setMargin(marginlessInsets);
//        
//        // PUT IT IN THE TOOLBAR
//        toolbar.add(button);    
//        
//        // AND SEND BACK THE BUTTON
//        return button;
//    }
//    /**
//     * This method switches the application to the game screen, making
//     * all the appropriate UI controls visible & invisible.
//     */
//    private void initWestToolbar()
//    {
//        // MAKE THE NORTH TOOLBAR, WHICH WILL HAVE FOUR BUTTONS
//        westToolbar = new JPanel();
//        westToolbar.setBackground(Color.LIGHT_GRAY);
//
//        // MAKE AND INIT THE GAME BUTTON
//        gameButton = initToolbarButton(northToolbar, KevinBaconPropertyType.GAME_IMG_NAME);
//        setTooltip(gameButton, KevinBaconPropertyType.GAME_TOOLTIP);
//        gameButton.addActionListener(new ActionListener()
//            {@Override
//            public void actionPerformed(ActionEvent ae)
//                {
//                    eventHandler.respondToSwitchScreenRequest(UIState.PLAY_GAME_STATE);
//                }
//            });
//
//        // MAKE AND INIT THE STATS BUTTON
//        statsButton = initToolbarButton(northToolbar, KevinBaconPropertyType.STATS_IMG_NAME);
//        setTooltip(statsButton, KevinBaconPropertyType.STATS_TOOLTIP);
//        statsButton.addActionListener(new ActionListener()
//            {@Override
//             public void actionPerformed(ActionEvent ae)
//                {
//                    eventHandler.respondToSwitchScreenRequest(UIState.VIEW_STATS_STATE);
//                }
//            });
//
//        // MAKE AND INIT THE HELP BUTTON
//        helpButton = initToolbarButton(northToolbar, KevinBaconPropertyType.HELP_IMG_NAME);
//        setTooltip(helpButton, KevinBaconPropertyType.HELP_TOOLTIP);        
//        helpButton.addActionListener(new ActionListener()
//            {@Override
//             public void actionPerformed(ActionEvent ae)
//                {
//                    eventHandler.respondToSwitchScreenRequest(UIState.VIEW_HELP_STATE);
//                }
//            });
//
//        // MAKE AND INIT THE EXIT BUTTON
//        exitButton = initToolbarButton(northToolbar, KevinBaconPropertyType.EXIT_IMG_NAME);
//        setTooltip(exitButton, KevinBaconPropertyType.EXIT_TOOLTIP);
//        exitButton.addActionListener(new ActionListener()
//            {@Override
//             public void actionPerformed(ActionEvent ae)
//                {
//                    eventHandler.respondToExitRequest(window);
//                }
//            });
//        
//        // AND NOW PUT THE NORTH TOOLBAR IN THE FRAME
//        window.getContentPane().add(northToolbar, BorderLayout.NORTH);
public void switchToLevelSelect()
{
    
        PropertiesManager props = PropertiesManager.getPropertiesManager();
            guiDialogs.get(GAME_HELP_SCREEN_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_LEVEL_STATE);
        
        guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
             guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setEnabled(false);
         for(int i=1; i<21; i++)
        {
        String str = "GAME_PLAY_LEVEL_WHITE_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.WHITE_STATE.toString());
        guiButtons.get(str).setEnabled(true);
        
        str = "GAME_PLAY_LEVEL_RED_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.RED_STATE.toString());
        guiButtons.get(str).setEnabled(true);
        
        str = "GAME_PLAY_LEVEL_GREEN_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.GREEN_STATE.toString());
        guiButtons.get(str).setEnabled(true);
        }
         guiButtons.get(GAME_X_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_X_BUTTON_TYPE).setEnabled(false);
         
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(false);
        }
             guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setEnabled(true);
        
          guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setEnabled(true);
        
          guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setEnabled(true);
        
          guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setEnabled(true);
        
          guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setEnabled(true);
        
          // DEACTIVATE THE SPECIAL BUTTONS
        ArrayList<String> specialButtons = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
        for (String specialButton : specialButtons)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setEnabled(false);
        
        
          // DEACTIVATE THE SPECIAL SELECT BUTTONS
          guiButtons.get(GAME_START_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_START_BUTTON_TYPE).setEnabled(false);
        
        
         guiDecor.get(LEVEL_SELECT_BACKGROUND_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
         
         guiDecor.get(GAME_TOOLBAR_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
         currentScreenState = GAME_LEVEL_STATE;
         
         guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
             guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setEnabled(true);
         
              ArrayList<String> specialButtons1 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
        for (String specialButton : specialButtons1)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        ArrayList<String> specialButtons2 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS2);
        for (String specialButton : specialButtons2)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        ArrayList<String> specialButtons3 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS3);
        for (String specialButton : specialButtons3)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
         ArrayList<String> specialButtons4 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS4);
        for (String specialButton : specialButtons4)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        guiButtons.get(HELP_QUIT_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_QUIT_TYPE).setEnabled(false);
         //currentScreenState = GAME_LEVEL_STATE;
         
        // PLAY THE GAMEPLAY SCREEN SONG
      //  audio.stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString()); 
      //  audio.play(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString(), true);
         
}
    


    public void switchToSettingsScreen()
    {
         guiDialogs.get(GAME_HELP_SCREEN_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SETTINGS_STATE);
        
         guiDialogs.get(GAME_HELP_SCREEN_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
         
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(false);
        }
        
             guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
             guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setEnabled(true);
             
             guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
             guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setEnabled(true);
        
          // DEACTIVATE THE SPECIAL BUTTONS
        ArrayList<String> specialButtons = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
        for (String specialButton : specialButtons)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        
          // DEACTIVATE THE SPECIAL SELECT BUTTONS
          guiButtons.get(GAME_START_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_START_BUTTON_TYPE).setEnabled(false);
        
        guiDecor.get(GAME_TOOLBAR_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.get(LEVEL_SELECT_BACKGROUND_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        
        for(int i=1; i<21; i++)
        {
        String str = "GAME_PLAY_LEVEL_WHITE_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_RED_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_GREEN_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        }
        guiButtons.get(HELP_QUIT_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_QUIT_TYPE).setEnabled(false);
        
        
        
         currentScreenState = GAME_SETTINGS_STATE;

        // PLAY THE GAMEPLAY SCREEN SONG
      //  audio.stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString()); 
      //  audio.play(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString(), true);
         
        
    }
    public void switchToGameScreen()
    {
         marginlessInsets = new Insets(0,0,0,0);
          guiDialogs.get(GAME_HELP_SCREEN_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        
         guiDecor.get(GAME_TOOLBAR_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        
         guiDecor.get(LEVEL_SELECT_BACKGROUND_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
         
         guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
             guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setEnabled(false);
         
          for(int i=1; i<21; i++)
        {
        String str = "GAME_PLAY_LEVEL_WHITE_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_RED_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_GREEN_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        }
          
        // DEACTIVATE THE LEVEL SELECT BUTTONS
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(false);
        }
        
      
          // ACTIVATE THE SPECIAL BUTTONS
        ArrayList<String> specialButtons = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
        for (String specialButton : specialButtons)
        {
           guiButtons.get(specialButton).setState(PathXCarState.VISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(true);
       } 
          
        ArrayList<String> specialButtons2 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS2);
        for (String specialButton : specialButtons2)
        {
           guiButtons.get(specialButton).setState(PathXCarState.VISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(true);
       } 
       
        ArrayList<String> specialButtons3 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS3);
        for (String specialButton : specialButtons3)
        {
           guiButtons.get(specialButton).setState(PathXCarState.VISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(true);
       } 
      
         ArrayList<String> specialButtons4 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS4);
        for (String specialButton : specialButtons4)
        {
           guiButtons.get(specialButton).setState(PathXCarState.VISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(true);
       } 
        
          guiButtons.get(GAME_START_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_START_BUTTON_TYPE).setEnabled(true);
        
        guiButtons.get(GAME_X_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_X_BUTTON_TYPE).setEnabled(true);
        
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setEnabled(true);
        
         guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setEnabled(true);
        
         guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setEnabled(true);
        
         guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setEnabled(true);
        
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setEnabled(true);
        
         guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setEnabled(true);
        
             guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setEnabled(false);
        guiButtons.get(HELP_QUIT_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_QUIT_TYPE).setEnabled(false);
       
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
        //guiButtons.get(NEW_GAME_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
       // guiButtons.get(NEW_GAME_BUTTON_TYPE).setEnabled(true);
        
       // guiButtons.get(BACK_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
       // guiButtons.get(BACK_BUTTON_TYPE).setEnabled(true);
        
        
        //guiButtons.get(UNDO_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
       // guiButtons.get(UNDO_BUTTON_TYPE).setEnabled(true);
        
       // guiDecor.get(MISCASTS_COUNT_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        //guiDecor.get(TIME_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        //guiButtons.get(STATS_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
       // guiButtons.get(STATS_BUTTON_TYPE).setEnabled(true);
       // guiDecor.get(ALGORITHM_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        
        // DEACTIVATE THE LEVEL SELECT BUTTONS
       // ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.LEVEL_OPTIONS);
       // for (String level : levels)
      //  {
       //     guiButtons.get(level).setState(PathXCarState.INVISIBLE_STATE.toString());
       //     guiButtons.get(level).setEnabled(false);
      //  }

        // AND CHANGE THE SCREEN STATE
        currentScreenState = GAME_SCREEN_STATE;

        // PLAY THE GAMEPLAY SCREEN SONG
        audio.stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString()); 
        audio.play(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString(), true);        
    }
    
    /**
     * This method switches the application to the menu screen, making
     * all the appropriate UI controls visible & invisible.
     */    
    public void switchToSplashScreen()
    {
         guiDialogs.get(GAME_HELP_SCREEN_STATE).setState(PathXCarState.INVISIBLE_STATE.toString());
         marginlessInsets = new Insets(0,0,0,0);
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(MENU_SCREEN_STATE);
        
        guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
             guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setEnabled(true);
      for(int i=1; i<21; i++)
        {
        String str = "GAME_PLAY_LEVEL_WHITE_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_RED_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_GREEN_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        }
      
         guiDecor.get(GAME_TOOLBAR_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
         PropertiesManager props = PropertiesManager.getPropertiesManager();
         // ACTIVATE THE MENU BUTTONS
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(PathXCarState.VISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(true);
        }
        
           // DEACTIVATE THE SPECIAL BUTTONS
        
        ArrayList<String> specialButtons = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
        for (String specialButton : specialButtons)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        ArrayList<String> specialButtons2 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS2);
        for (String specialButton : specialButtons2)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
        ArrayList<String> specialButtons3 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS3);
        for (String specialButton : specialButtons3)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
         ArrayList<String> specialButtons4 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS4);
        for (String specialButton : specialButtons4)
        {
           guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
           guiButtons.get(specialButton).setEnabled(false);
       } 
          guiButtons.get(GAME_START_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_START_BUTTON_TYPE).setEnabled(false);
        
        guiButtons.get(GAME_X_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_X_BUTTON_TYPE).setEnabled(false);
        
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setEnabled(false);
        
         guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setEnabled(false);
        
         guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setEnabled(false);
        
         guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setEnabled(false);
        
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setEnabled(false);
        
         guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setEnabled(false);
        
         guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setEnabled(false);
        
        guiDecor.get(LEVEL_SELECT_BACKGROUND_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        
         guiButtons.get(HELP_QUIT_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_QUIT_TYPE).setEnabled(false);
         
        
       // guiButtons.get(NEW_GAME_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
      //  guiButtons.get(NEW_GAME_BUTTON_TYPE).setEnabled(false);
      //  guiButtons.get(BACK_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
      //  guiButtons.get(BACK_BUTTON_TYPE).setEnabled(false);
        
      //  guiButtons.get(UNDO_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
      //  guiButtons.get(UNDO_BUTTON_TYPE).setEnabled(false);
        
      //  guiDecor.get(MISCASTS_COUNT_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
      //  guiDecor.get(TIME_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
     //   guiButtons.get(STATS_BUTTON_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
     //   guiButtons.get(STATS_BUTTON_TYPE).setEnabled(false);
      //  guiDecor.get(ALGORITHM_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
        
        // ACTIVATE THE LEVEL SELECT BUTTONS
        // DEACTIVATE THE LEVEL SELECT BUTTONS
       
    //    ArrayList<String> homeScreens = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
    //    for (String homeScreen : homeScreens)
    //    {
     //      guiButtons.get(homeScreen).setState(PathXCarState.VISIBLE_STATE.toString());
     //      guiButtons.get(homeScreen).setEnabled(true);
    //   } 
         
        // DEACTIVATE THE SPECIAL BUTTONS
       // ArrayList<String> specialButtons = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
      //  for (String specialButton : specialButtons)
      //  {
      ////     guiButtons.get(specialButton).setState(PathXCarState.INVISIBLE_STATE.toString());
     //      guiButtons.get(specialButton).setEnabled(false);
     //  }        

        // DEACTIVATE ALL DIALOGS
     //   guiDialogs.get(WIN_DIALOG_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());
     //   guiDialogs.get(STATS_DIALOG_TYPE).setState(PathXCarState.INVISIBLE_STATE.toString());

        // HIDE THE TILES
        ((PathXDataModel)data).enableTiles(false);

        // MAKE THE CURRENT SCREEN THE MENU SCREEN
        currentScreenState = MENU_SCREEN_STATE;
        
        // AND UPDATE THE DATA GAME STATE
        data.setGameState(MiniGameState.NOT_STARTED);
        
        // PLAY THE WELCOME SCREEN SONG
        audio.play(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString(), true); 
        audio.stop(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString());
    }
    
    // METHODS OVERRIDDEN FROM MiniGame
        // - initAudioContent
        // - initData
        // - initGUIControls
        // - initGUIHandlers
        // - reset
        // - updateGUI

    @Override
    /**
     * Initializes the sound and music to be used by the application.
     */
    public void initAudioContent()
    {
        try
        {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String audioPath = props.getProperty(PathXPropertyType.PATH_AUDIO);

            // LOAD ALL THE AUDIO
            loadAudioCue(PathXPropertyType.AUDIO_CUE_SELECT_TILE);
            loadAudioCue(PathXPropertyType.AUDIO_CUE_DESELECT_TILE);
            loadAudioCue(PathXPropertyType.AUDIO_CUE_GOOD_MOVE);
            loadAudioCue(PathXPropertyType.AUDIO_CUE_BAD_MOVE);
            loadAudioCue(PathXPropertyType.AUDIO_CUE_CHEAT);
            loadAudioCue(PathXPropertyType.AUDIO_CUE_UNDO);
            loadAudioCue(PathXPropertyType.AUDIO_CUE_WIN);
            loadAudioCue(PathXPropertyType.SONG_CUE_MENU_SCREEN);
            loadAudioCue(PathXPropertyType.SONG_CUE_GAME_SCREEN);

            // PLAY THE WELCOME SCREEN SONG
            audio.play(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString(), true);
        }
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException | InvalidMidiDataException | MidiUnavailableException e)
        {
            errorHandler.processError(PathXPropertyType.TEXT_ERROR_LOADING_AUDIO);
        }        
    }

    /**
     * This helper method loads the audio file associated with audioCueType,
     * which should have been specified via an XML properties file.
     */
    private void loadAudioCue(PathXPropertyType audioCueType) 
            throws  UnsupportedAudioFileException, IOException, LineUnavailableException, 
                    InvalidMidiDataException, MidiUnavailableException
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String audioPath = props.getProperty(PathXPropertyType.PATH_AUDIO);
        String cue = props.getProperty(audioCueType.toString());
        audio.loadAudio(audioCueType.toString(), audioPath + cue);        
    }
    
    /**
     * Initializes the game data used by the application. Note
     * that it is this method's obligation to construct and set
     * this Game's custom GameDataModel object as well as any
     * other needed game objects.
     */
    @Override
    public void initData()
    {        
        // INIT OUR ERROR HANDLER
        errorHandler = new PathXErrorHandler(window);
        
        // INIT OUR FILE MANAGER
        fileManager = new PathXFileManager(this);

        // LOAD THE PLAYER'S RECORD FROM A FILE
        record = fileManager.loadRecord();
        
        // INIT OUR DATA MANAGER
        data = new PathXDataModel(this);
    }
    
    /**
     * Initializes the game controls, like buttons, used by
     * the game application. Note that this includes the tiles,
     * which serve as buttons of sorts.
     */
   
    @Override
    public void initGUIControls()
    {
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
 
        
        // FIRST PUT THE ICON IN THE WINDOW
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);        
        String windowIconFile = props.getProperty(PathXPropertyType.IMAGE_WINDOW_ICON);
        img = loadImage(imgPath + windowIconFile);
        window.setIconImage(img);

        // CONSTRUCT THE PANEL WHERE WE'LL DRAW EVERYTHING
        canvas = new PathXPanel(this, (PathXDataModel)data);
        
         Viewport viewport = data.getViewport();
         
       ArrayList<PathXGameLevel> level = ((PathXDataModel)data).getLevelLocation();
       level.get(0).setStageUnlock(true);
       level.get(0).setLevelState(RED_STATE);
     
    
        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        currentScreenState = MENU_SCREEN_STATE;
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_MENU));
        sT = new SpriteType(BACKGROUND_TYPE);
        sT.addState(MENU_SCREEN_STATE, img);
        
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_GAME));
        sT.addState(GAME_SCREEN_STATE, img);
        
       img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_SETTINGS_WINDOW));
        sT.addState(GAME_SETTINGS_STATE, img);
        
        
         img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_GREEN));
        sT.addState(GAME_LEVEL_STATE , img);
        
      
        s = new Sprite(sT, 0, 0, 0, 0, MENU_SCREEN_STATE);
        guiDecor.put(BACKGROUND_TYPE, s);
        
        data.getViewport().initViewportMargins();
        //Add LEVEL SELECT MAP
           
          //The PUT THE HELP ICONS BUTTON
       
       
       
      //  sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        //s = new Sprite(sT, 0, 0, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
     //   guiDecor.put(LEVEL_SELECT_BACKGROUND_TYPE, s);
        
        
        
        
        // LOAD THE WAND CURSOR
       // String cursorName = props.getProperty(PathXPropertyType.IMAGE_CURSOR_WAND);
      //  img = loadImageWithColorKey(imgPath + cursorName, COLOR_KEY);
      //  Point cursorHotSpot = new Point(0,0);
     //   Cursor wandCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, cursorHotSpot, cursorName);
     //   window.setCursor(wandCursor);
        
        // ADD A BUTTON FOR EACH LEVEL AVAILABLE
        ArrayList<String> menuButton = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        ArrayList<String> menuMouseOverImageNames = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_MOUSE_OVER_IMAGE_OPTIONS);
        float totalWidth = menuButton.size() * (LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN) - LEVEL_BUTTON_MARGIN;
       viewport = data.getViewport();
        x = (viewport.getScreenWidth() - totalWidth)/2.0f;
        for (int i = 0; i < menuButton.size(); i++)
        {
            sT = new SpriteType(LEVEL_SELECT_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + menuButton.get(i), COLOR_KEY);
            sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
           img = loadImageWithColorKey(imgPath + menuMouseOverImageNames.get(i), COLOR_KEY);
            sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
            s = new Sprite(sT, x, LEVEL_BUTTON_Y, 0, 0, PathXCarState.VISIBLE_STATE.toString());
            guiButtons.put(menuButton.get(i), s);
            guiButtons.put(menuMouseOverImageNames.get(i), s);
            x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
        }
        
        //ADD SPECIAL BUTTONS
         ArrayList<String> specialButton = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS1);
         totalWidth = specialButton.size() * (SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN) - SPECIAL_BUTTON_MARGIN;
         viewport = data.getViewport();
        x = 17;//(viewport.getScreenWidth() - totalWidth)/2.0f;
        for (int i = 0; i < specialButton.size(); i++)
        {
            sT = new SpriteType(SPECIAL_SELECT_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + specialButton.get(i), COLOR_KEY);
            sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
          // img = loadImageWithColorKey(imgPath + menuMouseOverImageNames.get(i), COLOR_KEY);
            //sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
            s = new Sprite(sT, x, SPECIAL_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.put(specialButton.get(i), s);
           // guiButtons.put(menuMouseOverImageNames.get(i), s);
            x += SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN;
        }
         ArrayList<String> specialButton2 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS2);
         totalWidth = specialButton2.size() * (SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN) - SPECIAL_BUTTON_MARGIN;
         viewport = data.getViewport();
        x = 17;//(viewport.getScreenWidth() - totalWidth)/2.0f;
        for (int i = 0; i < specialButton2.size(); i++)
        {
            sT = new SpriteType(SPECIAL_SELECT_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + specialButton2.get(i), COLOR_KEY);
            sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
          // img = loadImageWithColorKey(imgPath + menuMouseOverImageNames.get(i), COLOR_KEY);
            //sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
            s = new Sprite(sT, x, SPECIAL_BUTTON_Y2, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.put(specialButton2.get(i), s);
           // guiButtons.put(menuMouseOverImageNames.get(i), s);
            x += SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN;
        }
        ArrayList<String> specialButton3 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS3);
         totalWidth = specialButton3.size() * (SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN) - SPECIAL_BUTTON_MARGIN;
         viewport = data.getViewport();
        x = 17;//(viewport.getScreenWidth() - totalWidth)/2.0f;
        for (int i = 0; i < specialButton3.size(); i++)
        {
            sT = new SpriteType(SPECIAL_SELECT_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + specialButton3.get(i), COLOR_KEY);
            sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
          // img = loadImageWithColorKey(imgPath + menuMouseOverImageNames.get(i), COLOR_KEY);
            //sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
            s = new Sprite(sT, x, SPECIAL_BUTTON_Y3, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.put(specialButton3.get(i), s);
           // guiButtons.put(menuMouseOverImageNames.get(i), s);
            x += SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN;
        }
        ArrayList<String> specialButton4 = props.getPropertyOptionsList(PathXPropertyType.SPECIAL_IMAGE_OPTIONS4);
         totalWidth = specialButton4.size() * (SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN) - SPECIAL_BUTTON_MARGIN;
         viewport = data.getViewport();
        x = 17;//(viewport.getScreenWidth() - totalWidth)/2.0f;
        for (int i = 0; i < specialButton4.size(); i++)
        {
            sT = new SpriteType(SPECIAL_SELECT_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + specialButton4.get(i), COLOR_KEY);
            sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
          // img = loadImageWithColorKey(imgPath + menuMouseOverImageNames.get(i), COLOR_KEY);
            //sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
            s = new Sprite(sT, x, SPECIAL_BUTTON_Y4, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.put(specialButton4.get(i), s);
           // guiButtons.put(menuMouseOverImageNames.get(i), s);
            x += SPECIAL_BUTTON_WIDTH + SPECIAL_BUTTON_MARGIN;
        }
        // ADD THE CONTROLS ALONG THE NORTH OF THE GAME SCREEN
          
       
       
         //The PUT THE X BUTTON
        String exitButton = props.getProperty(PathXPropertyType.HOME_SCREEN_IMAGE_EXIT);
        sT = new SpriteType(EXIT_GAME_BUTTON_TYPE);
        img = loadImage(imgPath + exitButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, EXIT_BUTTON_X, EXIT_BUTTON_Y, 0, 0, PathXCarState.VISIBLE_STATE.toString());
        guiButtons.put(EXIT_GAME_BUTTON_TYPE, s);
        
        
        //The PUT THE START BUTTON
        String startButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_START);
        sT = new SpriteType(GAME_START_BUTTON_TYPE);
        img = loadImage(imgPath + startButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, START_BUTTON_X, START_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_START_BUTTON_TYPE, s);
        
        //The PUT THE HOME BUTTON
        String homeButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_HOME);
        sT = new SpriteType(GAME_HOME_BUTTON_TYPE);
        img = loadImage(imgPath + homeButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, HOME_BUTTON_X, HOME_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_HOME_BUTTON_TYPE, s);
        
         String homeButtonSetting = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_HOME);
        sT = new SpriteType(GAME_HOME_BUTTON_SETTING_TYPE);
        img = loadImage(imgPath + homeButtonSetting);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, HOME_BUTTON_SETTING_X, HOME_BUTTON_SETTING_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_HOME_BUTTON_SETTING_TYPE, s);
         String settingHomeMouseOver = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_HOME_MOUSE_OVER);
        img = loadImage(imgPath + settingHomeMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, HOME_BUTTON_SETTING_X, HOME_BUTTON_SETTING_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_HOME_BUTTON_SETTING_TYPE, s);
        
        //The PUT THE SETTINGS X BUTTON
        String settingsXButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_X);
        sT = new SpriteType(GAME_SETTINGS_X_BUTTON_TYPE);
        img = loadImage(imgPath + settingsXButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, HOME_SETTINGS_X_BUTTON_X, HOME_SETTINGS_X_BUTTON_Y, 0, 0, PathXCarState.VISIBLE_STATE.toString());
        guiButtons.put(GAME_SETTINGS_X_BUTTON_TYPE, s);
         String settingXMouseOver = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_X_MOUSE_OVER);
        img = loadImage(imgPath + settingXMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, HOME_SETTINGS_X_BUTTON_X, HOME_SETTINGS_X_BUTTON_Y, 0, 0, PathXCarState.VISIBLE_STATE.toString());
        guiButtons.put(GAME_SETTINGS_X_BUTTON_TYPE, s);
        
        //The PUT THE HOME X BUTTON
        String homeXButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_X);
        sT = new SpriteType(GAME_X_BUTTON_TYPE);
        img = loadImage(imgPath + homeXButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, HOME_X_BUTTON_X, HOME_X_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_X_BUTTON_TYPE, s);
        
         //The PUT THE NORTH TOOLBAR
        String northToolBar = props.getProperty(PathXPropertyType.IMAGE_NORTH_TOOL_BAR);
        sT = new SpriteType(GAME_TOOLBAR_TYPE);
        img = loadImage(imgPath + northToolBar);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, TOOLBAR_X, TOOLBAR_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.put(GAME_TOOLBAR_TYPE, s);
        
            //THE LEVEL SELECT MAP    
        String levelSelect = props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_GAME_SELECT);
        sT = new SpriteType(LEVEL_SELECT_BACKGROUND_TYPE);
        img = loadImage(imgPath + levelSelect);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, 0, 200, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.put(LEVEL_SELECT_BACKGROUND_TYPE, s);
         
        //The PUT THE LEFT SCROLL BUTTON
        String scrollLeftButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_LEFT);
        sT = new SpriteType(GAME_SCROLL_LEFT_BUTTON_TYPE);
        img = loadImage(imgPath + scrollLeftButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_LEFT_BUTTON_X, SCROLL_LEFT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_LEFT_BUTTON_TYPE, s);
         String scrollLeftMouseOver = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_LEFT_MOUSE_OVER);
        img = loadImage(imgPath + scrollLeftMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_LEFT_BUTTON_X, SCROLL_LEFT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_LEFT_BUTTON_TYPE, s);
        
            //The PUT THE RIGHT SCROLL BUTTON
        String scrollRightButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_RIGHT);
        sT = new SpriteType(GAME_SCROLL_RIGHT_BUTTON_TYPE);
        img = loadImage(imgPath + scrollRightButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_RIGHT_BUTTON_X, SCROLL_RIGHT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_RIGHT_BUTTON_TYPE, s);
         String scrollRightMouseOver = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_RIGHT_MOUSE_OVER);
        img = loadImage(imgPath + scrollRightMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_RIGHT_BUTTON_X, SCROLL_RIGHT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_RIGHT_BUTTON_TYPE, s);
        
        
        //The PUT THE UP SCROLL BUTTON
        String scrollUpButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_UP);
        sT = new SpriteType(GAME_SCROLL_UP_BUTTON_TYPE);
        img = loadImage(imgPath + scrollUpButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_UP_BUTTON_X, SCROLL_UP_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_UP_BUTTON_TYPE, s);
         String scrollUPMouseOver = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_UP_MOUSE_OVER);
        img = loadImage(imgPath + scrollUPMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_UP_BUTTON_X, SCROLL_UP_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_UP_BUTTON_TYPE, s);
      
        //The PUT THE DOWN SCROLL BUTTON
        String scrollDownButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_DOWN);
        sT = new SpriteType(GAME_SCROLL_DOWN_BUTTON_TYPE);
        img = loadImage(imgPath + scrollDownButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_DOWN_BUTTON_X, SCROLL_DOWN_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_DOWN_BUTTON_TYPE, s);
        String scrollDownMouseOver = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_DOWN_MOUSE_OVER);
        img = loadImage(imgPath + scrollDownMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_DOWN_BUTTON_X, SCROLL_DOWN_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_DOWN_BUTTON_TYPE, s);
        
          //The PUT THE QUIT BUTTON
        String closeButton = props.getProperty(PathXPropertyType.HELP_SCREEN_IMAGE_BUTTON_QUIT);
        sT = new SpriteType(HELP_QUIT_TYPE);
        img = loadImage(imgPath + closeButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, QUIT_BUTTON_X, QUIT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(HELP_QUIT_TYPE, s);
        String closeButtonMouseOver = props.getProperty(PathXPropertyType.HELP_SCREEN_IMAGE_BUTTON_QUIT_MOUSE_OVER);
        img = loadImage(imgPath + closeButtonMouseOver);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, QUIT_BUTTON_X, QUIT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(HELP_QUIT_TYPE, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE1);
        img = loadImage(imgPath + levelGreenButton);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X1, LEVEL_OFFSET_LOCATION_Y1, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE1, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE1);
        img = loadImage(imgPath + levelRedButton);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X1, LEVEL_OFFSET_LOCATION_Y1, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE1, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE1);
        img = loadImage(imgPath + levelWhiteButton);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X1, LEVEL_OFFSET_LOCATION_Y1, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE1, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton2 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE2);
        img = loadImage(imgPath + levelGreenButton2);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X2, LEVEL_OFFSET_LOCATION_Y2, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE2, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton2 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE2);
        img = loadImage(imgPath + levelRedButton2);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X2, LEVEL_OFFSET_LOCATION_Y2, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE2, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton2 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE2);
        img = loadImage(imgPath + levelWhiteButton2);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X2, LEVEL_OFFSET_LOCATION_Y2, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE2, s);
   
       //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton3 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE3);
        img = loadImage(imgPath + levelGreenButton3);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X3, LEVEL_OFFSET_LOCATION_Y3, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE3, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton3 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE3);
        img = loadImage(imgPath + levelRedButton3);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X3, LEVEL_OFFSET_LOCATION_Y3, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE3, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton3 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE3);
        img = loadImage(imgPath + levelWhiteButton3);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X3, LEVEL_OFFSET_LOCATION_Y3, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE3, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton4 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE4);
        img = loadImage(imgPath + levelGreenButton4);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X4, LEVEL_OFFSET_LOCATION_Y4, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE4, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton4 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE4);
        img = loadImage(imgPath + levelRedButton4);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X4, LEVEL_OFFSET_LOCATION_Y4, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE4, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton4 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE4);
        img = loadImage(imgPath + levelWhiteButton4);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X4, LEVEL_OFFSET_LOCATION_Y4, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE4, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton5 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE5);
        img = loadImage(imgPath + levelGreenButton5);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X5, LEVEL_OFFSET_LOCATION_Y5, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE5, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton5 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE5);
        img = loadImage(imgPath + levelRedButton5);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X5, LEVEL_OFFSET_LOCATION_Y5, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE5, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton5 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE5);
        img = loadImage(imgPath + levelWhiteButton5);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X5, LEVEL_OFFSET_LOCATION_Y5, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE5, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton6 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE6);
        img = loadImage(imgPath + levelGreenButton6);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X6, LEVEL_OFFSET_LOCATION_Y6, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE6, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton6 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE6);
        img = loadImage(imgPath + levelRedButton6);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X6, LEVEL_OFFSET_LOCATION_Y6, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE6, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton6 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE6);
        img = loadImage(imgPath + levelWhiteButton6);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X6, LEVEL_OFFSET_LOCATION_Y6, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE6, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton7 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE7);
        img = loadImage(imgPath + levelGreenButton7);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X7, LEVEL_OFFSET_LOCATION_Y7, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE7, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton7 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE7);
        img = loadImage(imgPath + levelRedButton7);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X7, LEVEL_OFFSET_LOCATION_Y7, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE7, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton7 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE7);
        img = loadImage(imgPath + levelWhiteButton7);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X7, LEVEL_OFFSET_LOCATION_Y7, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE7, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton8 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE8);
        img = loadImage(imgPath + levelGreenButton8);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X8, LEVEL_OFFSET_LOCATION_Y8, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE8, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton8 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE8);
        img = loadImage(imgPath + levelRedButton8);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X8, LEVEL_OFFSET_LOCATION_Y8, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE8, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton8 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE8);
        img = loadImage(imgPath + levelWhiteButton8);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X8, LEVEL_OFFSET_LOCATION_Y8, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE8, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton9 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE9);
        img = loadImage(imgPath + levelGreenButton9);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X9, LEVEL_OFFSET_LOCATION_Y9, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE9, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton9 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE9);
        img = loadImage(imgPath + levelRedButton9);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X9, LEVEL_OFFSET_LOCATION_Y9, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE9, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton9 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE9);
        img = loadImage(imgPath + levelWhiteButton9);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X9, LEVEL_OFFSET_LOCATION_Y9, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE9, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton10 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE10);
        img = loadImage(imgPath + levelGreenButton10);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X10, LEVEL_OFFSET_LOCATION_Y10, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE10, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton10 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE10);
        img = loadImage(imgPath + levelRedButton10);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X10, LEVEL_OFFSET_LOCATION_Y10, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE10, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton10 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE10);
        img = loadImage(imgPath + levelWhiteButton10);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X10, LEVEL_OFFSET_LOCATION_Y10, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE10, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton11 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE11);
        img = loadImage(imgPath + levelGreenButton11);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X11, LEVEL_OFFSET_LOCATION_Y11, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE11, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton11 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE11);
        img = loadImage(imgPath + levelRedButton11);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X11, LEVEL_OFFSET_LOCATION_Y11, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE11, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton11 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE11);
        img = loadImage(imgPath + levelWhiteButton11);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X11, LEVEL_OFFSET_LOCATION_Y11, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE11, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton12 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE12);
        img = loadImage(imgPath + levelGreenButton12);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X12, LEVEL_OFFSET_LOCATION_Y12, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE12, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton12 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE12);
        img = loadImage(imgPath + levelRedButton12);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X12, LEVEL_OFFSET_LOCATION_Y12, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE12, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton12 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE12);
        img = loadImage(imgPath + levelWhiteButton12);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X12, LEVEL_OFFSET_LOCATION_Y12, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE12, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton13 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE13);
        img = loadImage(imgPath + levelGreenButton13);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X13, LEVEL_OFFSET_LOCATION_Y13, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE13, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton13 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE13);
        img = loadImage(imgPath + levelRedButton13);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X13, LEVEL_OFFSET_LOCATION_Y13, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE13, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton13 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE13);
        img = loadImage(imgPath + levelWhiteButton13);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X13, LEVEL_OFFSET_LOCATION_Y13, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE13, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton14 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE14);
        img = loadImage(imgPath + levelGreenButton14);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X14, LEVEL_OFFSET_LOCATION_Y14, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE14, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton14 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE14);
        img = loadImage(imgPath + levelRedButton14);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X14, LEVEL_OFFSET_LOCATION_Y14, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE14, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton14 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE14);
        img = loadImage(imgPath + levelWhiteButton14);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X14, LEVEL_OFFSET_LOCATION_Y14, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE14, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton15 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE15);
        img = loadImage(imgPath + levelGreenButton15);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X15, LEVEL_OFFSET_LOCATION_Y15, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE15, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton15 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE15);
        img = loadImage(imgPath + levelRedButton15);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X15, LEVEL_OFFSET_LOCATION_Y15, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE15, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton15 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE15);
        img = loadImage(imgPath + levelWhiteButton15);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X15, LEVEL_OFFSET_LOCATION_Y15, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE15, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton16 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE16);
        img = loadImage(imgPath + levelGreenButton16);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X16, LEVEL_OFFSET_LOCATION_Y16, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE16, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton16 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE16);
        img = loadImage(imgPath + levelRedButton16);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X16, LEVEL_OFFSET_LOCATION_Y16, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE16, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton16 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE16);
        img = loadImage(imgPath + levelWhiteButton16);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X16, LEVEL_OFFSET_LOCATION_Y16, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE16, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton17 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE17);
        img = loadImage(imgPath + levelGreenButton17);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X17, LEVEL_OFFSET_LOCATION_Y17, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE17, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton17 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE17);
        img = loadImage(imgPath + levelRedButton17);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X17, LEVEL_OFFSET_LOCATION_Y17, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE17, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton17 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE17);
        img = loadImage(imgPath + levelWhiteButton17);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X17, LEVEL_OFFSET_LOCATION_Y17, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE17, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton18 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE18);
        img = loadImage(imgPath + levelGreenButton18);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X18, LEVEL_OFFSET_LOCATION_Y18, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE18, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton18 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE18);
        img = loadImage(imgPath + levelRedButton18);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X18, LEVEL_OFFSET_LOCATION_Y18, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE18, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton18 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE18);
        img = loadImage(imgPath + levelWhiteButton18);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X18, LEVEL_OFFSET_LOCATION_Y18, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE18, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton19 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE19);
        img = loadImage(imgPath + levelGreenButton19);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X19, LEVEL_OFFSET_LOCATION_Y19, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE19, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton19 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE19);
        img = loadImage(imgPath + levelRedButton19);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X19, LEVEL_OFFSET_LOCATION_Y19, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE19, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton19 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE19);
        img = loadImage(imgPath + levelWhiteButton19);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X19, LEVEL_OFFSET_LOCATION_Y19, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE19, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelGreenButton20 = props.getProperty(PathXPropertyType.GREEN_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_GREEN_TYPE20);
        img = loadImage(imgPath + levelGreenButton20);
        sT.addState(PathXCarState.GREEN_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X20, LEVEL_OFFSET_LOCATION_Y20, 0, 0, PathXCarState.GREEN_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_GREEN_TYPE20, s);
        
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelRedButton20 = props.getProperty(PathXPropertyType.RED_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_RED_TYPE20);
        img = loadImage(imgPath + levelRedButton20);
        sT.addState(PathXCarState.RED_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X20, LEVEL_OFFSET_LOCATION_Y20, 0, 0, PathXCarState.RED_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_RED_TYPE20, s);
       
        //The PUT THE GREEN LEVEL ICONS BUTTON
        String levelWhiteButton20 = props.getProperty(PathXPropertyType.WHITE_LOCATION);
        sT = new SpriteType(GAME_PLAY_LEVEL_WHITE_TYPE20);
        img = loadImage(imgPath + levelWhiteButton20);
        sT.addState(PathXCarState.WHITE_STATE.toString(), img);
        s = new Sprite(sT, LEVEL_OFFSET_LOCATION_X20, LEVEL_OFFSET_LOCATION_Y20, 0, 0, PathXCarState.WHITE_STATE.toString());
        guiButtons.put(GAME_PLAY_LEVEL_WHITE_TYPE20, s);
        
       
        
         for(int i=1; i<21; i++)
        {
        String str = "GAME_PLAY_LEVEL_WHITE_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_RED_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
        str = "GAME_PLAY_LEVEL_GREEN_TYPE"+i;
         guiButtons.get(str).setState(PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.get(str).setEnabled(false);
        
         
        String HELPDisplay = props.getProperty(PathXPropertyType.IMAGE_HELP_SCREEN);
        sT = new SpriteType(GAME_HELP_SCREEN_STATE);
        img = loadImageWithColorKey(imgPath + HELPDisplay, COLOR_KEY);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        x = (viewport.getScreenWidth()/2) - (img.getWidth(null)/2);
        y = (viewport.getScreenHeight()/2) - (img.getHeight(null)/2);
        s = new Sprite(sT, x, y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDialogs.put(GAME_HELP_SCREEN_STATE, s);
        }
           /*
       
        
        // THEN THE BACK BUTTON
        String backButton = props.getProperty(PathXPropertyType.IMAGE_BUTTON_BACK);
        sT = new SpriteType(BACK_BUTTON_TYPE);
	img = loadImage(imgPath + backButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        String backMouseOverButton = props.getProperty(PathXPropertyType.IMAGE_BUTTON_BACK_MOUSE_OVER);
        img = loadImage(imgPath + backMouseOverButton);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, BACK_BUTTON_X, BACK_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(BACK_BUTTON_TYPE, s);
        
        
        // THEN THE UNDO BUTTON
        String undoButton = props.getProperty(PathXPropertyType.IMAGE_BUTTON_UNDO);
        sT = new SpriteType(UNDO_BUTTON_TYPE);
	img = loadImage(imgPath + undoButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        String undoMouseOverButton = props.getProperty(PathXPropertyType.IMAGE_BUTTON_UNDO_MOUSE_OVER);
        img = loadImage(imgPath + undoMouseOverButton);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, UNDO_BUTTON_X, UNDO_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(UNDO_BUTTON_TYPE, s);
        
        
        // 
        
        // AND THE TIME DISPLAY
        String timeContainer = props.getProperty(PathXPropertyType.IMAGE_DECOR_TIME);
        sT = new SpriteType(TIME_TYPE);
        img = loadImage(imgPath + timeContainer);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, TIME_X, TIME_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.put(TIME_TYPE, s);
        
        // AND THE STATS BUTTON
        String statsButton = props.getProperty(PathXPropertyType.IMAGE_BUTTON_STATS);
        sT = new SpriteType(STATS_BUTTON_TYPE);
        img = loadImage(imgPath + statsButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        String statsMouseOverButton = props.getProperty(PathXPropertyType.IMAGE_BUTTON_STATS_MOUSE_OVER);
        img = loadImage(imgPath + statsMouseOverButton);
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, STATS_X, STATS_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(STATS_BUTTON_TYPE, s);

        // AND THE TILE STACK
        String tileStack = props.getProperty(PathXPropertyType.IMAGE_BUTTON_TEMP_TILE);
        sT = new SpriteType(ALGORITHM_TYPE);
        img = loadImageWithColorKey(imgPath + tileStack, COLOR_KEY);
        
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
      //   String tileStackMouseOver = props.getProperty(PathXPropertyType.IMAGE_TILE_BACKGROUND_MOUSE_OVER);
        //sT = new SpriteType(ALGORITHM_TYPE);
     //   img = loadImage(imgPath + tileStackMouseOver);
        
        
        sT.addState(PathXCarState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, TEMP_TILE_X, TEMP_TILE_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.put(ALGORITHM_TYPE, s);

        // NOW ADD THE DIALOGS
        
        
    //    s = new Sprite(sT, TEMP_TILE_X, TEMP_TILE_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
    //    guiDecor.put(ALGORITHM_TYPE, s);
        
        
      
        
        // AND THE STATS DISPLAY
        String statsDialog = props.getProperty(PathXPropertyType.IMAGE_DIALOG_STATS);
        sT = new SpriteType(STATS_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + statsDialog, COLOR_KEY);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        x = (viewport.getScreenWidth()/2) - (img.getWidth(null)/2);
        y = (viewport.getScreenHeight()/2) - (img.getHeight(null)/2);
        s = new Sprite(sT, x, y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDialogs.put(STATS_DIALOG_TYPE, s);
        
        // AND THE WIN CONDITION DISPLAY
        String winDisplay = props.getProperty(PathXPropertyType.IMAGE_DIALOG_WIN);
        sT = new SpriteType(WIN_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + winDisplay, COLOR_KEY);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        x = (viewport.getScreenWidth()/2) - (img.getWidth(null)/2);
        y = (viewport.getScreenHeight()/2) - (img.getHeight(null)/2);
        s = new Sprite(sT, x, y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDialogs.put(WIN_DIALOG_TYPE, s);
		
        // THEN THE TILES STACKED TO THE TOP LEFT
        ((PathXDataModel)data).initTiles();
        
        
   */ 
    
          
    
    
    }		
    
    /**
     * Initializes the game event handlers for things like
     * game gui buttons.
     */
    @Override
    public void initGUIHandlers()
    {
        // WE'LL RELAY UI EVENTS TO THIS OBJECT FOR HANDLING
        eventHandler = new PathXEventHandler(this);
                
        // WE'LL HAVE A CUSTOM RESPONSE FOR WHEN THE USER CLOSES THE WINDOW
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we) 
            { eventHandler.respondToExitRequest(); }
        });

       
        // SEND ALL LEVEL SELECTION HANDLING OFF TO THE EVENT HANDLER
     
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String levelFile : levels)
        {
            Sprite levelButton = guiButtons.get(levelFile);
            levelButton.setActionCommand(PATH_DATA + levelFile);
            levelButton.setActionListener(new ActionListener(){
                Sprite s;
                public ActionListener init(Sprite initS) 
                {   s = initS; 
                    return this;    }
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.respondToSelectMenuRequest(s.getActionCommand());    }
            }.init(levelButton));
        }   
          //EXIT GAME EVENT HANDLER
       guiButtons.get(EXIT_GAME_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToExitRequest();     }
      });
        guiButtons.get(GAME_SETTINGS_X_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToExitRequest();     }
            
      });
         guiButtons.get(HELP_QUIT_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToQuitRequest();     }
            
      });
        
        
        
       //HOME GAME EVENT HANDLER
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToHomeRequest();     }
      });
        
          //HOME GAME EVENT HANDLER
        guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToHomeRequest();     }
      });
         
       //START GAME EVENT HANDLER
        guiButtons.get(GAME_START_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToStartRequest();     }
      });
        guiButtons.get(GAME_SCROLL_LEFT_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToScrollRequest(SCROLL_LEFT);     }
      });
        
         guiButtons.get(GAME_SCROLL_UP_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToScrollRequest(SCROLL_UP);     }
      });
         
          guiButtons.get(GAME_SCROLL_DOWN_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToScrollRequest(SCROLL_DOWN);     }
      });
        
           guiButtons.get(GAME_SCROLL_RIGHT_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToScrollRequest(SCROLL_RIGHT);     }
      });
             guiButtons.get(GAME_PLAY_LEVEL_RED_TYPE1).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToStageRequest(STAGE1);    
           
           
           }
      });
           
        
//           String exitButton = props.getProperty(PathXPropertyType.HOME_SCREEN_IMAGE_EXIT);
//         //EXIT GAME EVENT HANDLER
//         sT = new SpriteType(EXIT_GAME_BUTTON_TYPE);
//        Sprite exitRequest = guiButtons.get(EXIT_GAME_BUTTON_TYPE);
//      // guiButtons.get(EXIT_GAME_BUTTON_TYPE);
//
//        exitRequest.setActionCommand(exitButton);
//        exitRequest.setActionListener(new ActionListener(){
//        
//        
//       Sprite s;
//                public ActionListener init(Sprite initS) 
//                {   s = initS; 
//                    return this;    }
//                public void actionPerformed(ActionEvent ae)
//                {   eventHandler.respondToExitRequest();     }
//            }.init(exitRequest));  


//        String exitButton = props.getProperty(PathXPropertyType.HOME_SCREEN_IMAGE_EXIT);
//         //EXIT GAME EVENT HANDLER
//       guiButtons.get(EXIT_GAME_BUTTON_TYPE).setActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent ae)
//            {   eventHandler.respondToExitRequest();     }
//       });
     //
        
        // BACK EVENT HANDLER
     //   guiButtons.get(BACK_BUTTON_TYPE).setActionListener(new ActionListener(){
     //       public void actionPerformed(ActionEvent ae)
     //       {   eventHandler.respondToBackRequest();     }
     //   });
        
        // UNDO BUTTON EVENT HANDLER
     //   guiButtons.get(UNDO_BUTTON_TYPE).setActionListener(new ActionListener(){
     //       public void actionPerformed(ActionEvent ae)
     //       {   eventHandler.respondToUndoRequest();     }
     //   });
        
        // STATS BUTTON EVENT HANDLER
    //    guiButtons.get(STATS_BUTTON_TYPE).setActionListener(new ActionListener(){
    //        public void actionPerformed(ActionEvent ae)
    //        {   eventHandler.respondToDisplayStatsRequest();    }
    //    });
        
        // KEY LISTENER - LET'S US PROVIDE CUSTOM RESPONSES
        this.setKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {   
                eventHandler.respondToKeyPress(ke.getKeyCode());    
            }
        });
    }
    
    /**
     * Invoked when a new game is started, it resets all relevant
     * game data and gui control states. 
     */
    @Override
    public void reset()
    {
        data.reset(this);
    }
    
    /**
     * Updates the state of all gui controls according to the 
     * current game conditions.
     */
    @Override
    public void updateGUI()
    {
        // GO THROUGH THE VISIBLE BUTTONS TO TRIGGER MOUSE OVERS
        Iterator<Sprite> buttonsIt = guiButtons.values().iterator();
        while (buttonsIt.hasNext())
        {
            Sprite button = buttonsIt.next();
            
            // ARE WE ENTERING A BUTTON?
            if (button.getState().equals(PathXCarState.VISIBLE_STATE.toString()))
            {
                if (button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(PathXCarState.MOUSE_OVER_STATE.toString());
                }
            }
            // ARE WE EXITING A BUTTON?
            else if (button.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()))
            {
                 if (!button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(PathXCarState.VISIBLE_STATE.toString());
                }
            }
        
          /*  
      //     props.getPropertyOptionsList(PathXPropertyType.values()
                  ArrayList<SortingHatTile> tilesToSort;
                    
            tilesToSort =  ((PathXDataModel)data).getTilesToSort();
            
            
                    
          //  Iterator<Sprite> titlesIt = (SortingHatDateModel)data.ge
                     Iterator<SortingHatTile>  titlesIt = tilesToSort.iterator();
                    
        while (titlesIt.hasNext())
        {
            Sprite titles = titlesIt.next();
          
            // ARE WE ENTERING A BUTTON?
            if (titles.getState().equals(PathXCarState.VISIBLE_STATE.toString()))
            {
                if (titles.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    titles.setState(PathXCarState.MOUSE_OVER_STATE.toString());
                     // System.out.println(titles.getState()+" section 1");
                }
            }
            // ARE WE EXITING A BUTTON?
            else if (titles.getState().equals(PathXCarState.MOUSE_OVER_STATE.toString()))
            {
                 if (!titles.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    titles.setState(PathXCarState.VISIBLE_STATE.toString());
                  //   System.out.println(titles.getState()+" section ");
                }
            }
        }*/
    }   
}
}