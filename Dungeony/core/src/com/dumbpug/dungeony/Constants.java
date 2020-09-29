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
    // INPUT
    //==============================================================================================
    public static final float INPUT_CONTROLLER_AXIS_DEADZONE 	    = 0.1f;

    //==============================================================================================
    // SPLASH
    //==============================================================================================
    public static final long SPLASH_DURATION 	                    = 500l;

    //==============================================================================================
    // LEVEL
    //==============================================================================================
    public static final float LEVEL_TILE_SIZE                       = 64f;
    public static final float LEVEL_GRID_CELL_SIZE                  = LEVEL_TILE_SIZE * 4f;
    public static final float LEVEL_DEFAULT_ZOOM                    = 0.35f;

    //==============================================================================================
    // CHARACTER
    //==============================================================================================
    public static final int CHARACTER_DEFAULT_HEALTH_SLOTS          = 3;
    public static final int CHARACTER_INVENTORY_SLOTS               = 8;
    public static final int CHARACTER_INVENTORY_MAX_STACK_SIZE      = 40;

    //==============================================================================================
    // PLAYER
    //==============================================================================================
    public static final float PLAYER_MOVEMENT_PS                    = LEVEL_TILE_SIZE * 1.6f;
    public static final float PLAYER_SIZE                           = LEVEL_TILE_SIZE * 0.5f;

    //==============================================================================================
    // PICKUPS
    //==============================================================================================
    public static final float PICKUP_SIZE = 32f;

    //==============================================================================================
    // PROJECTILE
    //==============================================================================================
    public static final float PROJECTILE_VERY_SLOW_MOVEMENT_PS       = LEVEL_TILE_SIZE * 0.8f;
    public static final float PROJECTILE_SLOW_MOVEMENT_PS            = LEVEL_TILE_SIZE * 1.2f;
    public static final float PROJECTILE_DEFAULT_MOVEMENT_PS         = LEVEL_TILE_SIZE * 1.8f;
    public static final float PROJECTILE_FAST_MOVEMENT_PS            = LEVEL_TILE_SIZE * 2.4f;
    public static final float PROJECTILE_VERY_FAST_MOVEMENT_PS       = LEVEL_TILE_SIZE * 3.2f;
    public static final float PROJECTILE_SIZE_SMALL                  = LEVEL_TILE_SIZE * 0.2f;
    public static final float PROJECTILE_SIZE_MEDIUM                 = LEVEL_TILE_SIZE * 0.3f;
    public static final float PROJECTILE_SIZE_LARGE                  = LEVEL_TILE_SIZE * 0.4f;
}
