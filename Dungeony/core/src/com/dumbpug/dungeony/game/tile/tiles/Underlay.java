package com.dumbpug.dungeony.game.tile.tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileType;

/**
 * A tile that underlays another tile.
 * This is used to render the ground below tiles with partly transparent sprites.
 */
public class Underlay extends Tile {
    /**
     * The underlay tile sprite type to render for this tile.
     */
    private static Sprite sprite;

    static {
        sprite = Resources.getSprite(TileSprite.GROUND_0);
    }

    /**
     * Creates a new instance of the Underlay class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Underlay(int x, int y) {
        super(x, y);
    }

    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // Underlay tiles should be rendered early as they render below higher tiles.
        return -1;
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.NOTHING;
    }

    @Override
    public int getCollisionMask() {
        return EntityCollisionFlag.NOTHING;
    }

    @Override
    public void update(InteractiveEnvironment environment, float delta) { }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getLengthX(), this.getLengthY());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);
    }

    @Override
    public TileType getTileType() {
        return TileType.UNDERLAY;
    }
}
