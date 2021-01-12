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
     * Whether the light is enabled.
     */
    private boolean isEnabled = true;

    /**
     * Creates a new instance of the Light class that follows a target entity.
     * @param target The target entity.
     */
    public Light(Entity target) {
        this.target = target;
    }

    /**
     * Creates a new instance of the Light class that uses a position rather than a target entity.
     * @param x
     * @param y
     */
    public Light(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets whether the light is enabled.
     * @return Whether the light is enabled.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Sets whether the light is enabled.
     * @param enabled Whether the light is enabled.
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Sets the position for the light, but will error if the light follows a target rather than an absolute position.
     * @param x
     * @param y
     */
    public void setPosition(float x, float y) {
        if (this.target != null) {
            throw new RuntimeException("Cannot set position for light that follows a target entity.");
        }
        this.x = x;
        this.y = y;
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
