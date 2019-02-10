
//============================================================================
// GENERAL
//============================================================================

/** The number of tiles that make up a cell horizontally and vertically. */
const CELL_TILE_SIZE = 5;

/** The maximum number of times allowed for dungeon regeneration attempts. */
const MAX_DUNGEON_GENERATE_RETRY = 1000;


//============================================================================
// ROOMS
//============================================================================

/** The maximum number of times allowed for room regeneration attempts. */
const MAX_ROOM_GENERATE_RETRY = 1000;

/** The maximum number of rooms allowed per dungeon. */
const MAX_ROOMS_COUNT = 15;

/** The minimum number of rooms allowed per dungeon. */
const MIN_ROOMS_COUNT = 5;


//============================================================================
// ENUMS
//============================================================================

/** An enumeration of direction types. */
const DIRECTION = { 
    NORTH: 0,
    SOUTH: 1, 
    EAST: 2, 
    WEST: 3 
};

/** An enumeration of door types. */
const DOOR = { 
    LEVEL_DOOR: 0,
    NO_KEY: 1, 
    ONE_KEY: 2, 
    TWO_KEYS: 3, 
    THREE_KEYS: 4,
    FOUR_KEYS: 4,
    FIVE_KEYS: 4,
    SHOP: 4 
};

//============================================================================
// DISPLAY
//============================================================================

 /** The number of pixels that make up a tile when drawn to a canvas. */
 const TILE_PIXEL_SIZE = 10;