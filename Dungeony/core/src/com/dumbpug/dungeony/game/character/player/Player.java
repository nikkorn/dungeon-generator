package com.dumbpug.dungeony.game.character.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.particles.Emitter;
import com.dumbpug.dungeony.engine.utilities.GameMath;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticleEmitterActivity;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticleGenerator;
import com.dumbpug.dungeony.game.projectile.projectiles.Bullet;
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
            this.animations.put(state, Resources.getCharacterAnimation(state, details.getType()));
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
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getLengthY() {
        return Constants.PLAYER_SIZE * 0.5f;
    }

    @Override
    public float getLengthZ() {
        return Constants.PLAYER_SIZE;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // Get the input provider for the player.
        IPlayerInputProvider playerInputProvider = this.getDetails().getInputProvider();

        // TODO Check for player conditions (etc death, buffs) and update.
        // TODO Check for player actions.

        // Get the movement on each axis that the player is requesting to make.
        float movementAxisX = playerInputProvider.getMovementAxisX() * this.getMovementSpeed();
        float movementAxisY = playerInputProvider.getMovementAxisY() * this.getMovementSpeed();

        // Process player input which would influence the movement of the player.
        // Any entity movement has to be taken care of by the level grid which handles all entity collisions.
        environment.move(this, movementAxisX, movementAxisY, delta);

        // Update the actual state of the player to reflect and changes that have happened during this update.
        // Is the player idle and not moving in any direction?
        if (movementAxisX == 0f && movementAxisY == 0f) {
            // The player should be idle and facing whatever direction they already have been.
            switch (this.getState()) {
                case RUNNING_LEFT:
                case DODGING_LEFT:
                    this.setState(GameCharacterState.IDLE_LEFT);
                    break;
                case RUNNING_RIGHT:
                case DODGING_RIGHT:
                    this.setState(GameCharacterState.IDLE_RIGHT);
                    break;
                default:
                    break;
            }
        } else {
            // We are running because we are moving on either axis, but hte X axis movement determines which way we face.
            this.setState(movementAxisX < 0 ? GameCharacterState.RUNNING_LEFT : GameCharacterState.RUNNING_RIGHT);
        }

        // TODO: Remove this projectile generation test.
        if (playerInputProvider.isControlJustPressed(Control.PRIMARY_ACTION)) {
            float angleOfAim = GameMath.getAngle(0, 0, playerInputProvider.getAimAxisX(), playerInputProvider.getAimAxisY());

            Bullet bullet = new Bullet(new Position(this.getX(), this.getY()), angleOfAim);

            // TODO Does this defeat the point of having the projectiles collection???????
            environment.addEntity(bullet);
        }
    }
}
