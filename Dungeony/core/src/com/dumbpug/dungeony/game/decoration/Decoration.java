package com.dumbpug.dungeony.game.decoration;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;

/**
 * A non-interactable and non-collidable decoration in the level.
 */
public abstract class Decoration extends Entity<SpriteBatch> {
    /**
     * Creates a new instance of the Decoration class.
     * @param origin The initial origin of the entity.
     */
    public Decoration(Position origin) {
        super(origin);
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.NOTHING;
    }

    @Override
    public int getCollisionMask() {
        return EntityCollisionFlag.NOTHING;
    }
}
