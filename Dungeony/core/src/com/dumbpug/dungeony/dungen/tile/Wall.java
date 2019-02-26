package com.dumbpug.dungeony.dungen.tile;

/**
 * Represents a wall tile.
 */
public class Wall extends Tile {

	/**
	 * Create a new instance of the Wall class.
	 * @param x The tile x position.
	 * @param y The tile y position.
	 */
	public Wall(int x, int y) {
		super(x, y, 0);
	}

	@Override
	public TileType getType() {
		return TileType.WALL;
	}
}
