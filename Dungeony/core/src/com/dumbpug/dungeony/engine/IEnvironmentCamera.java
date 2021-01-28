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
}
