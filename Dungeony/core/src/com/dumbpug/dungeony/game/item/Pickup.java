package com.dumbpug.dungeony.game.item;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;

/**
 * An item pickup entity.
 */
public class Pickup extends Entity {
    /**
     * The pickup item.
     */
    private Item item;

    /**
     * Creates a new instance of the Pickup class.
     * @param origin The initial origin of the entity.
     * @param item The pickup item.
     */
    public Pickup(Position origin, Item item) {
        super(origin);
        this.item = item;
    }

    @Override
    public float getLengthX() {
        return Constants.GAME_PICKUP_SIZE;
    }

    @Override
    public float getLengthY() {
        return Constants.GAME_PICKUP_SIZE;
    }

    @Override
    public float getLengthZ() {
        return Constants.GAME_PICKUP_SIZE;
    }

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.PICKUP;
    }

    @Override
    public int getCollisionMask() {
        // Only walls and game objects should collide with pickups.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets whether the pickup is automatically picked up when walked over.
     * @return Whether the pickup is automatically picked up when walked over.
     */
    public boolean isAutoPickup() {
        switch (this.item.getType()) {
            case PISTOL_AMMO_15:
                return true;
            default:
                return false;
        }
    }
}
