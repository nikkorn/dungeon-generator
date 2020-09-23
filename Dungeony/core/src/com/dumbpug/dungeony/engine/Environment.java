package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.Renderables;

import java.util.ArrayList;

/**
 * Represents a game environment.
 * @param <TRenderContext> The render context.
 */
public class Environment<TRenderContext> {
    /**
     * The environment configuration.
     */
    private EnvironmentConfiguration configuration;
    /**
     * The spatial grid to use in finding entity collisions.
     */
    private EnvironmentCollisionGrid collisionGrid;
    /**
     * The renderables renderables collection which will handle entity render priority.
     */
    private Renderables<TRenderContext> renderables;
    /**
     * The collection of entities in the environment.
     */
    private Entities<TRenderContext> entities;
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
        this.collisionGrid = new EnvironmentCollisionGrid(configuration.gridCellSize);

        // Create the renderables collection which will handle entity render priority.
        this.renderables = new Renderables<TRenderContext>();

        // Create the environment interactivity layer that is available to entities during updates.
        this.interactiveEnvironment = new InteractiveEnvironment(this);

        // Create the entities collection.
        this.entities = new Entities(this.collisionGrid, this.renderables, this.interactiveEnvironment);
    }

    /**
     * Gets the spatial grid to use in finding entity collisions.
     * @return The spatial grid to use in finding entity collisions.
     */
    public EnvironmentCollisionGrid getGrid() {
        return this.collisionGrid;
    }

    /**
     * Gets the environment interactivity layer that is available to entities during updates.
     * @return The environment interactivity layer that is available to entities during updates.
     */
    public InteractiveEnvironment getInteractivityLayer() {
        return this.interactiveEnvironment;
    }

    /**
     * Add an entity to the environment.
     * @param entity The entity to add.
     */
    public void addEntity(Entity<TRenderContext> entity) {
        this.entities.add(entity);
    }

    /**
     * Add a list of entities to the environment.
     * @param list The list of entities to add.
     */
    public void addEntities(ArrayList<Entity<TRenderContext>> list) {
        for (Entity<TRenderContext> entity : list) {
            this.addEntity(entity);
        }
    }

    /**
     * Remove an entity from the environment.
     * @param entity The entity to remove.
     */
    public void removeEntity(Entity<TRenderContext> entity) {
        this.entities.remove(entity);
    }

    /**
     * Remove a list of entities from the environment.
     * @param list The list of entities to remove.
     */
    public void removeEntities(ArrayList<Entity<TRenderContext>> list) {
        for (Entity<TRenderContext> entity : list) {
            this.removeEntity(entity);
        }
    }

    /**
     * Update the environment and any contained entities.
     * @param delta The delta time.
     */
    public void update(float delta) {
        this.entities.update(delta);
    }

    /**
     * Render all of the renderable entities in the environment.
     * @param context The render context.
     */
    public void render(TRenderContext context) {
        this.renderables.render(context);
    }
}
