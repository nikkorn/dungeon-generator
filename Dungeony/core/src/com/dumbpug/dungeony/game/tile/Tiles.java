package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.engine.Environment;
import java.util.ArrayList;

/**
 * The list of in-level tiles.
 */
public class Tiles {
    /**
     * The underlying list of tiles.
     */
    private ArrayList<Tile> tiles;
    /**
     * The game environment.
     */
    private Environment environment;

    /**
     * Creates an instance of the Tiles class.
     * @param tiles The list of tiles.
     * @param environment The game environment.
     */
    public Tiles(ArrayList<Tile> tiles, Environment environment) {
        this.tiles       = tiles;
        this.environment = environment;

        // Add all of the tile entities to the game environment.
        this.environment.addEntities(tiles, "tile");
    }

    /**
     * Get the collection of all tiles.
     * @return The collection of all tiles.
     */
    public ArrayList<Tile> getAll() {
        return this.tiles;
    }

    /**
     * Gets the list of all available tile spawns.
     * @return The list of all available tile spawns.
     */
    public ArrayList<TileSpawn> getSpawns() {
        ArrayList<TileSpawn> spawns = new ArrayList<TileSpawn>();

        for (Tile tile : this.tiles) {
            if (tile.getTileSpawns() != null) {
                spawns.addAll(tile.getTileSpawns());
            }
        }

        return spawns;
    }
}
