package com.dumbpug.dungeony.game.character.player;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.dialog.Dialog;
import com.dumbpug.dungeony.engine.utilities.GameMath;
import com.dumbpug.dungeony.game.character.FacingDirection;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.lights.SpotLight;
import com.dumbpug.dungeony.game.rendering.GameCharacterSprite;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.input.Control;
import com.dumbpug.dungeony.input.IPlayerInputProvider;

/**
 * Represents an in-game player.
 */
public class Player extends GameCharacter {
    /**
     * The player details.
     */
    private PlayerDetails details;

    /**
     * Creates a new instance of the Player class.
     *  @param details The player details.
     * @param origin The initial origin of the Player.
     */
    public Player(PlayerDetails details, Position origin) {
        super(origin);
        this.details = details;

        // Populate the player animation map.
        for (GameCharacterState state : GameCharacterState.values()) {
            this.setAnimation(state, FacingDirection.LEFT, Resources.getCharacterAnimation(state, details.getType(), FacingDirection.LEFT));
            this.setAnimation(state, FacingDirection.RIGHT, Resources.getCharacterAnimation(state, details.getType(), FacingDirection.RIGHT));
        }

        // Set the player shadow sprite.
        this.shadowSprite = Resources.getCharacterSprite(GameCharacterSprite.SHADOW, details.getType());
    }

    /**
     * Gets the player details.
     * @return The player details.
     */
    public PlayerDetails getDetails() {
        return this.details;
    }

    @Override
    public float getMovementSpeed() {
        return Constants.PLAYER_MOVEMENT_PS;
    }

    @Override
    public float getLengthX() {
        return 18f;
    }

    @Override
    public float getLengthY() {
        return 12f;
    }

    @Override
    public float getLengthZ() {
        return 24f;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        environment.addLight(new SpotLight(this, 1f, 0.3f, 0.3f));
    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void onDamageTaken(InteractiveEnvironment environment, float delta, int points) { }

    @Override
    public void onHealthDepleted(InteractiveEnvironment environment, float delta) { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // Get the input provider for the player.
        IPlayerInputProvider playerInputProvider = this.getDetails().getInputProvider();

        // TODO: Check environment for any dialogs that this player entity is the interacting entity of.
        // Based on the dialog type, some input may be used
        Dialog interactingDialog = environment.getActiveDialog(this);

        // Set the player angle of view, or null if not aiming.
        float aimInputOffsetX = playerInputProvider.getAimAxisX();
        float aimInputOffsetY = playerInputProvider.getAimAxisY();
        this.setAngleOfView((aimInputOffsetX == 0 && aimInputOffsetY == 0) ? null : GameMath.getAngle(0, 0, aimInputOffsetX, aimInputOffsetY));

        // TODO Check for player conditions (etc death, buffs) and update.
        // TODO Check for player actions.

        // Get the movement on each axis that the player is requesting to make.
        float movementAxisX = playerInputProvider.getMovementAxisX() * this.getMovementSpeed();
        float movementAxisY = playerInputProvider.getMovementAxisY() * this.getMovementSpeed();

        this.move(environment, movementAxisX, movementAxisY, delta);

        // Are we using our equipped weapon?
        if (this.getWeapon() != null && playerInputProvider.isControlPressed(Control.PRIMARY_ACTION)) {
            this.getWeapon().use(environment, playerInputProvider.isControlJustPressed(Control.PRIMARY_ACTION), delta);
        }
    }
}
