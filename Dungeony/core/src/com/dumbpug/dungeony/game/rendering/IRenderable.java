package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A renderable.
 */
public interface IRenderable {
    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    int getRenderLayer();

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
