package pathx;

import java.awt.Color;
import java.awt.Font;

/**
 * This class stores all the constants used by The Sorting Hat application. We'll
 * do this here rather than load them from files because many of these are
 * derived from each other.
 * 
 * @author Richard McKenna
 */
public class PathXConstants
{
    // WE NEED THESE CONSTANTS JUST TO GET STARTED
    // LOADING SETTINGS FROM OUR XML FILES
    public static String PROPERTY_TYPES_LIST = "property_types.txt";
    public static String PROPERTIES_FILE_NAME = "properties.xml";
    public static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";    
    public static String PATH_DATA = "./data/";
    
    // THESE ARE THE TYPES OF CONTROLS, WE USE THESE CONSTANTS BECAUSE WE'LL
    // STORE THEM BY TYPE, SO THESE WILL PROVIDE A MEANS OF IDENTIFYING THEM
    
    // EACH SCREEN HAS ITS OWN BACKGROUND TYPE
    public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";
     public static final String LEVEL_SELECT_BACKGROUND_TYPE = "LEVEL_SELECT_BACKGROUND_TYPE";
    //public static final String BUBBLE_SORT = "BUBBLE_SORT";
   // public static final String SELECTION_SORT =   "SELECTION_SORT";
    
    // THIS REPRESENTS THE BUTTONS ON THE MENU SCREEN FOR LEVEL SELECTION
    public static final String LEVEL_SELECT_BUTTON_TYPE = "LEVEL_SELECT_BUTTON_TYPE";
    
    //THIS REPRESENTS THE SPECIAL EFFECT BUTTONS ON THE GAME SCREEN
    public static final String SPECIAL_SELECT_BUTTON_TYPE = "SPECIAL_SELECT_BUTTON_TYPE";

    // IN-GAME UI CONTROL TYPES
    public static final String EXIT_GAME_BUTTON_TYPE = "EXIT_GAME_BUTTON_TYPE";
        public static final String GAME_HOME_BUTTON_TYPE = "GAME_HOME_BUTTON_TYPE";
        public static final String GAME_HOME_BUTTON_SETTING_TYPE = "GAME_HOME_SETTING_BUTTON_TYPE";
            public static final String GAME_X_BUTTON_TYPE = "GAME_X_BUTTON_TYPE";
                public static final String GAME_START_BUTTON_TYPE = "GAME_START_BUTTON_TYPE";
                    public static final String GAME_SCROLL_LEFT_BUTTON_TYPE = "GAME_SCROLL_LEFT_BUTTON_TYPE";
             public static final String GAME_SCROLL_RIGHT_BUTTON_TYPE = "GAME_SCROLL_RIGHT_BUTTON_TYPE";
              public static final String GAME_SCROLL_UP_BUTTON_TYPE = "GAME_SCROLL_UP_BUTTON_TYPE";
               public static final String GAME_SCROLL_DOWN_BUTTON_TYPE = "GAME_SCROLL_DOWN_BUTTON_TYPE";
                 public static final String GAME_TOOLBAR_TYPE = "GAME_TOOLBAR_TYPE";
                 
