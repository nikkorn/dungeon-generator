package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A renderable.
 */
public interface IRenderable {
    /**
     * Gets the renderable index to use in sorting.
     * @return The renderable index to use in sorting.
     */
    float getRenderOrder();

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    void render(SpriteBatch batch);
}
