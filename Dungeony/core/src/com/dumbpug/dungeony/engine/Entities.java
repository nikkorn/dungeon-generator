package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.IRenderable;
import com.dumbpug.dungeony.engine.rendering.Renderables;
import com.dumbpug.dungeony.engine.rendering.RenderablesLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The list of the entities within an environment.
 * @param <TRenderContext> The render context.
 */
public class Entities<TRenderContext>  {
    /**
     * The underlying list of all entities.
     */
    private ArrayList<Entity<TRenderContext>> entities = new ArrayList<Entity<TRenderContext>>();
    /**
     * The underlying list of all updatable entities.
     * This is kept separate from the list of all entities to speed up updates.
     */
    private ArrayList<Entity<TRenderContext>> updatableEntities = new ArrayList<Entity<TRenderContext>>();
    /**
     * The mapping of entity group names to entity group lists.
     */
    private HashMap<String, ArrayList<Entity<TRenderContext>>> groups = new HashMap<String, ArrayList<Entity<TRenderContext>>>();
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
     * The comparator to use in sorting entities by update order.
     */
    private Comparator<Entity<TRenderContext>> updateOrderComparator;

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

        // Create the comparator we will use to sort the updatable entities whenever the collection changes.
        this.updateOrderComparator = new Comparator<Entity<TRenderContext>>() {
            @Override
            public int compare(Entity<TRenderContext> first, Entity<TRenderContext> second) {
                float difference = first.getUpdateOrder() - second.getUpdateOrder();

                if (difference < 0) {
                    return 1;
                } else if (difference > 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
    }

    /**
     * Gets a list of all entities.
     * @return a list of all entities.
     */
    public ArrayList<Entity<TRenderContext>> getEntities() {
        return this.entities;
    }

    /**
     * Gets a list of all entities withing the specified group.
     * @return a list of all entities withing the specified group.
     */
    public ArrayList<Entity<TRenderContext>> getEntities(String group) {
        // The entity group may not exist.
        if (!this.groups.containsKey(group.toUpperCase())) {
            throw new RuntimeException("entity group '" + group + "' does not exist");
        }

        return this.groups.get(group.toUpperCase());
    }

    /**
     * Add an entity to the list of in-game entities.
     * @param entity The entity to add.
     * @param group The group to add the entity against.
     */
    public void add(Entity<TRenderContext> entity, String group) {
        // There is nothing to do if the game object is already in out list of existing game objects.
        if (this.entities.contains(entity)) {
            return;
        }

        // Add the entity to our list of all entities.
        this.entities.add(entity);

        // Are we adding this entity as part of a group?
        if (group != null) {
            // Add the group if it does not already exist.
            if (!this.groups.containsKey(group.toUpperCase())) {
                this.groups.put(group.toUpperCase(), new ArrayList<Entity<TRenderContext>>());
            }

            // Add the entity to the specified group.
            this.groups.get(group.toUpperCase()).add(entity);
        }

        // Are we adding this to the list of updatable entities?
        if (entity.getUpdateStrategy() != EntityUpdateStrategy.NONE) {
            // Add the entity to our list of updatable entities.
            this.updatableEntities.add(entity);

            // Sort the updatable entities based on their update order.
            Collections.sort(this.updatableEntities, this.updateOrderComparator);
        }

        // Add the entity to the level grid.
        this.environmentCollisionGrid.add(entity);

        // Add the entity to the renderables list.
        this.renderables.add(entity);

        // Let the entity do some 'onEnvironmentEntry' logic.
        entity.onEnvironmentEntry(this.interactiveEnvironment);
    }

    /**
     * Add an entity to the list of in-game entities.
     * @param entity The entity to add.
     */
    public void add(Entity<TRenderContext> entity) {
        add(entity, null);
    }

    /**
     * Remove an entity from the list of in-game entities.
     * @param entity The entity to remove.
     */
    public void remove(Entity<TRenderContext> entity) {
        // There is nothing to do if the game object is not already in out list of existing game objects.
        if (!this.entities.contains(entity)) {
            return;
        }

        // Remove the entity from our list of all entities.
        this.entities.remove(entity);

        // Look through any groups we have and attempt to remove the entity.
        for (ArrayList<Entity<TRenderContext>> group : this.groups.values()) {
            if (group.remove(entity)) {
                break;
            }
        }

        // This entity may have been added to our list of updatable entities so attempt to remove it.
        this.updatableEntities.remove(entity);

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
        HashSet<Entity<TRenderContext>> pendingDestroy = new HashSet<Entity<TRenderContext>>();

        // Update each of the updatable entities sequentially.
        for (Entity<TRenderContext> entity : this.updatableEntities) {
            // TODO: Skip here if the update strategy of the entity is 'DELAY' and we have not waited long enough for the next update.

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
        for (Entity<TRenderContext> entity : pendingDestroy) {
            // Let the entity do some 'onDestroy' logic.
            entity.onDestroy();

            // Finally, remove the entity for the collection of environment entities.
            this.remove(entity);
        }
    }
}