                  public static final String GAME_PLAY_LEVEL_RED_TYPE1 = "GAME_PLAY_LEVEL_RED_TYPE1";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE1 = "GAME_PLAY_LEVEL_WHITE_TYPE1";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE1 = "GAME_PLAY_LEVEL_GREEN_TYPE1";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE2 = "GAME_PLAY_LEVEL_RED_TYPE2";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE2 = "GAME_PLAY_LEVEL_WHITE_TYPE2";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE2 = "GAME_PLAY_LEVEL_GREEN_TYPE2";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE3 = "GAME_PLAY_LEVEL_RED_TYPE3";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE3 = "GAME_PLAY_LEVEL_WHITE_TYPE3";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE3 = "GAME_PLAY_LEVEL_GREEN_TYPE3";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE4 = "GAME_PLAY_LEVEL_RED_TYPE4";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE4 = "GAME_PLAY_LEVEL_WHITE_TYPE4";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE4 = "GAME_PLAY_LEVEL_GREEN_TYPE4";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE5 = "GAME_PLAY_LEVEL_RED_TYPE5";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE5 = "GAME_PLAY_LEVEL_WHITE_TYPE5";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE5 = "GAME_PLAY_LEVEL_GREEN_TYPE5";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE6 = "GAME_PLAY_LEVEL_RED_TYPE6";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE6 = "GAME_PLAY_LEVEL_WHITE_TYPE6";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE6 = "GAME_PLAY_LEVEL_GREEN_TYPE6";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE7 = "GAME_PLAY_LEVEL_RED_TYPE7";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE7 = "GAME_PLAY_LEVEL_WHITE_TYPE7";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE7 = "GAME_PLAY_LEVEL_GREEN_TYPE7";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE8 = "GAME_PLAY_LEVEL_RED_TYPE8";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE8 = "GAME_PLAY_LEVEL_WHITE_TYPE8";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE8 = "GAME_PLAY_LEVEL_GREEN_TYPE8";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE9 = "GAME_PLAY_LEVEL_RED_TYPE9";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE9 = "GAME_PLAY_LEVEL_WHITE_TYPE9";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE9 = "GAME_PLAY_LEVEL_GREEN_TYPE9";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE10 = "GAME_PLAY_LEVEL_RED_TYPE10";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE10 = "GAME_PLAY_LEVEL_WHITE_TYPE10";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE10 = "GAME_PLAY_LEVEL_GREEN_TYPE10";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE11 = "GAME_PLAY_LEVEL_RED_TYPE11";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE11 = "GAME_PLAY_LEVEL_WHITE_TYPE11";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE11 = "GAME_PLAY_LEVEL_GREEN_TYPE11";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE12 = "GAME_PLAY_LEVEL_RED_TYPE12";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE12 = "GAME_PLAY_LEVEL_WHITE_TYPE12";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE12 = "GAME_PLAY_LEVEL_GREEN_TYPE12";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE13 = "GAME_PLAY_LEVEL_RED_TYPE13";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE13 = "GAME_PLAY_LEVEL_WHITE_TYPE13";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE13 = "GAME_PLAY_LEVEL_GREEN_TYPE13";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE14 = "GAME_PLAY_LEVEL_RED_TYPE14";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE14 = "GAME_PLAY_LEVEL_WHITE_TYPE14";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE14 = "GAME_PLAY_LEVEL_GREEN_TYPE14";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE15 = "GAME_PLAY_LEVEL_RED_TYPE15";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE15 = "GAME_PLAY_LEVEL_WHITE_TYPE15";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE15 = "GAME_PLAY_LEVEL_GREEN_TYPE15";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE16 = "GAME_PLAY_LEVEL_RED_TYPE16";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE16 = "GAME_PLAY_LEVEL_WHITE_TYPE16";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE16 = "GAME_PLAY_LEVEL_GREEN_TYPE16";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE17 = "GAME_PLAY_LEVEL_RED_TYPE17";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE17 = "GAME_PLAY_LEVEL_WHITE_TYPE17";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE17 = "GAME_PLAY_LEVEL_GREEN_TYPE17";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE18 = "GAME_PLAY_LEVEL_RED_TYPE18";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE18 = "GAME_PLAY_LEVEL_WHITE_TYPE18";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE18 = "GAME_PLAY_LEVEL_GREEN_TYPE18";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE19 = "GAME_PLAY_LEVEL_RED_TYPE19";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE19 = "GAME_PLAY_LEVEL_WHITE_TYPE19";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE19 = "GAME_PLAY_LEVEL_GREEN_TYPE19";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE20 = "GAME_PLAY_LEVEL_RED_TYPE20";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE20 = "GAME_PLAY_LEVEL_WHITE_TYPE20";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE20 = "GAME_PLAY_LEVEL_GREEN_TYPE20";
                  public static final String GAME_PLAY_LEVEL_RED_TYPE = "GAME_PLAY_LEVEL_RED_TYPE";
                  public static final String GAME_PLAY_LEVEL_WHITE_TYPE = "GAME_PLAY_LEVEL_WHITE_TYPE";
                  public static final String GAME_PLAY_LEVEL_GREEN_TYPE = "GAME_PLAY_LEVEL_GREEN_TYPE";
                  
    
   // public static final String BACK_BUTTON_TYPE = "BACK_BUTTON_TYPE";
      public static final String PLAY_GAME_BUTTON_TYPE = "PLAY_GAME_BUTTON_TYPE";
   // public static final String UNDO_BUTTON_TYPE ="UNDO_BUTTON_TYPE";
    
    
    
