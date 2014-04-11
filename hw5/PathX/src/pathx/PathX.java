package pathx;

import pathX_ui.PathXMiniGame;
import pathX_ui.PathXErrorHandler;
import xml_utilities.InvalidXMLFileFormatException;
import properties_manager.PropertiesManager;
import static pathx.PathXConstants.*;

/**
 * The Sorting Hat is a game application that's ready to be customized
 * to play different flavors of the game. It has been setup using art
 * from Harry Potter, but it could easily be swapped out just by changing
 * the artwork and audio files.
 * 
 * @author Richard McKenna & __Lamar Myles_
 */
public class PathX
{
    // THIS HAS THE FULL USER INTERFACE AND ONCE IN EVENT
    // HANDLING MODE, BASICALLY IT BECOMES THE FOCAL
    // POINT, RUNNING THE UI AND EVERYTHING ELSE
    static PathXMiniGame miniGame = new PathXMiniGame();

    /**
     * This is where The Sorting Hat game application starts execution. We'll
     * load the application properties and then use them to build our
     * user interface and start the window in real-time mode.
     */
    public static void main(String[] args)
    {
        try
        {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            
            // THEN WE'LL LOAD THE GAME FLAVOR AS SPECIFIED BY THE PROPERTIES FILE
            String gameFlavorFile = props.getProperty(PathXPropertyType.FILE_GAME_PROPERTIES);
            props.loadProperties(gameFlavorFile, PROPERTIES_SCHEMA_FILE_NAME);

            
            // NOW WE CAN LOAD THE UI, WHICH WILL USE ALL THE FLAVORED CONTENT
            String appTitle = props.getProperty(PathXPropertyType.TEXT_TITLE_BAR_GAME);
            miniGame.initMiniGame(appTitle, FPS, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            // GET THE PROPER WINDOW DIMENSIONS
            miniGame.startGame();
        }
        // THERE WAS A PROBLEM LOADING THE PROPERTIES FILE
        catch(InvalidXMLFileFormatException ixmlffe)
        {
            // LET THE ERROR HANDLER PROVIDE THE RESPONSE
            PathXErrorHandler errorHandler = miniGame.getErrorHandler();
            errorHandler.processError(PathXPropertyType.TEXT_ERROR_LOADING_XML_FILE);
        }
    }

   
    
    /**
     * SortingHatPropertyType represents the types of data that will need
     * to be extracted from XML files.
     */
    public enum PathXPropertyType
    {
        // LOADED FROM properties.xml
        
        /* SETUP FILE NAMES */
        FILE_GAME_PROPERTIES,
        FILE_PLAYER_RECORD,

        /* DIRECTORY PATHS FOR FILE LOADING */
       PATH_AUDIO,
        PATH_IMG,
        
        // LOADED FROM THE GAME FLAVOR PROPERTIES XML FILE
            // sorting_hat_properties.xml
                
        /* IMAGE FILE NAMES */
        IMAGE_BACKGROUND_GAME,
        IMAGE_BACKGROUND_MENU,
        IMAGE_SETTINGS_WINDOW,
        IMAGE_BACKGROUND_GAME_SELECT,
         IMAGE_WINDOW_ICON,
        GAME_SCREEN_IMAGE_BUTTON_HOME,
        GAME_SCREEN_IMAGE_BUTTON_X,
        GAME_SCREEN_IMAGE_BUTTON_START,
        GAME_SCREEN_IMAGE_BUTTON_SCROLL_LEFT,
        GAME_SCREEN_IMAGE_BUTTON_SCROLL_DOWN,
        GAME_SCREEN_IMAGE_BUTTON_SCROLL_RIGHT,
        GAME_SCREEN_IMAGE_BUTTON_SCROLL_UP,
        
       // IMAGE_BUTTON_NEW_MOUSE_OVER,
        /*IMAGE_BUTTON_BACK,
        IMAGE_BUTTON_BACK_MOUSE_OVER,
        IMAGE_BUTTON_STATS,
        
        IMAGE_BUTTON_UNDO_MOUSE_OVER,
        IMAGE_BUTTON_UNDO,
        
        
        IMAGE_BUTTON_STATS_MOUSE_OVER,
        IMAGE_BUTTON_TEMP_TILE,
        IMAGE_BUTTON_TEMP_TILE_MOUSE_OVER,
        IMAGE_CURSOR_WAND,
        IMAGE_DECOR_TIME,      
       // IMAGE_DECOR_MISCASTS,
       // IMAGE_DIALOG_STATS,
       // IMAGE_DIALOG_WIN,
       // IMAGE_SPRITE_SHEET_CHARACTER_TILES,        
        IMAGE_TILE_BACKGROUND,
        IMAGE_TILE_BACKGROUND_SELECTED,
        IMAGE_TILE_BACKGROUND_MOUSE_OVER,
        
        */
       
        /* GAME TEXT */
        TEXT_ERROR_LOADING_AUDIO,
        TEXT_ERROR_LOADING_LEVEL,
        TEXT_ERROR_LOADING_RECORD,
        TEXT_ERROR_LOADING_XML_FILE,
        TEXT_ERROR_SAVING_RECORD,
        
        /*TEXT_LABEL_STATS_ALGORITHM,
        TEXT_LABEL_STATS_GAMES,
        TEXT_LABEL_STATS_WINS,
        TEXT_LABEL_STATS_PERFECT_WINS,
        TEXT_LABEL_STATS_FASTEST_PERFECT_WIN,
        */
        
        TEXT_PROMPT_EXIT,
        TEXT_TITLE_BAR_GAME,
        TEXT_TITLE_BAR_ERROR,
        
        /* AUDIO CUES */
        AUDIO_CUE_BAD_MOVE,
        AUDIO_CUE_CHEAT,
        AUDIO_CUE_DESELECT_TILE,
        AUDIO_CUE_GOOD_MOVE,
        AUDIO_CUE_SELECT_TILE,
        AUDIO_CUE_UNDO,
        AUDIO_CUE_WIN,
        SONG_CUE_GAME_SCREEN,
        SONG_CUE_MENU_SCREEN,
        
        /* TILE LOADING STUFF */
        HOME_SCREEN_IMAGE_OPTIONS,
         SPECIAL_IMAGE_OPTIONS1, 
        SPECIAL_IMAGE_OPTIONS2,
        SPECIAL_IMAGE_OPTIONS3,
              SPECIAL_IMAGE_OPTIONS4,
       HOME_SCREEN_MOUSE_OVER_IMAGE_OPTIONS,
       HOME_SCREEN_IMAGE_EXIT
       
       
       
       
      
          
    }
}