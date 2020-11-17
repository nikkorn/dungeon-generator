package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.Renderables;
import com.dumbpug.dungeony.engine.utilities.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
     * Whether the list of entities is currently being updated.
     */
    private boolean areEntitiesBeingUpdated = false;
    /**
     * The queue of modifications to make to the entities collection populated during collection updates.
     */
    private Queue<EntitiesModification> pendingEntitiesModifications = new Queue<EntitiesModification>();

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
     * Gets the group that the entity resides in, or null if the entity is not in the entities list or a group.
     * @param entity The entity.
     * @return The group that the entity resides in, or null if the entity is not in the entities list or a group.
     */
    public String getGroupName(Entity entity) {
        for (Map.Entry<String, ArrayList<Entity<TRenderContext>>> entry : this.groups.entrySet()) {
            if (entry.getValue().contains(entity)) {
                // The entity was in the current group list.
                return entry.getKey();
            }
        }

        // The entity was not in any group lists.
        return null;
    }

    /**
     * Gets a list of all entities withing the specified group.
     * @return a list of all entities withing the specified group.
     */
    public <TType extends Entity<TRenderContext>> ArrayList<TType> getGroup(String group) {
        // The entity group may not exist.
        if (!this.groups.containsKey(group.toUpperCase())) {
            throw new RuntimeException("entity group '" + group + "' does not exist");
        }

        return (ArrayList<TType>)this.groups.get(group.toUpperCase());
    }

    /**
     * Add an entity to the list of in-game entities.
     * @param entity The entity to add.
     * @param group The group to add the entity against.
     */
    public void add(Entity<TRenderContext> entity, String group) {
        // We cannot add entities immediately if we are in an update, add them as pending collection modification to be made after the current update.
        if (this.areEntitiesBeingUpdated) {
            this.pendingEntitiesModifications.add(new EntitiesModification(EntitiesModificationType.ADD, entity, group));
            return;
        }

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
     * Add a list of entities to the list of in-game entities.
     * @param list The list of entities to add.
     * @param group The group to add the entities against.
     */
    public void add(ArrayList<? extends Entity<TRenderContext>> list, String group) {
        for (Entity<TRenderContext> entity : list) {
            this.add(entity, group);
        }
    }

    /**
     * Add a list of entities to the list of in-game entities.
     * @param list The list of entities to add.
     */
    public void add(ArrayList<? extends Entity<TRenderContext>> list) {
        this.add(list, null);
    }

    /**
     * Remove an entity from the list of in-game entities.
     * @param entity The entity to remove.
     */
    public void remove(Entity<TRenderContext> entity) {
        // We cannot remove entities immediately if we are in an update, add them as pending collection modification to be made after the current update.
        if (this.areEntitiesBeingUpdated) {
            this.pendingEntitiesModifications.add(new EntitiesModification(EntitiesModificationType.REMOVE, entity));
            return;
        }

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
     * Remove a list of entities from the list of in-game entities.
     * @param list The list of entities to remove.
     */
    public void removeEntities(ArrayList<? extends Entity<TRenderContext>> list) {
        for (Entity<TRenderContext> entity : list) {
            this.remove(entity);
        }
    }

    /**
     * Update each of the entities sequentially.
     * @param delta The delta time.
     */
    public void update(float delta) {
        // Set the flag that says whether we are currently in an update. Any entities that are
        // added as part of the update will have to be added after the update is finished.
        this.areEntitiesBeingUpdated = true;

        // Update each of the updatable entities sequentially.
        for (Entity<TRenderContext> entity : this.updatableEntities) {
            // TODO: Skip here if the update strategy of the entity is 'DELAY' and we have not waited long enough for the next update.

            // Update the entity, passing the environment interactivity layer with which the entity can interact with the environment.
            entity.update(this.interactiveEnvironment, delta);
        }

        // Unset the flag that says whether we are currently in an update, any entities
        // that are added now will go straight into our entities collection.
        this.areEntitiesBeingUpdated = false;

        // Process any entities that were added or removed as part of the update.
        while (this.pendingEntitiesModifications.hasNext()) {
            EntitiesModification modification = this.pendingEntitiesModifications.next();

            switch (modification.getType()) {
                case ADD:
                    this.add(modification.getEntity(), modification.getGroup());
                    break;
                case REMOVE:
                    this.remove(modification.getEntity());
                    break;
                default:
                    throw new RuntimeException("unknown entity modification type: " + modification.getType());
            }
        }
    }

    /**
     * Enumeration of entity modification types.
     */
    private enum EntitiesModificationType {
        ADD,
        REMOVE
    }

    /**
     * Represents a modification to make to an entities collection.
     */
    private class EntitiesModification {
        /**
         * The modification type type.
         */
        private EntitiesModificationType type;
        /**
         * The entity to add or remove.
         */
        private Entity<TRenderContext> entity;
        /**
         * The group to add the entity to if the modification type is 'ADD'.
         */
        private String group;

        /**
         * Creates a new instance of the EntitiesModification class.
         * @param type The modification type type.
         * @param entity The entity to add or remove.
         * @param group The group to add the entity to if the modification type is 'ADD'.
         */
        public EntitiesModification(EntitiesModificationType type, Entity<TRenderContext> entity, String group) {
            this.type   = type;
            this.entity = entity;
            this.group  = group;
        }

        /**
         * Creates a new instance of the EntitiesModification class.
         * @param type The modification type type.
         * @param entity The entity to add or remove.
         */
        public EntitiesModification(EntitiesModificationType type, Entity<TRenderContext> entity) {
            this(type, entity, null);
        }

        /**
         * Gets the modification type type.
         * @return The modification type type.
         */
        public EntitiesModificationType getType() {
            return type;
        }

        /**
         * Gets the entity to add or remove.
         * @return The entity to add or remove.
         */
        public Entity<TRenderContext> getEntity() {
            return entity;
        }

        /**
         * Gets the group to add the entity to if the modification type is 'ADD'.
         * @return The group to add the entity to if the modification type is 'ADD'.
         */
        public String getGroup() {
            return group;
        }
    }
}
