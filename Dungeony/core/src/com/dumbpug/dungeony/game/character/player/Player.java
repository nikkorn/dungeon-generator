package com.dumbpug.dungeony.game.character.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.particles.Emitter;
import com.dumbpug.dungeony.engine.utilities.GameMath;
import com.dumbpug.dungeony.game.character.FacingDirection;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticleEmitterActivity;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticleGenerator;
import com.dumbpug.dungeony.game.lights.SpotLight;
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
            // We are running because we are moving on either axis, but the X axis movement determines which way we face.
            if (movementAxisX < 0) {
                this.setState(GameCharacterState.RUNNING_LEFT);
                this.setFacingDirection(FacingDirection.LEFT);
            } else if (movementAxisX > 0) {
                this.setState(GameCharacterState.RUNNING_RIGHT);
                this.setFacingDirection(FacingDirection.RIGHT);
            }
        }

        if (this.getWeapon() != null) {
            // Get the player angle of aim.
            float angleOfAim = getPlayerAngleOfAim(playerInputProvider);

            // Update the position and angle of the equipped weapon.
            this.getWeapon().setPosition(this.getOrigin());
            this.getWeapon().setAngleOfAim(angleOfAim);

            // TODO: Remove this projectile generation test.
            if (playerInputProvider.isControlJustPressed(Control.PRIMARY_ACTION)) {
                Bullet bullet = new Bullet(new Position(this.getWeapon().getPosition()), angleOfAim);

                // TODO Does this defeat the point of having the projectiles collection???????
                environment.addEntity(bullet);

                // TODO: Replace this with a 'weapon.use(InteractiveEnvironment environment, float delta)'
            }
        }
    }

    /**
     * Gets the angle of aim for the character.
     * @param playerInputProvider The player input provider.
     * @return The angle of aim for the character.
     */
    private float getPlayerAngleOfAim(IPlayerInputProvider playerInputProvider) {
        float aimInputOffsetX = playerInputProvider.getAimAxisX();
        float aimInputOffsetY = playerInputProvider.getAimAxisY();

        // Check whether we are even moving, if not then the player aim will just follow their facing direction.
        if (aimInputOffsetX == 0 && aimInputOffsetY == 0) {
            return this.getFacingDirection() == FacingDirection.LEFT ? 270 : 90;
        }

        return GameMath.getAngle(0, 0, aimInputOffsetX, aimInputOffsetY);
    }
}
