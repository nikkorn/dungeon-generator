package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.Position;

/**
 * An in-game tile entity.
 */
public abstract class Tile extends Entity {
    /**
     * The x/y position of the tile.
     */
    private int x, y;

    /**
     * Creates a new instance of the Tile class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Tile(int x, int y) {
        super(new Position((x * Constants.GAME_TILE_SIZE) + (Constants.GAME_TILE_SIZE / 2), (y * Constants.GAME_TILE_SIZE) + (Constants.GAME_TILE_SIZE / 2)));
        this.x = x;
        this.y = y;
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
     * Gets the movement speed of the entity.
     * @return The movement speed of the entity.
     */
    public float getMovementSpeed() {
        // Static entities have no movement speed.
        return 0;
    }
}
