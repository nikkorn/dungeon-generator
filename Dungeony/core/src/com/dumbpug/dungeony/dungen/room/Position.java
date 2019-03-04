package com.dumbpug.dungeony.dungen.room;

/**
 * Represents an x/y position.
 */
public class Position {
	/**
	 * The x position.
	 */
	private int x;
	/**
	 * The y position.
	 */
	private int y;
	
	/**
	 * Creates a new instance of the Position class.
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x position.
	 * @return The x position.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y position.
	 * @return The y position.
	 */
	public int getY() {
		return y;
	}
}
