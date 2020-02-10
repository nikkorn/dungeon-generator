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
                boolean isEmptyBelow = tiles.find(x, y - 1) == TileType.EMPTY;
                boolean isEmptyAbove = tiles.find(x, y + 1) == TileType.EMPTY;
                boolean isEmptyLeft  = tiles.find(x - 1, y) == TileType.EMPTY;
                boolean isEmptyRight = tiles.find(x + 1, y ) == TileType.EMPTY;
                return new Wall(x, y, isEmptyAbove, isEmptyBelow, isEmptyLeft, isEmptyRight);
            default:
                throw new RuntimeException("cannot create tile instance for tile type: " + type);
        }
    }
}
