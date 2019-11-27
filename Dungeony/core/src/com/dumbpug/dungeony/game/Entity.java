package com.dumbpug.dungeony.game;

import com.dumbpug.dungeony.utilities.spatialgrid.IAABB;

/**
 * An in-game entity with a position and size that can collide with other entities.
 */
public abstract class Entity implements IAABB {
    /**
     * The origin position of the entity.
     */
    private Position origin;
    /**
     * The bottom-left position of the entity.
     */
    private Position position;

    /**
     * Creates a new instance of the Entity class.
     * @param origin The initial origin of the entity.
     */
    public Entity(Position origin) {
        // Set the entity origin.
        this.origin = origin;
        // Find the position as the bottom-left point of the entity based on its origin and size.
        this.position = new Position(
                origin.getX() - (this.getWidth() / 2f),
                origin.getY() - (this.getHeight() / 2f)
        );
    }

    /**
     * Gets the x position of the box.
     * @return The y position of the box.
     */
    @Override
    public float getX() {
        return this.position.getX();
    }

    /**
     * Gets the y position of the box.
     * @return The y position of the box.
     */
    @Override
    public float getY() {
        return this.position.getY();
    }

    /**
     * Gets the width of the box.
     * @return The width of the box.
     */
    @Override
    public abstract float getWidth();

    /**
     * Gets the height of the box.
     * @return The height of the box.
     */
    @Override
    public abstract float getHeight();

    /**
     * Gets the collision mask of the entity.
     * @return The collision mask of the entity.
     */
    public abstract int getCollisionMask();
}
