package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.rendering.IRenderWindow;

/**
 * Represents the camera used for the environment.
 */
public interface IEnvironmentCamera extends IRenderWindow {
    /**
     * Shake the environment camera.
     * @param duration The shake duration.
     * @param power The shake power.
     */
    void shake(long duration, float power);

    /**
     * Gets the camera viewport width.
     * @return The camera viewport width.
     */
    float getWidth();

    /**
     * Gets the camera viewport height.
     * @return The camera viewport height.
     */
    float getHeight();

    /**
     * Gets the camera X position.
     * @return The camera X position.
     */
    float getX();

    /**
     * Gets the camera Y position.
     * @return The camera Y position.
     */
    float getY();
}
