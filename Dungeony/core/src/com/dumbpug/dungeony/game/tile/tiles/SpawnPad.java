package com.dumbpug.dungeony.game.tile.tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileSpawn;
import java.util.ArrayList;

/**
 * A player spawn pad.
 */
public class SpawnPad extends Tile {
    /**
     * Creates a new instance of the SpawnPad class.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public SpawnPad(int x, int y) {
        super(x, y);
    }

    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // Spawn pads are drawn at ground level.
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
     * Gets the list of entity spawn positions for this tile.
     * @return The list of entity spawn positions for this tile.
     */
    @Override
    public ArrayList<TileSpawn> getTileSpawns() {
        ArrayList<TileSpawn> spawns = new ArrayList<TileSpawn>();

        // A spawn pad provides 4 spawn positions at each of its corners.
        spawns.add(new TileSpawn(new Position(this.getX(), this.getY())));
        spawns.add(new TileSpawn(new Position(this.getX() + (this.getLengthX() / 2f), this.getY())));
        spawns.add(new TileSpawn(new Position(this.getX(), this.getY() + (this.getLengthY() / 2f))));
        spawns.add(new TileSpawn(new Position(this.getX() + (this.getLengthX() / 2f), this.getY() + (this.getLengthY() / 2f))));

        return spawns;
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        Sprite sprite = Resources.getSprite(TileSprite.SPAWN_PAD);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getLengthX(), this.getLengthY());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(batch);
    }
}

