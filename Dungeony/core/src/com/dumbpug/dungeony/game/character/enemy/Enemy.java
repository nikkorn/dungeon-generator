package com.dumbpug.dungeony.game.character.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.level.LevelGrid;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.Resources;
import java.util.HashMap;

/**
 * Base class for all level enemies.
 */
public abstract class Enemy extends GameCharacter {
    /**
     * The enemy state.
     */
    protected EnemyState state = EnemyState.IDLE_LEFT;
    /**
     * The enemy state to animation map.
     */
    private HashMap<EnemyState, Animation> animations = new HashMap<EnemyState, Animation>();

    /**
     * Creates a new instance of the Enemy class.
     * @param origin The initial origin of the Enemy.
     */
    public Enemy(Position origin) {
        super(origin);

        // Populate the enemy animation map.
        for (EnemyState state : EnemyState.values()) {
            this.animations.put(state, Resources.getEnemyAnimation(state, this.getEnemyType()));
        }
    }

    @Override
    public float getWidth() {
        // TODO Replace in derived classes.
        return Constants.GAME_TILE_SIZE / 2;
    }

    @Override
    public float getHeight() {
        // TODO Replace in derived classes.
        return Constants.GAME_TILE_SIZE / 2;
    }

    /**
     * Gets the movement speed of the entity.
     * @return The movement speed of the entity.
     */
    public float getMovementSpeed() {
        // TODO Eventually should depend on enemy type.
        return Constants.GAME_PLAYER_MOVEMENT;
    }

    @Override
    public float getRenderOrder() {
        // Enemy sprites and animations should be rendered a little higher than their position for a 3D effect.
        return this.getY() + (this.getHeight() / 2f);
    }

    /**
     * Gets the enemy type.
     * @return The enemy type.
     */
    public abstract EnemyType getEnemyType();

    /**
     * Update the enemy as part of a single level update.
     * @param grid The level grid used to handle player movement during an update.
     */
    public abstract void update(LevelGrid grid);

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant animation for the enemy based on their current state.
        Animation animation = animations.get(this.state);

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame(true);

        // Render the enemies current animation frame.
        batch.draw(currentFrame, this.getX(), this.getY() + (this.getHeight() / 2f), 0, 0, this.getWidth(), this.getHeight(),1.2f, 1.2f, 0);
    }
}
