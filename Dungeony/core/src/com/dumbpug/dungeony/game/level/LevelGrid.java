package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.utilities.spatialgrid.SpatialGrid;

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
     * @param offsetX The offset to apply to the entity position on the X axis.
     * @param offsetY The offset to apply to the entity position on the Y axis.
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
    }
}
