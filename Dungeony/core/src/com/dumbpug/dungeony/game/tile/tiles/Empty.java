package com.dumbpug.dungeony.game.tile.tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
import com.dumbpug.dungeony.game.tile.Tile;
import java.util.ArrayList;
import java.util.Random;

/**
 * An empty tile.
 */
public class Empty extends Tile {
    /**
     * The ground tile sprite type to render for this tile.
     */
    private TileSprite tileSprite;

    /**
     * Creates a new instance of the Empty class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public Empty(int x, int y) {
        super(x, y);

        // Pick a random ground tile sprite to render for this tile.
        tileSprite = pickTileSprite();
    }

    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // Empty tiles should be rendered early as they render the level ground.
        return 0;
    }

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.NOTHING;
    }

    @Override
    public int getCollisionMask() {
        return EntityCollisionFlag.NOTHING;
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the ground sprite for this tile.
        Sprite sprite = Resources.getSprite(this.tileSprite);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getLengthX(), this.getLengthY());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);
    }

    /**
     * Pick a random ground tile sprite to render for this tile.
     * @return A random ground tile sprite to render for this tile.
     */
    private TileSprite pickTileSprite() {
        // Create a list that holds all possible ground tile sprite types.
        // This will exclude the empty GROUND_0 as that is better suited for use with full tile types.
        ArrayList<TileSprite> allGroundSprites = new ArrayList<TileSprite>();
        allGroundSprites.add(TileSprite.GROUND_1);
        allGroundSprites.add(TileSprite.GROUND_2);
        allGroundSprites.add(TileSprite.GROUND_3);
        allGroundSprites.add(TileSprite.GROUND_4);
        allGroundSprites.add(TileSprite.GROUND_5);
        allGroundSprites.add(TileSprite.GROUND_6);
        allGroundSprites.add(TileSprite.GROUND_7);
        allGroundSprites.add(TileSprite.GROUND_8);
        allGroundSprites.add(TileSprite.GROUND_9);
        allGroundSprites.add(TileSprite.GROUND_10);
        allGroundSprites.add(TileSprite.GROUND_11);
        allGroundSprites.add(TileSprite.GROUND_12);
        allGroundSprites.add(TileSprite.GROUND_9);
        allGroundSprites.add(TileSprite.GROUND_10);
        allGroundSprites.add(TileSprite.GROUND_11);
        allGroundSprites.add(TileSprite.GROUND_12);

        // Pick and return a random tile sprite.
        return allGroundSprites.get(new Random().nextInt(allGroundSprites.size()));
    }
}
