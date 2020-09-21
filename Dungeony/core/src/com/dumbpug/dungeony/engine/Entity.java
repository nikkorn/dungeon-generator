package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.IRenderable;
import com.dumbpug.dungeony.engine.utilities.spatialgrid.IAABB;

/**
 * An in-game entity with a position and size that can collide with other entities.
 * @param <TRenderContext> The render context.
 */
public abstract class Entity<TRenderContext> implements IAABB, IRenderable<TRenderContext> {
    /**
     * The origin position of the entity.
     */
    private Position origin;
    /**
     * The bottom-left position of the entity.
     */
    private Position position;

    /**
     * Creates a new instance of the Entity class.
     * @param origin The initial origin of the entity.
     */
    public Entity(Position origin) {
        // Set the entity origin.
        this.origin = origin;
        // Find the position as the bottom-left point of the entity based on its origin and size.
        this.position = new Position(
                origin.getX() - (this.getLengthX() / 2f),
                origin.getY() - (this.getLengthY() / 2f)
        );
    }

    /**
     * Gets the entity origin.
     * @return The entity origin.
     */
    public Position getOrigin() {
        return origin;
    }

    /**
     * Gets the x position of the box.
     * @return The y position of the box.
     */
    @Override
    public float getX() {
        return this.position.getX();
    }

    /**
     * Sets the x position of the box.
     * @param x The x position
     */
    public void setX(float x) {
        this.position.setX(x);
        this.origin.setX(x + (this.getLengthX() / 2f));
    }

    /**
     * Gets the y position of the box.
     * @return The y position of the box.
     */
    @Override
    public float getY() {
        return this.position.getY();
    }

    /**
     * Sets the y position of the box.
     * @param y The y position
     */
    public void setY(float y) {
        this.position.setY(y);
        this.origin.setY(y + (this.getLengthY() / 2f));
    }

    /**
     * Gets the length of the box on the X axis.
     * @return The length of the box on the X axis.
     */
    @Override
    public abstract float getLengthX();

    /**
     * Gets the length of the box on the Y axis.
     * @return The length of the box on the Y axis.
     */
    @Override
    public abstract float getLengthY();

    /**
     * Gets the length of the box on the Z axis.
     * @return The length of the box on the Z axis.
     */
    public abstract float getLengthZ();

    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // Entities should be at layer 1 be default.
        return 1;
    }

    /**
     * Gets the renderable index to use in sorting.
     * @return The renderable index to use in sorting.
     */
    @Override
    public float getRenderOrder() {
        // The entity render order depends on its y position by default.
        return this.position.getY();
    }

    /**
     * Update the entity.
     * @param environment The interactive environment.
     * @param delta The delta time.
     */
    public abstract void update(InteractiveEnvironment environment, float delta);

    /**
     * Render the renderable using the provided sprite batch.
     * @param context The render context.
     */
    public abstract void render(TRenderContext context);

    /**
     * Gets whether this entity collides with the specified entity.
     * @param entity The other entity to check.
     * @return Whether this entity collides with the specified entity.
     */
    public abstract boolean collidesWith(Entity entity);
}
