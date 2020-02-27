package com.dumbpug.dungeony.game.tile;

/**
 * Finder of tile types at specified positions.
 */
public interface ITileFinder {
    /**
     * Gets the type of the tile at the specified position.
     * @param x The tile based x position.
     * @param y The tile based y position.
     * @return The type of the tile at the specified position.
     */
    TileType find(int x, int y);

    /**
     * Gets the type of the tile at the specified offset from the origin tile.
     * @param origin The origin tile.
     * @param offset The offset from the origin tile.
     * @return The type of the tile at the specified offset from the origin tile.
     */
    TileType find(ITilePositionedEntity origin, TileOffset offset);
}
