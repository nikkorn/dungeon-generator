package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.Renderables;

/**
 * Represents a game environment.
 * @param <TEntity> The common type of all entities within the environment.
 * @param <TRenderContext> The render context.
 */
public class Environment<TEntity extends Entity, TRenderContext> {
    /**
     * The environment configuration.
     */
    private EnvironmentConfiguration configuration;
    /**
     * The spatial grid to use in finding entity collisions.
     */
    private EnvironmentCollisionGrid<TEntity> collisionGrid;
    /**
     * The renderables renderables collection which will handle entity render priority.
     */
    private Renderables<TRenderContext> renderables;
    /**
     * The collection of entities in the environment.
     */
    private Entities entities;
    /**
     * The environment interactivity layer that is available to entities during updates.
     */
    private InteractiveEnvironment interactiveEnvironment;

    /**
     * Creates a new instance of the Environment class.
     * @param configuration The environment configuration.
     */
    public Environment(EnvironmentConfiguration configuration) {
        // Keep a reference to the environment configuration.
        this.configuration = configuration;

        // Create the spatial grid to use in finding entity collisions.
        collisionGrid = new EnvironmentCollisionGrid<TEntity>(configuration.gridCellSize);

        // Create the renderables collection which will handle entity render priority.
        this.renderables = new Renderables<TRenderContext>();

        // Create the entities collection.
        this.entities = new Entities(collisionGrid, renderables);
    }

    /**
     * Gets the spatial grid to use in finding entity collisions.
     * @return The spatial grid to use in finding entity collisions.
     */
    public EnvironmentCollisionGrid<TEntity> getGrid() {
        return this.collisionGrid;
    }

    /**
     * Add an entity to the environment.
     * @param entity The entity to add.
     */
    public void addEntity(TEntity entity) {
        this.entities.add(entity);
    }

    /**
     * Remove an entity from the environment.
     * @param entity The entity to remove.
     */
    public void removeEntity(TEntity entity) {
        this.entities.remove(entity);
    }

    /**
     * Update the environment and any contained entities.
     * @param delta The delta time.
     */
    public void update(float delta) {
        this.entities.update(this.interactiveEnvironment, delta);
    }

    /**
     * Render all of the renderable entities in the environment.
     * @param context The render context.
     */
    public void render(TRenderContext context) {
        this.renderables.render(context);
    }
}
