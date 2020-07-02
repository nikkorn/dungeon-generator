package com.dumbpug.dungeony.game;

/**
 * Enumeration of cardinal directions.
 */
public enum Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    /**
     * Gets the angle of the cardinal direction.
     * @return The angle of the cardinal direction.
     */
    public float getAngle() {
        switch(this) {
            case NORTH:
                return 0;
            case NORTH_EAST:
                return 45;
            case EAST:
                return 90;
            case SOUTH_EAST:
                return 135;
            case SOUTH:
                return 180;
            case SOUTH_WEST:
                return 225;
            case WEST:
                return 270;
            case NORTH_WEST:
                return 315;
            default:
                throw new RuntimeException("unknown Direction enum value: " + this);
        }
    }
}
