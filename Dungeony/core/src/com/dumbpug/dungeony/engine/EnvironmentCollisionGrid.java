package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.utilities.spatialgrid.SpatialGrid;
import java.util.HashSet;

/**
 * A spatial grid that handles the movement and collisions of entities in an environment.
 */
public class EnvironmentCollisionGrid extends SpatialGrid<Entity> {
    /**
     * Create a new instance of the EnvironmentCollisionGrid class.
     * @param cellSize The size of cells in the spatial grid.
     */
    public EnvironmentCollisionGrid(float cellSize) {
        super(cellSize);
    }

    /**
     * Attempt to update the position of the specified entity in a specified direction.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param direction The direction to move in.
     * @param distance The distance to move the entity.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    public HashSet<Entity> moveByDirection(Entity subject, Direction direction, float distance, float delta) {
        return this.moveByAngle(subject, direction.getAngle(), distance, delta);
    }

    /**
     * Attempt to update the position of the specified entity at a specified angle.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param angle A value between 0 and 360 representing the angle of movement.
     * @param distance The distance to move the entity.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    public HashSet<Entity> moveByAngle(Entity subject, float angle, float distance, float delta) {
        // The entity must have already been added to the grid.
        if (!this.contains(subject)) {
            return null;
        }

        // Calculate the new position of the entity to move based on the amount to move and the entity movement speed.
        float offsetX = (float) Math.sin(angle * Math.PI / 180) * distance;
        float offsetY = (float) Math.cos(angle * Math.PI / 180) * distance;

        // Move the subject entity by finding the x/y offsets.
        return this.moveEntity(subject, offsetX, offsetY, delta);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param offsetX The offset to apply on the X axis.
     * @param offsetY The offset to apply on the Y axis.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    public HashSet<Entity> move(Entity subject, float offsetX, float offsetY, float delta) {
        // The entity must have already been added to the grid.
        if (!this.contains(subject)) {
            return null;
        }

        return this.moveEntity(subject, offsetX, offsetY, delta);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Delta time is applied to any position updates.
     * @param subject The subject entity to move.
     * @param offsetX The offset to apply on the X axis.
     * @param offsetY The offset to apply on the Y axis.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    private HashSet<Entity> moveEntity(Entity subject, float offsetX, float offsetY, float delta) {
        // TODO Break up or movement into small maximum x/y segments if the x/y offset is really big.
        // TODO For each x/y segment:
        //       - Move X axis first and try to find intersecting and/or colliding aabbs.
        //       - Move Y axis second and try to find intersecting and/or colliding aabbs.
        // This way we can move a fast moving or small entity with less chance of clipping or skipping an aabb.

        // Get the set of entities that the subject is already colliding with before the movement update happens.
        HashSet<Entity> existingCollisions = this.getColliding(subject);

        // Create a set of all entities that the moving entity has collided with during the entire movement
        // update, this includes the entities that the subject is colliding with before the movement is made.
        HashSet<Entity> allCollisions = new HashSet<Entity>(existingCollisions);

        // Update the position of the given entity on the each axis.
        moveEntityOnAxis(subject, offsetX, Axis.X, delta, allCollisions, existingCollisions);
        moveEntityOnAxis(subject, offsetY, Axis.Y, delta, allCollisions, existingCollisions);

        return allCollisions;
    }

    /**
     * Update the position of the given entity on the specified axis.
     * @param entity The entity to move.
     * @param offset The offset to be applied.
     * @param axis The axis on which to update the entity position.
     * @param delta The delta time.
     * @param allCollisions The set to populate with all entities that were collided with this movement update.
     * @param existingCollisions The set of entities that the subject is already colliding with before the movement update happens.
     */
    private void moveEntityOnAxis(Entity entity, float offset, Axis axis, float delta, HashSet<Entity> allCollisions, HashSet<Entity> existingCollisions) {
        // TODO We should chop up large movements into smaller increments.

        boolean collisionFound = false;

        // How the entity position is updated depends on the axis.
        if (axis == Axis.X) {
            // Get the initial position based on the axis we are dealing with.
            float origin = entity.getX();

            // TODO Determine whether we should stop a moving entity if our entity is ALREADY colliding with another.
            // It makes sense that two collidable entities that have got themselves in a state where they already
            // overlap at the start of a movement update should be given the chance to move off of each other.

            // Move to the position that we are moving to on the current axis.
            // We apply the application delta to this value for frame-independent movement speed.
            entity.setX(origin + (offset * delta));
            this.update(entity);

            // Find any level entities that the entity may now be colliding with.
            for (Entity collidingEntity : this.getColliding(entity)) {
                // Even though the entities collide in our level grid, they may not actually bump into each other.
                // We also want to exclude an entities that the subject was already colliding with before the
                // update, this allows already colliding entities to move away from each other.
                if (canEntitiesCollide(collidingEntity, entity) && !existingCollisions.contains(collidingEntity)) {
                    allCollisions.add(collidingEntity);
                    collisionFound = true;
                }
            }

            // Check whether the entity is now bumping into any others.
            if (collisionFound) {
                // TODO Move the entity to the position it would be in at the point it collided with the closest entity.
                // TODO For now just move the entity back to its original position.
                entity.setX(origin);
                this.update(entity);
            }
        } else {
            // Get the initial position based on the axis we are dealing with.
            float origin = entity.getY();

            // TODO Determine whether we should stop a moving entity if our entity is ALREADY colliding with another.
            // It makes sense that two collidable entities that have got themselves in a state where they already
            // overlap at the start of a movement update should be given the chance to move off of each other.

            // Move to the position that we are moving to on the current axis.
            // We apply the application delta to this value for frame-independent movement speed.
            entity.setY(origin + (offset * delta));
            this.update(entity);

            // Find any level entities that the entity may now be colliding with.
            for (Entity collidingEntity : this.getColliding(entity)) {
                // Even though the entities collide in our level grid, they may not actually bump into each other.
                // We also want to exclude an entities that the subject was already colliding with before the
                // update, this allows already colliding entities to move away from each other.
                if (canEntitiesCollide(collidingEntity, entity) && !existingCollisions.contains(collidingEntity)) {
                    allCollisions.add(collidingEntity);
                    collisionFound = true;
                }
            }

            // Check whether the entity is now bumping into any others.
            if (collisionFound) {
                // TODO Move the entity to the position it would be in at the point it collided with the closest entity.
                // TODO For now just move the entity back to its original position.
                entity.setY(origin);
                this.update(entity);
            }
        }
    }

    /**
     * Gets whether entity a can collide with entity b.
     * @param a Entity a.
     * @param b Entity b.
     * @return Whether entity a can collide with entity b.
     */
    private boolean canEntitiesCollide(Entity a, Entity b) {
        return (a.getCollisionMask() & b.getCollisionLayers()) > 0 || (b.getCollisionMask() & a.getCollisionLayers()) > 0;
    }
}