   // public static final String MISCASTS_COUNT_TYPE = "TILE_COUNT_TYPE";
   // public static final String TIME_TYPE = "TIME_TYPE"; 
   // public static final String STATS_BUTTON_TYPE = "STATS_BUTTON_TYPE";
   // public static final String ALGORITHM_TYPE = "ALGORITHM_TYPE";

    // DIALOG TYPES
  //  public static final String WIN_DIALOG_TYPE = "WIN_DIALOG_TYPE";
   // public static final String STATS_DIALOG_TYPE = "STATS_DIALOG_TYPE";
    
    // WE'LL USE THESE STATES TO CONTROL SWITCHING BETWEEN THE TWO
    public static final String MENU_SCREEN_STATE = "MENU_SCREEN_STATE";
    public static final String GAME_SCREEN_STATE = "GAME_SCREEN_STATE"; 
   public static final String GAME_SETTINGS_STATE = "GAME_SETTINGS_STATE";
   public static final String GAME_LEVEL_STATE = "GAME_LEVEL_STATE";
   
   
      public static final String SCROLL_LEFT = "SCROLL_LEFT";
      public static final String SCROLL_RIGHT = "SCROLL_RIGHT";
      public static final String SCROLL_UP = "SCROLL_UP";
      public static final String SCROLL_DOWN = "SCROLL_DOWN";
   
    // ANIMATION SPEED
    public static final int FPS = 30;

    // UI CONTROL SIZE AND POSITION SETTINGS
    public static final int WINDOW_WIDTH = 750; //640
    public static final int WINDOW_HEIGHT = 650; //480
    public static final int VIEWPORT_MARGIN_LEFT = 20;
    public static final int VIEWPORT_MARGIN_RIGHT = 20;
    public static final int VIEWPORT_MARGIN_TOP = 20;
    public static final int VIEWPORT_MARGIN_BOTTOM = 20;
    
   
    
        public static final int VIEWPORT_OFFSET_X = 350;
    public static final int VIEWPORT_OFFSET_Y = 350;
    
    public static final int LEVEL_BUTTON_WIDTH = 150;
    public static final int LEVEL_BUTTON_MARGIN = 5;
    
    public static final int SPECIAL_BUTTON_WIDTH = 28;
    public static final int SPECIAL_BUTTON_MARGIN = 8;
    public static final int SPECIAL_BUTTON_Y = 286;
     public static final int SPECIAL_BUTTON_Y3 = 335;
         public static final int SPECIAL_BUTTON_Y2 = 384;
         public static final int SPECIAL_BUTTON_Y4 = 434;
        
    public static final int LEVEL_BUTTON_Y = 510;
    public static final int VIEWPORT_INC = 5;
        
    // FOR TILE RENDERING
    public static final int NUM_TILES = 30;
    public static final int TILE_WIDTH = 135;
    public static final int TILE_HEIGHT = 126;
    public static final int TILE_IMAGE_OFFSET_X = 45;
    public static final int TILE_IMAGE_OFFSET_Y = 30;
    public static final String TILE_SPRITE_TYPE_PREFIX = "TILE_";
    public static final int COLOR_INC = 10;
    
    // FOR MOVING TILES AROUND
    public static final int MAX_TILE_VELOCITY = 20;
    
    // UI CONTROLS POSITIONS IN THE GAME SCREEN
    public static final int NORTH_PANEL_HEIGHT = 130;
    public static final int CONTROLS_MARGIN = 0;
    public static final int EXIT_BUTTON_X = 1190;
    public static final int EXIT_BUTTON_Y = 0;
       public static final int START_BUTTON_X = 60;
    public static final int START_BUTTON_Y = 240;
    
      public static final int HOME_BUTTON_X = 57;
    public static final int HOME_BUTTON_Y = 142;
    
    public static final int HOME_BUTTON_SETTING_X = 500;
    public static final int HOME_BUTTON_SETTING_Y = 50;
    
      public static final int HOME_X_BUTTON_X = 170;
    public static final int HOME_X_BUTTON_Y = 142;
    
    
      public static final int SCROLL_LEFT_BUTTON_X = 49;
    public static final int SCROLL_LEFT_BUTTON_Y = 585;;
    
    
      public static final int SCROLL_RIGHT_BUTTON_X = 181;
    public static final int SCROLL_RIGHT_BUTTON_Y = 586;
    
      public static final int SCROLL_UP_BUTTON_X = 114;
    public static final int SCROLL_UP_BUTTON_Y = 560;
    
