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
     * The four sprites we will use to render this tile
     */
    private TileSprite topLeftSprite, topRightSprite, tLeftSprite, bottomRightSprite;
    /**
     * The optional lip sprites to render above this wall.
     */
    private TileSprite leftLipSprite, rightLipSprite;

    /**
     * Creates a new instance of the Wall class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     * @param tileFinder The tile finder.
     */
    public Wall(int x, int y, ITileFinder tileFinder) {
        super(x, y);

        getWallSprites(tileFinder);
        getLipSprites(tileFinder);
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
        // Find half our tile size, each tiel sprite will use that as its dimensions.
        float spriteSize = this.getWidth() / 2f;

        // Render top-left tile.
        Sprite sprite = Resources.getSprite(this.topLeftSprite);
        sprite.setSize(spriteSize, spriteSize);
        sprite.setPosition(this.getX(), this.getY() + spriteSize);
        sprite.draw(batch);

        // Render top-right tile.
        sprite = Resources.getSprite(this.topLeftSprite);
        sprite.setSize(spriteSize, spriteSize);
        sprite.setPosition(this.getX() + spriteSize, this.getY() + spriteSize);
        sprite.draw(batch);

        // Render bottom-left tile.
        sprite = Resources.getSprite(this.topLeftSprite);
        sprite.setSize(spriteSize, spriteSize);
        sprite.setPosition(this.getX(), this.getY());
        sprite.draw(batch);

        // Render bottom-right tile.
        sprite = Resources.getSprite(this.topLeftSprite);
        sprite.setSize(spriteSize, spriteSize);
        sprite.setPosition(this.getX() + spriteSize, this.getY());
        sprite.draw(batch);

        // We may have to render an upper tile lip.
        if (this.topLeftSprite != null && this.topRightSprite != null) {
            // TODO Draw em!
        }
    }

    private void getWallSprites(ITileFinder tileFinder) {
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

        if (isEmptyLeft && isEmptyRight) {
            return TileSprite.WALL_VERTICAL;
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

    private void getLipSprites(ITileFinder tileFinder) {
        // Find whether the tiles around the wall tile we are about to make are empty tiles.
        boolean isEmptyAbove = tileFinder.find(this, TileOffset.ABOVE) == TileType.EMPTY;
        boolean isEmptyLeft  = tileFinder.find(this, TileOffset.LEFT) == TileType.EMPTY;
        boolean isEmptyRight = tileFinder.find(this, TileOffset.RIGHT) == TileType.EMPTY;

        // We are not rendering a lip if there is not an empty tile above.
        if (!isEmptyAbove) {
            return null;
        }

        if (isEmptyLeft && isEmptyRight) {
            return TileSprite.WALL_LIP_LEFT_RIGHT;
        }

        if (isEmptyLeft) {
            return TileSprite.WALL_LIP_LEFT;
        }

        if (isEmptyRight) {
            return TileSprite.WALL_LIP_RIGHT;
        }

        return TileSprite.WALL_LIP;
    }
}
