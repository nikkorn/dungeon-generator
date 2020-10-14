package com.dumbpug.dungeony.engine.lighting;

import com.dumbpug.dungeony.engine.Entity;

/**
 * An environment light.
 * @param <TRenderContext> The render context.
 */
public abstract class Light<TRenderContext> {
    /**
     * The target entity.
     */
    private Entity<TRenderContext> target;
    /**
     * The target position.
     */
    private float x, y;

    /**
     * Creates a new instance of the Light class.
     * @param x
     * @param y
     */
    public Light(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new instance of the Light class.
     * @param target
     */
    public Light(Entity target) {
        this.target = target;
    }

    public void callRender(TRenderContext context) {
        if (this.target != null) {
            this.render(context, this.target.getOrigin().getX(), this.target.getOrigin().getY());
        } else {
            this.render(context, this.x, this.y);
        }
    }

    /**
     * Render the light.
     * @param context The render context.
     * @param x The x position of the light.
     * @param y The y position of the light.
     */
    public abstract void render(TRenderContext context, float x, float y);
}
