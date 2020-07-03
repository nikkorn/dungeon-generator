package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.Position;

import java.util.ArrayList;

/**
 * An in-game tile entity.
 */
public abstract class Tile extends Entity implements ITilePositionedEntity {
    /**
     * The tile based x/y position of the tile.
     */
    private int tileX, tileY;

    /**
     * Creates a new instance of the Tile class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Tile(int x, int y) {
        super(new Position((x * Constants.GAME_TILE_SIZE) + (Constants.GAME_TILE_SIZE / 2), (y * Constants.GAME_TILE_SIZE) + (Constants.GAME_TILE_SIZE / 2)));
        this.tileX = x;
        this.tileY = y;
    }

    @Override
    public int getTileX() {
        return this.tileX;
    }

    @Override
    public int getTileY() {
        return this.tileY;
    }

    @Override
    public float getWidth() {
        return Constants.GAME_TILE_SIZE;
    }

    @Override
    public float getHeight() {
        return Constants.GAME_TILE_SIZE;
    }

    /**
     * Gets the list of entity spawn positions for this tile.
     * @return The list of entity spawn positions for this tile.
     */
    public ArrayList<TileSpawn> getTileSpawns() {
        // General tiles do not provide spawn positions.
        return null;
    }
}
