package com.dumbpug.dungeony.game.lights;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.lighting.Light;

public class SpotLight extends Light<SpriteBatch> {
    private float r,g,b;
    private Sprite sprite = new Sprite(new Texture("images/light/SPOT.png"));

    public SpotLight(float x, float y, float r, float g, float b) {
        super(x, y);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public SpotLight(Entity target, float r, float g, float b) {
        super(target);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void render(SpriteBatch spriteBatch, float x, float y) {
        this.sprite.setSize(60f, 60f);
        this.sprite.setPosition(x - 30f, y - 30f);
        this.sprite.setColor(this.r, this.g, this.b, 1f);
        this.sprite.draw(spriteBatch);
    }
}
