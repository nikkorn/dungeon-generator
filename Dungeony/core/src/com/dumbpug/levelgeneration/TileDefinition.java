package com.dumbpug.levelgeneration;

import java.util.ArrayList;

/**
 * A definition of a tile.
 */
public class TileDefinition {
    /**
     * The tile id.
     */
    private String type;
    /**
     * The x/y position of the tile.
     */
    private int x,y;
    /**
     * The list of entities positioned at this tile.
     */
    private ArrayList<EntityDefinition> entities = new ArrayList<EntityDefinition>();

    /**
     * Creates a new instance of the TileDefinition class.
     * @param type The stringy type of the tile.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     */
    public TileDefinition(String type, int x, int y) {
        this.type = type;
        this.x    = x;
        this.y    = y;
    }

    /**
     * Gets the list of entities positioned at this tile.
     * @return The list of entities positioned at this tile.
     */
    public ArrayList<EntityDefinition> getEntities() {
        return entities;
    }

    /**
     * Gets the type of the tile.
     * @return The type of the tile.
     */
    public String getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
