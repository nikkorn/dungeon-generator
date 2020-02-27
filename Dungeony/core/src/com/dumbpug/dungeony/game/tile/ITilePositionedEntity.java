package com.dumbpug.dungeony.game.tile;

/**
 * Represents a tile positioned entity.
 */
public interface ITilePositionedEntity {
    /**
     * Gets the tile based x position of the tile.
     * @return The tile based x position of the tile.
     */
    int getTileX();

    /**
     * Gets the tile based y position of the tile.
     * @return The tile based y position of the tile.
     */
    int getTileY();
}
