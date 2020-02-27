package com.dumbpug.dungeony.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.level.LevelGrid;
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
    public float getRenderOrder() {
        // Player sprites and animations should be rendered a little higher than their position for a 3D effect.
        return this.getY() + (this.getHeight() / 2f);
    }

    @Override
    public float getWidth() {
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getHeight() {
        return Constants.GAME_PLAYER_SIZE;
    }

    /**
     * Gets the movement speed of the entity.
     * @return The movement speed of the entity.
     */
    public float getMovementSpeed() {
        return Constants.GAME_PLAYER_MOVEMENT;
    }

    /**
     * Update the player.
     * @param grid The level grid used to handle player movement during an update.
     */
    public void update(LevelGrid grid) {
        // Get the input provider for the player.
        IPlayerInputProvider playerInputProvider = this.getDetails().getInputProvider();

        // TODO Check for player conditions (etc death, buffs) and update.
        // TODO Check for player actions.

        // Get the movement on each axis that the player is requesting to make.
        float movementAxisX = playerInputProvider.getMovementAxisX();
        float movementAxisY = playerInputProvider.getMovementAxisY();

        // Process player input which would influence the movement of the player.
        // Any entity movement has to be taken care of by the level grid which handles all entity collisions.
        grid.move(this, movementAxisX, movementAxisY);

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
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the player shadow sprite.
        Sprite shadowSprite = Resources.getSprite(PlayerSprite.SHADOW);
        shadowSprite.setSize(this.getWidth(), this.getHeight());
        shadowSprite.setPosition(this.getX(), this.getY() + (this.getHeight() / 2f));
        shadowSprite.setScale(1.2f,  1.2f);

        // Get the relevant animation for the player based on their current state.
        Animation animation = animations.get(this.state);

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame(true);

        // Draw the player shadow and then render the players current animation frame over it.
        shadowSprite.draw(batch);
        batch.draw(currentFrame, this.getX(), this.getY() + (this.getHeight() / 2f), 0, 0, this.getWidth(), this.getHeight(),1.2f, 1.2f, 0);
    }
}
