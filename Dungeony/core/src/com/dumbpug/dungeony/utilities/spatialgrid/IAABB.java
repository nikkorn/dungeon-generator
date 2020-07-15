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
     * Gets the length of the box on the X axis.
     * @return The length of the box on the X axis.
     */
    float getLengthX();

    /**
     * Gets the length of the box on the Y axis.
     * @return The length of the box on the Y axis.
     */
    float getLengthY();
}
