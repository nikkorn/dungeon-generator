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
     * The tile sprite to use in rendering this wall tile.
     */
    private TileSprite tileSprite;

    /**
     * Creates a new instance of the Wall class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     * @param isEmptyAbove
     * @param isEmptyBelow
     * @param isEmptyLeft
     * @param isEmptyRight
     */
    public Wall(int x, int y, boolean isEmptyAbove, boolean isEmptyBelow, boolean isEmptyLeft, boolean isEmptyRight) {
        super(x, y);

        // Determine which tile sprite we will use for this wall tile based on the surrounding empty tiles.
        this.tileSprite = getWallSprite(isEmptyAbove, isEmptyBelow, isEmptyLeft, isEmptyRight);
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
        Sprite sprite = Resources.getSprite(this.tileSprite);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getWidth(), this.getHeight());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);

        // TODO If there is no wall above this tile then render a tile lip above.
    }

    /**
     * Gets the tile sprite for this wall tile based on the surrounding empty tiles.
     * @param isEmptyAbove
     * @param isEmptyBelow
     * @param isEmptyLeft
     * @param isEmptyRight
     * @return
     */
    private TileSprite getWallSprite(boolean isEmptyAbove, boolean isEmptyBelow, boolean isEmptyLeft, boolean isEmptyRight) {

        // TODO This may eventually just have to take flags saying whether there is an EMPTY tile in place, rather than a wall.

        if (isEmptyAbove && isEmptyBelow && isEmptyLeft && isEmptyRight) {
            return TileSprite.BUSH;
        }

        if (isEmptyAbove && isEmptyBelow) {
            return TileSprite.BUSH;
        }

        if (isEmptyLeft && isEmptyRight) {
            return TileSprite.BUSH;
        }

        if (isEmptyAbove) {
            return TileSprite.WALL_BOTTOM;
        }

        if (isEmptyBelow) {
            return TileSprite.WALL_TOP;
        }

        if (isEmptyLeft) {
            return TileSprite.WALL_RIGHT;
        }

        if (isEmptyRight) {
            return TileSprite.WALL_LEFT;
        }

        return TileSprite.WALL;
    }
}
