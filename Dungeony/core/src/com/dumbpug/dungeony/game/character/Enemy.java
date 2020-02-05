package com.dumbpug.dungeony.game.character;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;

/**
 * Base class for all level enemies.
 */
public class Enemy extends GameCharacter {
    /**
     * Creates a new instance of the Enemy class.
     * @param origin The initial origin of the Enemy.
     */
    public Enemy(Position origin) {
        super(origin);
    }

    @Override
    public float getWidth() {
        // TODO Replace in derived classes.
        return Constants.GAME_TILE_SIZE / 2;
    }

    @Override
    public float getHeight() {
        // TODO Replace in derived classes.
        return Constants.GAME_TILE_SIZE / 2;
    }

    @Override
    public int getCollisionMask() {
        // TODO Replace in derived classes.
        return 0;
    }

    /**
     * Gets the movement speed of the entity.
     * @return The movement speed of the entity.
     */
    public float getMovementSpeed() {
        // TODO Eventually should depend on enemy type.
        return Constants.GAME_PLAYER_MOVEMENT;
    }
}
