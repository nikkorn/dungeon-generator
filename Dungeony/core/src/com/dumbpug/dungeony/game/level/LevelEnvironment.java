package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dumbpug.dungeony.engine.Environment;
import com.dumbpug.dungeony.engine.EnvironmentConfiguration;

public class LevelEnvironment extends Environment<SpriteBatch> {
    /**
     * The frame buffer to use in rendering the environment lights.
     */
    private FrameBuffer frameBuffer;
    /**
     * The level camera.
     */
    private LevelCamera levelCamera;

    /**
     * Creates a new instance of the LevelEnvironment class.
     * @param configuration The environment configuration.
     * @param camera        The environment camera.
     */
    public LevelEnvironment(EnvironmentConfiguration configuration, LevelCamera camera) {
        super(configuration, camera);
        this.levelCamera = camera;
        frameBuffer      = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override
    public void onBeforeEntitiesRender(SpriteBatch spriteBatch) { }

    @Override
    public void onAfterEntitiesRender(SpriteBatch spriteBatch) { }

    @Override
    public void onBeforeLightsRender(SpriteBatch spriteBatch) {
        // Cut it out application batch!
        spriteBatch.end();

        // TODO Check window size for change to update frame buffer?? Maybe?? Everything seems to work without doing this.

        // Draw to the light frame buffer.
        frameBuffer.begin();
        Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(this.levelCamera.getCombinedViewMatrix());
        spriteBatch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE);
        spriteBatch.begin();

        // Any sprites that are rendered now will be rendered to our light frame buffer.
    }

    @Override
    public void onAfterLightsRender(SpriteBatch spriteBatch) {
        spriteBatch.end();
        frameBuffer.end();

        // Any sprites that are rendered now will no longer be rendered to our light frame buffer.

        // Render the light frame buffer to the sprite batch.
        spriteBatch.setProjectionMatrix(spriteBatch.getProjectionMatrix().idt());
        spriteBatch.setBlendFunction( GL20.GL_ZERO,GL20.GL_SRC_COLOR);
        spriteBatch.begin();
        spriteBatch.draw(frameBuffer.getColorBufferTexture(),-1,1,2,-2);
        spriteBatch.end();

        // Reset and resume the sprite batch.
        // TODO Should the just be set to what it was when this was called?
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();
    }

    @Override
    public void onBeforeDialogsRender(SpriteBatch spriteBatch) { }

    @Override
    public void onAfterDialogsRender(SpriteBatch spriteBatch) { }
}
