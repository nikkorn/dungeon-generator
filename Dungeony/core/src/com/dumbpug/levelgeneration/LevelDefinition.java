package com.dumbpug.levelgeneration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Definition of a level.
 */
public class LevelDefinition {
    /**
     * The level name.
     */
    private String name;
    /**
     * The tiles definition.
     */
    private HashMap<String, TileDefinition> tileDefinitionMap = new HashMap<String, TileDefinition>();

    /**
     * Creates a new instance of the LevelDefinition class.
     * @param name The level name.
     * @param tileDefinitions The level tile definitions.
     */
    public LevelDefinition(String name, ArrayList<TileDefinition> tileDefinitions) {
        this.name = name;
        for (TileDefinition tile : tileDefinitions) {
            tileDefinitionMap.put(createKey(tile.getX(), tile.getY()), tile);
        }
    }

    /**
     * Get all tile definitions.
     * @return All tile definitions.
     */
    public Collection<TileDefinition> getTileDefinitions() {
        return this.tileDefinitionMap.values();
    }

    /**
     * Get the definition of the tile at x/y.
     * @param x
     * @param y
     * @return
     */
    public TileDefinition getTileDefinition(int x, int y) {
        if (this.tileDefinitionMap.containsKey(createKey(x, y))) {
            return this.tileDefinitionMap.get(createKey(x, y));
        }

        return null;
    }

    /**
     * Get the type of the tile at x/y.
     * @param x
     * @param y
     * @return
     */
    public String getTileType(int x, int y) {
        TileDefinition tile = getTileDefinition(x, y);

        return tile == null ? "UNKNOWN" : tile.getType();
    }

    /**
     * Create a unique key based on an x and y value.
     * @param x The x value.
     * @param y The y value.
     * @return A unique key based on an x and y value.
     */
    private String createKey(int x, int y) {
        return x + "_" + y;
    }
}
