package com.dumbpug.dungeony.game.character.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.particles.Emitter;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticle;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticleEmitterActivity;
import com.dumbpug.dungeony.game.character.particles.walking.WalkingDustParticleGenerator;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.PlayerSprite;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.input.IPlayerInputProvider;
import java.util.HashMap;

/**
 * Represents an in-game player.
 */
public class Player extends GameCharacter {
    /**
     * The player details.
     */
    private PlayerDetails details;
    /**
     * The player state.
     */
    private PlayerState state = PlayerState.IDLE_LEFT;
    /**
     * The player state to animation map.
     */
    private HashMap<PlayerState, Animation> animations = new HashMap<PlayerState, Animation>();
    /**
     * The walking dust particle emitter for the player.
     */
    private Emitter<SpriteBatch> walkingDustParticleEmitter;

    /**
     * Creates a new instance of the Player class.
     *  @param details The player details.
     * @param origin The initial origin of the Player.
     */
    public Player(PlayerDetails details, Position origin) {
        super(origin);
        this.details = details;

        // Populate the player animation map.
        for (PlayerState state : PlayerState.values()) {
            this.animations.put(state, Resources.getPlayerAnimation(state));
        }
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
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        // Create the emitter which will generate the dust particles shown when the player is walking.
        this.walkingDustParticleEmitter = new Emitter(new Position(), new WalkingDustParticleEmitterActivity(), new WalkingDustParticleGenerator());

        // Add the emitter to the environment.
        environment.addEntity(this.walkingDustParticleEmitter);
    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void onDestroy() { }

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
            switch (this.state) {
                case RUNNING_LEFT:
                case DODGING_LEFT:
                    this.state = PlayerState.IDLE_LEFT;
                    return;
                case RUNNING_RIGHT:
                case DODGING_RIGHT:
                    this.state = PlayerState.IDLE_RIGHT;
                    return;
                default:
                    return;
            }
        } else {
            // We are running because we are moving on either axis, but hte X axis movement determines which way we face.
            this.state = movementAxisX < 0 ? PlayerState.RUNNING_LEFT : PlayerState.RUNNING_RIGHT;
        }

        this.walkingDustParticleEmitter.setX(this.getX());
        this.walkingDustParticleEmitter.setY(this.getY());
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the player shadow sprite.
        Sprite shadowSprite = Resources.getSprite(PlayerSprite.SHADOW);
        shadowSprite.setSize(this.getLengthX(), this.getLengthZ());
        shadowSprite.setPosition(this.getX(), this.getY());
        shadowSprite.setScale(1.2f,  1.2f);

        // Get the relevant animation for the player based on their current state.
        Animation animation = animations.get(this.state);

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame(true);

        // Draw the player shadow and then render the players current animation frame over it.
        shadowSprite.draw(batch);
        batch.draw(currentFrame, this.getX(), this.getY(), 0, 0, this.getLengthX(), this.getLengthZ(), 1.2f, 1.2f, 0);
    }
}
