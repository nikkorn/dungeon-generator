package com.dumbpug.dungeony.engine.particles;

import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;

/**
 * A particle entity.
 * @param <TRenderContext> The render context.
 */
public abstract class Particle<TRenderContext> extends Entity<TRenderContext> {
    /**
     * The remaining life of this particle in seconds, defaults to a second.
     */
    private float life = 1;
    /**
     * Whether the particle has expired.
     */
    private boolean isExpired;
    /**
     * The particle sprite opacity, between 0 and 1.
     */
    private float opacity;

    /**
     * Creates a new instance of the Particle class.
     * @param origin The initial origin of the particle.
     */
    public Particle(Position origin) {
        super(origin);
    }

    /**
     * Gets whether the particle life has expired.
     * @return Whether the particle life has expired.
     */
    public boolean isExpired() {
        return isExpired;
    }

    /**
     * Get the remaining life of this particle in seconds.
     * @return The remaining life of this particle in seconds.
     */
    public float getLife() { return life; }

    /**
     * Set the remaining life of this particle in seconds.
     * @param life The remaining life of this particle in seconds.
     */
    public void setLife(float life) { this.life = life; }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void onDestroy() { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // Reduce the life of the particle by the game delta.
        this.life -= delta;

        if (life <= 0) {
            this.isExpired = true;
        }
    }

    /**
     * Called on the particle entity update.
     * @param environment The game environment.
     * @param delta The game delta.
     */
    public abstract void onUpdate(InteractiveEnvironment environment, float delta);
}
