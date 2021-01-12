package com.dumbpug.dungeony.engine;

/**
 * Represents the camera used for the environment.
 */
public interface IEnvironmentCamera {
    /**
     * Shake the environment camera.
     * @param duration The shake duration.
     * @param power The shake power.
     */
    void shake(long duration, float power);
}