      public static final int SCROLL_DOWN_BUTTON_X = 114;
    public static final int SCROLL_DOWN_BUTTON_Y = 618;
    
    
          public static final int TOOLBAR_X = 0;
    public static final int TOOLBAR_Y = 0;
    
    
    
   // public static final int BACK_BUTTON_X = 130;
   // public static final int BACK_BUTTON_Y = 0;
    
   //  public static final int UNDO_BUTTON_X = 950;
   //// public static final int UNDO_BUTTON_Y = 0;
    
    
   // public static final int TILE_COUNT_X = NEW_BUTTON_X + 260 + CONTROLS_MARGIN;
  //  public static final int TILE_COUNT_Y = 0;
  //  public static final int TILE_COUNT_OFFSET = 145;
 //   public static final int TILE_TEXT_OFFSET = 60;
 //   public static final int TIME_X = TILE_COUNT_X + 232 + CONTROLS_MARGIN;
  //  public static final int TIME_Y = 0;
  //  public static final int TIME_OFFSET = 130;
  //  public static final int TIME_TEXT_OFFSET = 55;
 //   public static final int STATS_X = TIME_X + 310 + CONTROLS_MARGIN;
   // public static final int STATS_Y = 0;
  //  public static final int TEMP_TILE_X = STATS_X + 280 + CONTROLS_MARGIN;
  //  public static final int TEMP_TILE_Y = 0;
  //  public static final int TEMP_TILE_OFFSET_X = 30;
  //  public static final int TEMP_TILE_OFFSET_Y = 12;
  //  public static final int TEMP_TILE_OFFSET2 = 105;
  //  
    // STATS DIALOG COORDINATES
   // public static final int STATS_LEVEL_INC_Y = 30;
  //  public static final int STATS_LEVEL_X = 460;
  //  public static final int STATS_LEVEL_Y = 300;
  //  public static final int ALGORITHM_TEMP_TILE_X = TEMP_TILE_X +10;
  //  public static final int  ALGORITHM_TEMP_TILE_Y =TEMP_TILE_Y+45;
   // public static final int STATS_ALGORITHM_Y = STATS_LEVEL_Y + (STATS_LEVEL_INC_Y * 2);
   // public static final int STATS_GAMES_Y = STATS_ALGORITHM_Y + STATS_LEVEL_INC_Y;
   // public static final int STATS_WINS_Y = STATS_GAMES_Y + STATS_LEVEL_INC_Y;
   // public static final int STATS_PERFECT_WINS_Y = STATS_WINS_Y + STATS_LEVEL_INC_Y;
   // public static final int STATS_FASTEST_PERFECT_WIN_Y = STATS_PERFECT_WINS_Y + STATS_LEVEL_INC_Y;
    
    
    // THESE ARE USED FOR FORMATTING THE TIME OF GAME
    public static final long MILLIS_IN_A_SECOND = 1000;
    public static final long MILLIS_IN_A_MINUTE = 1000 * 60;
    public static final long MILLIS_IN_AN_HOUR  = 1000 * 60 * 60;

    // USED FOR DOING OUR VICTORY ANIMATION
  //  public static final int WIN_PATH_NODES = 5;
  //  public static final int WIN_PATH_TOLERANCE = 100;
  //  public static final int WIN_PATH_COORD = 100;

    // COLORS USED FOR RENDERING VARIOUS THINGS, INCLUDING THE
    // COLOR KEY, WHICH REFERS TO THE COLOR TO IGNORE WHEN
    // LOADING ART.
    public static final Color COLOR_KEY = new Color(255, 174, 201);
    public static final Color COLOR_DEBUG_TEXT = Color.BLACK;
    public static final Color COLOR_TEXT_DISPLAY = new Color (10, 160, 10);
    public static final Color COLOR_STATS = new Color(0, 60, 0);
    public static final Color COLOR_ALGORITHM_HEADER = Color.WHITE;

    // FONTS USED DURING FOR TEXTUAL GAME DISPLAYS
    public static final Font FONT_TEXT_DISPLAY = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    public static final Font FONT_DEBUG_TEXT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    public static final Font FONT_STATS = new Font(Font.MONOSPACED, Font.BOLD, 20);
     public static final Font FONT_SORT_NAME = new Font(Font.MONOSPACED, Font.BOLD, 20);
}