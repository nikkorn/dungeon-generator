package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.game.Direction;
import com.dumbpug.dungeony.game.Entity;
import java.util.ArrayList;

/**
 * A level that a character or object can inspect and interact with.
 */
public class InteractiveLevel {
    /**
     * The level.
     */
    private Level level;

    /**
     * Creates a new instance of the InteractiveLevel class.
     * @param level The level instance.
     */
    public InteractiveLevel(Level level) {
        this.level = level;
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
        this.level.getGrid().moveByDirection(subject, direction, distance);
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
        this.level.getGrid().moveByAngle(subject, angle, distance);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param offsetX A value between -1 and 1 representing the movement to make to the X position of the entity.
     * @param offsetY A value between -1 and 1 representing the movement to make to the Y position of the entity.
     */
    public void move(Entity subject, float offsetX, float offsetY) {
        this.level.getGrid().move(subject, offsetX, offsetY);
    }

    /**
     * Attempt to update the position of the specified entity by moving them towards a target.
     * @param subject The subject entity to move.
     * @param target The target entity to move towards.
     * @param distance The distance to move the entity towards the target.
     */
    public void move(Entity subject, Entity target, float distance) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets whether the subject entity has a line of sight to the target entity.
     * @param subject The subject entity.
     * @param target The target entity.
     * @param distance The max line of sight distance.
     * @returns Whether the subject entity has a line of sight to the target entity.
     */
    public boolean canSee(Entity subject, Entity target, float distance) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the distance between the subject and target entity.
     * @param subject The subject entity.
     * @param target The target entity.
     * @returns The distance between the subject and target entity.
     */
    public float getDistanceBetween(Entity subject, Entity target) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a list of all level entities that overlap th specified area.
     * @param area The area of overlap.
     * @return A list of all level entities that overlap th specified area.
     */
    public ArrayList<Entity> getEntitiesInArea(Area area) {
        return new ArrayList<Entity>(this.level.getGrid().getOverlapping(area));
    }
}
