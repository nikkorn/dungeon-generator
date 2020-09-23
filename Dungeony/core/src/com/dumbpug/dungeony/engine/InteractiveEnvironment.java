package com.dumbpug.dungeony.engine;

import java.util.ArrayList;

/**
 * An interactivity layer that is available to entities during updates.
 */
public class InteractiveEnvironment {
    /**
     * The environment.
     */
    private Environment environment;

    /**
     * Creates a new instance of the InteractiveEnvironment class.
     * @param environment The environment.
     */
    public InteractiveEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Attempt to update the position of the specified entity in a specified direction.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param direction The direction to move in.
     * @param distance The distance to move the entity.
     * @param delta The delta time.
     */
    public void moveByDirection(Entity subject, Direction direction, float distance, float delta) {
        this.environment.getGrid().moveByDirection(subject, direction, distance, delta);
    }

    /**
     * Attempt to update the position of the specified entity at a specified angle.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param angle A value between 0 and 360 representing the angle of movement.
     * @param distance The distance to move the entity.
     * @param delta The delta time.
     */
    public void moveByAngle(Entity subject, float angle, float distance, float delta) {
        this.environment.getGrid().moveByAngle(subject, angle, distance, delta);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param offsetX A value between -1 and 1 representing the movement to make to the X position of the entity.
     * @param offsetY A value between -1 and 1 representing the movement to make to the Y position of the entity.
     * @param delta The delta time.
     */
    public void move(Entity subject, float offsetX, float offsetY, float delta) {
        this.environment.getGrid().move(subject, offsetX, offsetY, delta);
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
        return new ArrayList<Entity>(this.environment.getGrid().getOverlapping(area));
    }
}
