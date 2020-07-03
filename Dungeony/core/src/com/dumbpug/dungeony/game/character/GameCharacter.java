package com.dumbpug.dungeony.game.character;

import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;

/**
 * Represents an in-game character.
 */
public abstract class GameCharacter extends Entity {
    /**
     * Creates a new instance of the GameCharacter class.
     * @param origin The initial origin of the GameCharacter.
     */
    public GameCharacter(Position origin) {
        super(origin);
    }

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.CHARACTER;
    }

    @Override
    public int getCollisionMask() {
        // Everything should collide with a character by default.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the character movements speed.
     * @return The character movements speed.
     */
    public abstract float getMovementSpeed();
}
