package com.dumbpug.dungeony.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.GameObjectSprite;
import com.dumbpug.dungeony.game.rendering.Resources;
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
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // TODO Our player shall not be a pot!
        Sprite sprite = Resources.getSprite(GameObjectSprite.POT);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getWidth(), this.getHeight());

        // Set the x/y of the sprite to match the player position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);
    }
}
