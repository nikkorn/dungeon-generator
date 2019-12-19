package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.level.LevelGrid;
import com.dumbpug.dungeony.game.rendering.Renderables;
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
     * The spatial grid to use in finding game entity collisions.
     */
    private LevelGrid levelGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates an instance of the Tiles class.
     * @param tiles The list of tiles.
     * @param levelGrid The level grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public Tiles(ArrayList<Tile> tiles, LevelGrid levelGrid, Renderables renderables) {
        this.tiles       = tiles;
        this.levelGrid   = levelGrid;
        this.renderables = renderables;

        // Add the initial list of tiles to the level grid.
        this.levelGrid.add(tiles);

        // Add the initial list of tiles to the renderables list.
        this.renderables.add(tiles);
    }

    /**
     * Gets the list of available spawn positions.
     * @return The list of available spawn positions.
     */
    public ArrayList<Position> getSpawnPositions() {
        // TODO Work this out correctly eventually!!!
        return new ArrayList<Position>() {{
           add(new Position(0, 0));
        }};
    }
}
