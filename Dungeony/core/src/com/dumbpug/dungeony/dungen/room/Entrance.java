package com.dumbpug.dungeony.dungen.room;

import com.dumbpug.dungeony.dungen.Direction;

/**
 * Represents an entrance to a cell.
 */
public class Entrance {
	/**
	 * The direction defining the side of a cell at which the entrance is placed.
	 */
	private Direction direction;
	/**
	 * The door type.
	 */
	private String door;
	
	/**
	 * Create a new instance of the Entrance class.
	 * @param direction The direction defining the side of a cell at which the entrance is placed.
	 * @param door The door type.
	 */
	public Entrance(Direction direction, String door) {
		this.direction = direction;
		this.door      = door;
	}

	/**
	 * Get the direction defining the side of a cell at which the entrance is placed.
	 * @return The direction defining the side of a cell at which the entrance is placed.
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Get the door type.
	 * @return The door type.
	 */
	public String getDoor() {
		return door;
	}
}
