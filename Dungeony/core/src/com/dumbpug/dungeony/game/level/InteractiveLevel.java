package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.game.Direction;
import com.dumbpug.dungeony.game.Entity;

/**
 * A level that a character can inspect and interact with.
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
     * @param amount A value between -1 and 1 representing the amount of movement to make based on subject movement speed.
     */
    public void moveByDirection(Entity subject, Direction direction, float amount) {
        this.level.getGrid().moveByDirection(subject, direction, amount);
    }

    /**
     * Attempt to update the position of the specified entity at a specified angle.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param angle A value between 0 and 360 representing the angle of movement.
     * @param amount A value between -1 and 1 representing the amount of movement to make based on subject movement speed.
     */
    public void moveByAngle(Entity subject, float angle, float amount) {
        this.level.getGrid().moveByAngle(subject, angle, amount);
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
     */
    public void move(Entity subject, Entity target) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets whether the subject entity has a line of sight to the target entity.
     * @param subject The subject entity.
     * @param target The target entity.
     * @returns Whether the subject entity has a line of sight to the target entity.
     */
    public boolean canSee(Entity subject, Entity target) {
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
}
