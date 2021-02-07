package com.dumbpug.dungeony.game.tile;

/**
 * Enumeration of tile types.
 */
public enum TileType {
    UNKNOWN,
    EMPTY,
    WALL,
    SPAWN_PAD,
    SPAWN_DOOR,
    UNDERLAY;

    /**
     * Gets whether the tile is walkable.
     * @return Whether the tile is walkable.
     */
    public boolean isWalkable() {
        switch(this) {
            case UNKNOWN:
            case WALL:
                return false;
            default:
                return true;
        }
    }
}
