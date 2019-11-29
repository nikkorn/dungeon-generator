package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.game.Position;

/**
 * Factory for creating Tile instances.
 */
public class TileFactory {
    /**
     * Create a Tile instance.
     * @param type
     * @param origin
     * @param x
     * @param y
     * @return
     */
    public static Tile createTile(TileType type, Position origin, int x, int y) {
        return new Tile(type, origin, x, y);
    }
}
