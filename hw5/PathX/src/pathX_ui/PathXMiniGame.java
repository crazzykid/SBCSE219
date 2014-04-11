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

    public void switchToSettingsScreen()
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SETTINGS_STATE);
        
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(false);
        }
             guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setState(PathXCarState.VISIBLE_STATE.toString());
        guiButtons.get(GAME_HOME_BUTTON_SETTING_TYPE).setEnabled(true);
        
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
        
        
        
         currentScreenState = GAME_SETTINGS_STATE;

        // PLAY THE GAMEPLAY SCREEN SONG
      //  audio.stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString()); 
      //  audio.play(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString(), true);
         
        
    }
    public void switchToGameScreen()
    {
         marginlessInsets = new Insets(0,0,0,0);
         
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        
        
        // DEACTIVATE THE LEVEL SELECT BUTTONS
        ArrayList<String> levels = props.getPropertyOptionsList(PathXPropertyType.HOME_SCREEN_IMAGE_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(PathXCarState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(true);
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
         marginlessInsets = new Insets(0,0,0,0);
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(MENU_SCREEN_STATE);
        
     
        
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
        
        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        currentScreenState = MENU_SCREEN_STATE;
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_MENU));
        sT = new SpriteType(BACKGROUND_TYPE);
        sT.addState(MENU_SCREEN_STATE, img);
        
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_GAME));
        sT.addState(GAME_SCREEN_STATE, img);
        
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_SETTINGS_WINDOW));
        sT.addState(GAME_SETTINGS_STATE, img);
        s = new Sprite(sT, 0, 0, 0, 0, MENU_SCREEN_STATE);
    
        guiDecor.put(BACKGROUND_TYPE, s);
        
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
        Viewport viewport = data.getViewport();
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
        x = 21;//(viewport.getScreenWidth() - totalWidth)/2.0f;
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
        x = 21;//(viewport.getScreenWidth() - totalWidth)/2.0f;
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
        x = 21;//(viewport.getScreenWidth() - totalWidth)/2.0f;
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
        x = 21;//(viewport.getScreenWidth() - totalWidth)/2.0f;
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
        
        //The PUT THE HOME X BUTTON
        String homeXButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_X);
        sT = new SpriteType(GAME_X_BUTTON_TYPE);
        img = loadImage(imgPath + homeXButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, HOME_X_BUTTON_X, HOME_X_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_X_BUTTON_TYPE, s);
        
      
        //The PUT THE LEFT SCROLL BUTTON
        String scrollLeftButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_LEFT);
        sT = new SpriteType(GAME_SCROLL_LEFT_BUTTON_TYPE);
        img = loadImage(imgPath + scrollLeftButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_LEFT_BUTTON_X, SCROLL_LEFT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_LEFT_BUTTON_TYPE, s);
        
            //The PUT THE RIGHT SCROLL BUTTON
        String scrollRightButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_RIGHT);
        sT = new SpriteType(GAME_SCROLL_RIGHT_BUTTON_TYPE);
        img = loadImage(imgPath + scrollRightButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_RIGHT_BUTTON_X, SCROLL_RIGHT_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_RIGHT_BUTTON_TYPE, s);
         
        //The PUT THE UP SCROLL BUTTON
        String scrollUpButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_UP);
        sT = new SpriteType(GAME_SCROLL_UP_BUTTON_TYPE);
        img = loadImage(imgPath + scrollUpButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_UP_BUTTON_X, SCROLL_UP_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_UP_BUTTON_TYPE, s);
      
        //The PUT THE DOWN SCROLL BUTTON
        String scrollDownButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_SCROLL_DOWN);
        sT = new SpriteType(GAME_SCROLL_DOWN_BUTTON_TYPE);
        img = loadImage(imgPath + scrollDownButton);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, SCROLL_DOWN_BUTTON_X, SCROLL_DOWN_BUTTON_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiButtons.put(GAME_SCROLL_DOWN_BUTTON_TYPE, s);
        
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
        
        
        // AND THE MISCASTS COUNT
        String miscastCountContainer = props.getProperty(PathXPropertyType.IMAGE_DECOR_MISCASTS);
        sT = new SpriteType(MISCASTS_COUNT_TYPE);
        img = loadImage(imgPath + miscastCountContainer);
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        s = new Sprite(sT, TILE_COUNT_X, TILE_COUNT_Y, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
        guiDecor.put(MISCASTS_COUNT_TYPE, s);
        
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
   */ }		
    
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
        
       //HOME GAME EVENT HANDLER
        guiButtons.get(GAME_HOME_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToHomeRequest();     }
      });
        
         
       //START GAME EVENT HANDLER
        guiButtons.get(GAME_START_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
           {   eventHandler.respondToStartRequest();     }
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