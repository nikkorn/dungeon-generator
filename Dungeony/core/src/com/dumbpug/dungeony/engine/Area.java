package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.utilities.spatialgrid.IAABB;

/**
 * Represents an area in a level.
 */
public class Area implements IAABB {
    /**
     * The x/y position of the bottom-left point of the area.
     */
    private float x, y;
    /**
     * The x/y length of the area.
     */
    private float lengthX, lengthY;

    /**
     * Creates a new instance of the Area class.
     * @param x The x position of the bottom-left point of the area.
     * @param y The y position of the bottom-left point of the area.
     * @param lengthX The x length of the area.
     * @param lengthY The y length of the area.
     */
    public Area(float x, float y, float lengthX, float lengthY) {
        this.x       = x;
        this.y       = y;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    /**
     * Creates a new instance of the Area class.
     * @param origin The origin of the area.
     * @param lengthX The x length of the area.
     * @param lengthY The y length of the area.
     */
    public Area(Position origin, float lengthX, float lengthY) {
        this(origin.getX() - (lengthX / 2f), origin.getY() - (lengthY / 2f), lengthX, lengthY);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getLengthX() {
        return this.lengthX;
    }

    @Override
    public float getLengthY() {
        return this.lengthY;
    }
}
