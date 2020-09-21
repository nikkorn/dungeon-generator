package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.Renderables;
import java.util.ArrayList;

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
     * Creates an instance of the Entities class.
     * @param environmentCollisionGrid The environment collision grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public Entities(EnvironmentCollisionGrid environmentCollisionGrid, Renderables renderables) {
        this.environmentCollisionGrid = environmentCollisionGrid;
        this.renderables              = renderables;
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
    }

    /**
     * Update each of the entities sequentially.
     * @param environment The interactive environment.
     * @param delta The delta time.
     */
    public void update(InteractiveEnvironment environment, float delta) {
        // Update each of the entities sequentially.
        for (Entity entity : this.entities) {
            entity.update(environment, delta);
        }
    }
}
