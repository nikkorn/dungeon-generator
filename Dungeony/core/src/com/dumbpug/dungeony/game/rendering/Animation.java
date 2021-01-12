package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * An animation.
 * @param <TState> The entity state associated with the animation.
 */
public class Animation<TState extends Enum> {
    /**
     * The underlying LIBGDX animation.
     */
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;
    /**
     * The animation frames.
     */
    private TextureRegion[] frames;
    /**
     * The state time.
     */
    private float stateTime = 0f;
    /**
     * The optional entity state associated with the animation.
     */
    private TState state = null;

    /**
     * Creates a new instance of the Animation class that is associated with some entity state.
     * @param state The entity state.
     * @param texture The animation texture.
     * @param columns The number of columns in the texture.
     * @param rows The number of rows in the texture.
     * @param step The time step for the animation.
     */
    public Animation(TState state, Texture texture, int columns, int rows, float step){
        TextureRegion[][] tempRegion = TextureRegion.split(texture, texture.getWidth()/columns, texture.getHeight()/rows);
        frames = new TextureRegion[rows*columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tempRegion[i][j];
            }
        }
        animation = new com.badlogic.gdx.graphics.g2d.Animation<>(step, frames);
    }

    /**
     * Creates a new instance of the Animation class that is associates.
     * @param texture The animation texture.
     * @param columns The number of columns in the texture.
     * @param rows The number of rows in the texture.
     * @param step The time step for the animation.
     */
    public Animation(Texture texture, int columns, int rows, float step){
        this(null, texture, columns, rows, step);
    }

    /**
     * Gets the state associated with the animation.
     * @return The state associated with the animation.
     */
    public TState getState() {
        return state;
    }

    /**
     * Gets the current animation frame.
     * @param loop Whether the animation is looping.
     * @return The current animation frame.
     */
    public TextureRegion getCurrentFrame(boolean loop){
        stateTime += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(stateTime, loop);
    }

    /**
     * Gets whether the animation is finished if not looping.
     * @return Whether the animation is finished if not looping.
     */
    public boolean isFinished(){
        return animation.isAnimationFinished(stateTime);
    }
}