package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.game.tile.tiles.Empty;
import com.dumbpug.dungeony.game.tile.tiles.SpawnPad;
import com.dumbpug.dungeony.game.tile.tiles.Wall;

/**
 * Factory for creating Tile instances.
 */
public class TileFactory {
    /**
     * Create a Tile instance.
     * @param type The type of the tile.
     * @param x The tile-based x position of the tile.
     * @param y The tile-based y position of the tile.
     * @param tileFinder The tiles finder.
     * @return The tile instance.
     */
    public static Tile createTile(TileType type, int x, int y, ITileFinder tileFinder) {
        switch (type) {
            case EMPTY:
                return new Empty(x, y);
            case WALL:
                return new Wall(x, y, tileFinder);
            case SPAWN_PAD:
                return new SpawnPad(x, y);
            default:
                throw new RuntimeException("cannot create tile instance for tile type: " + type);
        }
    }
}
