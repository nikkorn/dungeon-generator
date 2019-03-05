package com.dumbpug.dungeony.dungen.room;

import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import com.dumbpug.dungeony.dungen.Direction;

/**
 * Represents a cell in a room.
 */
public class Cell {
	/**
	 * The position of the cell.
	 */
	private Position position;
	/**
	 * The cell entrance, or null if this cell is not an entrance cell.
	 */
	private Entrance entrance;
	/**
	 * The list of the directions in which anchors cannot be attached to the cell.
	 */
	private ArrayList<Direction> blockedDirections;
	/**
	 * The JSON array holding generatable entities for the cell.
	 */
	private JSONArray generatableEntities;
	
	/**
	 * Create a new instance of the Cell class.
	 * @param position The position of the cell.
	 * @param entrance The cell entrance, or null if this cell is not an entrance cell.
	 * @param blockedDirections The list of the directions in which anchors cannot be attached to the cell.
	 * @param entities The generatable entites
	 */
	public Cell(Position position, Entrance entrance, ArrayList<Direction> blockedDirections, JSONArray generatableEntities) {
		this.position            = position;
		this.entrance            = entrance;
		this.blockedDirections   = blockedDirections;
		this.generatableEntities = generatableEntities;
	}
	
	/**
	 * Get the cell position.
	 * @return The cell position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Get the cell entrance, or null if this cell is not an entrance cell.
	 * @return The cell entrance, or null if this cell is not an entrance cell.
	 */
	public Entrance getEntrance() {
		return entrance;
	}
	
	/**
	 * Generate entities for this cell.
	 * @param random The rng to use in generating entities.
	 * @return The generated entities for this cell.
	 */
	public ArrayList<Entity> generateEntities(Random random) {
		return GeneratableEntitiesProcessor.process(this.generatableEntities, random);		
	}
	
	/**
	 * Gets whether this cell is blocked in the specified direction.
	 * @param direction The direction to check.
	 * @return Whether this cell is blocked in the specified direction.
	 */
	public boolean isBlockedAt(Direction direction) {
		for (Direction blocked : this.blockedDirections) {
			if (direction == blocked) {
				// This cell is blocked in the specified direction.
				return true;
			}
		}
		// The direction is not blocked.
		return false;
	}
}
