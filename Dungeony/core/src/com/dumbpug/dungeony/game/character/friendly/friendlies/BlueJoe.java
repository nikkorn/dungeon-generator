package com.dumbpug.dungeony.game.character.friendly.friendlies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.friendly.Friendly;
import com.dumbpug.dungeony.game.character.friendly.FriendlyType;
import com.dumbpug.dungeony.game.lights.SpotLight;

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
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getLengthY() {
        // BlueJoe is the same depth as the player.
        return Constants.PLAYER_SIZE * 0.5f;
    }

    @Override
    public float getLengthZ() {
        // BlueJoe is the same height as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        environment.addLight(new SpotLight(this, 1f, 0.3f, 0.3f));
    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public float getMovementSpeed() {
        // BlueJoe can move at the default speed of the player.
        return Constants.PLAYER_MOVEMENT_PS;
    }

    @Override
    public void onDamageTaken(InteractiveEnvironment environment, float delta, int points) { }

    @Override
    public void onHealthDepleted(InteractiveEnvironment environment, float delta) { }

    @Override
    public FriendlyType getFriendlyType() {
        return FriendlyType.BLUEJOE;
    }
}
