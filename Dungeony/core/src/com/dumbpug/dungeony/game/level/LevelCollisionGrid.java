package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Axis;
import com.dumbpug.dungeony.game.Direction;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.utilities.spatialgrid.SpatialGrid;
import java.util.ArrayList;

/**
 * A spatial grid that handles the movement and collisions of level entities.
 */
public class LevelCollisionGrid extends SpatialGrid<Entity> {
    /**
     * Create a new instance of the LevelCollisionGrid class.
     */
    public LevelCollisionGrid() {
        super(Constants.GAME_GRID_CELL_SIZE);
    }

    /**
     * Attempt to update the position of the specified entity in a specified direction.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param direction The direction to move in.
     * @param distance The distance to move the entity.
     */
    public void moveByDirection(Entity subject, Direction direction, float distance) {
        this.moveByAngle(subject, direction.getAngle(), distance);
    }

    /**
     * Attempt to update the position of the specified entity at a specified angle.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param angle A value between 0 and 360 representing the angle of movement.
     * @param distance The distance to move the entity.
     */
    public void moveByAngle(Entity subject, float angle, float distance) {
        // The entity must have already been added to the grid.
        if (!this.contains(subject)) {
            return;
        }

        // Calculate the new position of the entity to move based on the amount to move and the entity movement speed.
        float offsetX = (float) Math.sin(angle * Math.PI / 180) * distance;
        float offsetY = (float) Math.cos(angle * Math.PI / 180) * distance;

        // Move the subject entity by finding the x/y offsets.
        this.moveEntity(subject, offsetX, offsetY);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param offsetX The offset to apply on the X axis.
     * @param offsetY The offset to apply on the Y axis.
     */
    public void move(Entity subject, float offsetX, float offsetY) {
        // The entity must have already been added to the grid.
        if (!this.contains(subject)) {
            return;
        }

        this.moveEntity(subject, offsetX, offsetY);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Delta time is applied to any position updates.
     * @param subject The subject entity to move.
     * @param offsetX The offset to apply on the X axis.
     * @param offsetY The offset to apply on the Y axis.
     */
    private void moveEntity(Entity subject, float offsetX, float offsetY) {
        // TODO Break up or movement into small maximum x/y segments if the x/y offset is really big.
        // TODO For each x/y segment:
        //       - Move X axis first and try to find intersecting and/or colliding aabbs.
        //       - Move Y axis second and try to find intersecting and/or colliding aabbs.
        // This way we can move a fast moving or small entity with less chance of clipping or skipping an aabb.

        // Update the position of the given entity on the each axis.
        moveEntityOnAxis(subject, offsetX, Axis.X);
        moveEntityOnAxis(subject, offsetY, Axis.Y);
    }

    /**
     * Update the position of the given entity on the specified axis.
     * @param entity The entity to move.
     * @param offset The offset to be applied.
     * @param axis The axis on which to update the entity position.
     */
    private void moveEntityOnAxis(Entity entity, float offset, Axis axis) {
        // TODO We should chop up large movements into smaller increments.

        // How the entity position is updated depends on the axis.
        if (axis == Axis.X) {
            // Get the initial position based on the axis we are dealing with.
            float origin = entity.getX();

            // TODO Determine whether we should stop a moving entity if our entity is ALREADY colliding with another.
            // It makes sense that two collidable entities that have got themselves in a state where they already
            // overlap at the start of a movement update should be given the chance to move off of each other.

            // Move to the position that we are moving to on the current axis.
            // We apply the application delta to this value for frame-independent movement speed.
            entity.setX(origin + (offset * Gdx.graphics.getDeltaTime()));
            this.update(entity);

            // Find any level entities that the entity may now be colliding with.
            ArrayList<Entity> collidingEntities = new ArrayList<>();
            for (Entity collidingEntity : this.getColliding(entity)) {
                // Even though the entities collide in our level grid, they may not actually bump into each other.
                if (collidingEntity.collidesWith(entity)) {
                    collidingEntities.add(collidingEntity);
                }
            }

            // Check whether the entity is now bumping into any others.
            if (collidingEntities.size() > 0) {
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
            entity.setY(origin + (offset * Gdx.graphics.getDeltaTime()));
            this.update(entity);

            // Find any level entities that the entity may now be colliding with.
            ArrayList<Entity> collidingEntities = new ArrayList<>();
            for (Entity collidingEntity : this.getColliding(entity)) {
                // Even though the entities collide in our level grid, they may not actually bump into each other.
                if (collidingEntity.collidesWith(entity)) {
                    collidingEntities.add(collidingEntity);
                }
            }

            // Check whether the entity is now bumping into any others.
            if (collidingEntities.size() > 0) {
                // TODO Move the entity to the position it would be in at the point it collided with the closest entity.
                // TODO For now just move the entity back to its original position.
                entity.setY(origin);
                this.update(entity);
            }
        }
    }
}
