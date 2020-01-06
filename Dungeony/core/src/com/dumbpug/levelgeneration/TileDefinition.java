package com.dumbpug.levelgeneration;

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
