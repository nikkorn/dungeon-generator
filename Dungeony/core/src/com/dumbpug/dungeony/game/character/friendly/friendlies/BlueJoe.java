package com.dumbpug.dungeony.game.character.friendly.friendlies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.friendly.Friendly;
import com.dumbpug.dungeony.game.character.friendly.FriendlyType;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;

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
    public float getWidth() {
        // Fishman is the same width as the player.
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getHeight() {
        // Fishman is the same height as the player.
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getMovementSpeed() {
        // BlueJoe can move at the default speed of the player.
        return Constants.GAME_PLAYER_MOVEMENT;
    }

    @Override
    public FriendlyType getFriendlyType() {
        return FriendlyType.BLUEJOE;
    }

    @Override
    public void update(LevelCollisionGrid grid) {
        // Blue joe just does a bunch o' nothin'.
    }
}
