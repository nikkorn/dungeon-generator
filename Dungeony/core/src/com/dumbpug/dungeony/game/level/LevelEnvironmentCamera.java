package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.IEnvironmentCamera;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.rendering.IRenderable;

/**
 * Represents the level camera, wrapping the orthographic game camera.
 */
public class LevelEnvironmentCamera implements IEnvironmentCamera {
    /**
     * The orthographic game camera.
     */
    private OrthographicCamera camera;
    /**
     * The current target of camera focus.
     */
    private Entity target = null;
    /**
     * The x/y position that the camera will target if no target entity is defined.
     */
    private float x, y;
    /**
     * The active camera shake
     */
    private LevelCameraShake activeShake = null;

    /**
     * Creates a new instance of the LevelEnvironmentCamera class.
     * @param camera The orthographic game camera.
     */
    public LevelEnvironmentCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Gets the current target of camera focus.
     * @return The current target of camera focus.
     */
    public Entity getTarget() {
        return target;
    }

    /**
     * Sets the current target of camera focus.
     * @param target The current target of camera focus.
     */
    public void setTarget(Entity target) {
        this.target = target;
    }

    /**
     * Sets the value of the camera zoom.
     * @param value The value of the camera zoom.
     */
    public void setZoom(float value) {
        this.camera.zoom = value;
        this.camera.update();
    }

    /**
     * Sets the x/y position of the centre point of the camera.
     * @param x The x position of the centre point of the camera view.
     * @param y The y position of the centre point of the camera view.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Update the camera.
     * @param delta The delta time.
     */
    public void update(float delta) {
        // By default, lerp to the x/y position that we have.
        float cameraTargetX = this.x;
        float cameraTargetY = this.y;

        // We will try to lerp toward the target entity if one is defined.
        if (this.target != null) {
            Position targetOrigin = this.target.getOrigin();
            cameraTargetX         = targetOrigin.getX();
            cameraTargetY         = targetOrigin.getY();
        }

        // Lerp towards the camera target.
        this.camera.position.lerp(new Vector3(cameraTargetX, cameraTargetY, 0), .02f);

        // If we have an active level camera shake then let us shake!
        if (this.activeShake != null && !this.activeShake.isExpired()) {
            this.activeShake.update(delta, this.camera, cameraTargetX, cameraTargetY);
        }

        this.camera.update();
    }

    @Override
    public void shake(long duration, float power) {
        this.activeShake = new LevelCameraShake(duration, power);
    }

    /**
     * Gets the combined projection and view matrix.
     * @return The combined projection and view matrix
     */
    public Matrix4 getCombinedViewMatrix() {
        return this.camera.combined;
    }

    @Override
    public boolean contains(IRenderable renderable) {
        float halfCameraWidth  = (this.camera.viewportWidth * this.camera.zoom) / 2f;
        float halfCameraHeight = (this.camera.viewportHeight * this.camera.zoom) / 2f;

        if (renderable.getX() > (this.camera.position.x + halfCameraWidth)) {
            return false;
        }
        if ((renderable.getX() + renderable.getLengthX()) < (this.camera.position.x - halfCameraWidth)) {
            return false;
        }
        if (renderable.getY() > (this.camera.position.y + halfCameraHeight)) {
            return false;
        }
        if ((renderable.getY() + renderable.getLengthY()) < (this.camera.position.y - halfCameraHeight)) {
            return false;
        }

        return true;
    }
}
