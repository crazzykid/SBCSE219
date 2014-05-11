package pathx;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

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
    
    public static String PROPERTIES_FILE_LEVEL = "level.xml";
    
    public static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    
    public static String PROPERTIES_SCHEMA_FILE_LEVEL = "PathXLevelSchema.xsd";
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
    public static final String GAME_BUTTON_ZOMBIE_TYPE = "GAME_BUTTON_ZOMBIE_TYPE";
    public static final String GAME_BUTTON_PLAYER_TYPE = "GAME_BUTTON_PLAYER_TYPE";
    public static final String GAME_BUTTON_POLICE_TYPE = "GAME_BUTTON_POLICE_TYPE";
    public static final String GAME_BUTTON_BANDIT_TYPE = "GAME_BUTTON_BANDIT_TYPE";
    
    public static final String GAME_X_BUTTON_TYPE = "GAME_X_BUTTON_TYPE";
    public static final String GAME_SETTINGS_X_BUTTON_TYPE = "GAME_SETTINGS_X_BUTTON_TYPE";
    public static final String GAME_START_BUTTON_TYPE = "GAME_START_BUTTON_TYPE";
    public static final String GAME_SCROLL_LEFT_BUTTON_TYPE = "GAME_SCROLL_LEFT_BUTTON_TYPE";
    public static final String GAME_SCROLL_RIGHT_BUTTON_TYPE = "GAME_SCROLL_RIGHT_BUTTON_TYPE";
    public static final String GAME_SCROLL_UP_BUTTON_TYPE = "GAME_SCROLL_UP_BUTTON_TYPE";
    public static final String GAME_SCROLL_DOWN_BUTTON_TYPE = "GAME_SCROLL_DOWN_BUTTON_TYPE";
    public static final String GAME_SCROLL_PAUSE_BUTTON_TYPE = "GAME_SCROLL_PAUSE_BUTTON_TYPE";
    public static final String GAME_TOOLBAR_TYPE = "GAME_TOOLBAR_TYPE";
    
    public static final String HELP_QUIT_TYPE = "HELP_QUIT_TYPE";
    public static final String HELP_QUIT_TYPE2 = "HELP_QUIT_TYPE2";
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
    
    public static final String GAME_HELP_SCREEN_STATE = "GAME_HELP_SCREEN_STATE";
    
    public static final String GAME_DIALOG_STATE = "GAME_DIALOG_STATE";
    
    
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
    
    
    public static final String  RED_STATE = " RED_STATE";
    public static final String  GREEN_STATE = " RED_STATE";
    public static final String  WHITE_STATE = " RED_STATE";
    
    public static final String SCROLL_LEFT = "SCROLL_LEFT";
    public static final String SCROLL_RIGHT = "SCROLL_RIGHT";
    public static final String SCROLL_UP = "SCROLL_UP";
    public static final String SCROLL_DOWN = "SCROLL_DOWN";
    
    public static final String LEVEL1 = "LEVEL1";
    public static final String LEVEL = "LEVEL";
    public static final String LEVEL2 = "LEVEL2";
    public static final String LEVEL3 = "LEVEL3";
    public static final String LEVEL4 = "LEVEL4";
    public static final String LEVEL5 = "LEVEL5";
    public static final String LEVEL6 = "LEVEL6";
    public static final String LEVEL7 = "LEVEL7";
    public static final String LEVEL8 = "LEVEL8";
    public static final String LEVEL9 = "LEVEL9";
    public static final String LEVEL10 = "LEVEL10";
    public static final String LEVEL11 = "LEVEL11";
    public static final String LEVEL12 = "LEVEL12";
    public static final String LEVEL13 = "LEVEL13";
    public static final String LEVEL14 = "LEVEL14";
    public static final String LEVEL15 = "LEVEL15";
    public static final String LEVEL16 = "LEVEL16";
    public static final String LEVEL17 = "LEVEL17";
    public static final String LEVEL18 = "LEVEL18";
    public static final String LEVEL19 = "LEVEL19";
    public static final String LEVEL20 = "LEVEL20";
    
    public static final String POLICE = "POLICE";
    
    public static final String BANDIT = "BANDIT";
    public static final String ZOMBIE = "ZOMBIE";
    
    public static final String PLAYER = "PLAYER";
    
    
    public static final String NAMELEVEL1 = "\tStage 1 \nThe California Lemonade Stand Circuit";
    public static final String NAMELEVEL2 = "\tStage 2 \nThe California Lemonade Stand Circuit";
    public static final String NAMELEVEL3 = "\tStage 3 \nThe California Lemonade Stand Circuit";
    public static final String NAMELEVEL4 = "\tStage 4 \nThe Silicon Valley Tech Circuit";
    public static final String NAMELEVEL5 = "\tStage 5 \nThe Silicon Valley Tech Circuit";
    public static final String NAMELEVEL6 = "\tStage 6 \nThe Silicon Valley Tech Circuit";
    public static final String NAMELEVEL7 = "\tStage 7 \nThe Nevada Casino Circuit";
    public static final String NAMELEVEL8 = "\tStage 8 \nThe Nevada Casino Circuit";
    public static final String NAMELEVEL9 = "\tStage 9 \nThe Nevada Casino Circuit";
    public static final String NAMELEVEL10 = "\tStage 10 \nThe Old West Bank Circuit";
    public static final String NAMELEVEL11 = "\tStage 11 \nThe Old West Bank Circuit";
    public static final String NAMELEVEL12 = "\tStage 12 \nThe Old West Bank Circuit";
    public static final String NAMELEVEL13 = "\tStage 13 \nThe Florida Retirees Circuit";
    public static final String NAMELEVEL14 = "\tStage 14 \nThe Florida Retirees Circuit";
    public static final String NAMELEVEL15 = "\tStage 15 \nThe Florida Retirees Circuit";
    public static final String NAMELEVEL16 = "\tStage 16 \nThe I-95 Corridor Circuit";
    public static final String NAMELEVEL17 = "\tStage 17 \nThe I-95 Corridor Circuit";
    public static final String NAMELEVEL18 = "\tStage 18 \nThe I-95 Corridor Circuit";
    public static final String NAMELEVEL19 = "\tStage 19 \nThe Wall Street Circuit";
    public static final String NAMELEVEL20 = "\tStage 20 \nThe Wall Street Circuit";
    
    
    //    public static final int SCROLL_SPEED = 6;
    // ANIMATION SPEED
    public static final int TOTALMONEYLEVEL = 0;
    public static final int TOTALMONEYLEVEL1 = 80;
    public static final int TOTALMONEYLEVEL2 = 120;
    public static final int TOTALMONEYLEVEL3 = 180;
    public static final int TOTALMONEYLEVEL4 = 280;
    public static final int TOTALMONEYLEVEL5 = 350;
    public static final int TOTALMONEYLEVEL6 = 400;
    public static final int TOTALMONEYLEVEL7 = 520;
    public static final int TOTALMONEYLEVEL8 = 610;
    public static final int TOTALMONEYLEVEL9 = 699;
    public static final int TOTALMONEYLEVEL10 = 785;
    public static final int TOTALMONEYLEVEL11 = 800;
    public static final int TOTALMONEYLEVEL12 = 830;
    public static final int TOTALMONEYLEVEL13 = 840;
    public static final int TOTALMONEYLEVEL14 = 890;
    public static final int TOTALMONEYLEVEL15 = 910;
    public static final int TOTALMONEYLEVEL16 = 930;
    public static final int TOTALMONEYLEVEL17 = 955;
    public static final int TOTALMONEYLEVEL18 = 970;
    public static final int TOTALMONEYLEVEL19 = 985;
    public static final int TOTALMONEYLEVEL20 = 1000;
    
    public static final int LEVEL_OFFSET_LOCATION_X = 50;
    public static final int LEVEL_OFFSET_LOCATION_Y = 320;
    public static final int LEVEL_OFFSET_LOCATION_X1 = 80;
    public static final int LEVEL_OFFSET_LOCATION_Y1 = 470;
    public static final int LEVEL_OFFSET_LOCATION_X2 = 300;
    public static final int LEVEL_OFFSET_LOCATION_Y2 = 530;
    public static final int LEVEL_OFFSET_LOCATION_X3 =300;
    public static final int LEVEL_OFFSET_LOCATION_Y3 = 415;
    public static final int LEVEL_OFFSET_LOCATION_X4 = 295;
    public static final int LEVEL_OFFSET_LOCATION_Y4 = 500;
    public static final int LEVEL_OFFSET_LOCATION_X5 = 305;
    public static final int LEVEL_OFFSET_LOCATION_Y5 = 650;
    public static final int LEVEL_OFFSET_LOCATION_X6 = 500;
    public static final int LEVEL_OFFSET_LOCATION_Y6 = 570;
    public static final int LEVEL_OFFSET_LOCATION_X7 = 580;
    public static final int LEVEL_OFFSET_LOCATION_Y7 = 415;
    public static final int LEVEL_OFFSET_LOCATION_X8 = 610;
    public static final int LEVEL_OFFSET_LOCATION_Y8 = 650;
    public static final int LEVEL_OFFSET_LOCATION_X9 = 605;
    public static final int LEVEL_OFFSET_LOCATION_Y9 = 870;
    public static final int LEVEL_OFFSET_LOCATION_X10 = 605;
    public static final int LEVEL_OFFSET_LOCATION_Y10 = 500;
    public static final int LEVEL_OFFSET_LOCATION_X11 = 705;
    public static final int LEVEL_OFFSET_LOCATION_Y11 = 550;
    public static final int LEVEL_OFFSET_LOCATION_X12 = 650;
    public static final int LEVEL_OFFSET_LOCATION_Y12 = 770;
    public static final int LEVEL_OFFSET_LOCATION_X13 = 700;
    public static final int LEVEL_OFFSET_LOCATION_Y13 = 700;
    public static final int LEVEL_OFFSET_LOCATION_X14 = 730;
    public static final int LEVEL_OFFSET_LOCATION_Y14 = 740;
    public static final int LEVEL_OFFSET_LOCATION_X15 = 760;
    public static final int LEVEL_OFFSET_LOCATION_Y15 = 650;
    public static final int LEVEL_OFFSET_LOCATION_X16 = 870;
    public static final int LEVEL_OFFSET_LOCATION_Y16 = 670;
    public static final int LEVEL_OFFSET_LOCATION_X17 = 850;
    public static final int LEVEL_OFFSET_LOCATION_Y17 = 585;
    public static final int LEVEL_OFFSET_LOCATION_X18 = 919;
    public static final int LEVEL_OFFSET_LOCATION_Y18 = 785;
    public static final int LEVEL_OFFSET_LOCATION_X19 = 930;
    public static final int LEVEL_OFFSET_LOCATION_Y19 = 705;
    public static final int LEVEL_OFFSET_LOCATION_X20 = 1100;
    public static final int LEVEL_OFFSET_LOCATION_Y20 = 765;
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
    
    public static final int LEVEL1X  = VIEWPORT_OFFSET_X-160;
    public static final int LEVEL1Y  = VIEWPORT_OFFSET_X-220;
    
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
    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;
    public static final int TILE_IMAGE_OFFSET_X = 45;
    public static final int TILE_IMAGE_OFFSET_Y = 30;
    public static final String TILE_SPRITE_TYPE_PREFIX = "TILE_";
    public static final int COLOR_INC = 10;
    
    
    public static final int ZOMBIEID = 10;
    public static final int BANDITID = 100;
    public static final int POLICEID = 200;
    
    // FOR MOVING TILES AROUND
    public static final int MAX_TILE_VELOCITY = 20;
    
    // UI CONTROLS POSITIONS IN THE GAME SCREEN
    public static final int NORTH_PANEL_HEIGHT = 130;
    public static final int CONTROLS_MARGIN = 0;
    public static final int EXIT_BUTTON_X = 1190;
    public static final int EXIT_BUTTON_Y = 0;
    public static final int START_BUTTON_X = 20;
    public static final int START_BUTTON_Y = 215;
    
    
    
    public static final int HOME_BUTTON_SETTING_X = 620;
    public static final int HOME_BUTTON_SETTING_Y = 5;
    
    
    public static final int PLAYERLEVEL1X = 50;
    public static final int PLAYERLEVEL1Y = 51;
    
    
    
    
    public static final int HOME_SETTINGS_X_BUTTON_X = 685;
    public static final int HOME_SETTINGS_X_BUTTON_Y = 5;
    
    public static final int HOME_BUTTON_X = 25;
    public static final int HOME_BUTTON_Y = 128;
    
    public static final int HOME_X_BUTTON_X = 93;
    public static final int HOME_X_BUTTON_Y = 128;
    
    
    public static final int SCROLL_LEFT_BUTTON_X = 40;
    public static final int SCROLL_LEFT_BUTTON_Y = 525;;
    
    
    public static final int SCROLL_RIGHT_BUTTON_X = 102;
    public static final int SCROLL_RIGHT_BUTTON_Y = 525;
    
    public static final int SCROLL_UP_BUTTON_X = 72;
    public static final int SCROLL_UP_BUTTON_Y = 488;
    
    public static final int SCROLL_DOWN_BUTTON_X = 72;
    public static final int SCROLL_DOWN_BUTTON_Y = 560;
    
    public static final int SCROLL_PAUSE_BUTTON_X = 70;
    public static final int SCROLL_PAUSE_BUTTON_Y = 515;
    
    public static final int QUIT_BUTTON_X = 285;
    public static final int QUIT_BUTTON_Y = 425;
    
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
    public static final int MOVE_PATH_NODES = 5;
    public static final int MOVE_PATH_TOLERANCE = 100;
    public static final int MOVE_PATH_COORD = 100;
    
    // COLORS USED FOR RENDERING VARIOUS THINGS, INCLUDING THE
    // COLOR KEY, WHICH REFERS TO THE COLOR TO IGNORE WHEN
    // LOADING ART.
    public static final Color COLOR_KEY = new Color(255, 174, 201);
    public static final Color COLOR_DEBUG_TEXT = Color.BLACK;
    public static final Color COLOR_IMGAE = Color.GRAY;
    public static final Color COLOR_TEXT_DISPLAY = new Color (10, 160, 10);
    public static final Color COLOR_STATS = new Color(0, 60, 0);
    public static final Color COLOR_HEADER = Color.BLACK;
    
    // FONTS USED DURING FOR TEXTUAL GAME DISPLAYS
    public static final Font FONT_TEXT_DISPLAY = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    public static final Font FONT_DEBUG_TEXT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    public static final Font FONT_STATS = new Font(Font.MONOSPACED, Font.BOLD, 20);
    public static final Font FONT_NAME = new Font(Font.SANS_SERIF, Font.BOLD, 38);
    public static final String BALANCE = "BALANCE  : $";
    public static final String BANK =    "BANK        : $";


    
    
    // LEVEL EDITOR PATHS
    public static final String  DATA_PATH               = "data/";
    public static final String  BUTTONS_PATH            = DATA_PATH + "buttons/";
    public static final String  CURSORS_PATH            = DATA_PATH + "cursors/";
    public static final String  LEVELS_PATH             = DATA_PATH + "levels/";
    
    // BUTTON IMAGES
    public static final String  NEW_BUTTON_FILE     = BUTTONS_PATH + "New.png";
    public static final String  OPEN_BUTTON_FILE    = BUTTONS_PATH + "Open.png";
    public static final String  SAVE_BUTTON_FILE    = BUTTONS_PATH + "Save.png";
    public static final String  SAVE_AS_BUTTON_FILE = BUTTONS_PATH + "SaveAs.png";
    public static final String  EXIT_BUTTON_FILE    = BUTTONS_PATH + "Exit.png";
    public static final String  BG_BUTTON_FILE      = BUTTONS_PATH + "ChangeBackground.png";
    public static final String  START_BUTTON_FILE   = BUTTONS_PATH + "SelectStartLocation.png";
    public static final String  DEST_BUTTON_FILE    = BUTTONS_PATH + "SelectDestination.png";
    public static final String  MONEY_LABEL_FILE   = BUTTONS_PATH + "Money.png";
    public static final String  POLICE_LABEL_FILE   = BUTTONS_PATH + "Police.png";
    public static final String  BANDITS_LABEL_FILE  = BUTTONS_PATH + "Bandits.png";
    public static final String  ZOMBIES_LABEL_FILE  = BUTTONS_PATH + "Zombies.png";
    
    // BUTTON MOUSE OVER TEXT/ACTION COMMANDS
    public static final String NEW_COMMAND          = "New Level";
    public static final String OPEN_COMMAND         = "Open Level";
    public static final String SAVE_COMMAND         = "Save Level";
    public static final String SAVE_AS_COMMAND      = "Save As Level";
    public static final String EXIT_COMMAND         = "Exit";
    public static final String BG_IMAGE_COMMAND     = "Select Background Image";
    public static final String START_IMAGE_COMMAND  = "Select Start Image";
    public static final String DEST_IMAGE_COMMAND   = "Selecct Destination Image";
    public static final String MONEY_COMMAND        = "Update $ Value of Level";
    public static final String POLICE_COMMAND       = "Inc/Dec Number of Police";
    public static final String BANDITS_COMMAND      = "Inc/Dec Number of Bandits";
    public static final String ZOMBIES_COMMAND      = "Inc/Dec Number of Zombies";
    
    // CURSOR FILES
    public static final String DEFAULT_CURSOR_IMG   = "DefaultCursor.png";
    public static final String ADD_INT_CURSOR_IMG   = "AddIntersectionCursor.png";
    public static final String ADD_ROAD_CURSOR_IMG  = "AddRoadCursor.png";
    
    
    
    public static final String MAKE_LIGHT_GREEN = "IMGAE_BUTTON_GREEN_LIGHT.png";
    public static final String MAKE_LIGHT_RED = "IMGAE_BUTTON_RED_LIGHT.png";
    public static final String DECREASE_SPEED_LIMIT = "IMGAE_BUTTON_DECREASE_SPEED.png";
    public static final String INCREASE_SPEED_LIMIT = "IMGAE_BUTTON_INCREASE_SPEED.png";
    public static final String INCREASE_PLAYER_SPEED = "IMGAE_BUTTON_INCREASE_PLAYER_SPEED.png";
    public static final String FLAT_TIRE            = "IMGAE_BUTTON_FLAT_TIRE.png";
    public static final String EMPTY_GAS_TANK       = "IMGAE_BUTTON_EMPTY_GAS.png";
    public static final String CLOSE_ROAD           = "IMGAE_BUTTON_ROAD_CLOSE.png";
    public static final String STEAL                = "IMGAE_BUTTON_STEAL.png";
    public static final String MIND_CONTROL         = "IMGAE_BUTTON_MIND_CONTROL.png";
    public static final String INTANGIBILITY       = "IMGAE_BUTTON_INTANGIBILITY.png";
    public static final String MINDLESS_TERROR = "IMGAE_BUTTON_MIND_TERROR.png";
    public static final String FLYING           = "IMGAE_BUTTON_FLY.png";
    public static final String INVINCIBILITY =     "IMGAE_BUTTON_INVINCIBILITY.png";
    
    
    
    // DEFAULT IMAGE FILES
    public static final String DEFAULT_BG_IMG       = "DeathValleyBackground.png";
    public static final String DEFAULT_START_IMG    = "DefaultStartLocation.png";
    public static final String DEFAULT_DEST_IMG     = "DefaultDestination.png";
    
    /***** DIALOG MESSAGES AND TITLES *****/
    // DIALOG BOX MESSAGES TO GIVE FEEDBACK BACK TO THE USER
    public static final String LEVEL_NAME_REQUEST_TEXT              = "What do you want to name your level?";
    public static final String LEVEL_NAME_REQUEST_TITLE_TEXT        = "Enter Level File Name";
    public static final String OVERWRITE_FILE_REQUEST_TEXT_A        = "There is already a file called \n";
    public static final String OVERWRITE_FILE_REQUEST_TEXT_B        = "\nWould you like to overwrite it?";
    public static final String OVERWRITE_FILE_REQUEST_TITLE_TEXT    = "Overwrite File?";
    public static final String NO_FILE_SELECTED_TEXT                = "No File was Selected to Open";
    public static final String NO_FILE_SELECTED_TITLE_TEXT          = "No File Selected";
    public static final String LEVEL_LOADED_TEXT                    = "Level File has been Loaded";
    public static final String LEVEL_LOADED_TITLE_TEXT              = "Level File Loaded";
    public static final String LEVEL_LOADING_ERROR_TEXT             = "An Error Occured While Loading the Level";
    public static final String LEVEL_LOADING_ERROR_TITLE_TEXT       = "Level Loading Error";
    public static final String LEVEL_SAVED_TEXT                     = "Level File has been Saved";
    public static final String LEVEL_SAVED_TITLE_TEXT               = "Level File Saved";
    public static final String LEVEL_SAVING_ERROR_TEXT              = "An Error Occured While Saving the Level";
    public static final String LEVEL_SAVING_ERROR_TITLE_TEXT        = "Level Saving Error";
    public static final String PROMPT_TO_SAVE_TEXT                  = "Would you like to save your Level?";
    public static final String PROMPT_TO_SAVE_TITLE_TEXT            = "Save your Level?";
    
    // WE'LL NEED THESE TO DYNAMICALLY BUILD TEXT
    public static final String TITLE                        = "pathX Level Editor";
    public static final String EMPTY_TEXT                   = "";
    public static final String LEVEL_FILE_EXTENSION         = ".xml";
    public static final String APP_NAME                     = "PathX Level Editor";
    public static final String APP_NAME_FILE_NAME_SEPARATOR = " - ";
    public static final String PNG_FORMAT_NAME              = "png";
    public static final String PNG_FILE_EXTENSION           = "." + PNG_FORMAT_NAME;
    
    // LEVEL EDITOR UI DIMENSIONS
    //  public static final int WINDOW_WIDTH = 1000;
    //  public static final int WINDOW_HEIGHT = 650;
    public static final int BUTTON_INSETS = 2;
    public static final int BUTTON_WIDTH = 32;
    public static final int BUTTON_HEIGHT = 32;
    
    // FOR SCROLLING THE VIEWPORT
    public static final int SCROLL_SPEED = 6;
    
    // RENDERING SETTINGS
    public static final int INTERSECTION_RADIUS = 20;
    public static final int INT_STROKE = 3;
    public static final int ONE_WAY_TRIANGLE_HEIGHT = 40;
    public static final int ONE_WAY_TRIANGLE_WIDTH = 60;
    
    // INITIAL START/DEST LOCATIONS
    public static final int DEFAULT_START_X = 32;
    public static final int DEFAULT_START_Y = 100;
    public static final int DEFAULT_DEST_X = 650;
    public static final int DEFAULT_DEST_Y = 100;
    
    // FOR INITIALIZING THE SPINNERS
    public static final int MIN_BOTS_PER_LEVEL = 0;
    public static final int MAX_BOTS_PER_LEVEL = 50;
    public static final int BOTS_STEP = 1;
    public static final int MIN_MONEY = 100;
    public static final int MAX_MONEY = 10000;
    public static final int STEP_MONEY = 100;
    public static final int DEFAULT_MONEY = 100;
    
    // AND FOR THE ROAD SPEED LIMITS
    public static final int DEFAULT_SPEED_LIMIT = 30;
    public static final int MIN_SPEED_LIMIT = 10;
    public static final int MAX_SPEED_LIMIT = 100;
    public static final int SPEED_LIMIT_STEP = 10;
    
    // OTHER UI SETTINGS
    public static final Border  TOOLBAR_BORDER                  = BorderFactory.createEtchedBorder();
    public static final Border  LABELED_SPINNER_PANEL_BORDER    = BorderFactory.createEtchedBorder();
    
    // DEFAULT COLORS
    public static final Color   INT_OUTLINE_COLOR   = Color.BLACK;
    public static final Color   HIGHLIGHTED_COLOR = Color.YELLOW;
    public static final Color   OPEN_INT_COLOR      = Color.GREEN;
    public static final Color   CLOSED_INT_COLOR    = Color.RED;
    
    // FOR RENDERING STATS
    public static final Color STATS_TEXT_COLOR = Color.ORANGE;
    public static final Font STATS_TEXT_FONT = new Font("Monospace", Font.BOLD, 16);
    public static final String MOUSE_SCREEN_POSITION_TITLE = "Screen Mouse Position: ";
    public static final String MOUSE_LEVEL_POSITION_TITLE = "Level Mouse Position: ";
    public static final String VIEWPORT_POSITION_TITLE = "Viewport Position: ";
    
    // FOR POSITIONING THE STATS
    public static final int STATS_X = 20;
    public static final int STATS_Y_DIFF = 20;
    public static final int MOUSE_SCREEN_POSITION_Y = 20;
    public static final int MOUSE_LEVEL_POSITION_Y = MOUSE_SCREEN_POSITION_Y + STATS_Y_DIFF;
    public static final int VIEWPORT_POSITION_Y = MOUSE_LEVEL_POSITION_Y + STATS_Y_DIFF;
    
    // FOR LOADING STUFF FROM OUR LEVEL XML FILES
    // THIS IS THE NAME OF THE SCHEMA
    public static final String  LEVEL_SCHEMA = "PathXLevelSchema.xsd";
    
    // CONSTANTS FOR LOADING DATA FROM THE XML FILES
    // THESE ARE THE XML NODES
    public static final String LEVEL_NODE = "level";
    public static final String INTERSECTIONS_NODE = "intersections";
    public static final String INTERSECTION_NODE = "intersection";
    public static final String ROADS_NODE = "roads";
    public static final String ROAD_NODE = "road";
    public static final String START_INTERSECTION_NODE = "start_intersection";
    public static final String DESTINATION_INTERSECTION_NODE = "destination_intersection";
    public static final String MONEY_NODE = "money";
    public static final String POLICE_NODE = "police";
    public static final String BANDITS_NODE = "bandits";
    public static final String ZOMBIES_NODE = "zombies";
    
    // AND THE ATTRIBUTES FOR THOSE NODES
    public static final String NAME_ATT = "name";
    public static final String IMAGE_ATT = "image";
    public static final String ID_ATT = "id";
    public static final String X_ATT = "x";
    public static final String Y_ATT = "y";
    public static final String OPEN_ATT = "open";
    public static final String INT_ID1_ATT = "int_id1";
    public static final String INT_ID2_ATT = "int_id2";
    public static final String SPEED_LIMIT_ATT = "speed_limit";
    public static final String ONE_WAY_ATT = "one_way";
    public static final String AMOUNT_ATT = "amount";
    public static final String NUM_ATT = "num";
    
    // FOR NICELY FORMATTED XML OUTPUT
    public static final String XML_INDENT_PROPERTY = "{http://xml.apache.org/xslt}indent-amount";
    public static final String XML_INDENT_VALUE = "5";
    public static final String YES_VALUE = "Yes";
    
       public static final int TEMP_TILE_Y = 0;
         public static final int TEMP_TILE_X = STATS_X + 280 + CONTROLS_MARGIN;
    public static final int BALANCE_X = 280;
    public static final int  BALANCE_Y = 45;
}