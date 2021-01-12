package com.dumbpug.dungeony.engine.utilities;

import com.dumbpug.dungeony.engine.Position;

/**
 * Game math helper functions.
 */
public class GameMath {
    /**
     * Gets the angle of the line between two points.
     * @param aX The X position of the first point.
     * @param aY The Y position of the first point.
     * @param bX The X position of the second point.
     * @param bY The Y position of the second point.
     * @return The angle of the line between two points.
     */
    public static float getAngle(float aX, float aY, float bX, float bY) {
        return (float) Math.toDegrees(Math.atan2(bX - aX, bY - aY));
    }

    /**
     * Gets the length of the line between two points.
     * @param aX The X position of the first point.
     * @param aY The Y position of the first point.
     * @param bX The X position of the second point.
     * @param bY The Y position of the second point.
     * @return The length of the line between two points.
     */
    public static float getLength(float aX, float aY, float bX, float bY) {
        float dx = bX - aX;
        float dy = bY - aY;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Gets the position defined by an origin x/y, angle and length.
     * @param x The X position of the first point.
     * @param y The Y position of the first point.
     * @param angle The angle to follow.
     * @param length The length to follow.
     * @return The position defined by an origin x/y, angle and length
     */
    public static Position getPositionForAngle(float x, float y, float angle, float length) {
        float offsetX = (float) Math.sin(angle * Math.PI / 180) * length;
        float offsetY = (float) Math.cos(angle * Math.PI / 180) * length;
        return new Position(x + offsetX, y + offsetY);
    }
}
