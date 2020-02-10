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
     * @param tiles The tiles finder.
     * @return The tile instance.
     */
    public static Tile createTile(TileType type, int x, int y, ITileFinder tiles) {
        switch (type) {
            case EMPTY:
                return new Empty(x, y);
            case WALL:
                // Find whether the tiles around the wall tile we are about to make are also wall tiles.
                boolean hasWallBelow = tiles.find(x, y - 1) == TileType.WALL;
                boolean hasWallAbove = tiles.find(x, y + 1) == TileType.WALL;
                boolean hasWallLeft  = tiles.find(x - 1, y) == TileType.WALL;
                boolean hasWallRight = tiles.find(x + 1, y ) == TileType.WALL;
                return new Wall(x, y, hasWallAbove, hasWallBelow, hasWallLeft, hasWallRight);
            default:
                throw new RuntimeException("cannot create tile instance for tile type: " + type);
        }
    }
}
