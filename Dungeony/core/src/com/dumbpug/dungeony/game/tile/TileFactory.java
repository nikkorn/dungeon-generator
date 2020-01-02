package com.dumbpug.dungeony.game.tile;

/**
 * Factory for creating Tile instances.
 */
public class TileFactory {
    /**
     * Create a Tile instance.
     * @param type The type of the tile.
     * @param x The tile-based x position of the tile.
     * @param y The tile-based y position of the tile.
     * @return The tile instance.
     */
    public static Tile createTile(TileType type, int x, int y) {
        switch (type) {
            case EMPTY:
                return new Empty(x, y);
            case WALL:
                // TODO Need to pass in reference to all x/y tiletype positions to change render based on surroundings.
                return new Wall(x, y);
            default:
                throw new RuntimeException("cannot create tile instance for tile type: " + type);
        }
    }
}
