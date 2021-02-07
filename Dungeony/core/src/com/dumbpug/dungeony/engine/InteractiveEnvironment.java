package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.audio.IAudioPlayer;
import com.dumbpug.dungeony.engine.dialog.Dialog;
import com.dumbpug.dungeony.engine.lighting.Light;
import java.util.ArrayList;
import java.util.HashSet;

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
     * The environment audio player.
     */
    private IAudioPlayer audioPlayer;

    /**
     * Creates a new instance of the InteractiveEnvironment class.
     * @param environment The environment.
     * @param camera The environment camera.
     * @param audioPlayer The audio player.
     */
    public InteractiveEnvironment(Environment environment, IEnvironmentCamera camera, IAudioPlayer audioPlayer) {
        this.environment = environment;
        this.camera      = camera;
        this.audioPlayer = audioPlayer;
    }

    /**
     * Shake the environment camera for the specified duration in millis.
     * @param duration The shake duration in millis.
     * @param power The shake power.
     */
    public void shakeCamera(long duration, float power) {
        this.camera.shake(duration, power);
    }

    /**
     * Attempt to update the position of the specified by moving them towards the target position.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param target The target position.
     * @param distance The distance to move the entity.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    public HashSet<Entity> moveTowards(Entity subject, Position target, float distance, float delta) {
        return this.environment.getGrid().moveTowards(subject, target, distance, delta);
    }

    /**
     * Attempt to update the position of the specified by moving them towards the target entity.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param target The target entity.
     * @param distance The distance to move the entity.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    public HashSet<Entity> moveTowards(Entity subject, Entity target, float distance, float delta) {
        return this.environment.getGrid().moveTowards(subject, target, distance, delta);
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
        return this.environment.getGrid().moveByDirection(subject, direction, distance, delta);
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
        return this.environment.getGrid().moveByAngle(subject, angle, distance, delta);
    }

    /**
     * Attempt to update the position of the specified entity by applying the given x/y offset.
     * Entity movements are shortened or prevented entirely if a full movement
     * would cause the entity to overlap another entity that it collides with.
     * @param subject The subject entity to move.
     * @param offsetX A value between -1 and 1 representing the movement to make to the X position of the entity.
     * @param offsetY A value between -1 and 1 representing the movement to make to the Y position of the entity.
     * @param delta The delta time.
     * @return The set of entities that the subject entity collided with during the movement update.
     */
    public HashSet<Entity> move(Entity subject, float offsetX, float offsetY, float delta) {
        return this.environment.getGrid().move(subject, offsetX, offsetY, delta);
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
     * Gets the group that the entity resides in, or null if the entity is not in the environment or a group.
     * @param entity The entity.
     * @return The group that the entity resides in, or null if the entity is not in the environment or a group.
     */
    public String getEntityGroup(Entity entity) {
        return this.environment.getEntities().getGroupName(entity);
    }

    /**
     * Gets a list of all level entities that are withing the specified entity group.
     * @param group The entity group.
     * @return A list of all level entities that are withing the specified entity group.
     */
    public ArrayList<Entity> getEntitiesInGroup(String group) {
        return this.environment.getEntities().getGroup(group);
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
        this.environment.getEntities().add(entity, group);
    }

    /**
     * Remove an entity from the environment.
     * @param entity The entity to remove.
     */
    public void removeEntity(Entity entity) {
        this.environment.getEntities().remove(entity);
    }

    /**
     * Add a light to the environment.
     * @param light The light to add.
     */
    public void addLight(Light light) {
        this.environment.getLights().add(light);
    }

    /**
     * Remove a light from the environment.
     * @param light The light to remove.
     */
    public void removeLight(Light light) {
        this.environment.getLights().remove(light);
    }

    /**
     * Add a dialog to the environment.
     * @param dialog The dialog to add.
     */
    public void addDialog(Dialog dialog) {
        this.environment.getDialogs().add(dialog);
    }

    /**
     * Remove a dialog from the environment.
     * @param dialog The dialog to remove.
     */
    public void removeDialog(Dialog dialog) {
        this.environment.getDialogs().remove(dialog);
    }

    /**
     * Gets the active dialog for the given entity, or null if the entity has no associated dialog.
     * @param entity The entity.
     * @return The active dialog for the given entity, or null if the entity has no associated dialog.
     */
    public Dialog getActiveDialog(Entity entity) {
        // Get a list off all dialogs in the environment where the entity is the interacting entity.
        ArrayList<Dialog> activeDialogs = this.environment.getDialogs().getDialogsWithInteractingEntity(entity);

        // Return the first dialog where the entity is the interacting entity.
        return activeDialogs.isEmpty() ? null : activeDialogs.get(0);
    }

    /**
     *
     * @param sound
     * @param source
     */
    public void playSound(String sound, Position source, float volume) {
        float cameraXMin = this.camera.getX() - (this.camera.getWidth() / 2f);
        float cameraXMax = this.camera.getX() + (this.camera.getWidth() / 2f);
        float cameraYMin = this.camera.getY() - (this.camera.getHeight() / 2f);
        float cameraYMax = this.camera.getY() + (this.camera.getHeight() / 2f);

        // Is the source of the sound actually within the camera bounds then the volume should not be modified.
        if (source.getX() >= cameraXMin && source.getX() <= cameraXMax && source.getY() >= cameraYMin && source.getY() <= cameraYMax) {
            this.audioPlayer.playSound(sound, volume);
            return;
        }

        // TODO Modify volume based on distance to camera bounds.
    }

    /**
     *
     * @param sound
     * @param source
     */
    public void playSound(String sound, Position source) {
        this.playSound(sound, source, 1f);
    }

    /**
     *
     * @param sound
     */
    public void playSound(String sound, float volume) {
        this.audioPlayer.playSound(sound, volume);
    }

    /**
     *
     * @param sound
     */
    public void playSound(String sound) {
        this.playSound(sound, 1f);
    }
}
