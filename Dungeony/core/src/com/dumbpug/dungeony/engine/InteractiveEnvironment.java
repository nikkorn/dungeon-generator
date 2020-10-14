package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.lighting.Light;
import com.dumbpug.dungeony.engine.lighting.Lights;

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
     * The environment camera.
     */
    private IEnvironmentCamera camera;
    /**
     * The environment lights.
     */
    private Lights lights;

    /**
     * Creates a new instance of the InteractiveEnvironment class.
     * @param environment The environment.
     * @param camera The environment camera.
     */
    public InteractiveEnvironment(Environment environment, IEnvironmentCamera camera, Lights lights) {
        this.environment = environment;
        this.camera      = camera;
        this.lights      = lights;
    }

    /**
     * Shake the environment camera for the specified duration in millis.
     * @param duration The shake duration in millis.
     */
    public void shakeCamera(long duration) {
        this.camera.shake(duration);
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
     * Gets the group that the entity resides in, or null if the entity is not in the environment or a group.
     * @param entity The entity.
     * @return The group that the entity resides in, or null if the entity is not in the environment or a group.
     */
    public String getEntityGroup(Entity entity) {
        return this.environment.getEntityGroup(entity);
    }

    /**
     * Gets a list of all level entities that overlap th specified area.
     * @param area The area of overlap.
     * @return A list of all level entities that overlap th specified area.
     */
    public ArrayList<Entity> getEntitiesInArea(Area area) {
        return new ArrayList<Entity>(this.environment.getGrid().getOverlapping(area));
    }

    /**
     * Gets a list of all level entities that are currently colliding the specified entity.
     * @param entity The entity.
     * @return A list of all level entities that are currently colliding the specified entity.
     */
    public ArrayList<Entity> getColliding(Entity entity) {
        return new ArrayList<Entity>(this.environment.getGrid().getColliding(entity));
    }

    /**
     * Add an entity to the environment.
     * @param entity The entity to add.
     */
    public void addEntity(Entity entity) {
        this.addEntity(entity, null);
    }

    /**
     * Add an entity to the environment.
     * @param entity The entity to add.
     * @param group The group to add the entity against.
     */
    public void addEntity(Entity entity, String group) {
        this.environment.addEntity(entity, group);
    }

    /**
     * Remove an entity from the environment.
     * @param entity The entity to remove.
     */
    public void removeEntity(Entity entity) {
        this.environment.removeEntity(entity);
    }

    /**
     * Add a light to the environment.
     * @param light The light to add.
     */
    public void addLight(Light light) {
        this.lights.add(light);
    }

    /**
     * Remove a light from the environment.
     * @param light The light to remove.
     */
    public void removeLight(Light light) {
        this.lights.remove(light);
    }
}
