package com.dumbpug.dungeony.dungen.tile;

/**
 * Represents a tile in the dungeon.
 */
public abstract class Tile {
	/**
	 * The tile position.
	 */
	private int x, y;
	/**
	 * The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	private int depth;
	
	/**
	 * Create a new instance of the Tile class.
	 * @param x The tile x position.
	 * @param y The tile y position.
	 * @param depth The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	public Tile(int x, int y, int depth) {
		this.x     = x;
		this.y     = y;
		this.depth = depth;
	}
	
	/**
	 * Get the x position of this tile.
	 * @return The tile x position.
	 */
	public int getX() { return this.x; }
	
	/**
	 * Get the y position of this tile.
	 * @return The tile y position.
	 */
	public int getY() { return this.y; }
	
	/**
	 * Get the depth of the tile as the number of rooms that were passed through to reach it.
	 * @return The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	public int getDepth() { return this.depth; }
	
	/**
	 * Get the tile type.
	 * @return The tile type.
	 */
	public abstract TileType getType();
}
