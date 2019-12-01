package com.dumbpug.dungeony.game.character;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;

/**
 * Represents an in-game player.
 */
public class Player extends GameCharacter {
    /**
     * The player identifier.
     */
    private PlayerIdentifier id;

    /**
     * Creates a new instance of the Player class.
     *  @param id The player id.
     * @param origin The initial origin of the Player.
     */
    public Player(PlayerIdentifier id, Position origin) {
        super(origin);
        this.id = id;
    }

    /**
     * Gets the player id.
     * @return The player id.
     */
    public PlayerIdentifier getId() {
        return id;
    }

    @Override
    public float getWidth() {
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getHeight() {
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public int getCollisionMask() {
        return 0;
    }
}
