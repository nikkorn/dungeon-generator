package com.dumbpug.dungeony;

/**
 * Application constants.
 */
public class Constants {
    //==============================================================================================
    // FONT
    //==============================================================================================
    public static final String FONT_TYPE_MAIN                       = "Boxy-Bold.ttf";
    public static final int FONT_SIZE_SMALL                         = 4;
    public static final int FONT_SIZE_MEDIUM                        = 8;
    public static final int FONT_SIZE_LARGE                         = 12;

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
    public static final float LEVEL_TILE_SIZE                       = 32f;
    public static final float LEVEL_GRID_CELL_SIZE                  = LEVEL_TILE_SIZE * 4f;
    public static final float LEVEL_DEFAULT_ZOOM                    = 0.25f; // 0.18f;
    public static final float LEVEL_CAMERA_SHAKE_POWER              = 1;

    //==============================================================================================
    // CHARACTER
    //==============================================================================================
    public static final int CHARACTER_DEFAULT_HEALTH_SLOTS          = 5;
    public static final int CHARACTER_INVENTORY_SLOTS               = 8;
    public static final int CHARACTER_INVENTORY_MAX_STACK_SIZE      = 40;
    public static final long CHARACTER_DAMAGE_OVERLAY_DURATION_MS   = 100l;

    //==============================================================================================
    // PLAYER
    //==============================================================================================
    public static final float PLAYER_MOVEMENT_PS                    = LEVEL_TILE_SIZE * 2.6f;
    public static final float PLAYER_SIZE                           = LEVEL_TILE_SIZE * 0.5f;

    //==============================================================================================
    // PICKUPS
    //==============================================================================================
    public static final float PICKUP_SIZE = 32f;

    //==============================================================================================
    // PROJECTILES
    //==============================================================================================
    public static final float PROJECTILE_VERY_SLOW_MOVEMENT_PS       = LEVEL_TILE_SIZE * 1.4f;
    public static final float PROJECTILE_SLOW_MOVEMENT_PS            = LEVEL_TILE_SIZE * 2f;
    public static final float PROJECTILE_DEFAULT_MOVEMENT_PS         = LEVEL_TILE_SIZE * 3f;
    public static final float PROJECTILE_FAST_MOVEMENT_PS            = LEVEL_TILE_SIZE * 3.6f;
    public static final float PROJECTILE_VERY_FAST_MOVEMENT_PS       = LEVEL_TILE_SIZE * 4.6f;
    public static final float PROJECTILE_SIZE_SMALL                  = 6f;
    public static final float PROJECTILE_SIZE_MEDIUM                 = 8f;
    public static final float PROJECTILE_SIZE_LARGE                  = 12f;

    //==============================================================================================
    // WEAPONS
    //==============================================================================================
    public static final long WEAPON_MUZZLE_FLASH_DURATION_MS         = 150l;

}
