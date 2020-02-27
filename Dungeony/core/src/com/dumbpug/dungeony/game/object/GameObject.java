package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;

/**
 * An object that can exist in the game.
 */
public abstract class GameObject extends Entity {
    /**
     * Creates a new instance of the GameObject class.
     * @param origin The initial origin of the GameObject.
     */
    public GameObject(Position origin) {
        super(origin);
    }

    /**
     * Gets the type of the game object.
     * @return The type of the game object.
     */
    public abstract GameObjectType getType();

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.OBJECT;
    }

    @Override
    public int getCollisionMask() {
        // Everything should collide with an object by default.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.PROJECTILE | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the movement speed of the entity.
     * @return The movement speed of the entity.
     */
    public float getMovementSpeed() {
        // Static entities have no movement speed.
        return 0;
    }
}
