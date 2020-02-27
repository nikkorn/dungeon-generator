package com.dumbpug.dungeony.utilities.spatialgrid;

/**
 * Represents an axis-aligned bounding box.
 */
public interface IAABB {
    /**
     * Gets the x position of the box.
     * @return The y position of the box.
     */
    float getX();

    /**
     * Gets the y position of the box.
     * @return The y position of the box.
     */
    float getY();

    /**
     * Gets the width of the box.
     * @return The width of the box.
     */
    float getWidth();

    /**
     * Gets the height of the box.
     * @return The height of the box.
     */
    float getHeight();
}
