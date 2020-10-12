package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.IRenderWindow;
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
     * Gets the group that the entity resides in, or null if the entity is not in the environment or a group.
     * @param entity The entity.
     * @return The group that the entity resides in, or null if the entity is not in the environment or a group.
     */
    public String getEntityGroup(Entity entity) {
        return this.entities.getEntityGroup(entity);
    }

    /**
     * Add an entity to the environment.
     * @param entity The entity to add.
     * @param group The group to add the entity against.
     */
    public void addEntity(Entity<TRenderContext> entity, String group) {
        this.entities.add(entity, group);
    }

    /**
     * Add an entity to the environment.
     * @param entity The entity to add.
     */
    public void addEntity(Entity<TRenderContext> entity) {
        this.addEntity(entity, null);
    }

    /**
     * Add a list of entities to the environment.
     * @param list The list of entities to add.
     * @param group The group to add the entities against.
     */
    public void addEntities(ArrayList<Entity<TRenderContext>> list, String group) {
        for (Entity<TRenderContext> entity : list) {
            this.addEntity(entity, group);
        }
    }

    /**
     * Add a list of entities to the environment.
     * @param list The list of entities to add.
     */
    public void addEntities(ArrayList<Entity<TRenderContext>> list) {
        this.addEntities(list, null);
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
     * Render the renderable entities in the environment that lie within the given render window.
     * @param context The render context.
     * @param window The render window.
     */
    public void render(TRenderContext context, IRenderWindow window) {
        this.renderables.render(context, window);
    }

    /**
     * Render all of the renderable entities in the environment.
     * @param context The render context.
     */
    public void render(TRenderContext context) {
        this.render(context, null);
    }
}
