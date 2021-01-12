package com.dumbpug.dungeony.engine.rendering;

/**
 * A renderable element.
 * @param <TRenderContext> The render context.
 */
public interface IRenderable<TRenderContext> {
    /**
     * Gets the x position of the box.
     * @return The y position of the box.
     */
    float getX();

    /**
     * Gets the y position of the box.
     * @return The y position of the box.
     */
    float getY();

    /**
     * Gets the length of the box on the X axis.
     * @return The length of the box on the X axis.
     */
    float getLengthX();

    /**
     * Gets the length of the box on the Y axis.
     * @return The length of the box on the Y axis.
     */
    float getLengthY();

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
     * @param context The render context.
     */
    void render(TRenderContext context);
}
