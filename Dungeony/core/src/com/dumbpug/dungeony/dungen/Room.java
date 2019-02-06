package com.dumbpug.dungeony.dungen;

import java.awt.Point;
import java.util.Random;

/**
 * Represents a generated room.
 */
public class Room {
	
	/** The position of the room. */
	private int x, y;
	
	/** The dimensions of the room. */
	private int width, height;
	
	/** The point at the approximate centre of the room. */
	private Point centre;
	
	/**
	 * Create a new instance of the Room class.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Room(int x, int y, int width, int height) {
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
		this.centre = new Point((int) (x + (width / 2)),(int) (y + (height / 2)));
	}
	
	/**
	 * Generate a random room based on a defined RNG and configuration.
	 * @param rng
	 * @param config
	 */
	public static Room generate(Random rng, Configuration config) {
		// Generate a random position for the room.
		int x = rng.nextInt(config.width);
		int y = rng.nextInt(config.height);
		// Generate some random dimensions for the room.
		int width  = rng.nextInt(config.maxRoomSize - config.minRoomSize) + config.minRoomSize;
		int height = rng.nextInt(config.maxRoomSize - config.minRoomSize) + config.minRoomSize;
		// Return our randomly generated room.
		return new Room(x, y, width, height);
	}
	
	/**
	 * Get the x position of this room.
	 * @return x
	 */
	public int getX() { return this.x; }
	
	/**
	 * Get the y position of this room.
	 * @return y
	 */
	public int getY() { return this.y; }
	
	/**
	 * Get the width of this room.
	 * @return width
	 */
	public int getWidth() { return this.width; }
	
	/**
	 * Get the height of this room.
	 * @return height
	 */
	public int getHeight() { return this.height; }
	
	/**
	 * Get the centre of the room.
	 * @return centre
	 */
	public Point getCentre() { return this.centre; }
}
