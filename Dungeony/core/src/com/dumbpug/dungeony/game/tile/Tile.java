package com.dumbpug.dungeony.game.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;

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
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Tile(TileType type, int x, int y) {
        super(new Position((x * Constants.GAME_TILE_SIZE) + (Constants.GAME_TILE_SIZE / 2), (y * Constants.GAME_TILE_SIZE) + (Constants.GAME_TILE_SIZE / 2)));
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

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant sprite for this tile.
        Sprite sprite = Resources.getSprite(TileSprite.WALL);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getWidth(), this.getHeight());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);
    }
}
