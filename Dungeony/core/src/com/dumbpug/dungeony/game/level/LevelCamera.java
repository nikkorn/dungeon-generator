package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.dumbpug.dungeony.engine.IEnvironmentCamera;
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
     * Creates a new instance of the LevelCamera class.
     * @param camera The orthographic game camera.
     */
    public LevelCamera(OrthographicCamera camera) {
        this.camera = camera;
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
        this.camera.position.set(x, y, 0);
        this.camera.update();
    }

    /**
     * Sets the x/y position of the centre point of the camera with lerp.
     * @param x The x position of the centre point of the camera view.
     * @param y The y position of the centre point of the camera view.
     * @param lerpAlpha The lerp alpha.
     */
    public void setPosition(float x, float y, float lerpAlpha) {
        this.camera.position.lerp(new Vector3(x, y,0), lerpAlpha);
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
