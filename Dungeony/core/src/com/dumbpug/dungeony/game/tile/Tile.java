package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;

/**
 * An in-game tile entity.
 */
public class Tile extends Entity {
    /**
     * The type of the tile.
     */
    private TileType type;
    /**
     * The x/y position of the tile.
     */
    private int x, y;

    /**
     * Creates a new instance of the Tile class.
     * @param type The type of the tile.
     * @param origin The initial absolute origin of the Tile.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Tile(TileType type, Position origin, int x, int y) {
        super(origin);
        this.type = type;
        this.x    = x;
        this.y    = y;
    }

    @Override
    public float getWidth() {
        return Constants.GAME_TILE_SIZE;
    }

    @Override
    public float getHeight() {
        return Constants.GAME_TILE_SIZE;
    }

    @Override
    public int getCollisionFlag() {
        switch (this.type) {
            case WALL:
                return EntityCollisionFlag.WALL;
            default:
                return EntityCollisionFlag.NOTHING;
        }
    }

    @Override
    public int getCollisionMask() {
        switch (this.type) {
            case WALL:
                // Everything bumps into a wall!
                return EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.PROJECTILE | EntityCollisionFlag.OBJECT;
            default:
                // Nothing bumps into an unknown tile type!
                return EntityCollisionFlag.NOTHING;
        }
    }
}
