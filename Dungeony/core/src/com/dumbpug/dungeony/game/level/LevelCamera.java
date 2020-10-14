package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.IEnvironmentCamera;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.rendering.IRenderWindow;
import com.dumbpug.dungeony.engine.rendering.IRenderable;

/**
 * Represents the level camera, wrapping the orthographic game camera.
 */
public class LevelCamera implements IRenderWindow, IEnvironmentCamera {
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
     * Creates a new instance of the LevelCamera class.
     * @param camera The orthographic game camera.
     */
    public LevelCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public Entity getTarget() {
        return target;
    }

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
        // TODO: Rumble any rumblies.

        // We will try to lerp toward the target entity if one is defined, otherwise we will just lerp towards x/y.
        if (this.target != null) {
            // Get teh target origin.
            Position targetOrigin = this.target.getOrigin();

            // Lerp to the target origin.
            this.camera.position.lerp(new Vector3(targetOrigin.getX(), targetOrigin.getY(), 0), .02f);
        } else {
            // Lerp to the x/y position that we have as we do not have a target entity.
            this.camera.position.lerp(new Vector3(this.x, this.y, 0), .02f);
        }

        this.camera.update();
    }

    @Override
    public void shake(long duration) {
        // TODO: Shame the camera
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
