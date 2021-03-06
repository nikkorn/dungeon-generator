package com.dumbpug.dungeony.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.levelgeneration.IEntityProperties;
import java.util.HashMap;

/**
 * An object that can exist in the game.
 */
public abstract class GameObject extends Entity<SpriteBatch> {
    /**
     * The object state.
     */
    private GameObjectState state = GameObjectState.IDLE;
    /**
     * The game object state to animation map.
     */
    private HashMap<GameObjectState, Animation> animations = new HashMap<GameObjectState, Animation>();

    /**
     * Creates a new instance of the GameObject class.
     * @param origin The initial origin of the GameObject.
     * @param properties The entity properties.
     */
    public GameObject(Position origin, IEntityProperties properties) {
        super(origin);

        // Populate the game object animation map.
        for (GameObjectState state : GameObjectState.values()) {
            this.animations.put(state, Resources.getGameObjectAnimation(state, this.getType()));
        }
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.OBJECT;
    }

    @Override
    public int getCollisionMask() {
        // Everything should collide with an object by default.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.PROJECTILE | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the type of the game object.
     * @return The type of the game object.
     */
    public abstract GameObjectType getType();

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant animation for the game object based on their current state.
        Animation animation = animations.get(this.state);

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame();

        // Draw the current animation frame.
        batch.draw(currentFrame, this.getX(), this.getY(), this.getLengthX(), this.getLengthZ());
    }

    /**
     * Called after the absolute position for this entity has been applied.
     */
    public void onPositioned() {
        // An object does nothing by default when it is positioned.
    }
}
