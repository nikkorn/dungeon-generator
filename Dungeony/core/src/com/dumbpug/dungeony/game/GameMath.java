package com.dumbpug.dungeony.game;

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
}
