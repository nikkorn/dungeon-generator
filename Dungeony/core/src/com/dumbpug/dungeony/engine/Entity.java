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
     * Whether the entity has been marked as to be destroyed.
     */
    private boolean isMarkedForDestroy = false;

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
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // The render layer for entities will always default to 0.
        return 0;
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
     * Gets the update strategy of the entity.
     * @return The update strategy of the entity.
     */
    public EntityUpdateStrategy getUpdateStrategy() {
        return EntityUpdateStrategy.PER_UPDATE;
    }

    /**
     * Gets the update delay of the entity which is applied if the entity update strategy type is 'DELAY'.
     * @return The update delay of the entity which is applied if the entity update strategy type is 'DELAY'.
     */
    public long getUpdateDelay() {
        return 0;
    }

    /**
     * Gets the update order of the entity, defining the order in which an entity update is processed as part of an environment update.
     * @return The update order of the entity, defining the order in which an entity update is processed as part of an environment update.
     */
    public int getUpdateOrder() {
        // By default all entities will have an update order of 0.
        return 0;
    }

    /**
     * Gets whether the entity has been marked as to be destroyed.
     * @return Whether the entity has been marked as to be destroyed.
     */
    public boolean isMarkedForDestroy() {
        return this.isMarkedForDestroy;
    }

    /**
     * Marks the entity as needing to be destroyed and removed from an environment it may be in.
     */
    public void destroy() {
        this.isMarkedForDestroy = true;
    }

    /**
     * Gets the length of the box on the Z axis.
     * @return The length of the box on the Z axis.
     */
    public abstract float getLengthZ();

    /**
     * Gets the collision layer of the entity.
     * @return The collision layer of the entity.
     */
    public abstract int getCollisionLayers();

    /**
     * Gets the collision mask of the entity.
     * @return The collision mask of the entity.
     */
    public abstract int getCollisionMask();

    /**
     * Called when the entity is added to an environment.
     * @param environment The interactive environment.
     */
    public abstract void onEnvironmentEntry(InteractiveEnvironment environment);

    /**
     * Called when the entity is removed from an environment.
     * @param environment The interactive environment.
     */
    public abstract void onEnvironmentExit(InteractiveEnvironment environment);

    /**
     * Called when the entity is destroyed.
     */
    public abstract void onDestroy();

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
}
