package com.dumbpug.dungeony.game.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.EntityUpdateStrategy;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import java.util.ArrayList;

/**
 * An in-game tile entity.
 */
public abstract class Tile extends Entity<SpriteBatch> implements ITilePositionedEntity {
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
        super(new Position((x * Constants.LEVEL_TILE_SIZE) + (Constants.LEVEL_TILE_SIZE / 2), (y * Constants.LEVEL_TILE_SIZE) + (Constants.LEVEL_TILE_SIZE / 2)));
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
    public float getLengthX() {
        return Constants.LEVEL_TILE_SIZE;
    }

    @Override
    public float getLengthY() {
        return Constants.LEVEL_TILE_SIZE;
    }

    @Override
    public float getLengthZ() {
        return Constants.LEVEL_TILE_SIZE;
    }

    /**
     * Gets the update strategy of the entity.
     * @return The update strategy of the entity.
     */
    public EntityUpdateStrategy getUpdateStrategy() {
        // Tiles will not have their own update by default.
        return EntityUpdateStrategy.NONE;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {}

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) {}

    /**
     * Gets the list of entity spawn positions for this tile.
     * @return The list of entity spawn positions for this tile.
     */
    public ArrayList<TileSpawn> getTileSpawns() {
        // General tiles do not provide spawn positions.
        return null;
    }

    /**
     * Gets the type of the tile.
     * @return The type of the tile.
     */
    public abstract TileType getTileType();
}
