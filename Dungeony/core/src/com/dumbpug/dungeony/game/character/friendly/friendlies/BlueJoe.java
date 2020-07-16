package com.dumbpug.dungeony.game.character.friendly.friendlies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.friendly.Friendly;
import com.dumbpug.dungeony.game.character.friendly.FriendlyType;

/**
 * Just ol' Blue Joe.
 */
public class BlueJoe extends Friendly {
    /**
     * Creates a new instance of the GameCharacter class.
     * @param origin The initial origin of the GameCharacter.
     */
    public BlueJoe(Position origin) {
        super(origin);
    }

    @Override
    public float getLengthX() {
        // BlueJoe is the same width as the player.
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getLengthY() {
        // BlueJoe is the same depth as the player.
        return Constants.GAME_PLAYER_SIZE * 0.5f;
    }

    @Override
    public float getLengthZ() {
        // BlueJoe is the same height as the player.
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getMovementSpeed() {
        // BlueJoe can move at the default speed of the player.
        return Constants.GAME_PLAYER_MOVEMENT_PS;
    }

    @Override
    public FriendlyType getFriendlyType() {
        return FriendlyType.BLUEJOE;
    }
}
