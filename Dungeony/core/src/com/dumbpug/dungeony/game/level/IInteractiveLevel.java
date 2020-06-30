package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.game.Entity;

/**
 * A representation of a level that a character can interact with.
 */
public interface IInteractiveLevel {
    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param offsetX A value between -1 and 1 representing the movement to make to the X position of the entity.
     * @param offsetY A value between -1 and 1 representing the movement to make to the Y position of the entity.
     */
    void move(Entity subject, float offsetX, float offsetY);

    void canSee(Entity subject, Entity target);

    void getDistanceBetween(Entity subject, Entity target);
}
