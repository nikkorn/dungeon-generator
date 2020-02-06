package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Axis;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.utilities.spatialgrid.SpatialGrid;
import java.util.ArrayList;

/**
 * A spatial grid that handles the movement and collisions of level entities.
 */
public class LevelGrid extends SpatialGrid<Entity> {
    /**
     * Create a new instance of the LevelGrid class.
     */
    public LevelGrid() {
        super(Constants.GAME_GRID_CELL_SIZE);
    }

    /**
     * Update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param entity The entity to move.
     * @param offsetX A value between -1 and 1 representing the movement to make to the X position of the entity.
     * @param offsetY A value between -1 and 1 representing the movement to make to the Y position of the entity.
     */
    public void move(Entity entity, float offsetX, float offsetY) {
        // The entity must have already been added to the grid.
        if (!this.contains(entity)) {
            return;
        }

        // TODO Break up or movement into small maximum x/y segments if the x/y offset is really big.
        // TODO For each x/y segment:
        //       - Move X axis first and try to find intersecting and/or colliding aabbs.
        //       - Move Y axis second and try to find intersecting and/or colliding aabbs.
        // This way we can move a fast moving or small entity with less chance of clipping or skipping an aabb.

        // Get the delta time so we can have frame-independent movement.
        float delta = Gdx.graphics.getDeltaTime() * 50f;

        moveEntityOnAxis(entity, offsetX, delta, Axis.X);
        moveEntityOnAxis(entity, offsetY, delta, Axis.Y);

        // entity.setX(entity.getX() + ((offsetX * entity.getMovementSpeed()) * delta));
        // entity.setY(entity.getY() + ((offsetY * entity.getMovementSpeed()) * delta));
    }

    private void moveEntityOnAxis(Entity entity, float offset, float delta, Axis axis) {
        // TODO We should chop up large movements into smaller increments.

        // How the entity position is updated depends on the axis.
        if (axis == Axis.X) {
            // Get the initial position based on the axis we are dealing with.
            float origin = entity.getX();

            // Move to the position that we are moving to on the axis based on the entity movement speed.
            // We apply the application delta to this value for frame-independent movement speed.
            entity.setX(origin + ((offset * entity.getMovementSpeed()) * delta));
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

            // Move to the position that we are moving to on the axis based on the entity movement speed.
            // We apply the application delta to this value for frame-independent movement speed.
            entity.setY(origin + ((offset * entity.getMovementSpeed()) * delta));
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
