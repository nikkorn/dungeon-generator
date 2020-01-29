package com.dumbpug.dungeony.game.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;

/**
 * A wall tile.
 */
public class Wall extends Tile {
    /**
     * Whether the tile above this one is also a wall.
     */
    private boolean hasWallAbove;
    /**
     * Whether the tile below this one is also a wall.
     */
    private boolean hasWallBelow;

    /**
     * Creates a new instance of the Wall class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     * @param hasWallAbove
     * @param hasWallBelow
     */
    public Wall(int x, int y, boolean hasWallAbove, boolean hasWallBelow) {
        super(x, y);
        this.hasWallAbove = hasWallAbove;
        this.hasWallBelow = hasWallBelow;
    }

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.WALL;
    }

    @Override
    public int getCollisionMask() {
        // Everything bumps into a wall!
        return EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.PROJECTILE | EntityCollisionFlag.OBJECT;
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant sprite for this tile.
        Sprite sprite = Resources.getSprite(this.hasWallBelow ? TileSprite.WALL_BLOCKED : TileSprite.WALL);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getWidth(), this.getHeight());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);

        // TODO If there is no wall above this tile then render a tile lip above.
    }
}
