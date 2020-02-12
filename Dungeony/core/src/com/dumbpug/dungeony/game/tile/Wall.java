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
     * @param tileFinder The tile finder.
     */
    public Wall(int x, int y, ITileFinder tileFinder) {
        super(x, y);

        // Determine which tile sprite we will use for this wall tile based on the surrounding tiles.
        this.tileSprite = getWallSprite(tileFinder);
    }

    /**
     * Gets the renderable index to use in sorting.
     * @return The renderable index to use in sorting.
     */
    @Override
    public float getRenderOrder() {
        // As a wall tile is higher than most entities we will draw it a little higher to seem layered.
        return this.getY() + (this.getHeight() / 2f);
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

        // Set the x/y of the sprite to match the tile position, pushing it up to seem layered.
        sprite.setPosition(this.getX(), this.getY() + (this.getHeight() / 2f));

        // Draw the sprite.
        sprite.draw(batch);
    }

    /**
     * Gets the tile sprite for this wall tile based on the surrounding tiles.
     * @param tileFinder The tile finder.
     * @return The tile sprite for this wall tile based on the surrounding tiles.
     */
    private TileSprite getWallSprite(ITileFinder tileFinder) {
        // Find whether the tiles around the wall tile we are about to make are empty tiles.
        boolean isEmptyBelow = tileFinder.find(this, TileOffset.BELOW) == TileType.EMPTY;
        boolean isEmptyAbove = tileFinder.find(this, TileOffset.ABOVE) == TileType.EMPTY;
        boolean isEmptyLeft  = tileFinder.find(this, TileOffset.LEFT) == TileType.EMPTY;
        boolean isEmptyRight = tileFinder.find(this, TileOffset.RIGHT) == TileType.EMPTY;
        boolean isEmptyBelowLeft = tileFinder.find(this, TileOffset.BELOW_LEFT) == TileType.EMPTY;
        boolean isEmptyBelowRight = tileFinder.find(this, TileOffset.BELOW_RIGHT) == TileType.EMPTY;
        boolean isEmptyAboveLeft = tileFinder.find(this, TileOffset.ABOVE_LEFT) == TileType.EMPTY;
        boolean isEmptyAboveRight = tileFinder.find(this, TileOffset.ABOVE_RIGHT) == TileType.EMPTY;

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
