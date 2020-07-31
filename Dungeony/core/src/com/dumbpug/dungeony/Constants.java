package com.dumbpug.dungeony;

/**
 * Application constants.
 */
public class Constants {
    //==============================================================================================
    // DISPLAY
    //==============================================================================================
    public static final int WINDOW_HEIGHT 	                        = 1000;
    public static final int WINDOW_WIDTH	                        = 1200;

    //==============================================================================================
    // INOUT
    //==============================================================================================
    public static final float INPUT_CONTROLLER_AXIS_DEADZONE 	    = 0.1f;

    //==============================================================================================
    // SPLASH
    //==============================================================================================
    public static final long SPLASH_DURATION 	                    = 500l;

    //==============================================================================================
    // GAME
    //==============================================================================================
    public static final float GAME_TILE_SIZE 	                    = 64f;
    public static final float GAME_GRID_CELL_SIZE                   = GAME_TILE_SIZE * 4f;
    public static final float GAME_PLAYER_SIZE                      = GAME_TILE_SIZE * 0.5f;

    public static final float GAME_PLAYER_MOVEMENT_PS               = GAME_TILE_SIZE * 1.6f;

    public static final float GAME_PICKUP_SIZE                      = 32f;
}
