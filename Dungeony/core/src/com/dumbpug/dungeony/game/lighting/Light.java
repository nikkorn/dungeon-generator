package com.dumbpug.dungeony.game.lighting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.Entity;

public class Light {
    private LightType type;
    private Entity target;
    private float x, y;
    private Sprite sprite = new Sprite(new Texture("images/light/SPOT.png"));

    public Light(LightType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Light(LightType type, Entity target) {
        this.type = type;
        this.target = target;
    }

    /**
     *
     * @param batch
     */
    public void render(SpriteBatch batch) {
        this.sprite.setSize(60f, 60f);
        this.sprite.setPosition(this.target.getOrigin().getX() - 30f, this.target.getOrigin().getY() - 30f);
        this.sprite.setColor(1f, 0.3f, 0.3f, 1f);
        this.sprite.draw(batch);
    }
}
