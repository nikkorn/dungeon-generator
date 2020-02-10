package com.dumbpug.dungeony.game.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;

/**
 * An empty tile.
 */
public class Empty extends Tile {
    /**
     * Creates a new instance of the Empty class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Empty(int x, int y) {
        super(x, y);
    }

    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // Empty tiles should be rendered early as they render the level ground.
        return 0;
    }

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.NOTHING;
    }

    @Override
    public int getCollisionMask() {
        return EntityCollisionFlag.NOTHING;
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant sprite for this tile.
        Sprite sprite = Resources.getSprite(TileSprite.GROUND);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getWidth(), this.getHeight());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);
    }
}
