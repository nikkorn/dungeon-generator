package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.Renderables;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The list of the entities within an environment.
 */
public class Entities<TEntity extends Entity> {
    /**
     * The underlying list of entities.
     */
    private ArrayList<TEntity> entities = new ArrayList<TEntity>();
    /**
     * The spatial grid to use in finding entity collisions.
     */
    private EnvironmentCollisionGrid environmentCollisionGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;
    /**
     * The environment interactivity layer that is available to entities during updates.
     */
    private InteractiveEnvironment interactiveEnvironment;

    /**
     * Creates an instance of the Entities class.
     * @param environmentCollisionGrid The environment collision grid.
     * @param renderables The renderables list to keep updated with this list.
     * @param environment The interactive environment.
     */
    public Entities(EnvironmentCollisionGrid environmentCollisionGrid, Renderables renderables, InteractiveEnvironment environment) {
        this.environmentCollisionGrid = environmentCollisionGrid;
        this.renderables              = renderables;
        this.interactiveEnvironment   = environment;
    }

    /**
     * Add an entity to the list of in-game entities.
     * @param entity The entity to add.
     */
    public void add(TEntity entity) {
        // There is nothing to do if the game object is already in out list of existing game objects.
        if (this.entities.contains(entity)) {
            return;
        }

        // Add the entity to our list of game objects.
        this.entities.add(entity);

        // Add the entity to the level grid.
        this.environmentCollisionGrid.add(entity);

        // Add the entity to the renderables list.
        this.renderables.add(entity);

        // Let the entity do some 'onEnvironmentEntry' logic.
        entity.onEnvironmentEntry(this.interactiveEnvironment);
    }

    /**
     * Remove an entity from the list of in-game entities.
     * @param entity The entity to remove.
     */
    public void remove(TEntity entity) {
        // There is nothing to do if the game object is not already in out list of existing game objects.
        if (!this.entities.contains(entity)) {
            return;
        }

        // Remove the entity from our list of game objects.
        this.entities.remove(entity);

        // Remove the entity from the level grid.
        this.environmentCollisionGrid.remove(entity);

        // Remove the entity from the renderables list.
        this.renderables.remove(entity);

        // Let the entity do some 'onEnvironmentExit' logic.
        entity.onEnvironmentExit(this.interactiveEnvironment);
    }

    /**
     * Update each of the entities sequentially.
     * @param delta The delta time.
     */
    public void update(float delta) {
        // Create a set of all entities that must be destroyed and removed from the collection.
        HashSet<TEntity> pendingDestroy = new HashSet<TEntity>();

        // Update each of the entities sequentially.
        for (TEntity entity : this.entities) {
            // The entity may have already been marked for destruction, maybe via another entities update.
            if (entity.isMarkedForDestroy()) {
                pendingDestroy.add(entity);
                continue;
            }

            // Update the entity, passing the environment interactivity layer with which the entity can interact with the environment.
            entity.update(this.interactiveEnvironment, delta);

            // The entity may have marked itself for destruction during its update.
            if (entity.isMarkedForDestroy()) {
                pendingDestroy.add(entity);
            }
        }

        // We process each entity destruction after the updates are all finished with.
        for (TEntity entity : pendingDestroy) {
            // Let the entity do some 'onDestroy' logic.
            entity.onDestroy();

            // Finally, remove the entity for the collection of environment entities.
            this.remove(entity);
        }
    }
}
