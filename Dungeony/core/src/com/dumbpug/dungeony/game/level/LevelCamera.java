package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.engine.rendering.IRenderWindow;
import com.dumbpug.dungeony.engine.rendering.IRenderable;

/**
 * Represents the level camera.
 */
public class LevelCamera implements IRenderWindow {
    /**
     * The x/y position of the centre point of the camera.
     */
    private float x, y;
    /**
     * The x/y length of the camera view.
     */
    private float lengthX, lengthY;

    /**
     * Creates a new instance of the LevelCamera class.
     * @param x The x position of the centre point of the camera view.
     * @param y The y position of the centre point of the camera view.
     * @param lengthX The x length of the camera view.
     * @param lengthY The y length of the camera view.
     */
    public LevelCamera(float x, float y, float lengthX, float lengthY) {
        this.x       = x;
        this.y       = y;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    /**
     * Sets the x/y position of the centre point of the camera.
     * @param x The x position of the centre point of the camera view.
     * @param y The y position of the centre point of the camera view.
     */
    public void set(float x, float y, float lengthX, float lengthY) {
        this.x       = x;
        this.y       = y;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    @Override
    public boolean contains(IRenderable renderable) {
        float halfCameraWidth  = this.lengthX / 2f;
        float halfCameraHeight = this.lengthY / 2f;

        if (renderable.getX() > (this.x + halfCameraWidth)) {
            return false;
        }
        if ((renderable.getX() + renderable.getLengthX()) < (this.x - halfCameraWidth)) {
            return false;
        }
        if (renderable.getY() > (this.y + halfCameraHeight)) {
            return false;
        }
        if ((renderable.getY() + renderable.getLengthY()) < (this.y - halfCameraHeight)) {
            return false;
        }

        return true;
    }
}
