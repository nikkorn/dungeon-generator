package com.dumbpug.dungeony.dungen.tile;

import com.dumbpug.dungeony.dungen.Direction;

/**
 * Represents a door tile.
 */
public class Door extends Tile {
	/**
	 * The type of the door.
	 */
	private String doorType;
	/**
	 * The facing direction of the door.
	 */
	private Direction doorFacingDirection;

	/**
	 * Create a new instance of the Door class.
	 * @param x The tile x position.
	 * @param y The tile y position.
	 * @param depth The depth of the tile as the number of rooms that were passed through to reach it.
	 * @param door The door type.
	 * @param direction The door facing direction.
	 */
	public Door(int x, int y, int depth, String door, Direction direction) {
		super(x, y, depth);
		this.doorType            = door;
		this.doorFacingDirection = direction;
	}
	
	/**
	 * Get the type of the door.
	 * @return The type of the door.
	 */
	public String getDoorType() {
		return doorType;
	}

	/**
	 * Get the facing direction of the door.
	 * @return The facing direction of the door.
	 */
	public Direction getDoorFacingDirection() {
		return doorFacingDirection;
	}

	@Override
	public TileType getType() {
		return TileType.DOOR;
	}
}
