/**
 * Constants.
 */
const CAMERA_ZOOM     = 3;
const TILE_SIZE       = 25;
const CANVAS_SIZE     = 2000;
const CHARACTER_SIZE  = 15;
const PLAYER_MOVEMENT = 1;
const ENEMY_MOVEMENT  = PLAYER_MOVEMENT * 2;

/**
 * Some colours to use.
 */
const colours = {
    "red": "#b50327",
    "green": "#027a16",
    "blue": "#0574b5",
    "orange": "#c67d07",
    "purple": "#650289",
    "pink": "#e58bd9",
    "dark_grey": "#364260",
    "grey": "#bcc7e5"
};

/**
 * Some useful event key codes.
 */
const KEY_CODE = {
    W: 87,
    A: 65,
    S: 83,
    D: 68
};

/**
 * Enemy types.
 */
const ENEMY_TYPE = {
    WALKER: 0,
    FOLLOWER: 1
} 