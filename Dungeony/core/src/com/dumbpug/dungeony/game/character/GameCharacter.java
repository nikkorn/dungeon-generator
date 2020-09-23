package com.dumbpug.dungeony.game.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;

/**
 * Represents an in-game character.
 */
public abstract class GameCharacter extends Entity<SpriteBatch> {
    /**
     * Creates a new instance of the GameCharacter class.
     * @param origin The initial origin of the GameCharacter.
     */
    public GameCharacter(Position origin) {
        super(origin);
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.CHARACTER;
    }

    @Override
    public int getCollisionMask() {
        // Everything should collide with a character by default.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the character movements speed per second.
     * @return The character movements speed per second.
     */
    public abstract float getMovementSpeed();
}
