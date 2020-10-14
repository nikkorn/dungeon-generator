package com.dumbpug.dungeony.game.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dumbpug.dungeony.game.level.LevelCamera;
import java.util.ArrayList;

/**
 * The collection of level lights.
 */
public class Lights {
    /**
     * The collection of lights.
     */
    private ArrayList<Light> lights = new ArrayList<Light>();
    /**
     * The sprite batch to use in rendering the lights.
     */
    private SpriteBatch lightSpriteBatch;
    /**
     * The frame buffer.
     */
    private FrameBuffer frameBuffer;

    /**
     * Creates a new instance of the Lights class.
     */
    public Lights() {
        lightSpriteBatch = new SpriteBatch();
        frameBuffer      = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    /**
     *
     * @param light
     */
    public void add(Light light) {
        this.lights.add(light);
    }

    /**
     *
     * @param light
     */
    public void remove(Light light) {
        this.lights.remove(light);
    }

    /**
     *
     * @param batch
     * @param camera
     */
    public void render(SpriteBatch batch, LevelCamera camera) {
        // There is nothing to do if we have no lights.
        if (this.lights.isEmpty()) {
            return;
        }


        // Cut it out application batch!
        batch.end();


        // TODO Check window size for change to update frame buffer?? Maybe?? Everything seems to work without doing this.


        // Draw to the light frame buffer.
        frameBuffer.begin();
        Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.getCombinedViewMatrix());
        batch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE);
        batch.begin();
        for (Light light : this.lights) {
            light.render(batch);
        }
        batch.end();
        frameBuffer.end();


        // Render the light frame buffer to the sprite batch.
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt());
        batch.setBlendFunction( GL20.GL_ZERO,GL20.GL_SRC_COLOR);
        batch.begin();
        batch.draw(frameBuffer.getColorBufferTexture(),-1,1,2,-2);
        batch.end();


        // Reset and resume the sprite batch.
        // TODO Should the just be set to what it was when this was called?
        batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.begin();
    }
}
