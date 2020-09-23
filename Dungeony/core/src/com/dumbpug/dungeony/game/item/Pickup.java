package com.dumbpug.dungeony.game.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;

/**
 * An item pickup entity.
 */
public class Pickup extends Entity<SpriteBatch> {
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
        return Constants.PICKUP_SIZE;
    }

    @Override
    public float getLengthY() {
        return Constants.PICKUP_SIZE;
    }

    @Override
    public float getLengthZ() {
        return Constants.PICKUP_SIZE;
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.PICKUP;
    }

    @Override
    public int getCollisionMask() {
        // Only walls and game objects should collide with pickups.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.OBJECT;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void onDestroy() { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) { }

    @Override
    public void render(SpriteBatch spriteBatch) { }

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
